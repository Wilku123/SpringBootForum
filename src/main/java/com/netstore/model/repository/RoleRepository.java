package com.netstore.model.repository;

import com.netstore.model.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Master on 2017-09-17.
 */
@Repository
@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity,Integer>{

}
