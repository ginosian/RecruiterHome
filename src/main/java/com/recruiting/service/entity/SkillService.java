package com.recruiting.service.entity;

import com.recruiting.domain.Skill;

import java.util.Collection;

/**
 * Created by Martha on 5/20/2017.
 */
public interface SkillService {

    Skill findById(Long id);

    Collection<Skill> findAll();

    void delete(Long id);
}
