package com.recruiting.validation;

import com.recruiting.domain.Message;
import com.recruiting.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Created by Martha on 7/4/2017.
 */
@PropertySource("classpath:validation.properties")
public class AbstractMessageValidation {

    // region Instance Fields
    @Value("${message.content.length}")
    protected int message_content_length;

    @Value("${message.title.content.length}")
    protected int message_title_content_length;

    //endregion

    protected void validateMessage(Message message, String prefix, Errors errors) {
        String title = message.getTitle();
        String content = message.getContent();
        if ((StringUtils.isNullOrEmpty(title) && StringUtils.isNullOrEmpty(title))
                || StringUtils.isNullOrEmpty(content)
                || StringUtils.isNullOrEmpty(title)) {

            ValidationUtils.rejectIfEmpty(errors, prefix + "title", "required.message.title");
            ValidationUtils.rejectIfEmpty(errors, prefix + "content", "required.message.content");
            return;
        }

        if (title.length() > message_title_content_length) {
            errors.rejectValue(prefix + "title", "length.restriction.message.title");
            return;
        }
        if (content.length() > message_content_length) {
            errors.rejectValue(prefix + "content", "length.restriction.message.content");
            return;
        }
    }


}
