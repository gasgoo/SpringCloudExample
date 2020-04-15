package com.client.repository;

import com.client.domain.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @TODO
 * @Date 2019/5/13 19:26
 */
@Repository
public interface UserResp extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {

}