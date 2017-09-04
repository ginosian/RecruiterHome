package com.recruiting.controller;

import com.recruiting.domain.Candidate;
import com.recruiting.domain.Company;
import com.recruiting.domain.Conversation;
import com.recruiting.domain.Message;
import com.recruiting.model.modelUtils.PageWrapper;
import com.recruiting.service.CommunicationService;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.CompanyService;
import com.recruiting.service.entity.ConversationService;
import com.recruiting.utils.TypeConversionUtils;
import com.recruiting.validation.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Martha on 6/19/2017.
 */
@Controller
@RequestMapping(value = "/company/messages")
@PreAuthorize("hasAnyAuthority('ROLE_COMPANY')")
public class CompanyConversationsController extends AbstractController {

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;

    @Autowired
    CommunicationService communicationService;

    @Autowired
    ConversationService conversationService;

    @Autowired
    CompanyService companyService;

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    MessageValidator messageValidator;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String messages(Model model, Pageable pageable) {
        Page<Conversation> conversations = conversationService.findAllByCompany(pageable, (Company) getAuthorizedUser());
        PageWrapper<Conversation> pageWrapper = new PageWrapper<>(conversations, "/company/messages");
        model.addAttribute("pageWrapper", pageWrapper);
        return "company-conversations";
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public String message(
            @PathVariable(value = "id") Long id,
            Model model,
            Pageable pageable) {
        prepareModelForConversationPreviewAndNewMessage(model, pageable, id, null);
        return "company-conversation-preview";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(
            @Validated @ModelAttribute(value = "message") Message message,
            BindingResult bindingResult,
            Model model,
            Pageable pageable) {
        Conversation conversation = conversationService.findById(message.getConversation().getId());
        if (bindingResult.hasErrors()) {
            prepareModelForConversationPreviewAndNewMessage(model, pageable, conversation.getId(), message);
            return "company-conversation-preview";
        }
        communicationService.updateCommunication(conversation, message, conversation.getCompany(), conversation.getCandidate());
        Candidate candidate = conversation.getCandidate();
        emailService.sendUserReceiveMessage(candidate.getUsername(), TypeConversionUtils.buildReducedFullName(candidate.getName()),
                "You have a new email from " + conversation.getCompany().getName() + ".");
        return "redirect:/company/messages/" + conversation.getId();
    }

    @InitBinder("message")
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(messageValidator);
    }

}
