package com.recruiting.controller;

import com.recruiting.domain.Conversation;
import com.recruiting.domain.Message;
import com.recruiting.domain.User;
import com.recruiting.model.modelUtils.PageWrapper;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.entity.ConversationService;
import com.recruiting.service.entity.ExtendedUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

/**
 * Created by Martha on 6/29/2017.
 */

public abstract class AbstractController {

    @Autowired
    ExtendedUserDetailService extendedUserDetailService;

    @Autowired
    ConversationService conversationService;

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    protected boolean approved() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (auth == null) return false;
        String authName = auth.getName();
        User user = extendedUserDetailService.findUserByUsername(authName);
        return user.getApproved();
    }

    protected Model prepareModelForConversationPreviewAndNewMessage(Model model,
            Pageable pageable,
            Long conversationId,
            Message message) {
        // Finds conversation and sets last message as seen aas this step guarantee that all messages are seen
        Conversation conversation = conversationService.findById(conversationId);
        Message conversationLastMessage = conversation.getLastMessage();
        if (conversationLastMessage != null && (!conversationLastMessage.getSeen())) {
            conversationLastMessage.setSeen(true);
            conversation = conversationService.save(conversation);
        }

        // Creates new message as a placeholder in case user will replay on conversation
        Message newMessage;
        if (message == null) newMessage = emptyEntityCreationService.emptyMessage();
        else newMessage = message;
        newMessage.setConversation(conversation);

        //Find messages for conversation
        Page<Message> messages = conversationService.findByConversation(conversation, pageable);

        // Prepares ui with data
        PageWrapper<Message> pageWrapper = new PageWrapper<>(messages, "");
        model.addAttribute("pageWrapper", pageWrapper);
        model.addAttribute("conversation", conversation);
        model.addAttribute("message", newMessage);

        return model;
    }

    protected Model prepareModelForConversationPreview(Model model, Pageable pageable, Long conversationId) {
        // Finds conversation and sets last message as seen aas this step guarantee that all messages are seen
        Conversation conversation = conversationService.findById(conversationId);

        //Find messages for conversation
        Page<Message> messages = conversationService.findByConversation(conversation, pageable);

        // Prepares ui with data
        PageWrapper<Message> pageWrapper = new PageWrapper<>(messages, "");
        model.addAttribute("pageWrapper", pageWrapper);
        model.addAttribute("conversation", conversation);

        return model;
    }

    protected User getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authName = auth.getName();
        return extendedUserDetailService.findUserByUsername(authName);
    }
}
