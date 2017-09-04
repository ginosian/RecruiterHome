package com.elasticsearch.recruiting.service.impl;


import com.elasticsearch.recruiting.file.ElasticCandidate;
import com.elasticsearch.recruiting.repository.CandidateElasticRepository;
import com.elasticsearch.recruiting.repository.CandidateElasticSearchRepository;
import com.elasticsearch.recruiting.service.CandidateElasticService;
import com.recruiting.model.modelUtils.RangeModel;
import com.recruiting.model.searchModel.CandidateSearchModel;
import com.recruiting.utils.TypeConversionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Martha on 5/25/2017.
 */
@Service
@PropertySource("classpath:search.properties")
public class CandidateElasticServiceImpl implements CandidateElasticService {

    @Value("${skill.experience.min.rate}")
    int skill_experience_min;

    @Value("${skill.experience.max.rate}")
    int skill_experience_max;

    @Value("${starting.date.max.rate}")
    int starting_date_max;

    @Value("${hourly.rate.min.rate}")
    int hourly_rate_min;

    @Value("${hourly.rate.max.rate}")
    int hourly_rate_max;


    @Autowired
    private CandidateElasticRepository repository;

    @Autowired
    private CandidateElasticSearchRepository candidateElasticSearchRepository;

    @Override
    public void save(ElasticCandidate candidate) {
        repository.save(candidate);
    }

    @Override
    public Collection<ElasticCandidate> findAll() {
        Iterable<ElasticCandidate> candidates =  repository.findAll();
        List<ElasticCandidate> candidatesList = new ArrayList<>();
        candidates.forEach(candidatesList :: add);
        return candidatesList;
    }

    @Override
    public Page<ElasticCandidate> searchCandidates(CandidateSearchModel candidateSearchModel, Pageable pageable) {
        if(candidateSearchModel == null || candidateSearchModel.requiredFieldsAreEmpty()) return new PageImpl<>(new ArrayList<>());
        if (pageable == null) pageable = new PageRequest(0, 10);

        RangeModel<Integer> skillExperienceRange = new RangeModel<>(skill_experience_min, skill_experience_max, candidateSearchModel.getSkillPlaceholder().getExperienceDuration());
        RangeModel<Integer> hourlyRateRange = new RangeModel<>(hourly_rate_min, hourly_rate_max, candidateSearchModel.getHourlyRate());

        candidateSearchModel.setSkillExperienceRange(skillExperienceRange);
        candidateSearchModel.setHourlyRateRange(hourlyRateRange);
        candidateSearchModel.setCertifications((Set)TypeConversionUtils.pickActiveItemsTitlesFromCheckbox(candidateSearchModel.getCertificationsDecorator()));


        Date dt = new Date();
        DateTime dtOrg = new DateTime(dt);
        DateTime dtPlusOne = dtOrg.plusDays(starting_date_max);

        candidateSearchModel.setStartingDateUpperRange(dtPlusOne.toDate());


        return candidateElasticSearchRepository.searchCandidates(candidateSearchModel, pageable);

//        return repository.findAll(pageable);
    }

}
