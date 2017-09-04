package com.recruiting.service.entity;

import com.recruiting.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * Created by Martha on 5/22/2017.
 */
public interface CompanyService {

    Company findById(Long id);

    Company findByUsername(String username);

    Company findByName(String name);

    Page<Company> findByApprovedFalse(Pageable pageable);

    Page<Company> findByApprovedTrue(Pageable pageable);

    Collection<Company> findAll();

    void delete(Long id);
}
