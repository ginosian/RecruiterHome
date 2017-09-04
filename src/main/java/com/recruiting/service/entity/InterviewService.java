package com.recruiting.service.entity;

import com.recruiting.domain.Company;
import com.recruiting.domain.Candidate;
import com.recruiting.domain.Interview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/16/2017.
 */
public interface InterviewService {

    Interview save(Interview interview);

    Page<Interview> findAcceptedInterviewsByCompany(Company company, LocalDateTime localDateTime, Pageable pageable);

    Page<Interview> findAcceptedInterviewsByCandidate(Candidate candidate, LocalDateTime localDateTime, Pageable pageable);
}
