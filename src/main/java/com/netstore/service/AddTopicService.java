package com.netstore.service;

import com.netstore.model.CircleEntity;
import com.netstore.model.TopicEntity;
import com.netstore.model.repository.CircleRepository;
import com.netstore.model.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Master on 2017-04-28.
 */
@Service
@Repository
public class AddTopicService {
    @Autowired
    TopicRepository topicRepository;

    @Transactional
    public TopicEntity saveAndFlush(TopicEntity topicEntity){
        topicEntity = topicRepository.saveAndFlush(topicEntity);
        return topicEntity;
    }
}
