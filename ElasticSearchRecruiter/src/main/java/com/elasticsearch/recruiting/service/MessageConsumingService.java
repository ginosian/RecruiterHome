package com.elasticsearch.recruiting.service;

import com.elasticsearch.recruiting.file.ElasticCandidate;

/**
 * Created by Martha on 9/2/2017.
 */
public interface MessageConsumingService {

    ElasticCandidate consumeAndSavCandidate();

}
