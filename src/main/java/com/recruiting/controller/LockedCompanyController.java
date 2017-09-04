package com.recruiting.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Martha on 9/4/2017.
 */
@Controller
@RequestMapping(value = "/company/non-approved")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY_LOCKED')")
public class LockedCompanyController extends AbstractController {

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Model model) {
        model.addAttribute("company", getAuthorizedUser());
        return "company-account";
    }
}
