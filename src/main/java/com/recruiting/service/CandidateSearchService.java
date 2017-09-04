package com.recruiting.service;

import com.recruiting.domain.Candidate;
import com.recruiting.domain.Company;
import com.recruiting.domain.Interview;
import com.recruiting.model.searchModel.CandidateSearchModel;

/**
 * Created by Martha on 5/31/2017.
 */
public interface CandidateSearchService {

    CandidateSearchModel createCandidateSearchModel();

    Interview findInterview(Company company, Candidate candidate);
}
