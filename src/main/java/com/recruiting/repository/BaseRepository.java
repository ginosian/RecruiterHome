package com.recruiting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Martha on 4/5/2017.
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, Long> {

}
