package com.netstore.model.repository.rest;

import com.netstore.model.UserRestViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Master on 2017-09-06.
 */
public interface UserRestRepository extends JpaRepository<UserRestViewEntity,Integer>, JpaSpecificationExecutor<UserRestViewEntity> {
    List<UserRestViewEntity> findAllByIdUser(Integer id);
    UserRestViewEntity findByIdUser(Integer id);
}
