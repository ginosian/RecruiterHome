package com.recruiting.repository;

import com.recruiting.domain.Candidate;
import com.recruiting.domain.Company;
import com.recruiting.domain.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/19/2017.
 */
public interface ConversationRepository extends BasePagingAndSortingRepository<Conversation> {

    Page<Conversation> findByCompanyAndRemovedByCompanyFalseOrderByLastMessageCreatedAtDesc(Company company, Pageable pageable);

    Page<Conversation> findByCandidateAndRemovedByCandidateFalseOrderByLastMessageCreatedAtDesc(Candidate candidate, Pageable pageable);

    Conversation findConversationByCompanyAndCandidate(Company company, Candidate candidate);

    Page<Conversation> findByInterviewInterviewDateAfterAndCompany(LocalDateTime localDateTime, Company company, Pageable pageable);

    Page<Conversation> findByInterviewInterviewDateAfterAndCandidate(LocalDateTime localDateTime, Candidate candidate, Pageable pageable);


}
