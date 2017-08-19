package com.netstore.api;

import com.netstore.model.SubscribedTopicEntity;
import com.netstore.model.TopicEntity;
import com.netstore.model.TopicRestViewEntity;
import com.netstore.model.repository.SubscribedTopicRepository;
import com.netstore.model.repository.TopicRestViewRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddTopicService;
import com.netstore.service.AddTopicSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 2017-07-28.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/topic")
public class TopicRest {

    @Autowired
    private TopicRestViewRepository topicRestViewRepository;
    @Autowired
    private SubscribedTopicRepository subscribedTopicRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddTopicService addTopicService;
    @Autowired
    private AddTopicSubscriptionService addTopicSubscriptionService;

    private TopicRestViewEntity generateTopicList(int sub, TopicRestViewEntity i) {

        Integer idTopic = i.getIdTopic();
        String name = i.getName();
        Integer author = i.getUserIdUser();
        Integer isSub = sub;
        Timestamp publishDate = i.getPublishDate();
        Integer circleId = i.getCircleIdCircle();

        TopicRestViewEntity topicRestViewEntity = new TopicRestViewEntity();

        topicRestViewEntity.setIdTopic(idTopic);
        topicRestViewEntity.setName(name);
        topicRestViewEntity.setUserIdUser(author);
        topicRestViewEntity.setIsSub(isSub);
        topicRestViewEntity.setPublishDate(publishDate);
        topicRestViewEntity.setCircleIdCircle(circleId);

        return topicRestViewEntity;
    }


    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<List<TopicRestViewEntity>> getOneTopic(@RequestHeader(value = "Token") String token, @RequestParam(value = "id") Integer id) {

        TopicRestViewEntity topicEntity = topicRestViewRepository.findOne(id);
        List<TopicRestViewEntity> topicRestViewEntityList = new ArrayList<>();


        if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(userRepository.findByToken(token).getIdUser(), topicEntity.getIdTopic())) != null) {
            topicRestViewEntityList.add(generateTopicList(1, topicEntity));
        } else {
            topicRestViewEntityList.add(generateTopicList(0, topicEntity));
        }

        return new ResponseEntity<>(topicRestViewEntityList, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addTopic(@RequestHeader(value = "Token") String token, @RequestParam(value = "name") String name, @RequestParam(value = "circleId") Integer circleId) {

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setCircleIdCircle(circleId);
        topicEntity.setName(name);
        topicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
        topicEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
        this.addTopicService.saveAndFlush(topicEntity);

        return new ResponseEntity<>("Added Topic", HttpStatus.OK);
    }

    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    public ResponseEntity<List<TopicRestViewEntity>> getLimitedTopic(@RequestHeader(value = "Token") String token, @RequestParam(value = "limit") Integer limit, @RequestParam(value = "circleId") Integer circleId) {

        List<TopicRestViewEntity> topicEntityList = topicRestViewRepository.findAllByCircleIdCircle(circleId);
        List<TopicRestViewEntity> topicRestList = new ArrayList<>();

        for (TopicRestViewEntity i : topicEntityList) {
            if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(userRepository.findByToken(token).getIdUser(), i.getIdTopic())) != null) {
                topicRestList.add(generateTopicList(1, i));
            } else {
                topicRestList.add(generateTopicList(0, i));
            }
        }

        if (topicRestList.size() < limit)
        {
            return new ResponseEntity<>(topicRestList.subList(topicRestList.size(), topicRestList.size()), HttpStatus.OK);
        }
//        else if (limit < 0)
//        {
//            return new ResponseEntity<>(topicRestList.subList(0, 0), HttpStatus.OK);
//        }
        else if (topicRestList.size() < limit + 10)
        {
            return new ResponseEntity<>(topicRestList.subList(limit, topicRestList.size()), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(topicRestList.subList(limit, limit + 10), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/sub", method = RequestMethod.POST)
    public ResponseEntity<String> subscribeTopic(@RequestHeader(value = "Token") String token, @RequestParam(value = "id") Integer id) {

        SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
        subscribedTopicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
        subscribedTopicEntity.setTopicIdTopic(id);
        this.addTopicSubscriptionService.saveAndFlush(subscribedTopicEntity);


        return new ResponseEntity<>("Subbded", HttpStatus.OK);
    }
    @RequestMapping(value = "/unsub", method = RequestMethod.POST)
    public ResponseEntity<String> unsubscribeCircle(@RequestHeader(value = "Token") String token, @RequestParam(value = "id") Integer id) {

        SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
        subscribedTopicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
        subscribedTopicEntity.setTopicIdTopic(id);
        subscribedTopicRepository.delete(subscribedTopicEntity);
        return new ResponseEntity<>("UnSubbded", HttpStatus.OK);
    }
}