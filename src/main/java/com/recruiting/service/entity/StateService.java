package com.recruiting.service.entity;

import com.recruiting.domain.State;

import java.util.Collection;

/**
 * Created by Martha on 5/20/2017.
 */
public interface StateService {

    State findById(Long id);

    Collection<State> findAll();

    void delete(Long id);
}
