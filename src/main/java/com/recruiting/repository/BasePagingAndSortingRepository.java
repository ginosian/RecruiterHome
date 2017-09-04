package com.recruiting.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Martha on 6/19/2017.
 */
@NoRepositoryBean
public interface BasePagingAndSortingRepository<T> extends PagingAndSortingRepository<T, Long> {

}
