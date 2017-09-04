package com.recruiting.service;

import com.recruiting.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/19/2017.
 */
public interface CommunicationService {

    Conversation sendInvitationForInterview(Conversation conversation);

    Conversation responseToInvitationForInterview(Conversation conversation, boolean rejected);

    Conversation updateMessage(Conversation conversation);

    Conversation updateCommunication(Conversation conversation, Message message, User author, User reciever);

    Page<Interview> findAcceptedInterviewsByCompany(Company company, LocalDateTime localDateTime, Pageable pageable);

    Page<Interview> findAcceptedInterviewsByCandidate(Candidate candidate, LocalDateTime localDateTime, Pageable pageable);
}
