package com.recruiting.service.entity.impl;

import com.recruiting.domain.Company;
import com.recruiting.repository.CompanyRepository;
import com.recruiting.service.entity.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Martha on 5/22/2017.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company findById(Long id) {
        return companyRepository.findOne(id);
    }

    @Override
    public Company findByUsername(String username) {
        return companyRepository.findByUsername(username);
    }

    @Override
    public Company findByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public Page<Company> findByApprovedFalse(Pageable pageable) {
        return companyRepository.findByApprovedFalse(pageable);
    }

    @Override
    public Page<Company> findByApprovedTrue(Pageable pageable) {
        return companyRepository.findByApprovedTrue(pageable);
    }

    @Override
    public Collection<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        companyRepository.delete(id);
    }
}
