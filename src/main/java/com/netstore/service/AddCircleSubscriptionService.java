package com.netstore.service;

import com.netstore.model.entity.SubscribedCircleEntity;
import com.netstore.model.repository.SubscribedCircleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Master on 2017-08-03.
 */
@Service
@Repository
public class AddCircleSubscriptionService {
    @Autowired
    private SubscribedCircleRepository subscribedCircleRepository;

    @Transactional
    public SubscribedCircleEntity saveAndFlush(SubscribedCircleEntity subscribedCircleEntity){
        subscribedCircleEntity = subscribedCircleRepository.saveAndFlush(subscribedCircleEntity);
        return subscribedCircleEntity;
    }

}
