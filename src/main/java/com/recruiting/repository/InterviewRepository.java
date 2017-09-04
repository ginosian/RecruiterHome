package com.recruiting.repository;

import com.recruiting.domain.Interview;
import com.recruiting.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Created by Martha on 6/16/2017.
 */
public interface InterviewRepository extends BaseRepository<Interview> {

    Page<Interview> findByAcceptedTrueAndCompanyMessageAuthorAndInterviewDateAfter(User company, LocalDateTime localDateTime, Pageable pageable);

    Page<Interview> findByAcceptedTrueAndCandidateResponseAuthorAndInterviewDateAfter(User candidate, LocalDateTime localDateTime, Pageable pageable);
}
