package com.recruiting.validation;

import com.recruiting.domain.Address;
import com.recruiting.domain.CandidateSkill;
import com.recruiting.utils.StringUtils;
import com.recruiting.utils.ValidationPatternUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.webflow.execution.RequestContextHolder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Martha on 5/14/2017.
 */
@PropertySource("classpath:validation.properties")
public class AbstractProfileInfoValidation {

    @Value("${city.lenght}")
    protected int city_lenght;

    @Value("${address.length}")
    protected int address_length;

    @Value("${email.lenght}")
    protected int email_lenght;

//    @Value("${email.pattern}")
//    protected String email_pattern;
//
//    @Value("${password.pattern}")
//    protected String password_pattern;
//
//    @Value("${usa.phone.number.pattern}")
//    protected String usa_phone_number_pattern;
//
//    @Value("${alphabet.space.pattern}")
//    protected String alphabet_space_pattern;

    @Value("${full.name.length}")
    protected int full_name_length;

    @Value("${company.name.length}")
    protected int company_name_length;


    /**
     * Checks if any of address fields is null or empty sets error on a field by field name and returns.
     * As an initial field name the name of address object in html should be passed, fields names like .city, .address, .zipCode and .state.id are added to initial field name
     * internally within this method.
     * Error messages are read from messages.properties file.
     * No default message is provided so make sure to have all error messages by codes.
     * Error messages codes used in this method are
     * "required.city"
     * "required.address"
     * "required.zip"
     * "required.state".
     * Also state should be bind by id.
     */
    public void validateAddress(Address address, String initialFieldName, Errors errors) {
        if (address == null) return;
        if (StringUtils.isNullOrEmpty(address.getCity())) {
            errors.rejectValue(initialFieldName + ".city", "required.city");
            return;
        }
        if (!StringUtils.isNullOrEmpty(address.getCity()) && address.getCity().length() > city_lenght) {
            errors.rejectValue(initialFieldName + ".city", "length.restriction.address");
            return;
        }
        if (StringUtils.isNullOrEmpty(address.getAddress())) {
            errors.rejectValue(initialFieldName + ".address", "required.address");
            return;
        }
        if (!StringUtils.isNullOrEmpty(address.getAddress()) && address.getAddress().length() > address_length) {
            errors.rejectValue(initialFieldName + ".address", "length.restriction.city");
            return;
        }
        if (address.getZipCode() == null || address.getZipCode() < 1) {
            errors.rejectValue(initialFieldName + ".zipCode", "required.zip");
            return;
        }
        if (address.getState() == null) {
            errors.rejectValue(initialFieldName + ".state", "required.state");
        } else
            validateEmptySelectionValue(address.getState().getId().toString(), errors, initialFieldName + ".state", "required.state");

    }

    public void validateAuthenticationCredentials(boolean agreed, String initialUsername, String username, String password, String verifyPassword, String initialFieldName, boolean isSignedInUser, String newPassword, Errors errors) {
        if (isSignedInUser) {
            if (!initialUsername.equals(username)) validateUsername(username, initialFieldName, errors);
        } else validateUsername(username, initialFieldName, errors);

        if (isSignedInUser && !newPassword.equals(password)) {
            if (!StringUtils.isNullOrEmpty(newPassword))
                validatePassword(newPassword, "", verifyPassword, ".newPassword", errors);
        } else validatePassword(password, initialFieldName, verifyPassword, ".password", errors);

        if (!isSignedInUser) validateAgreed(agreed, errors);
    }

    private void validateUsername(String username, String initialFieldName, Errors errors) {
        if (!StringUtils.isNullOrEmpty(username) && username.length() > email_lenght)
            errors.rejectValue(initialFieldName + ".username", "length.restriction.email");
        validateRequiredByPatternNonEmpty(username,
                ValidationPatternUtils.EMAIL_PATTERN_DEVELOPMENT,
                initialFieldName + ".username",
                "invalid.email",
                errors);
    }

