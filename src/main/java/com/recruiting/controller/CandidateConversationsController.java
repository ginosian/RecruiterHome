package com.recruiting.controller;

import com.recruiting.domain.*;
import com.recruiting.model.CandidateInterviewModel;
import com.recruiting.model.modelUtils.PageWrapper;
import com.recruiting.service.CommunicationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.CandidateService;
import com.recruiting.service.entity.ConversationService;
import com.recruiting.validation.CandidateInterviewModelValidator;
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

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/24/2017.
 */
@Controller
@RequestMapping(value = "/candidate/messages")
@PreAuthorize("hasAnyAuthority('ROLE_CANDIDATE')")
public class CandidateConversationsController extends AbstractController {

    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;

    @Autowired
    CommunicationService communicationService;

    @Autowired
    ConversationService conversationService;

    @Autowired
    CandidateService candidateService;

    @Autowired
    MessageValidator messageValidator;

    @Autowired
    CandidateInterviewModelValidator candidateInterviewModelValidator;


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String messages(Model model, Pageable pageable) {
        Page<Conversation> conversations = conversationService.findAllByCandidate(pageable, (Candidate) getAuthorizedUser());
        PageWrapper<Conversation> pageWrapper = new PageWrapper<>(conversations, "/candidate/messages");
        model.addAttribute("pageWrapper", pageWrapper);
        return "candidate-conversations";
    }


    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public String message(@PathVariable(value = "id") Long id, Model model, Pageable pageable) {
        buildInterviewStatus(model, id);
        prepareModelForConversationPreviewAndNewMessage(model, pageable, id, null);
        return "candidate-conversation-preview";
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String send(
            @Validated @ModelAttribute(value = "message") Message message,
            BindingResult bindingResult,
            Model model,
            Pageable pageable) {
        Conversation conversation = conversationService.findById(message.getConversation().getId());
        if (bindingResult.hasErrors()) {
            buildInterviewStatus(model, conversation.getId());
            prepareModelForConversationPreviewAndNewMessage(model, pageable, conversation.getId(), message);
            return "candidate-conversation-preview";
        }

        communicationService.updateCommunication(conversation, message, conversation.getCandidate(), conversation.getCompany());

        Company company = conversation.getCompany();
        emailService.sendUserReceiveMessage(company.getUsername(), company.getName(),
                "You have a new email from " + conversation.getCompany().getName() + ".");

        return "redirect:/candidate/messages/" + conversation.getId();
    }

    @RequestMapping(value = "/interview", method = RequestMethod.POST)
    public String responseToInterview(
            @Validated @ModelAttribute(value = "initialConversation") CandidateInterviewModel candidateInterviewModel,
            BindingResult bindingResult,
            Model model,
            Pageable pageable) {
        if (bindingResult.hasErrors()) {
            Long id = candidateInterviewModel.getConversation().getId();
            buildInterviewStatus(model, id);
            prepareModelForConversationPreviewAndNewMessage(model, pageable, id, null);
            return "candidate-conversation-preview";
        }
        Message candidateResponce = candidateInterviewModel.getConversation().getInterview().getCandidateResponse();
        Conversation conversation = conversationService.findById(candidateInterviewModel.getConversation().getId());
        Interview interview = conversation.getInterview();
        interview.setInterviewDate(candidateInterviewModel.getInterviewDate());
        interview.setCandidateResponse(candidateResponce);
        if (candidateInterviewModel.getInterviewStatus().equals("Reject")) {
            communicationService.responseToInvitationForInterview(conversation, true);
        } else if (candidateInterviewModel.getInterviewStatus().equals("Accept")) {
            communicationService.responseToInvitationForInterview(conversation, false);
        }
        return "redirect:/candidate/messages/" + candidateInterviewModel.getConversation().getId();
    }

    @InitBinder("message")
    protected void initMessageBinder(final WebDataBinder binder) {
        binder.addValidators(messageValidator);
    }

    @InitBinder("initialConversation")
    protected void initCandidateInterviewModelBinder(final WebDataBinder binder) {
        binder.addValidators(candidateInterviewModelValidator);
    }


    private Model buildInterviewStatus(Model model, Long conversationId) {
        //Create interview status changes model for interview invitations
        Conversation conversation = conversationService.findById(conversationId);
        if (conversation != null && !conversation.getInterview().getAccepted() && !conversation.getInterview().getRejected()) {
            CandidateInterviewModel candidateInterviewModel = new CandidateInterviewModel(
                    conversation,
                    conversation.getInterview().getInterviewDate(),
                    "Accept",
                    "Reject",
                    "");
            model.addAttribute("initialConversation", candidateInterviewModel);
            Interview interview = conversation.getInterview();
            LocalDateTime date = interview.getInterviewDate();
            LocalDateTime dateOptional1 = interview.getInterviewDateOptional1();
            LocalDateTime dateOptional2 = interview.getInterviewDateOptional2();

            if (date.isBefore(LocalDateTime.now()) && dateOptional1.isBefore(LocalDateTime.now()) && dateOptional2.isBefore(LocalDateTime.now()))
                model.addAttribute("datesExpired", true);
            else model.addAttribute("datesExpired", false);
        } else {
            model.addAttribute("initialConversation", null);
        }
        return model;
    }

}
