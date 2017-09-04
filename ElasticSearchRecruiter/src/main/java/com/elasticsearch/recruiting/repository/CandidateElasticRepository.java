package com.elasticsearch.recruiting.repository;

import com.elasticsearch.recruiting.file.ElasticCandidate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martha on 5/25/2017.
 */
@Repository
public interface CandidateElasticRepository extends ElasticsearchRepository<ElasticCandidate, Long> {
}
