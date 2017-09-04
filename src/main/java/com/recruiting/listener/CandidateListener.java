package com.recruiting.listener;

import com.recruiting.domain.Candidate;
import com.recruiting.elastic.repository.CandidateElasticRepository;
import com.recruiting.utils.TypeConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Created by Martha on 5/26/2017.
 */

public class CandidateListener {

    @Autowired
    private CandidateElasticRepository repository;

    @PostPersist
    public void candidatePostPersist(Candidate ob) {

        AutowireHelper.autowire(this, this.repository);
        repository.save(TypeConversionUtils.jpaToElasticCandidate(ob));

    }
}
