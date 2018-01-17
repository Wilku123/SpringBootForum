package com.netstore.api.react.controller;

import com.netstore.api.mobile.controller.CircleRest;
import com.netstore.model.API.mobile.circle.CircleIdModel;
import com.netstore.model.API.mobile.circle.CircleSubbedModel;
import com.netstore.model.API.mobile.circle.CircleWithAuthor;
import com.netstore.model.API.react.circle.CircleToUnSub;
import com.netstore.model.API.react.ReactStatus;
import com.netstore.model.entity.CircleEntity;
import com.netstore.model.entity.EventEntity;
import com.netstore.model.entity.SubscribedCircleEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddCircleService;
import com.netstore.model.service.AddCircleSubscriptionService;
import com.netstore.model.service.AddEventService;
import com.netstore.model.view.CircleRestViewEntity;
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
public class CircleReact {

    @Autowired
    private CircleRestViewRepository circleRestViewRepository;
    @Autowired
    private SubscribedCircleRepository subscribedCircleRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private AddCircleService addCircleService;
    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private TopicRestViewRepository topicRestViewRepository;
    @Autowired
    private AddCircleSubscriptionService addCircleSubscriptionService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddEventService addEventService;
    @Autowired
    private CircleRest circleRest;


    @RequestMapping(value = "/getCircle", method = RequestMethod.POST)
    public ResponseEntity<List<CircleWithAuthor>> getCircle(Authentication auth) {


        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAllByPublishDateIsLessThanOrderByPublishDateDesc(new Timestamp(System.currentTimeMillis()));
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


    @RequestMapping(value = "/getOneCircle", method = RequestMethod.POST)
    public ResponseEntity<CircleRestViewEntity> getOneCircle(@RequestBody CircleIdModel oneById) {

        CircleRestViewEntity circleEntity = circleRestViewRepository.findOne(oneById.getId());

        return new ResponseEntity<>(circleEntity, HttpStatus.OK);


    }
    @RequestMapping(value = "/getOneCircleFromTopic", method = RequestMethod.POST)
    public ResponseEntity<CircleRestViewEntity> getOneCircleFromTopic(@RequestBody CircleIdModel oneById) {

        CircleRestViewEntity circleEntity = circleRestViewRepository.findOne(topicRestViewRepository.findOne(oneById.getId()).getCircleIdCircle());

        return new ResponseEntity<>(circleEntity, HttpStatus.OK);


    }

    @RequestMapping(value = "/subbedCircle", method = RequestMethod.POST)
    public ResponseEntity<List<CircleWithAuthor>> getSubbedCircle(Authentication auth) {


        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAll();
        List<CircleWithAuthor> circleRestList = new ArrayList<>();

        for (CircleRestViewEntity i : circleEntityList) {
            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRestRepository.findByEmail(auth.getName()).getIdUser(), i.getIdCircle())) != null) {
                circleRestList.add(circleRest.generateCircleList(1, i));
            }
        }
        return new ResponseEntity<>(circleRestList, HttpStatus.OK);
    }

    @RequestMapping(value = "/unSubCircle", method = RequestMethod.POST)
    public ResponseEntity<ReactStatus> unSubCircle(Authentication auth, @RequestBody CircleToUnSub circleToUnSub) {


        List<Integer> list = circleToUnSub.getList();
        SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
        for (Integer i : list) {
            subscribedCircleEntity.setUserIdUser(userRestRepository.findByEmail(auth.getName()).getIdUser());
            subscribedCircleEntity.setCircleIdCircle(i);
            subscribedCircleRepository.delete(subscribedCircleEntity);
        }
        ReactStatus reactStatus = new ReactStatus();
        reactStatus.setStatus(true);
        return new ResponseEntity<>(reactStatus, HttpStatus.OK);

    }

    @RequestMapping(value = "/subCircle", method = RequestMethod.POST)
    public ResponseEntity<ReactStatus> subscribeCircle(Authentication auth, @RequestBody CircleSubbedModel subById) {
        ReactStatus reactStatus = new ReactStatus();

        if (subById.isStatus()) {
                SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
                subscribedCircleEntity.setUserIdUser(userRepository.findByEmail(auth.getName()).getIdUser());
                subscribedCircleEntity.setCircleIdCircle(subById.getId());
                this.addCircleSubscriptionService.saveAndFlush(subscribedCircleEntity);
                reactStatus.setStatus(true);
                return new ResponseEntity<>(reactStatus, HttpStatus.OK);
            } else {
                SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
                subscribedCircleEntity.setUserIdUser(userRepository.findByEmail(auth.getName()).getIdUser());
                subscribedCircleEntity.setCircleIdCircle(subById.getId());
                subscribedCircleRepository.delete(subscribedCircleEntity);
                reactStatus.setStatus(false);
                return new ResponseEntity<>(reactStatus, HttpStatus.OK);
            }

    }

    @Transactional
    @RequestMapping(value = "/addCircle", method = RequestMethod.POST)
    public ResponseEntity<?> addCircle(Authentication auth, @RequestBody CircleEntity addingCircle) {


        ReactStatus reactStatus = new ReactStatus();
        if (addingCircle.getName().length() >= 3
                && addingCircle.getDescription().length() >= 5
                && addingCircle.getName().length() <= 40
                && addingCircle.getDescription().length() <=120) {
            CircleEntity circleEntity = new CircleEntity();

            circleEntity.setName(addingCircle.getName());
            circleEntity.setDescription(addingCircle.getDescription());
            circleEntity.setUserIdUser(userRepository.findByEmail(auth.getName()).getIdUser());
            circleEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
            circleEntity.setUuid(UUID.randomUUID().toString());
            this.addCircleService.saveAndFlush(circleEntity);

            SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
            subscribedCircleEntity.setUserIdUser(userRepository.findByEmail(auth.getName()).getIdUser());
            subscribedCircleEntity.setCircleIdCircle(circleEntity.getIdCircle());
            this.addCircleSubscriptionService.saveAndFlush(subscribedCircleEntity);

            EventEntity eventEntity = new EventEntity();
            eventEntity.setType("Circle");
            eventEntity.setEntityId(circleEntity.getIdCircle());
            eventEntity.setAuthorId(circleEntity.getUserIdUser());
            eventEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
            this.addEventService.saveAndFlush(eventEntity);

            CircleRestViewEntity circleRestViewEntity;
            circleRestViewEntity = circleRestViewRepository.findOne(circleEntity.getIdCircle());
            CircleWithAuthor circleWithAuthor = circleRest.generateCircleList(1,circleRestViewEntity);
            List<CircleWithAuthor> circleWithAuthors = new ArrayList<>();
            circleWithAuthors.add(circleWithAuthor);

            return new ResponseEntity<>(circleWithAuthors, HttpStatus.OK);
        } else {
            reactStatus.setStatus(false);
            return new ResponseEntity<>(reactStatus, HttpStatus.OK);
        }
    }
}
