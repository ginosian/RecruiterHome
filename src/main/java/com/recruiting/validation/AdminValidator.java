package com.recruiting.validation;

import com.recruiting.domain.Administrator;
import com.recruiting.model.flowModel.AdminFormModel;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Martha on 7/6/2017.
 */
public class AdminValidator extends AbstractProfileInfoValidation implements Validator {

    @Autowired
    ExtendedUserDetailService userDetailsService;

    public boolean supports(Class clazz) {
        return clazz.equals(AdminFormModel.class);
    }

    public void validate(Object object, Errors errors) {
        AdminFormModel adminModel = (AdminFormModel) object;

        String transitionId = resolveTransitionId();
        String stateId = resolveStateId();

        if (stateId.equals("stepOne") && transitionId.equals("next")) validateFirstStep(adminModel, errors);
    }

    private void validateFirstStep(AdminFormModel adminModel, Errors errors) {

        Administrator admin = adminModel.getAdmin();
        String newName = adminModel.getNewName();
        if (adminModel.isSignedInUser()) {
            if (!newName.equals(admin.getName())) validateName(newName, "newName", errors);
        } else validateName(admin.getName(), "admin.name", errors);

        String adminUsername = admin.getUsername();
        String adminInitialUsername = adminModel.getInitialUsername();
        String adminPassword = admin.getPassword();
        String adminVerifyPassword = adminModel.getVerifyPassword();
        validateAuthenticationCredentials(true, adminInitialUsername, adminUsername, adminPassword, adminVerifyPassword, "admin", adminModel.isSignedInUser(), adminModel.getNewPassword(), errors);
        if (adminModel.isSignedInUser()) {
            if (!adminInitialUsername.equals(adminUsername)) validateAdminEmailExists(adminUsername, errors);
        } else if (!StringUtils.isNullOrEmpty(adminUsername)) validateAdminEmailExists(adminUsername, errors);
    }

    private void validateName(String adminName, String fieldName, Errors errors) {
        if (StringUtils.isNullOrEmpty(adminName))
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "required.name");
        if (adminName.length() > company_name_length)
            errors.rejectValue(fieldName, "length.restriction.company.name");
    }

    // TODO Abstract this from here and from Company and Candidate
    private void validateAdminEmailExists(String adminEmail, Errors errors) {
        if (userDetailsService.findUserByUsername(adminEmail) != null) {
            errors.rejectValue("admin.username", "exists.mail.company");
        }
    }
}
