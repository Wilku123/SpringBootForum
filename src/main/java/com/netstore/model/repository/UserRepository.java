package com.netstore.model.repository;

import com.netstore.model.entity.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Master on 2017-05-21.
 */
public interface UserRepository extends JpaRepository<UserEntity,Integer>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findAllByIdUser(Integer id);
    UserEntity findByActiveToken(String activeToken);
    UserEntity findFirstByEmail(String email);

}
