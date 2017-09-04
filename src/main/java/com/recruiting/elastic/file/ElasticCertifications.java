package com.recruiting.elastic.file;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Martha on 9/2/2017.
 */
public class ElasticCertifications {
    private Long jpaId;

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String title;

    public ElasticCertifications() {
    }

    public ElasticCertifications(Long jpaId, String title) {
        this.jpaId = jpaId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }
}
