package com.recruiting.service.impl;

import com.recruiting.domain.*;
import com.recruiting.service.CommunicationService;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.email.EmailService;
import com.recruiting.service.entity.ConversationService;
import com.recruiting.service.entity.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/19/2017.
 */
@Service
@PropertySource("classpath:texts.properties")
public class CommunicationServiceImpl implements CommunicationService {

    @Value("${interview.company.message.title}")
    protected String interview_company_message_title;

    @Value("${interview.reject.text}")
    protected String interview_reject_text;

    @Value("${interview.accept.text}")
    protected String interview_accept_text;


    @Qualifier("javaEmailService")
    @Autowired
    EmailService emailService;

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    InterviewService interviewService;

    @Autowired
    ConversationService conversationService;

    @Override
    public Conversation sendInvitationForInterview(Conversation conversation) {
        Message message = conversation.getInterview().getCompanyMessage();
        message.setSeen(false);
        message.setTitle(interview_company_message_title);
        message.setAuthor(conversation.getCompany());
        message.setReceiver(conversation.getCandidate());


        Interview interview = conversation.getInterview();
        interview.setAccepted(false);
        interview.setRejected(false);
        interview.setCompanyMessage(message);

        conversation.setInterview(interview);
        conversation.setLastMessage(message);
        conversation = conversationService.save(conversation);

        message.setConversation(conversation);
        message = conversationService.save(message);

        interview.setCompanyMessage(message);
        interviewService.save(interview);

        return conversation;
    }

    @Override
    public Conversation responseToInvitationForInterview(Conversation conversation, boolean rejected) {
        Message message = conversation.getInterview().getCandidateResponse();
        if (rejected) message.setTitle(interview_reject_text);
        else message.setTitle(interview_accept_text);
        message.setAuthor(conversation.getCandidate());
        message.setReceiver(conversation.getCompany());
        message.setSeen(false);
        message = conversationService.save(message);

        Interview interview = conversation.getInterview();
        interview.setCandidateResponse(message);
        interview.setRejected(rejected);
        interview.setAccepted(!rejected);
        if (rejected) {
            interview.setInterviewDate(null);
            interview.setInterviewDateOptional1(null);
            interview.setInterviewDateOptional2(null);
        } else {
            interview.setInterviewDateOptional1(null);
            interview.setInterviewDateOptional2(null);
        }

        conversation.setInterview(interview);
        conversation.setLastMessage(message);
        message.setConversation(conversation);
        conversation = conversationService.save(conversation);


        interview.setCandidateResponse(message);
        interviewService.save(interview);
        return conversation;
    }

    @Override
    public Conversation updateCommunication(Conversation conversation, Message message, User author, User receiver) {
        message.setSeen(false);
        message.setAuthor(author);
        message.setReceiver(receiver);
        message.setConversation(conversation);
        conversation.setLastMessage(message);
        conversation = conversationService.save(conversation);

        return conversation;
    }

    @Override
    public Conversation updateMessage(Conversation conversation) {

        conversation = conversationService.save(conversation);
        Message message = conversation.getLastMessage();
        message.setConversation(conversation);
        conversationService.save(message);

        return conversation;
    }

    @Override
    public Page<Interview> findAcceptedInterviewsByCompany(Company company,
            LocalDateTime localDateTime,
            Pageable pageable) {
        return interviewService.findAcceptedInterviewsByCompany(company, localDateTime, pageable);
    }

    @Override
    public Page<Interview> findAcceptedInterviewsByCandidate(Candidate candidate,
            LocalDateTime localDateTime,
            Pageable pageable) {
        return interviewService.findAcceptedInterviewsByCandidate(candidate, localDateTime, pageable);
    }
}
