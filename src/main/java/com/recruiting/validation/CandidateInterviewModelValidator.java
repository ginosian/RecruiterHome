package com.recruiting.validation;

import com.recruiting.model.CandidateInterviewModel;
import com.recruiting.utils.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Martha on 6/29/2017.
 */
@Component
public class CandidateInterviewModelValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(CandidateInterviewModel.class);
    }

    public void validate(Object object, Errors errors) {
        CandidateInterviewModel model = (CandidateInterviewModel) object;

        if (model.getInterviewDate() == null) errors.rejectValue("interviewDate", "required.interview.date");
        if (StringUtils.isNullOrEmpty(model.getInterviewStatus()) && StringUtils.isNullOrEmpty(model.getRejected()))
            errors.rejectValue("interviewStatus", "required.interview.status");
        if (StringUtils.isNullOrEmpty(model.getConversation().getInterview().getCandidateResponse().getContent()))
            errors.rejectValue("conversation.interview.candidateResponse.content", "required.message.content");
    }
}

