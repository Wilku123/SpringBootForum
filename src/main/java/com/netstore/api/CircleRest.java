package com.netstore.api;

import com.netstore.model.CircleEntity;
import com.netstore.model.CircleRestViewEntity;
import com.netstore.model.SubscribedCircleEntity;
import com.netstore.model.repository.CircleRestViewRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddCircleService;
import com.netstore.service.AddCircleSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 2017-04-27.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/circle")
public class CircleRest {

    @Autowired
    private CircleRestViewRepository circleRestViewRepository;
    @Autowired
    private SubscribedCircleRepository subscribedCircleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddCircleService addCircleService;
    @Autowired
    private AddCircleSubscriptionService addCircleSubscriptionService;

    private CircleRestViewEntity generateCircleList(int sub, CircleRestViewEntity i) {

        Integer idCircle = i.getIdCircle();
        String name = i.getName();
        String description = i.getDescription();
        Integer author = i.getUserIdUser();
        Long countSubb = i.getCountSubbed();
        Integer isSub = sub;
        Long countTopic = i.getCountTopic();
        Timestamp publishDate = i.getPublishDate();

        CircleRestViewEntity circleRestViewEntity = new CircleRestViewEntity();

        circleRestViewEntity.setIdCircle(idCircle);
        circleRestViewEntity.setName(name);
        circleRestViewEntity.setDescription(description);
        circleRestViewEntity.setUserIdUser(author);
        circleRestViewEntity.setCountSubbed(countSubb);
        circleRestViewEntity.setIsSub(isSub);
        circleRestViewEntity.setCountTopic(countTopic);
        circleRestViewEntity.setPublishDate(publishDate);

        return circleRestViewEntity;
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<TopicEntity>> listTopics(){
//        return new ResponseEntity<List<TopicEntity>>(topicRepository.findAll(), HttpStatus.OK);
//    }
//    @RequestMapping(value = "/kek",method = RequestMethod.GET)
//    public ResponseEntity<Integer> howManySubs(){
//        return new ResponseEntity<Integer>(userSubCircle.getCount(), HttpStatus.OK);
//    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<CircleRestViewEntity> getCircle(@PathVariable Integer id) {
//        circleRestViewRepository.findOne(id).setIsSub(2);
//        return new ResponseEntity<CircleRestViewEntity>(circleRestViewRepository.findOne(id) ,HttpStatus.OK);
//
//
//
//    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<String> addCircle(@RequestHeader(value = "Token") String token,@RequestParam(value = "name")String name,@RequestParam(value = "description")String description) {

        CircleEntity circleEntity = new CircleEntity();
        circleEntity.setName(name);
        circleEntity.setDescription(description);
        circleEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
        circleEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
        this.addCircleService.saveAndFlush(circleEntity);

        return new ResponseEntity<>("Added Circle",HttpStatus.OK);
    }

    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    public ResponseEntity<List<CircleRestViewEntity>> getLimitedCircle(@RequestHeader(value = "Token") String token, @RequestParam(value = "limit") Integer limit) {

        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAll();
        List<CircleRestViewEntity> circleRestList = new ArrayList<>();

        for (CircleRestViewEntity i : circleEntityList) {
            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRepository.findByToken(token).getIdUser(), i.getIdCircle())) != null) {
                circleRestList.add(generateCircleList(1, i));
            } else {
                circleRestList.add(generateCircleList(0, i));
            }
        }
        //OutOfBound
        if (circleRestList.size() < limit)
        {
            return new ResponseEntity<>(circleRestList.subList(circleRestList.size(), circleRestList.size()), HttpStatus.OK);
        }
        //show From Limit To Max
        else if (circleRestList.size() < limit + 10)
        {
            return new ResponseEntity<>(circleRestList.subList(limit, circleRestList.size()), HttpStatus.OK);
        }
        //limit less than 0 fk that guy
        else if (limit < 0)
        {
            return new ResponseEntity<>(circleRestList.subList(0, 0), HttpStatus.OK);
        }
        //Everything is git gut
        else
        {
            return new ResponseEntity<>(circleRestList.subList(limit, limit + 10), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<List<CircleRestViewEntity>> getOneCircle(@RequestHeader(value = "Token") String token, @RequestParam(value = "id") Integer id) {

        CircleRestViewEntity circleEntity = circleRestViewRepository.findOne(id);
        List<CircleRestViewEntity> circleRestList = new ArrayList<>();


        if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRepository.findByToken(token).getIdUser(), circleEntity.getIdCircle())) != null) {
            circleRestList.add(generateCircleList(1, circleEntity));
        } else {
            circleRestList.add(generateCircleList(0, circleEntity));
        }

        return new ResponseEntity<>(circleRestList, HttpStatus.OK);
    }

    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResponseEntity<List<CircleRestViewEntity>> getAllCircles(@RequestHeader(value = "Token") String token) {

        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAll();
        List<CircleRestViewEntity> circleRestList = new ArrayList<>();

        for (CircleRestViewEntity i : circleEntityList) {
            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRepository.findByToken(token).getIdUser(), i.getIdCircle())) != null) {
                circleRestList.add(generateCircleList(1, i));
            } else {
                circleRestList.add(generateCircleList(0, i));
            }
        }
        return new ResponseEntity<>(circleRestList, HttpStatus.OK);
    }
    @RequestMapping(value = "/sub", method = RequestMethod.POST)
    public ResponseEntity<String> subscribeCircle(@RequestHeader(value = "Token") String token, @RequestParam(value = "id") Integer id) {

        SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
        subscribedCircleEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
        subscribedCircleEntity.setCircleIdCircle(id);
        this.addCircleSubscriptionService.saveAndFlush(subscribedCircleEntity);
        return new ResponseEntity<>("Subbded", HttpStatus.OK);
    }
    @RequestMapping(value = "/unsub", method = RequestMethod.POST)
    public ResponseEntity<String> unsubscribeCircle(@RequestHeader(value = "Token") String token, @RequestParam(value = "id") Integer id) {

        SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
        subscribedCircleEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
        subscribedCircleEntity.setCircleIdCircle(id);
        subscribedCircleRepository.delete(subscribedCircleEntity);
        return new ResponseEntity<>("UnSubbded", HttpStatus.OK);
    }
}
