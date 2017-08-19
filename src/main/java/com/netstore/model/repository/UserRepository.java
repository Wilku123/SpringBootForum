package com.netstore.model.repository;

import com.netstore.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Master on 2017-05-21.
 */
public interface UserRepository extends JpaRepository<UserEntity,Integer>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findAllByIdUser(Integer id);
    UserEntity findByToken(String token);
}
