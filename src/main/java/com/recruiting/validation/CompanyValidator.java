package com.recruiting.validation;

import com.recruiting.domain.Address;
import com.recruiting.domain.Company;
import com.recruiting.domain.CompanyStaff;
import com.recruiting.model.flowModel.CompanyFormModel;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.service.form.CompanyFormService;
import com.recruiting.utils.StringUtils;
import com.recruiting.utils.ValidationPatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Martha on 5/8/2017.
 * Validator for Candidate account creation and edit wizard pages.
 * Each step of validation validates a page from wizard.
 * Validator is called from Spring Web Flow context.
 */

@Component
public class CompanyValidator extends AbstractProfileInfoValidation implements Validator {

    @Autowired
    ExtendedUserDetailService userDetailsService;

    @Autowired
    CompanyFormService companyFormService;

    public boolean supports(Class clazz) {
        return clazz.equals(CompanyFormModel.class);
    }

    public void validate(Object object, Errors errors) {
        CompanyFormModel companyModel = (CompanyFormModel) object;

        String transitionId = resolveTransitionId();
        String stateId = resolveStateId();

        if (stateId.equals("stepOne") && transitionId.equals("next")) validateFirstStep(companyModel, errors);
        if (stateId.equals("stepTwo") && transitionId.equals("next")) validateSecondStep(companyModel, errors);
        if (stateId.equals("stepFinal") && transitionId.equals("preview")) validateFinalStep(companyModel, errors);
        if (stateId.equals("stepFinal") && transitionId.equals("addAddress")) {
            validateAddress(companyModel.getAdditionalAddressPlaceholder(), "additionalAddressPlaceholder", errors);
        }
    }

    private void validateFirstStep(CompanyFormModel companyModel, Errors errors) {

        Company company = companyModel.getCompany();
        String newName = companyModel.getNewName();
        if (companyModel.isSignedInUser()) {
            if (!newName.equals(company.getName())) validateName(newName, "newName", errors);
        } else validateName(company.getName(), "company.name", errors);

        String companyPhone = company.getPhone();
        validateRequiredByPatternNonEmpty(companyPhone,
                ValidationPatternUtils.USA_PHONE_NUMBER_PATTERN,
                "company.phone",
                "invalid.phone",
                errors);

        String companyUsername = company.getUsername();
        String companyInitialUsername = companyModel.getInitialUsername();
        String companyPassword = company.getPassword();
        String companyVerifyPassword = companyModel.getVerifyPassword();
        Boolean agreed = companyModel.isAgreed();
        validateAuthenticationCredentials(agreed,
                companyInitialUsername,
                companyUsername,
                companyPassword,
                companyVerifyPassword,
                "company",
                companyModel.isSignedInUser(),
                companyModel.getNewPassword(),
                errors);
        if (companyModel.isSignedInUser()) {
            if (!companyInitialUsername.equals(companyUsername)) validateCompanyEmailExists(companyUsername, errors);
        } else if (!StringUtils.isNullOrEmpty(companyUsername)) validateCompanyEmailExists(companyUsername, errors);
    }

    private void validateSecondStep(CompanyFormModel companyModel, Errors errors) {
        if (companyModel.getCompany()
                .getIndustry() == null) {
            errors.rejectValue("company.industry", "required.industry");
        } else validateEmptySelectionValue(companyModel.getCompany()
                .getIndustry()
                .getId()
                .toString(), errors, "company.industry", "required.industry");

        validateAddress(companyModel.getCompany()
                .getAddress(), "company.address", errors);

        CompanyStaff mainContact = companyModel.getCompany()
                .getMainContact();

        String mainContactName = mainContact.getName();
        if (!StringUtils.isNullOrEmpty(mainContactName) && mainContactName.length() > full_name_length)
            errors.rejectValue("company.mainContact.name", "length.restriction.name");
        validateRequiredByPatternNonEmpty(mainContactName,
                ValidationPatternUtils.ALPHABET_SPACE_PATTERN,
                "company.mainContact.name",
                "invalid.name",
                errors);

        String mainContactUsername = mainContact.getEmail();
        if (!StringUtils.isNullOrEmpty(mainContactUsername) && mainContactUsername.length() > email_lenght)
            errors.rejectValue("company.mainContact.email", "length.restriction.email");
        validateByPattern(mainContactUsername,
                ValidationPatternUtils.EMAIL_PATTERN,
                "company.mainContact.email",
                "invalid.email",
                errors);

        String mainContactPhone = mainContact.getPhone();
        validateByPattern(mainContactPhone,
                ValidationPatternUtils.USA_PHONE_NUMBER_PATTERN,
                "company.mainContact.phone",
                "invalid.phone",
                errors);
    }

    private void validateFinalStep(CompanyFormModel companyModel, Errors errors) {
        Address[] addresses = companyModel.getAdditionalAddressesDecorator();
        if (addresses != null) {
            for (int i = 0; i < addresses.length; i++) {
                Address address = addresses[i];
                if (address != null) {
                    int initialErrorCount = errors.getErrorCount();
                    validateAddress(address, "addressForError", errors);
                    int finalErrorCount = errors.getErrorCount();
                    if (initialErrorCount != finalErrorCount) return;
                }
            }
        }
    }

    private void validateName(String companyName, String fieldName, Errors errors) {
        if (StringUtils.isNullOrEmpty(companyName))
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, "required.name");
        if (companyName.length() > company_name_length)
            errors.rejectValue(fieldName, "length.restriction.company.name");
        else validateCompanyNameExists(companyName, errors);
    }


    // region Company registration specific validations
    private void validateCompanyNameExists(String companyName, Errors errors) {
        if (companyFormService.companyExists(companyName)) {
            errors.rejectValue("company.name", "exists.company");
        }
    }

    private void validateCompanyEmailExists(String companyEmail, Errors errors) {
        if (userDetailsService.findUserByUsername(companyEmail) != null) {
            errors.rejectValue("company.username", "exists.mail.company");
        }
    }
    // endregion
}
