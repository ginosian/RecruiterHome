package com.recruiting.controller;

import com.recruiting.service.entity.CompanyService;
import com.recruiting.service.entity.ExtendedUserDetailService;
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
@RequestMapping(value = "/company")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
public class CompanyController extends AbstractController {

    @Autowired
    CompanyService companyService;

    @Autowired
    ExtendedUserDetailService userDetailService;


    @RequestMapping(value = "")
    public String company() {
        if (!approved()) return "redirect:/company/account";
        return "redirect:/company/search";
    }

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Model model) {
        model.addAttribute("company", getAuthorizedUser());
        return "company-account";
    }

    @RequestMapping(value = "/edit-account", method = RequestMethod.GET)
    public String editAccount(ModelMap model) {
        model.put("companyUsername", getAuthorizedUser().getUsername());
        return "redirect:/edit-account/company";
    }

    @RequestMapping(value = "/under-construction")
    public String underConstruction() {
        return "under-construction-company";
    }

}
