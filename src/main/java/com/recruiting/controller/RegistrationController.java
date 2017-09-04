package com.recruiting.controller;

import com.recruiting.domain.User;
import com.recruiting.domain.VerificationToken;
import com.recruiting.model.modelUtils.StringItemModel;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.service.entity.GenericCrudService;
import com.recruiting.utils.ConstantLabels;
import com.recruiting.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

/**
 * Created by Martha on 5/4/2017.
 */
@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    ExtendedUserDetailService userDetailsService;

    @Autowired
    GenericCrudService genericCrudService;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String register(Model model) {
        String error = (String) model.asMap().get("error");
        model.addAttribute("error", StringUtils.isNullOrEmpty(error) ? null : error);
        model.addAttribute("registrationtypes", ConstantLabels.REGISTRATION_TYPES_LIST);
        model.addAttribute("choosenType", new StringItemModel());
        return "registration";
    }

    @RequestMapping(value = "/redirect")
    public String resolveRegistrationType(@RequestParam("type") String type) {
        return "redirect:/registration/" + type.toLowerCase();
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmRegistration(RedirectAttributes redirectAttributes, @RequestParam("token") String token) {


        VerificationToken verificationToken = userDetailsService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = "Invalid token.";
            redirectAttributes.addFlashAttribute("error", message);
            return "redirect:/registration";
        }

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            String messageValue = "Token expired.";
            redirectAttributes.addFlashAttribute("error", messageValue);
            return "redirect:/registration";
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        genericCrudService.save(user);
        return "redirect:/login";
    }

}
