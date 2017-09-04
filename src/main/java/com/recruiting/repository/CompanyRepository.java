package com.recruiting.repository;

import com.recruiting.domain.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Martha on 5/8/2017.
 */

public interface CompanyRepository extends BaseRepository<Company> {

    Company findByName(String name);

    Company findByUsername(String username);

    Page<Company> findByApprovedFalse(Pageable pageable);

    Page<Company> findByApprovedTrue(Pageable pageable);

}
