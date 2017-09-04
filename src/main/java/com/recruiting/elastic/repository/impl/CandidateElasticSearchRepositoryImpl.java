package com.recruiting.elastic.repository.impl;


import com.recruiting.elastic.file.ElasticCandidate;
import com.recruiting.elastic.repository.CandidateElasticSearchRepository;
import com.recruiting.model.modelUtils.RangeModel;
import com.recruiting.model.searchModel.CandidateSearchModel;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import static org.elasticsearch.index.query.MatchQueryBuilder.Operator.OR;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by Martha on 6/6/2017.
 */
@Repository
@PropertySource("classpath:search.properties")
public class CandidateElasticSearchRepositoryImpl implements CandidateElasticSearchRepository {

    @Value("${search_criteria_degrade_rate}")
    int search_criteria_degrade_rate;

    @Value("${search_criteria_degrade_quantity}")
    int search_criteria_degrade_quantity;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public Page<ElasticCandidate> searchCandidates(CandidateSearchModel model, Pageable pageable) {

        if (model == null && model.getSkillPlaceholder() == null) return null;


        RangeModel<Integer> rangeExperience = model.getSkillExperienceRange();
        RangeModel<Integer> rangeHourlyRate = model.getHourlyRateRange();
        Page<ElasticCandidate> sampleEntities = search(model, pageable);

        if (sampleEntities.getContent().size() == 0) {
            int quantity = search_criteria_degrade_quantity;
            while (sampleEntities.getContent().size() == 0 && quantity > 0) {
                rangeExperience.setChangeRate(rangeExperience.getChangeRate() + search_criteria_degrade_rate);
                rangeHourlyRate.setChangeRate(rangeHourlyRate.getChangeRate() + search_criteria_degrade_rate);
                search(model, pageable);
                --quantity;
            }
            return sampleEntities;
        }
        return sampleEntities;
    }

    private Page<ElasticCandidate> search(CandidateSearchModel model, Pageable pageable) {
        // region Prepare data
        // Prepares skill
        Integer experienceDuration = model.getSkillPlaceholder()
                .getExperienceDuration();
        String title = model.getSkillPlaceholder()
                .getSkill()
                .getTitle();
        RangeModel<Integer> rangeExperience = model.getSkillExperienceRange();
        Integer experienceUpperLimit = rangeExperience.getUpperLimit();
        Integer experienceLowerLimit = rangeExperience.getLowerLimit();

        // Prepares location
        String location = model.getLocation();

        // Prepare hourly rate
        Integer hourlyRate = model.getHourlyRate();
        RangeModel<Integer> rangeHourlyRate = model.getHourlyRateRange();
        Integer hourlyRateUpperLimit = rangeHourlyRate.getUpperLimit();
        Integer hourlyRateLowerLimit = rangeHourlyRate.getLowerLimit();

        // Prepares certifications
        Set<String> certifications = model.getCertifications();
        if (certifications != null && certifications.size() != 0) {
            Iterator iterator = certifications.iterator();
            String[] certificationsArray = new String[certifications.size()];
            for (int i = 0; i < certificationsArray.length; i++) {
                certificationsArray[i] = (String) iterator.next();
            }
        }

        // Prepares Starting date  postponed !!
//            Long startingDate = model.getStartingDate().getTime();
//            Long startingDateUpperLimit = model.getStartingDateUpperRange().getTime();

        // Prepares time period
        String timePeriod = model.getTimePeriod();
        // endregion

        // region Build query
        SearchQuery searchQuery;


        if (certifications != null && certifications.size() != 0)
            searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())


