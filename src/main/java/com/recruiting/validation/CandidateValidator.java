package com.recruiting.validation;

import com.recruiting.domain.Candidate;
import com.recruiting.domain.CandidateSkill;
import com.recruiting.model.flowModel.CandidateFormModel;
import com.recruiting.service.entity.ExtendedUserDetailService;
import com.recruiting.utils.StringUtils;
import com.recruiting.utils.ValidationPatternUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Martha on 5/15/2017.
 * Validator for Candidate account creation and edit wizard pages.
 * Each step of validation validates a page from wizard.
 * Validator is called from Spring Web Flow context.
 */
@Component
public class CandidateValidator extends AbstractProfileInfoValidation implements Validator {


    @Autowired
    ExtendedUserDetailService userDetailsService;

    public boolean supports(Class clazz) {
        return clazz.equals(CandidateFormModel.class);
    }

    public void validate(Object object, Errors errors) {
        CandidateFormModel candidateModel = (CandidateFormModel) object;

        String transitionId = resolveTransitionId();
        String stateId = resolveStateId();

        if (stateId.equals("stepOne") && transitionId.equals("next")) validateFirstStep(candidateModel, errors);
        if (stateId.equals("stepTwo") && transitionId.equals("next")) validateSecondStep(candidateModel, errors);
        if (stateId.equals("stepFinal") && transitionId.equals("preview")) validateFinalStep(candidateModel, errors);
        if (stateId.equals("stepFinal") && transitionId.equals("addSkill")) {
            validateSkill(candidateModel.getSkillPlaceholder(), "skillPlaceholder.skill", "skillPlaceholder.experienceDuration", errors);
        }
    }

    private void validateFirstStep(CandidateFormModel candidateModel, Errors errors) {
        Candidate candidate = candidateModel.getCandidate();

        String candidateUsername = candidate.getUsername();
        String candidateInitialUsername = candidateModel.getInitialUsername();
        String candidatePassword = candidate.getPassword();
        String candidateVerifyPassword = candidateModel.getVerifyPassword();
        Boolean agreed = candidateModel.isAgreed();

        validateAuthenticationCredentials(agreed,
                candidateInitialUsername,
                candidateUsername,
                candidatePassword,
                candidateVerifyPassword,
                "candidate",
                candidateModel.isSignedInUser(),
                candidateModel.getNewPassword(),
                errors);

        if (candidateModel.isSignedInUser()) {
            if (!candidateInitialUsername.equals(candidateUsername))
                validateCandidateEmailExists(candidateUsername, errors);
        } else if (!StringUtils.isNullOrEmpty(candidateUsername))
            validateCandidateEmailExists(candidateUsername, errors);

    }

    public void validateSecondStep(CandidateFormModel candidateModel, Errors errors) {

        Candidate candidate = candidateModel.getCandidate();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "candidate.name", "required.name");
        if (!StringUtils.isNullOrEmpty(candidate.getName()) && candidate.getName()
                .length() > ValidationPatternUtils.FULL_NAME_LENGTH)
            errors.rejectValue("candidate.name", "length.restriction.name");

        if (candidateModel.getAreaDecorator() == null || candidateModel.noAreaSelected())
            errors.rejectValue("error", null, "Select at least one area or if you don't see area selection box choose a state.");
        validateAddress(candidateModel.getCandidate()
                .getAddress(), "candidate.address", errors);
        validateEmptySelectionValue(candidateModel.getCandidate()
                .getTimePeriod(), errors, "candidate.timePeriod", "required.timePeriod");

        Date input = candidateModel.getCandidate().getBirthDate();
        if (input == null) {
            candidateModel.getCandidate().setBirthDate(null);
            errors.rejectValue("candidate.birthDate", null, "Birth is mandatory");
        } else {
            LocalDate date = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (date.isAfter(LocalDate.now())) {
                candidateModel.getCandidate().setBirthDate(null);
                errors.rejectValue("candidate.birthDate", null, "Invalid birth date");
            }
        }
    }


    private void validateFinalStep(CandidateFormModel candidateModel, Errors errors) {
        byte[] file = candidateModel.getCandidate().getResume().getFile();
        CandidateSkill[] candidateSkills = candidateModel.getSkillsDecorator();
        if (file == null) {
            if (candidateModel.noSkillsInDecorator()) errors.rejectValue("error", "skill.or.resume");
            for (CandidateSkill skill : candidateSkills) {
                int initialErrorCount = errors.getErrorCount();
                if (skill != null)
                    validateSkill(skill, "errorSkillPlaceholder.skill", "errorSkillPlaceholder.experienceDuration", errors);
                int finalErrorCount = errors.getErrorCount();
                if (initialErrorCount != finalErrorCount) return;
            }
        }
        validateHourlyRate(candidateModel.getCandidate().getHourlyRate(), "candidate.hourlyRate", "required.hourlyRate", errors);
    }


    // region Candidate registration specific validations

    private void validateCandidateEmailExists(String candidateEmail, Errors errors) {
        if (userDetailsService.findUserByUsername(candidateEmail) != null) {
            errors.rejectValue("candidate.username", "exists.mail.candidate");
        }
    }
    // endregion
}
