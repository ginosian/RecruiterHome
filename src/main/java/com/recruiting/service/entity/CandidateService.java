package com.recruiting.service.entity;

import com.recruiting.domain.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Created by Martha on 5/22/2017.
 */
public interface CandidateService {

    Candidate findById(Long id);

    Candidate findByUsername(String username);

    Page<Candidate> findByApprovedFalse(Pageable pageable);

    Page<Candidate> findByApprovedTrue(Pageable pageable);

    Collection<Candidate> findAll();

    void delete(Long id);
}
