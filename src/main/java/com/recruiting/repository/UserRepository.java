package com.recruiting.repository;

import com.recruiting.domain.User;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by Martha on 4/26/2017.
 */
@Primary
@Repository
public interface UserRepository extends BaseRepository<User> {

    User findByUsername(String username);

    Page<User> findByDtypeNotOrderByCreatedAtDesc(String dtype, Pageable pageable);

}
