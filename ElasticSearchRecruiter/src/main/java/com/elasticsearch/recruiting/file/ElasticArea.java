package com.elasticsearch.recruiting.file;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by Martha on 5/30/2017.
 */
public class ElasticArea {

    private Long jpaId;

    @Field(type = FieldType.String)
    private String title;

    public ElasticArea() {
    }

    public ElasticArea(Long jpaId, String title) {
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
