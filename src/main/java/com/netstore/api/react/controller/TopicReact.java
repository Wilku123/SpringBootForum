package com.netstore.api.react.controller;

import com.netstore.api.mobile.controller.TopicRest;
import com.netstore.model.API.mobile.topic.LimitTopicModel;
import com.netstore.model.API.mobile.topic.TopicIdModel;
import com.netstore.model.API.mobile.topic.TopicWithAuthor;
import com.netstore.model.API.react.ReactStatus;
import com.netstore.model.API.react.topic.TopicToUnSub;
import com.netstore.model.entity.CircleEntity;
import com.netstore.model.entity.EventEntity;
import com.netstore.model.entity.SubscribedTopicEntity;
import com.netstore.model.entity.TopicEntity;
import com.netstore.model.repository.SubscribedTopicRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddEventService;
import com.netstore.model.service.AddTopicService;
import com.netstore.model.service.AddTopicSubscriptionService;
import com.netstore.model.view.TopicRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddEventService addEventService;
    @Autowired
    private AddTopicService addTopicService;
    @Autowired
    private AddTopicSubscriptionService addTopicSubscriptionService;


    @RequestMapping(value = "/getTopic", method = RequestMethod.POST)
    public ResponseEntity<List<TopicWithAuthor>> getTopic(Authentication auth, @RequestBody TopicEntity TopicEntity) {


        List<TopicRestViewEntity> topicRestViewEntitiesList = topicRestViewRepository.findAllByCircleIdCircleAndPublishDateIsLessThanOrderByPublishDateDesc(TopicEntity.getCircleIdCircle(), new Timestamp(System.currentTimeMillis()));
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
    @RequestMapping(value = "/getOneTopic", method = RequestMethod.POST)
    public ResponseEntity<TopicRestViewEntity> getOneCircle(@RequestBody TopicIdModel oneById) {

        TopicRestViewEntity topicRestViewEntity = topicRestViewRepository.findOne(oneById.getId());

        return new ResponseEntity<>(topicRestViewEntity, HttpStatus.OK);


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
    @Transactional
    @RequestMapping(value = "/addTopic", method = RequestMethod.POST)
    public ResponseEntity<?> addTopic(Authentication auth, @RequestBody TopicEntity addingTopic) {


        ReactStatus reactStatus = new ReactStatus();
        if (addingTopic.getName().length() >= 3
                && addingTopic.getDescription().length() >= 5
                && addingTopic.getName().length() <= 40
                && addingTopic.getDescription().length() <=120) {
            TopicEntity topicEntity = new TopicEntity();


            topicEntity.setName(addingTopic.getName());
            topicEntity.setDescription(addingTopic.getDescription());
            topicEntity.setUserIdUser(userRepository.findByEmail(auth.getName()).getIdUser());
            topicEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
            topicEntity.setUuid(UUID.randomUUID().toString());
            topicEntity.setCircleIdCircle(addingTopic.getCircleIdCircle());
            this.addTopicService.saveAndFlush(topicEntity);

            SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
            subscribedTopicEntity.setUserIdUser(userRepository.findByEmail(auth.getName()).getIdUser());
            subscribedTopicEntity.setTopicIdTopic(topicEntity.getIdTopic());
            this.addTopicSubscriptionService.saveAndFlush(subscribedTopicEntity);

            EventEntity eventEntity = new EventEntity();
            eventEntity.setType("Topic");
            eventEntity.setEntityId(topicEntity.getIdTopic());
            eventEntity.setAuthorId(topicEntity.getUserIdUser());
            eventEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
            this.addEventService.saveAndFlush(eventEntity);


            TopicRestViewEntity topicRestViewEntity = topicRestViewRepository.findOne(topicEntity.getIdTopic());
            TopicWithAuthor topicWithAuthor = topicRest.generateTopicWithAuthor(1 , topicRestViewEntity);
            List<TopicWithAuthor> topicWithAuthorList = new ArrayList<>();
            topicWithAuthorList.add(topicWithAuthor);

            return new ResponseEntity<>(topicWithAuthorList, HttpStatus.OK);
        } else {
            reactStatus.setStatus(false);
            return new ResponseEntity<>(reactStatus, HttpStatus.OK);
        }
    }

}
