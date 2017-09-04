package com.recruiting.validation;

import com.recruiting.domain.Message;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Martha on 6/29/2017.
 */
@Component
public class MessageValidator extends AbstractMessageValidation implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(Message.class);
    }

    public void validate(Object object, Errors errors) {
        Message message = (Message) object;
        validateMessage(message, "", errors);
    }
}
