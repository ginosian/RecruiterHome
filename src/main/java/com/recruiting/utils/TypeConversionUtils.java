package com.recruiting.utils;

import com.recruiting.domain.*;
import com.recruiting.elastic.file.ElasticCertifications;
import com.recruiting.elastic.file.ElasticArea;
import com.recruiting.elastic.file.ElasticCandidate;
import com.recruiting.elastic.file.ElasticCandidateSkill;
import com.recruiting.model.modelUtils.CheckboxListModel;

import java.util.*;

/**
 * Created by Martha on 6/9/2017.
 */
public class TypeConversionUtils {

    public static CheckboxListModel[] collectionToArrayForCheckbox(Collection<? extends AbstractTitledEntity> collection) {
        if (collection == null || collection.size() == 0) return null;
        Iterator iterator = collection.iterator();
        CheckboxListModel[] result = new CheckboxListModel[collection.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = new CheckboxListModel((AbstractTitledEntity) iterator.next(), false);
        }
        return result;
    }

    public static Collection<AbstractTitledEntity> pickActiveItemsFromCheckbox(CheckboxListModel<? extends AbstractTitledEntity>[] checkboxListModel) {
        Collection<AbstractTitledEntity> result = new HashSet<>();
        if (checkboxListModel == null || checkboxListModel.length == 0) return null;
        for (CheckboxListModel model : checkboxListModel) {
            AbstractTitledEntity abstractEntity = model.getModel();
            if (abstractEntity != null && model.isActive()) {
                result.add(abstractEntity);
            }
        }
        return result;
    }

    public static Collection<String> pickActiveItemsTitlesFromCheckbox(CheckboxListModel<? extends AbstractTitledEntity>[] checkboxListModel) {
        Collection<String> result = new HashSet<>();
        if (checkboxListModel == null || checkboxListModel.length == 0) return null;
        for (CheckboxListModel model : checkboxListModel) {
            AbstractTitledEntity abstractEntity = model.getModel();
            if (abstractEntity != null && model.isActive()) {
                result.add(abstractEntity.getTitle());
            }
        }
        return result;
    }

    public static ElasticCandidate jpaToElasticCandidate(Candidate candidate) {
        List<ElasticCertifications> accountingCertifications = new ArrayList<>();
        List<ElasticCandidateSkill> candidateSkills = new ArrayList<>();
        List<ElasticArea> areas = new ArrayList<>();

        for (Certifications certifications : candidate.getCertifications()) {
            accountingCertifications.add(new ElasticCertifications(certifications.getId(), certifications.getTitle()));
        }

        for (CandidateSkill skill : candidate.getSkills()) {
            candidateSkills.add(new ElasticCandidateSkill(skill.getId(), skill.getSkill().getTitle(), skill.getExperienceDuration()));
        }

        for (com.recruiting.domain.Area area : candidate.getAreas()) {
            areas.add(new ElasticArea(area.getId(), area.getTitle()));
        }

        String name = buildReducedFullName(candidate.getName());
        Address address = candidate.getAddress();

        ElasticCandidate elasticCandidate = new ElasticCandidate(
                candidate.getId(),
                name,
                candidate.getTimePeriod(),
                candidate.getHourlyRate(),
                address.getAddress(),
                address.getCity(),
                address.getState().getTitle(),
                address.getZipCode(),
                candidateSkills,
                accountingCertifications,
                areas,
                candidate.getStartingDate()
        );
        return elasticCandidate;
    }

    public static String buildReducedFullName(String name) {
        String[] nameArray = name.split(" ");
        if (nameArray.length <= 1) return name;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(nameArray[1]).append(" ");
        for (int i = 0; i < nameArray.length; i++) {
            if (i == 1) continue;
            stringBuilder.append(nameArray[i].charAt(0)).append(".");
        }
        return stringBuilder.toString();
    }
}
