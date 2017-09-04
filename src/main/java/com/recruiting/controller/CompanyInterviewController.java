package com.recruiting.controller;

import com.recruiting.domain.Company;
import com.recruiting.domain.Interview;
import com.recruiting.model.modelUtils.PageWrapper;
import com.recruiting.service.CommunicationService;
import com.recruiting.service.entity.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

/**
 * Created by Martha on 7/6/2017.
 */
@Controller
@RequestMapping(value = "/company/interviews")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
public class CompanyInterviewController extends AbstractController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CommunicationService communicationService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String pendingInterviews(Model model, Pageable pageable) {
        Page<Interview> interviews = communicationService.findAcceptedInterviewsByCompany((Company) getAuthorizedUser(), LocalDateTime.now(), pageable);
        PageWrapper<Interview> pageWrapper = new PageWrapper<>(interviews, "");
        model.addAttribute("pageWrapper", pageWrapper);
        return "company-interviews";
    }
}
