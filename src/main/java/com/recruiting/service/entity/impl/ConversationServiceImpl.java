package com.recruiting.service.entity.impl;

import com.recruiting.domain.Company;
import com.recruiting.domain.Candidate;
import com.recruiting.domain.Conversation;
import com.recruiting.domain.Message;
import com.recruiting.repository.ConversationRepository;
import com.recruiting.repository.InterviewRepository;
import com.recruiting.repository.MessageRepository;
import com.recruiting.service.entity.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Martha on 6/20/2017.
 */
@Service
public class ConversationServiceImpl implements ConversationService {

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    InterviewRepository interviewRepository;

    @Override
    public Conversation findById(Long id) {
        return conversationRepository.findOne(id);
    }

    @Override
    public Page<Conversation> findAllByCompany(Pageable pageable, Company company) {
        return conversationRepository
                .findByCompanyAndRemovedByCompanyFalseOrderByLastMessageCreatedAtDesc(company, pageable);
    }

    @Override
    public Page<Conversation> findAllByCandidate(Pageable pageable, Candidate candidate) {
        return conversationRepository.findByCandidateAndRemovedByCandidateFalseOrderByLastMessageCreatedAtDesc(
                candidate, pageable);
    }

    @Override
    public Conversation findByCompanyAndCandidate(Company company, Candidate candidate) {
        return conversationRepository.findConversationByCompanyAndCandidate(company, candidate);
    }

    @Override
    public Page<Message> findByConversation(Conversation conversation, Pageable pageable) {
        return messageRepository.findByConversationOrderByCreatedAtDesc(conversation, pageable);
    }

    @Override
    public Conversation save(Conversation conversation) {
        return conversationRepository.save(conversation);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

}
