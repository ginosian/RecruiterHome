package com.recruiting.repository;

import com.recruiting.domain.Authority;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Martha on 5/14/2017.
 */
@Primary
@Repository
public interface AuthorityRepository extends BaseRepository<Authority> {

    @Query("SELECT a FROM Authority a WHERE a.role =:role")
    Authority findByRole(@Param("role") String role);
}
