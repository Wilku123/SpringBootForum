package com.netstore.service;

import com.netstore.model.entity.CredentialsEntity;
import com.netstore.model.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Master on 2017-09-23.
 */
@Service
@Repository
public class AddCredentialsService {

    @Autowired
    private CredentialsRepository credentialsRepository;
    @Transactional
    public CredentialsEntity saveAndFlush(CredentialsEntity credentialsEntity)
    {
        credentialsEntity = credentialsRepository.saveAndFlush(credentialsEntity);
        return credentialsEntity;
    }
}
