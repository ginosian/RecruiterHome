package com.elasticsearch.recruiting.service;

import com.elasticsearch.recruiting.file.ElasticCandidate;
import com.recruiting.model.searchModel.CandidateSearchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Created by Martha on 5/25/2017.
 */
public interface CandidateElasticService {

    void save(ElasticCandidate candidate);

    Collection<ElasticCandidate> findAll();

    Page<ElasticCandidate> searchCandidates(CandidateSearchModel candidateSearchModel, Pageable pageable);


}
