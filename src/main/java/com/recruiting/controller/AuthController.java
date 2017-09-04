package com.recruiting.controller;

import com.recruiting.domain.PasswordResetToken;
import com.recruiting.domain.User;
import com.recruiting.handler.SecuritySuccessHandler;
import com.recruiting.model.PasswordDTO;
import com.recruiting.model.modelUtils.StringItemModel;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Created by Martha on 4/18/2017.
 */
@Controller
@RequestMapping(value = "")
//@PreAuthorize("permitAll()")
public class AuthController {

    @Autowired
    PersistentTokenRepository persistentTokenRepository;

    @Autowired
    ExtendedUserDetailService userDetailsService;

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;


    /**
     * Resolves authorization and redirects to eligible page. If user is not authorized navigates to home page.
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String base(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (authentication != null && authentication.isAuthenticated()) return
                "redirect:" + SecuritySuccessHandler.resolveTargetURL(authentication, Constants.PATH_BASE);
        return "home";
    }

    /**
     * Resolves authorization and redirects to eligible page. If user is not authorized navigates to home page.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (authentication != null && authentication.isAuthenticated()) return
                "redirect:" + SecuritySuccessHandler.resolveTargetURL(authentication, Constants.PATH_BASE);
        return "home";
    }

    /**
     * Resolves authorization and redirects to eligible page. If user is not authorized navigates to login page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) return
                "redirect:" + SecuritySuccessHandler.resolveTargetURL(authentication, Constants.PATH_BASE);
        return "login";
    }

    @RequestMapping(value = "/access-denied", method = RequestMethod.GET)
    public String accessDenied() {
        return "access-denied";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request, Authentication authentication) throws ServletException {
        if (authentication != null) {
            persistentTokenRepository.removeUserTokens(authentication.getName());
        }
        request.logout();
        return "redirect:/login";
    }

    /**
     * Initial navigation to recover password page from login page.
     */
    @RequestMapping(value = "/password-lost", method = RequestMethod.GET)
    public String passwordLost(Model model) {
        model.addAttribute("username", new StringItemModel());
        return "password-lost";
    }

    @RequestMapping(value = "/recover-password", method = RequestMethod.POST)
    public String recoverPassword(@ModelAttribute StringItemModel username, RedirectAttributes redirectAttributes) {
        User user = (User) userDetailsService.findUserByUsername(username.getType());
        if (user == null) {
            String messageValue = "No such user. Please register if you don't have an account";
            redirectAttributes.addFlashAttribute("error", messageValue);
            return "redirect:/registration";
        }
        PasswordResetToken passwordResetToken = emptyEntityCreationService.emptyPasswordResetToken();
        passwordResetToken.setUser(user);
        userDetailsService.savePasswordResetToken(passwordResetToken);

        String name = null;
        name = (user).getName();

        emailService.sendUserPasswordChange(username.getType(), name, passwordResetToken.getToken());

        return "redirect:/login";
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.GET)
    String changePassword(RedirectAttributes redirectAttributes, Model model, @RequestParam("token") String token) {
        PasswordResetToken passwordResetToken = userDetailsService.getPasswordResetToken(token);
        if (passwordResetToken == null) {
            String message = "Invalid token.";
            redirectAttributes.addFlashAttribute("error", message);
            return "redirect:/login";
        }

        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            String messageValue = "Token expired.";
            redirectAttributes.addFlashAttribute("error", messageValue);
            return "redirect:/login";
        }


        User user = passwordResetToken.getUser();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                user, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        model.addAttribute("passwordModel", new PasswordDTO());
        return "update-password";
    }

    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public String updatePassword(@Validated @ModelAttribute PasswordDTO passwordDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setPassword(passwordDto.getNewPassword());
        userDetailsService.save(user);
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/";

    }

    @RequestMapping(value = "/under-construction")
    public String underConstruction() {
        return "under-construction-home";
    }

}
