package com.recruiting.validation;

import com.recruiting.domain.Conversation;
import com.recruiting.domain.Interview;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/29/2017.
 */
@Component
public class ConversationValidator extends AbstractMessageValidation implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(Conversation.class);
    }

    public void validate(Object object, Errors errors) {
        Interview interview = ((Conversation) object).getInterview();
        if (interview == null) {
            ValidationUtils.rejectIfEmpty(errors, "interview.interviewDate", "invalid.date");
            return;
        }

        if (interview.getInterviewDate() == null || interview.getInterviewDate().isBefore(LocalDateTime.now())) {
            interview.setInterviewDate(null);
            errors.rejectValue("interview.interviewDate", "invalid.date");
            return;
        }

        LocalDateTime dateOptional1 = interview.getInterviewDateOptional1();
        LocalDateTime dateOptional2 = interview.getInterviewDateOptional2();
        if (dateOptional1 != null && dateOptional1.isBefore(LocalDateTime.now())) {
            interview.setInterviewDateOptional1(null);
            errors.rejectValue("interview.interviewDateOptional1", "invalid.date");
            return;
        }
        if (dateOptional2 != null && dateOptional2.isBefore(LocalDateTime.now())) {
            interview.setInterviewDateOptional2(null);
            errors.rejectValue("interview.interviewDateOptional2", "invalid.date");
            return;
        }

        validateMessage(interview.getCompanyMessage(), "interview.companyMessage.", errors);
    }
}