                    .withQuery(
                            boolQuery()
                                    .must(queryForMustSkill(title))
                                    .should(queryForExactValueSkill(title, experienceDuration))
                                    .should(queryForExactValueHourlyRate(hourlyRate))
                                    .should(queryForFuzzyHourlyRate(hourlyRate))
                                    .should(queryForFuzzySkill(title, experienceDuration))
                                    .should(queryForRangeSkill(title, experienceLowerLimit, experienceUpperLimit))
                                    .should(queryForRangeHourlyRate(hourlyRateLowerLimit, hourlyRateUpperLimit))
                                    .must(queryForLocation(location))
//                        .must(queryForStartingDay(startingDate, startingDateUpperLimit))
                                    .must(queryForTimePeriod(timePeriod))
                                    .must(queryForCertifications(certifications)))
                    .withPageable(pageable)
                    .withFilter(queryForMustSkill(title))
                    .withFilter(queryForExactValueSkill(title, experienceDuration))
                    .withFilter(queryForExactValueHourlyRate(hourlyRate))
                    .withFilter(queryForFuzzyHourlyRate(hourlyRate))
                    .withFilter(queryForFuzzySkill(title, experienceDuration))
                    .withFilter(queryForRangeSkill(title, experienceLowerLimit, experienceUpperLimit))
                    .withFilter(queryForRangeHourlyRate(hourlyRateLowerLimit, hourlyRateUpperLimit))
                    .withFilter(queryForLocation(location))
                    .withFilter(queryForTimePeriod(timePeriod))
                    .withFilter(queryForCertifications(certifications))
//                        .withSort(SortBuilders.fieldSort("hourlyRate")
//                                .order(SortOrder.ASC))
                    .build();

        else


            searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())


                    .withQuery(
                            boolQuery()
                                    .must(queryForMustSkill(title))
                                    .should(queryForExactValueSkill(title, experienceDuration))
                                    .should(queryForExactValueHourlyRate(hourlyRate))
                                    .should(queryForFuzzyHourlyRate(hourlyRate))
                                    .should(queryForFuzzySkill(title, experienceDuration))
                                    .should(queryForRangeSkill(title, experienceLowerLimit, experienceUpperLimit))
                                    .should(queryForRangeHourlyRate(hourlyRateLowerLimit, hourlyRateUpperLimit))
                                    .must(queryForLocation(location))
//                                      .must(queryForStartingDay(startingDate, startingDateUpperLimit))
                                    .must(queryForTimePeriod(timePeriod)))
                    .withPageable(pageable)
                    .withFilter(queryForMustSkill(title))
                    .withFilter(queryForExactValueSkill(title, experienceDuration))
                    .withFilter(queryForExactValueHourlyRate(hourlyRate))
                    .withFilter(queryForFuzzyHourlyRate(hourlyRate))
                    .withFilter(queryForFuzzySkill(title, experienceDuration))
                    .withFilter(queryForRangeSkill(title, experienceLowerLimit, experienceUpperLimit))
                    .withFilter(queryForRangeHourlyRate(hourlyRateLowerLimit, hourlyRateUpperLimit))
                    .withFilter(queryForLocation(location))
                    .withFilter(queryForTimePeriod(timePeriod))
