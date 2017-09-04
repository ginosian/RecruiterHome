package com.recruiting.controller;

import com.recruiting.domain.*;
import com.recruiting.model.modelUtils.PageWrapper;
import com.recruiting.service.CommunicationService;
import com.recruiting.service.entity.CompanyService;
import com.recruiting.service.entity.CandidateService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Martha on 7/4/2017.
 */
@Controller
@RequestMapping(value = "/admin")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
public class AdminController extends AbstractController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    CommunicationService communicationService;

    @Autowired
    ExtendedUserDetailService extendedUserDetailService;

    @RequestMapping(value = "")
    public String home(Model model, Pageable pageable) {
        Page<User> allUsers = extendedUserDetailService.FindAllUsersExceptAdmin(pageable);
        PageWrapper<User> pageWrapper = new PageWrapper<>(allUsers, "");
        model.addAttribute("pageWrapper", pageWrapper);
        return "admin-home";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String navigateToUserAccount(@PathVariable(value = "id") Long id) {
        User user = extendedUserDetailService.findById(id);
        String authority = user.getAuthorities().get(0).getAuthority();
        switch (authority) {
            case "ROLE_COMPANY":
                return "redirect:/admin/company-account/" + id;
            case "ROLE_CANDIDATE":
                return "redirect:/admin/candidate-account/" + id;
            default:
                return "redirect:/admin";
        }
    }

    @RequestMapping(value = "/pending-approval/companies", method = RequestMethod.GET)
    public String nonApprovedCompanies(Model model, Pageable pageable) {
        Page<Company> nonApprovedCompanies = companyService.findByApprovedFalse(pageable);
        PageWrapper<Company> pageWrapper = new PageWrapper<>(nonApprovedCompanies, "");
        model.addAttribute("pageWrapper", pageWrapper);
        return "admin-companies-nonapproved";
    }

    @RequestMapping(value = "/pending-approval/candidates", method = RequestMethod.GET)
    public String nonApprovedCandidates(Model model, Pageable pageable) {
        Page<Candidate> nonApprovedCandidates = candidateService.findByApprovedFalse(pageable);
        PageWrapper<Candidate> pageWrapper = new PageWrapper<>(nonApprovedCandidates, "");
        model.addAttribute("pageWrapper", pageWrapper);
        return "admin-candidates-nonapproved";
    }

    @RequestMapping(value = "companies", method = RequestMethod.GET)
    public String companies(Model model, Pageable pageable) {
        Page<Company> nonApprovedCompanies = companyService.findByApprovedTrue(pageable);
        PageWrapper<Company> pageWrapper = new PageWrapper<>(nonApprovedCompanies, "");
        model.addAttribute("pageWrapper", pageWrapper);
        return "admin-companies-approved";
    }

    @RequestMapping(value = "candidates", method = RequestMethod.GET)
    public String candidates(Model model, Pageable pageable) {
        Page<Candidate> nonApprovedCandidates = candidateService.findByApprovedTrue(pageable);
        PageWrapper<Candidate> pageWrapper = new PageWrapper<>(nonApprovedCandidates, "");
        model.addAttribute("pageWrapper", pageWrapper);
        return "admin-candidates-approved";
    }

    @RequestMapping(value = "/company-account/{id}", method = {RequestMethod.GET})
    public String companyAccount(
            @PathVariable(value = "id") Long id,
            Model model) {

        Company company = companyService.findById(id);
        model.addAttribute("company", company);
        return "company-account";
    }

    @RequestMapping(value = "/candidate-account/{id}", method = {RequestMethod.GET})
    public String candidateAccount(
            @PathVariable(value = "id") Long id,
            Model model) {
        Candidate candidate = candidateService.findById(id);
        model.addAttribute("candidate", candidate);
        return "candidate-account";
    }

    @RequestMapping(value = "/approve_user/{id}", method = {RequestMethod.GET})
    public String companyApprove(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        extendedUserDetailService.approveUser(id);
        String referer = request.getHeader("Referer");
        if (StringUtils.isNullOrEmpty(referer)) return "admin-home";
        return "redirect:" + referer;
    }

    @RequestMapping(value = "/conversations/company/{id}", method = {RequestMethod.GET})
    public String companyConversations(@PathVariable(value = "id") Long id, Pageable pageable, Model model) {
        Company company = companyService.findById(id);

        Page<Conversation> conversations = conversationService.findAllByCompany(pageable, company);
        PageWrapper<Conversation> pageWrapper = new PageWrapper<>(conversations, "");

        model.addAttribute("pageWrapper", pageWrapper);
        model.addAttribute("id", id);
        return "admin-company-conversations";
    }

    @RequestMapping(value = "/conversations/candidate/{id}", method = {RequestMethod.GET})
    public String candidateConversations(@PathVariable(value = "id") Long id, Pageable pageable, Model model) {
        Candidate candidate = candidateService.findById(id);

        Page<Conversation> conversations = conversationService.findAllByCandidate(pageable, candidate);
        PageWrapper<Conversation> pageWrapper = new PageWrapper<>(conversations, "");

        model.addAttribute("pageWrapper", pageWrapper);
        model.addAttribute("id", id);
        return "admin-candidate-conversations";
    }

    @RequestMapping(value = "/company/conversation/{id}", method = {RequestMethod.GET})
    public String companyConversation(@PathVariable(value = "id") Long id, Pageable pageable, Model model) {
        prepareModelForConversationPreview(model, pageable, id);
        return "admin-company-conversation-preview";

    }

    @RequestMapping(value = "/candidate/conversation/{id}", method = {RequestMethod.GET})
    public String candidateConversation(@PathVariable(value = "id") Long id, Pageable pageable, Model model) {
        prepareModelForConversationPreview(model, pageable, id);
        return "admin-candidate-conversation-preview";
    }

    @RequestMapping(value = "/edit-company-account/{id}", method = {RequestMethod.GET})
    public String editCompanyAccount(
            @PathVariable(value = "id") Long id,
            ModelMap model) {
        Company company = companyService.findById(id);
        model.put("companyUsername", company.getUsername());
        return "redirect:/edit-account/company";
    }

    @RequestMapping(value = "/edit-candidate-account/{id}", method = {RequestMethod.GET})
    public String editCandidateAccount(
            @PathVariable(value = "id") Long id,
            ModelMap model) {
        Candidate candidate = candidateService.findById(id);
        model.put("candidateUsername", candidate.getUsername());
        return "redirect:/edit-account/candidate";
    }

    @RequestMapping(value = "/edit-admin-account/{id}", method = {RequestMethod.GET})
    public String editAdminAccount(
            @PathVariable(value = "id") Long id,
            ModelMap model) {
        Administrator administrator = (Administrator) extendedUserDetailService.findById(id);
        model.put("adminUsername", administrator.getUsername());
        return "redirect:/edit-account/admin";
    }

    @RequestMapping(value = "/create-company")
    public String createCompany() {
        return "redirect:/registration/company";
    }

    @RequestMapping(value = "/create-candidate")
    public String createCandidate() {
        return "redirect:/registration/candidate";
    }


}
