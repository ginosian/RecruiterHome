package com.recruiting.repository;

import com.recruiting.domain.Certifications;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Created by Martha on 5/15/2017.
 */
@Primary
@Repository
public interface CertificationsRepository extends BaseRepository<Certifications> {
}
