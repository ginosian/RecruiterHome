package com.recruiting.service.entity.impl;

import com.recruiting.domain.Candidate;
import com.recruiting.repository.CandidateRepository;
import com.recruiting.service.entity.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Martha on 5/22/2017.
 */
@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Override
    public Candidate findById(Long id) {
        return candidateRepository.findOne(id);
    }

    @Override
    public Candidate findByUsername(String username) {
        return candidateRepository.findByUsername(username);
    }

    @Override
    public Page<Candidate> findByApprovedFalse(Pageable pageable) {
        return candidateRepository.findByApprovedFalse(pageable);
    }

    @Override
    public Page<Candidate> findByApprovedTrue(Pageable pageable) {
        return candidateRepository.findByApprovedTrue(pageable);
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        candidateRepository.delete(id);
    }
}
