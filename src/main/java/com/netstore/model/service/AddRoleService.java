package com.netstore.model.service;

import com.netstore.model.entity.RoleEntity;
import com.netstore.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Master on 2017-09-17.
 */
@Service
@Repository
public class AddRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public RoleEntity saveAndFlush(RoleEntity roleEntity)
    {
        roleEntity = roleRepository.saveAndFlush(roleEntity);
        return roleEntity;
    }
}
