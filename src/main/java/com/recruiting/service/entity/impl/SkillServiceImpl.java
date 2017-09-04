package com.recruiting.service.entity.impl;

import com.recruiting.domain.Skill;
import com.recruiting.repository.SkillRepository;
import com.recruiting.service.entity.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by Martha on 5/20/2017.
 */
@Service
@Transactional
public class SkillServiceImpl implements SkillService {

    @Autowired
    SkillRepository skillRepository;

    @Override
    public Skill findById(Long id) {
        return skillRepository.findOne(id);
    }

    @Override
    public Collection<Skill> findAll() {
        return skillRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
    }

    @Override
    public void delete(Long id) {
        skillRepository.delete(id);
    }
}
