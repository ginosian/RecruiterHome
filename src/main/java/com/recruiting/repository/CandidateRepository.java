package com.recruiting.repository;

import com.recruiting.domain.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Martha on 5/15/2017.
 */

public interface CandidateRepository extends BaseRepository<Candidate> {

    Candidate findByUsername(String username);

    Page<Candidate> findByApprovedFalse(Pageable pageable);

    Page<Candidate> findByApprovedTrue(Pageable pageable);

}
