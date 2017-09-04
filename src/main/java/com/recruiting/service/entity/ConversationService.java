package com.recruiting.service.entity;

import com.recruiting.domain.Candidate;
import com.recruiting.domain.Company;
import com.recruiting.domain.Conversation;
import com.recruiting.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Martha on 6/20/2017.
 */
public interface ConversationService {

    Conversation findById(Long id);

    Page<Conversation> findAllByCompany(Pageable pageable, Company company);

    Page<Conversation> findAllByCandidate(Pageable pageable, Candidate candidate);

    Conversation findByCompanyAndCandidate(Company company, Candidate candidate);

    Page<Message> findByConversation(Conversation conversation, Pageable pageable);

    Conversation save(Conversation conversation);

    Message save(Message message);


}
