package com.elasticsearch.recruiting.repository;

import com.elasticsearch.recruiting.file.ElasticCandidate;
import com.recruiting.model.searchModel.CandidateSearchModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Martha on 6/6/2017.
 */
public interface CandidateElasticSearchRepository {

   Page<ElasticCandidate> searchCandidates(CandidateSearchModel model, Pageable pageable);
}
