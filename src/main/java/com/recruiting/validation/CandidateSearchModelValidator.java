package com.recruiting.validation;

import com.recruiting.model.searchModel.CandidateSearchModel;
import com.recruiting.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Martha on 6/6/2017.
 */
@Component
public class CandidateSearchModelValidator extends AbstractProfileInfoValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(CandidateSearchModel.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CandidateSearchModel model = (CandidateSearchModel) target;
        validateSkill(model.getSkillPlaceholder(), "skillPlaceholder.skill", "skillPlaceholder.experienceDuration", errors);
        validateHourlyRate(model.getHourlyRate(), "hourlyRate", "required.hourlyRate", errors);
        validateEmptySelectionValue(model.getTimePeriod(), errors, "timePeriod", "required.timePeriod");
        if (StringUtils.isNullOrEmpty(model.getLocation())) errors.rejectValue("location", "required.location");
    }
}
