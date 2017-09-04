package com.recruiting.service.impl;

import com.recruiting.domain.Certifications;
import com.recruiting.domain.Candidate;
import com.recruiting.domain.Company;
import com.recruiting.domain.Interview;
import com.recruiting.model.modelUtils.CheckboxListModel;
import com.recruiting.model.searchModel.CandidateSearchModel;
import com.recruiting.service.CandidateSearchService;
import com.recruiting.service.EmptyEntityCreationService;
import com.recruiting.service.entity.ConversationService;
import com.recruiting.service.entity.CertificationsService;
import com.recruiting.utils.TypeConversionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Martha on 5/31/2017.
 */
@Service
public class CandidateSearchServiceImpl implements CandidateSearchService {

    @Autowired
    EmptyEntityCreationService emptyEntityCreationService;

    @Autowired
    CertificationsService certificationsService;

    @Autowired
    ConversationService conversationService;

    @Override
    public CandidateSearchModel createCandidateSearchModel() {
        CandidateSearchModel candidateSearchModel = new CandidateSearchModel();
        candidateSearchModel.setSkillPlaceholder(emptyEntityCreationService.emptyCandidateSkill());

        CheckboxListModel<Certifications>[] certificationsDecorator = TypeConversionUtils.collectionToArrayForCheckbox(certificationsService.findAll());
        candidateSearchModel.setCertificationsDecorator(certificationsDecorator);

//        Date date = new Date();
//        candidateSearchModel.setStartingDate(date);

        return candidateSearchModel;
    }

    @Override
    public Interview findInterview(Company company, Candidate candidate) {
        return conversationService.findByCompanyAndCandidate(company, candidate).getInterview();
    }
}
