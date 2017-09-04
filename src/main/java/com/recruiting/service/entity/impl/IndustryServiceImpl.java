package com.recruiting.service.entity.impl;

import com.recruiting.domain.Industry;
import com.recruiting.repository.IndustryRepository;
import com.recruiting.service.entity.IndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Martha on 5/20/2017.
 */
@Service
@Transactional
public class IndustryServiceImpl implements IndustryService {

    @Autowired
    IndustryRepository industryRepository;

    @Override
    public Industry findById(Long id) {
        return industryRepository.findOne(id);
    }

    @Override
    public Collection<Industry> findAll() {
        return industryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        industryRepository.delete(id);
    }

}
