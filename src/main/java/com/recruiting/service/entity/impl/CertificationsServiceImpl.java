package com.recruiting.service.entity.impl;

import com.recruiting.domain.Certifications;
import com.recruiting.repository.CertificationsRepository;
import com.recruiting.service.entity.CertificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by Martha on 5/31/2017.
 */
@Service
public class CertificationsServiceImpl implements CertificationsService {

    @Autowired
    CertificationsRepository accountingCertificationsRepository;

    @Override
    public Certifications findById(Long id) {
        return accountingCertificationsRepository.findOne(id);
    }

    @Override
    public Collection<Certifications> findAll() {
        return accountingCertificationsRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
    }

    @Override
    public void delete(Long id) {

    }
}
