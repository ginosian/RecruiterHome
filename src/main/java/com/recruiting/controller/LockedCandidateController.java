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
@RequestMapping(value = "/candidate/non-approved")
@PreAuthorize("hasAnyAuthority('ROLE_CANDIDATE_LOCKED')")
public class LockedCandidateController extends AbstractController {

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String account(Model model) {
        model.addAttribute("candidate", getAuthorizedUser());
        return "candidate-account";
    }
}
