package com.recruiting.model.searchModel;

import com.recruiting.domain.Certifications;
import com.recruiting.domain.CandidateSkill;
import com.recruiting.elastic.file.ElasticCandidate;
import com.recruiting.model.modelUtils.CheckboxListModel;
import com.recruiting.model.modelUtils.Model;
import com.recruiting.model.modelUtils.PageWrapper;
import com.recruiting.model.modelUtils.RangeModel;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by Martha on 5/25/2017.
 */
public class CandidateSearchModel extends Model implements Serializable {

    // region Instance fields
    private PageWrapper<ElasticCandidate> candidatePageWrapper;
    private Page<ElasticCandidate> candidatesPage;
    private CandidateSkill skillPlaceholder;
    private Set<String> certifications;
    private String location;
    private String timePeriod;
    private Date startingDate;
    private Date StartingDateUpperRange;
    private Integer hourlyRate;
    private CheckboxListModel<Certifications>[] certificationsDecorator;
    private RangeModel<Integer> skillExperienceRange;
    private RangeModel<Integer> hourlyRateRange;
    private String error;
    // endregion

    // region Getters and Setters

    public Page<ElasticCandidate> getCandidatesPage() {
        return candidatesPage;
    }

    public void setCandidatesPage(Page<ElasticCandidate> candidatesPage) {
        this.candidatesPage = candidatesPage;
    }

    public PageWrapper<ElasticCandidate> getCandidatePageWrapper() {
        return candidatePageWrapper;
    }

    public void setCandidatePageWrapper(PageWrapper<ElasticCandidate> candidatePageWrapper) {
        this.candidatePageWrapper = candidatePageWrapper;
    }

    public CandidateSkill getSkillPlaceholder() {
        return skillPlaceholder;
    }

    public void setSkillPlaceholder(CandidateSkill skillPlaceholder) {
        this.skillPlaceholder = skillPlaceholder;
    }

    public CheckboxListModel<Certifications>[] getCertificationsDecorator() {
        return certificationsDecorator;
    }

    public void setCertificationsDecorator(CheckboxListModel<Certifications>[] certificationsDecorator) {
        this.certificationsDecorator = certificationsDecorator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Integer getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public RangeModel<Integer> getSkillExperienceRange() {
        return skillExperienceRange;
    }

    public void setSkillExperienceRange(RangeModel<Integer> skillExperienceRange) {
        this.skillExperienceRange = skillExperienceRange;
    }

    public RangeModel<Integer> getHourlyRateRange() {
        return hourlyRateRange;
    }

    public void setHourlyRateRange(RangeModel<Integer> hourlyRateRange) {
        this.hourlyRateRange = hourlyRateRange;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Set<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<String> certifications) {
        this.certifications = certifications;
    }

    public Date getStartingDateUpperRange() {
        return StartingDateUpperRange;
    }

    public void setStartingDateUpperRange(Date startingDateUpperRange) {
        StartingDateUpperRange = startingDateUpperRange;
    }

    // endregion

    public static boolean isEmptyCandidateSkill(CandidateSkill skill) {
        return !(skill != null
                && (skill.getSkill() != null && skill.getSkill().getId() != null));
    }

    public boolean requiredFieldsAreEmpty() {
        return
                skillPlaceholder == null
                        || skillPlaceholder.getSkill() == null
                        || skillPlaceholder.getExperienceDuration() == null
                        || hourlyRate == null
                        || timePeriod == null
                        || location == null;
    }


}
