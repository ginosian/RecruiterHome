package com.recruiting.controller;

import com.recruiting.service.entity.CandidateService;
import com.recruiting.service.entity.GenericCrudService;
import com.recruiting.service.entity.SkillService;
import com.recruiting.utils.ConstantLabels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Martha on 4/28/2017.
 */
@Controller
@RequestMapping(value = "/candidate")
@PreAuthorize("hasAnyAuthority('ROLE_CANDIDATE')")
public class CandidateController extends AbstractController {

    @Autowired
    SkillService skillService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    GenericCrudService genericCrudService;

    @RequestMapping(value = "")
    public String candidate() {
        return "redirect:/candidate/messages/";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Model model) {
        model.addAttribute("candidate", getAuthorizedUser());
        return "candidate-account";
    }

    @RequestMapping(value = "/edit-account", method = RequestMethod.GET)
    public String editAccount(ModelMap model) {
        model.put("candidateUsername", getAuthorizedUser().getUsername());
        model.put("skills", skillService.findAll());
        model.put("timePeriods", ConstantLabels.TIME_PERIODS_LIST);
        return "redirect:/edit-account/candidate";
    }

    @RequestMapping(value = "/under-construction")
    public String underConstruction() {
        return "under-construction-candidate";
    }

}