    private void validatePassword(String password, String initialFieldName, String verifyPassword, String fieldName, Errors errors) {
        if (!StringUtils.isNullOrEmpty(password) && password.length() > email_lenght)
            errors.rejectValue(initialFieldName + fieldName, "length.restriction.password");
        validateRequiredByPatternNonEmpty(password,
                ValidationPatternUtils.PASSWORD_PATTERN, initialFieldName + fieldName, "invalid.password", errors);

        if (StringUtils.isNullOrEmpty(verifyPassword))
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "verifyPassword", "required.verifyPassword");
        else validateVerifyPassword(password, verifyPassword, "verifyPassword", errors);
    }

    public void validateByPattern(String input, String pattern, String fieldName, String errorCode, Errors errors) {
        if (StringUtils.isNullOrEmpty(input)) return;
        Pattern patternEmail = Pattern.compile(pattern);
        Matcher matcher = patternEmail.matcher(input);
        if (!matcher.matches()) {
            errors.rejectValue(fieldName, errorCode);
        }
    }

    public void validateRequiredByPatternNonEmpty(String input, String pattern, String fieldName, String errorCode, Errors errors) {
        if (StringUtils.isNullOrEmpty(input)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, fieldName, errorCode);
            return;
        }
        Pattern patternEmail = Pattern.compile(pattern);
        Matcher matcher = patternEmail.matcher(input);
        if (!matcher.matches()) {
            errors.rejectValue(fieldName, errorCode);
        }
    }

    public void validateEmptySelectionValue(String selecctedValue, Errors errors, String field, String errorCode) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, errorCode);
        if (selecctedValue.equals("0")) errors.rejectValue(field, errorCode);
    }

    public void validateEquality(String originalValue, String givenValue, String fieldName, Errors errors) {
        if (!originalValue.equals(givenValue)) {
            errors.rejectValue(fieldName, "no.match.password");
        }
    }

    public void validateBooleanIsTrue(boolean value, String field, String errorCode, Errors errors) {
        if (!value) {
            errors.rejectValue(field, errorCode);
        }
    }

    public void validateVerifyPassword(String originalPassword, String verifyPassword, String fieldName, Errors errors) {
        if (!originalPassword.equals(verifyPassword)) {
            errors.rejectValue(fieldName, "no.match.password");
        }
    }

    public void validateAgreed(boolean agreed, Errors errors) {
        if (!agreed) {
            errors.rejectValue("agreed", "agree.with.terms");
        }
    }

    protected void validateSkill(CandidateSkill candidateSkill, String skillFieldName, String experienceFieldName, Errors errors) {
        if (candidateSkill == null || (candidateSkill.getSkill() == null && candidateSkill.getExperienceDuration() == null)) {
            errors.rejectValue(skillFieldName, "required.skill");
            errors.rejectValue(experienceFieldName, "required.experience");
            return;
        }
        String title = candidateSkill.getSkill().getTitle();
        if (StringUtils.isNullOrEmpty(title)) errors.rejectValue(skillFieldName, "required.skill");
        else validateEmptySelectionValue(title, errors, skillFieldName, "required.skill");
        Integer experience = candidateSkill.getExperienceDuration();
        if (experience == null || experience == 0) errors.rejectValue(skillFieldName, "required.experience");
        else {
            if (experience <= 0) errors.rejectValue(experienceFieldName, "required.experience");
            if (experience > 99) errors.rejectValue(experienceFieldName, "too.much.experience");
        }
    }

    protected void validateHourlyRate(Integer hourlyRate, String fieldName, String errorCode, Errors errors) {
        if (hourlyRate == null || hourlyRate <= 0) {
            errors.rejectValue(fieldName, errorCode);
        }
    }

    protected String resolveTransitionId() {
        return RequestContextHolder.getRequestContext().getCurrentView().getUserEventState().toString().split("eventId = ")[1].split("mappingResults")[0].replaceAll("[^a-zA-Z]", "");
    }

    protected String resolveStateId() {
        return RequestContextHolder.getRequestContext().getFlowExecutionContext().getActiveSession().getState().getId();
    }

}
