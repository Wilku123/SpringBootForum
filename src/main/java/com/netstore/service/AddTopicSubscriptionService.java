package com.netstore.service;

import com.netstore.model.SubscribedTopicEntity;
import com.netstore.model.repository.SubscribedTopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Master on 2017-08-03.
 */
@Service
@Repository
public class AddTopicSubscriptionService {

    @Autowired
    private SubscribedTopicRepository subscribedTopicRepository;

    @Transactional
    public SubscribedTopicEntity saveAndFlush(SubscribedTopicEntity subscribedTopicEntity){
        subscribedTopicEntity = subscribedTopicRepository.saveAndFlush(subscribedTopicEntity);
        return subscribedTopicEntity;
    }
}
