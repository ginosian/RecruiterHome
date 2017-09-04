package com.recruiting.service.entity.impl;

import com.recruiting.domain.Company;
import com.recruiting.domain.Candidate;
import com.recruiting.domain.Interview;
import com.recruiting.repository.InterviewRepository;
import com.recruiting.service.entity.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/16/2017.
 */
@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    InterviewRepository interviewRepository;

    @Override
    public Interview save(Interview interview) {
        return interviewRepository.save(interview);
    }

    @Override
    public Page<Interview> findAcceptedInterviewsByCompany(Company company,
            LocalDateTime localDateTime,
            Pageable pageable) {
        return interviewRepository.findByAcceptedTrueAndCompanyMessageAuthorAndInterviewDateAfter(company, localDateTime, pageable);
    }

    @Override
    public Page<Interview> findAcceptedInterviewsByCandidate(Candidate candidate,
            LocalDateTime localDateTime,
            Pageable pageable) {
        return interviewRepository.findByAcceptedTrueAndCandidateResponseAuthorAndInterviewDateAfter(candidate, localDateTime, pageable);
    }
}
