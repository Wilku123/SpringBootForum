package com.netstore.api.react.controller;

import com.netstore.api.mobile.controller.TopicRest;
import com.netstore.model.API.mobile.SchemaRestList;
import com.netstore.model.API.mobile.circle.CircleWithAuthor;
import com.netstore.model.API.mobile.topic.LimitTopicModel;
import com.netstore.model.API.mobile.topic.TopicWithAuthor;
import com.netstore.model.API.react.entry.ReactStatus;
import com.netstore.model.API.react.topic.TopicToUnSub;
import com.netstore.model.entity.SubscribedTopicEntity;
import com.netstore.model.repository.SubscribedTopicRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.view.CircleRestViewEntity;
import com.netstore.model.view.TopicRestViewEntity;
import com.netstore.model.view.UserRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/react/main")
public class TopicReact {

    @Autowired
    private TopicRestViewRepository topicRestViewRepository;
    @Autowired
    private SubscribedTopicRepository subscribedTopicRepository;
    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private TopicRest topicRest;


    @RequestMapping(value = "/getTopic", method = RequestMethod.POST)
    public ResponseEntity<List<TopicWithAuthor>> getTopic(Authentication auth, @RequestBody LimitTopicModel limitTopicModel) {


        List<TopicRestViewEntity> topicRestViewEntitiesList = topicRestViewRepository.findAllByCircleIdCircleAndPublishDateIsLessThanOrderByPublishDateDesc(limitTopicModel.getId(), new Timestamp(System.currentTimeMillis()));
        List<TopicWithAuthor> topicWithAuthorList = new ArrayList<>();

        for (TopicRestViewEntity i : topicRestViewEntitiesList) {
            if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(userRestRepository.findByEmail(auth.getName()).getIdUser(), i.getIdTopic())) != null) {
                topicWithAuthorList.add(topicRest.generateTopicWithAuthor(1, i));
            } else {
                topicWithAuthorList.add(topicRest.generateTopicWithAuthor(0, i));
            }
        }

        return new ResponseEntity<>(topicWithAuthorList, HttpStatus.OK);


    }

    @RequestMapping(value = "/subbedTopic", method = RequestMethod.POST)
    public ResponseEntity<List<TopicWithAuthor>> getLimitedTopic(Authentication auth) {

        List<TopicRestViewEntity> topicEntityList = topicRestViewRepository.findAll();
        List<TopicWithAuthor> topicWithAuthor = new ArrayList<>();


        for (TopicRestViewEntity i : topicEntityList) {
            if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(userRestRepository.findByEmail(auth.getName()).getIdUser(), i.getIdTopic())) != null) {
                topicWithAuthor.add(topicRest.generateTopicWithAuthor(1, i));
            }
        }

        return new ResponseEntity<>(topicWithAuthor, HttpStatus.OK);

    }
    @RequestMapping(value = "/unSubTopic", method = RequestMethod.POST)
    public ResponseEntity<ReactStatus> unSubTopic(Authentication auth, @RequestBody TopicToUnSub topicToUnSub) {


        List<Integer> list = topicToUnSub.getList();
        SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
        for (Integer i : list) {
            subscribedTopicEntity.setUserIdUser(userRestRepository.findByEmail(auth.getName()).getIdUser());
            subscribedTopicEntity.setTopicIdTopic(i);
            subscribedTopicRepository.delete(subscribedTopicEntity);
        }
        ReactStatus reactStatus = new ReactStatus();
        reactStatus.setStatus(true);
        return new ResponseEntity<>(reactStatus, HttpStatus.OK);


    }
}