//                        .withSort(SortBuilders.fieldSort("hourlyRate")
//                                .order(SortOrder.ASC))
                    .build();

        // endregion

        // region Test
        Page<ElasticCandidate> queryForExactValueSkill = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForExactValueSkill(title, experienceDuration))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForExactValueHourlyRate = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForExactValueHourlyRate(hourlyRate))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForFuzzySkill = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForFuzzySkill(title, experienceDuration))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForFuzzyHourlyRate = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForFuzzyHourlyRate(hourlyRate))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForRangeSkill = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForRangeSkill(title, experienceLowerLimit, experienceUpperLimit))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForRangeHourlyRate = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForRangeHourlyRate(hourlyRateLowerLimit, hourlyRateUpperLimit))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForLocation = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForLocation(location))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForTimePeriod = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForTimePeriod(timePeriod))
                .build(), ElasticCandidate.class);
        Page<ElasticCandidate> queryForCertifications = elasticsearchTemplate.queryForPage(new NativeSearchQueryBuilder().withQuery(
                queryForCertifications(certifications))
                .build(), ElasticCandidate.class);
        // endregion


        Page<ElasticCandidate> sampleEntities = elasticsearchTemplate.queryForPage(searchQuery,
                ElasticCandidate.class);
        return sampleEntities;
    }

    private QueryBuilder queryForMustSkill(String title) {
        QueryBuilder queryForSkill = QueryBuilders.nestedQuery("skills",
                boolQuery()
                        .must(
                                termQuery("skills.title", title))
        );
        return queryForSkill;
    }

    private QueryBuilder queryForExactValueSkill(String title, Integer experienceDuration) {
        QueryBuilder queryForSkill = QueryBuilders.nestedQuery("skills",
                boolQuery()
                        .must(
                                termQuery("skills.title", title))
                        .must(
                                matchQuery("skills.experienceDuration", experienceDuration))
        );
        return queryForSkill;
    }

    private QueryBuilder queryForFuzzySkill(String title, Integer experienceDuration) {
        QueryBuilder queryForSkill = QueryBuilders.nestedQuery("skills",
                boolQuery()
                        .must(
                                termQuery("skills.title", title))
                        .must(
                                fuzzyQuery("skills.experienceDuration", experienceDuration).fuzziness(Fuzziness.AUTO))
        );
        return queryForSkill;
    }

    // Tested !
    private QueryBuilder queryForRangeSkill(String title,
            Integer lowerLimit,
            Integer upperLimit) {
        QueryBuilder queryForSkill = QueryBuilders.nestedQuery("skills",
                boolQuery()
                        .must(
                                termQuery("skills.title", title))
                        .must(
                                boolQuery()
                                        .should(
                                                rangeQuery("skills.experienceDuration").gte(lowerLimit)
                                                        .lte(upperLimit)

                                        )
                        )

        );
        FieldSortBuilder test = SortBuilders.fieldSort("skills.experienceDuration")
                .order(SortOrder.ASC)
                .setNestedPath("skills")
                .setNestedFilter(queryForSkill);
        return queryForSkill;
    }

    // Tested
    private QueryBuilder queryForLocation(String location) {
        QueryBuilder queryForLocation = QueryBuilders
                .boolQuery()
                .must(
                        boolQuery()
                                .should(
                                        nestedQuery("areas", queryStringQuery("North Bay")
                                                .defaultField("areas.title")
                                                .fuzziness(Fuzziness.AUTO)))
                                .should(
                                        multiMatchQuery(location,
                                                "state",
                                                "city")
                                                .operator(OR)
                                                .fuzziness(Fuzziness.AUTO)
                                                .prefixLength(0)
                                )
                );
        return queryForLocation;
    }

    private QueryBuilder queryForExactValueHourlyRate(Integer hourlyRate) {
        QueryBuilder queryForSkill = QueryBuilders.nestedQuery("skills",
                boolQuery()
                        .should(
                                matchQuery("hourlyRate", hourlyRate))
        );
        return queryForSkill;
    }

    private QueryBuilder queryForFuzzyHourlyRate(Integer hourlyRate) {
        QueryBuilder queryForSkill = QueryBuilders.nestedQuery("skills",
                boolQuery()
                        .should(
                                fuzzyQuery("hourlyRate", hourlyRate).fuzziness(Fuzziness.AUTO))
        );
        return queryForSkill;
    }

    // Tested
    private QueryBuilder queryForRangeHourlyRate(Integer lowerLimit, Integer upperLimit) {

        QueryBuilder queryForSkill = QueryBuilders.boolQuery()
                .should(
                        rangeQuery("hourlyRate").gte(lowerLimit)
                                .lte(upperLimit)
                );
        return queryForSkill;
    }

    // Tested
    private QueryBuilder queryForCertifications(Set<String> certificationsSet) {

        QueryBuilder queryForCertifications = QueryBuilders.nestedQuery("certifications", boolQuery()
                .should(termsQuery("certifications.title", certificationsSet)));
        return queryForCertifications;
    }

    private QueryBuilder queryForStartingDay(Date date, Date upperRange) {
        QueryBuilder queryForStartingDate = QueryBuilders.boolQuery()
                .should(
                        matchQuery("startingDate", date))
                .should(
                        rangeQuery("startingDate").lte(upperRange)
                                .includeLower(true));
        return queryForStartingDate;
    }

    // Tested
    private QueryBuilder queryForStartingDay(Long date, Long upperRange) {

        QueryBuilder queryForStartingDate = QueryBuilders.boolQuery()
                .should(
                        fuzzyQuery("startingDate", date))
                .should(
                        rangeQuery("startingDate").gte(date)
                                .lte(upperRange)
                                .includeLower(true));
        return queryForStartingDate;
    }


    // Tested
    private QueryBuilder queryForTimePeriod(String timePeriod) {
        QueryBuilder queryForTimePeriod = QueryBuilders.boolQuery()
                .must(termQuery("timePeriod", timePeriod));
        return queryForTimePeriod;
    }

}
