package com.netstore.api.react.controller;

import com.netstore.api.mobile.controller.AnswerRest;
import com.netstore.api.mobile.controller.CircleRest;
import com.netstore.api.mobile.controller.TopicRest;
import com.netstore.model.API.mobile.answer.AnswerWithAuthor;
import com.netstore.model.API.mobile.circle.CircleWithAuthor;
import com.netstore.model.API.mobile.topic.TopicWithAuthor;
import com.netstore.model.entity.AnswerEntity;
import com.netstore.model.entity.CircleEntity;
import com.netstore.model.entity.TopicEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.repository.SubscribedTopicRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.repository.rest.AnswerRestRepository;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddAnswersService;
import com.netstore.model.service.AddCircleService;
import com.netstore.model.service.AddCircleSubscriptionService;
import com.netstore.model.service.AddEventService;
import com.netstore.model.view.AnswerRestViewEntity;
import com.netstore.model.view.CircleRestViewEntity;
import com.netstore.model.view.TopicRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/react/main")
public class SearchingForContentReact {

    @Autowired
    private CircleRestViewRepository circleRestViewRepository;
    @Autowired
    private SubscribedCircleRepository subscribedCircleRepository;
    @Autowired
    private SubscribedTopicRepository subscribedTopicRepository;
    @Autowired
    private AnswerRestRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRest answerRest;

    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private TopicRestViewRepository topicRestViewRepository;
    @Autowired
    private CircleRest circleRest;
    @Autowired
    private TopicRest topicRest;

    @PostMapping("/getCirclesByContent")
    public ResponseEntity<List<CircleWithAuthor>> lookForCircles(@RequestBody CircleEntity circleEntity, Authentication auth)
    {
        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAllByNameContainingOrDescriptionContaining(circleEntity.getName(),circleEntity.getName());
        List<CircleWithAuthor> circleRestList = new ArrayList<>();

        for (CircleRestViewEntity i : circleEntityList) {
            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRestRepository.findByEmail(auth.getName()).getIdUser(), i.getIdCircle())) != null) {
                circleRestList.add(circleRest.generateCircleList(1, i));
            } else {
                circleRestList.add(circleRest.generateCircleList(0, i));
            }
        }

        return new ResponseEntity<>(circleRestList, HttpStatus.OK);
    }
    @RequestMapping(value = "/getTopicsByContent", method = RequestMethod.POST)
    public ResponseEntity<List<TopicWithAuthor>> lookForTopics(Authentication auth, @RequestBody TopicEntity topicEntity) {


        List<TopicRestViewEntity> topicRestViewEntitiesList = topicRestViewRepository.findAllByNameContainingOrDescriptionContaining(topicEntity.getName(),topicEntity.getName());
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
    @RequestMapping(value = "/getAnswersByContent", method = RequestMethod.POST)
    public ResponseEntity<List<AnswerWithAuthor>> lookForAnswers(Authentication auth, @RequestBody AnswerEntity answerEntity) {

//        if (topicRestViewRepository.exists(topicEntity.getIdTopic())) {
        List<AnswerRestViewEntity> answerEntityList = answerRepository.findAllByContentContaining(answerEntity.getContent());
        List<AnswerWithAuthor> answerRestList = new ArrayList<>();

        for (AnswerRestViewEntity i : answerEntityList) {
            if (i.getUserIdUser() == userRepository.findByEmail(auth.getName()).getIdUser()) {
                answerRestList.add(answerRest.generateAnswerList(1, i));
            } else {
                answerRestList.add(answerRest.generateAnswerList(0, i));
            }

        }
        return new ResponseEntity<>(answerRestList, HttpStatus.OK);


//        }

    }
}
