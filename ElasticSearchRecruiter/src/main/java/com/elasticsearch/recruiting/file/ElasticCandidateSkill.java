package com.elasticsearch.recruiting.file;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Martha on 5/25/2017.
 */
public class ElasticCandidateSkill {

    private Long jpaId;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String title;
    private Integer experienceDuration;

    public ElasticCandidateSkill() {
    }

    public ElasticCandidateSkill(Long jpaId, String title, Integer experienceDuration) {
        this.jpaId = jpaId;
        this.title = title;
        this.experienceDuration = experienceDuration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExperienceDuration() {
        return experienceDuration;
    }

    public void setExperienceDuration(Integer experienceDuration) {
        this.experienceDuration = experienceDuration;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }
}
