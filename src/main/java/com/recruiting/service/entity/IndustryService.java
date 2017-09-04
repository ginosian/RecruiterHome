package com.recruiting.service.entity;

import com.recruiting.domain.Industry;

import java.util.Collection;

/**
 * Created by Martha on 5/20/2017.
 */
public interface IndustryService {

    Industry findById(Long id);

    Collection<Industry> findAll();

    void delete(Long id);

}
