package com.elasticsearch.recruiting.file;

import com.recruiting.domain.AbstractEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * Created by Martha on 5/25/2017.
 */
@Document(indexName = "candidate", type = "candidate", shards = 1, replicas = 0)
public class ElasticCandidate {

    @Id
    private Long jpaId;
    private String name;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String timePeriod;
    private Integer hourlyRate;
    private String address;
    private String city;
    private String state;
    private Integer zipCode;
//    @Field(type = FieldType.Date, format = DateFormat.date, pattern = "MM-dd-yyyy")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    @Field(type = FieldType.Date, index = FieldIndex.not_analyzed)
    private Date startingDate;

    @Field(type= FieldType.Nested)
    private List<ElasticCandidateSkill> skills;

    @Field(type= FieldType.Nested)
    private List<ElasticCertifications> certifications;

    @Field(type= FieldType.Nested)
    private List<ElasticArea> areas;

    // endregion

    // region Constructors
    public ElasticCandidate() {
    }

    public ElasticCandidate(Long jpaId,
            String name,
            String timePeriod,
            Integer hourlyRate,
            String address,
            String city,
            String state,
            Integer zipCode,
            List<ElasticCandidateSkill> skills,
            List<ElasticCertifications> certifications,
            List<ElasticArea> areas,
            Date startingDate) {
        this.jpaId = jpaId;
        this.name = name;
        this.timePeriod = timePeriod;
        this.hourlyRate = hourlyRate;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.skills = skills;
        this.certifications = certifications;
        this.areas = areas;
        this.startingDate = startingDate;
    }

    // endregion

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }

    public String getFullName() {
        return name;
    }

    public void setFullName(String name) {
        this.name = name;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Integer getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Integer hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public List<ElasticCandidateSkill> getSkills() {
        return skills;
    }

    public void setSkills(List<ElasticCandidateSkill> skills) {
        this.skills = skills;
    }

    public List<ElasticCertifications> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<ElasticCertifications> certifications) {
        this.certifications = certifications;
    }

    public List<ElasticArea> getAreas() {
        return areas;
    }

    public void setAreas(List<ElasticArea> areas) {
        this.areas = areas;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    // region Overrides
    @Override
    public String toString() {
        return "Candidate [" +
                "id: " + jpaId + "\t" +
                "name: " + name + "\t" +
                "timePeriod: " + timePeriod + "\t" +
                "]";
    }

    // endregion

    // region Hashcode/equals
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof AbstractEntity)) return false;
        Long id = this.getJpaId();
        return (id != null && id.equals(((AbstractEntity) obj).getId())) || super.equals(obj);
    }

    @Override
    public int hashCode() {
        int result = getJpaId() != null ? getJpaId().hashCode() : 0;
        result = 31 * result + (getJpaId() != null ? getJpaId().hashCode() : 0);
        return result;
    }
    // endregion
}
