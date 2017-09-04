package com.recruiting.service.entity.impl;

import com.recruiting.domain.State;
import com.recruiting.repository.StateRepository;
import com.recruiting.service.entity.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Martha on 5/20/2017.
 */
@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateRepository stateRepository;

    @Override
    public State findById(Long id) {
        return stateRepository.findOne(id);
    }

    @Override
    public Collection<State> findAll() {
        return stateRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
    }

    @Override
    public void delete(Long id) {
        stateRepository.delete(id);
    }
}
