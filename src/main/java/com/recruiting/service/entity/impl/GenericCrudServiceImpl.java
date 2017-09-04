package com.recruiting.service.entity.impl;

import com.recruiting.domain.AbstractEntity;
import com.recruiting.repository.AbstractRepository;
import com.recruiting.service.entity.GenericCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Martha on 4/25/2017.
 */
@Service
@Transactional
public class GenericCrudServiceImpl implements GenericCrudService {

    @Autowired
    AbstractRepository abstractRepository;

    @Override
    public AbstractEntity save(AbstractEntity entity) {
        return abstractRepository.save(entity);
    }

    @Override
    public AbstractEntity update(AbstractEntity entity) {
        return null;
    }

    @Override
    public void delete(AbstractEntity entity) {
        abstractRepository.delete(entity);
    }
}
