package com.recruiting.service.entity;

import com.recruiting.domain.AbstractEntity;

/**
 * Created by Martha on 4/25/2017.
 * Is to be used only for initially easily loading data, can not be used in development.
 */
public interface GenericCrudService<T extends AbstractEntity> {

    T save(T entity);

    T update(T entity);

    void delete(T entity);
}
