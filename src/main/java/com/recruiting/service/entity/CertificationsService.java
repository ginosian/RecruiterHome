package com.recruiting.service.entity;

import com.recruiting.domain.Certifications;

import java.util.Collection;

/**
 * Created by Martha on 5/31/2017.
 */
public interface CertificationsService {

    Certifications findById(Long id);

    Collection<Certifications> findAll();

    void delete(Long id);
}
