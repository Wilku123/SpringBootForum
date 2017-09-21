package com.netstore.service;

import com.netstore.model.entity.UserEntity;
import com.netstore.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Master on 2017-09-16.
 */
@Service
@Repository
public class AddUserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserEntity saveAndFlush(UserEntity userEntity)
    {
        userEntity = userRepository.saveAndFlush(userEntity);
        return userEntity;
    }


}
