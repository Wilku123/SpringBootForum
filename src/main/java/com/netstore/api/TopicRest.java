package com.netstore.api;

import com.netstore.model.API.SchemaRest;
import com.netstore.model.API.SchemaRestList;
import com.netstore.model.API.topic.*;
import com.netstore.model.SubscribedTopicEntity;
import com.netstore.model.TopicEntity;
import com.netstore.model.TopicRestViewEntity;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.SubscribedTopicRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddTopicService;
import com.netstore.service.AddTopicSubscriptionService;
import com.netstore.utility.LimitedListGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private CircleRestViewRepository circleRestViewRepository;


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
    public ResponseEntity<SchemaRest> getOneTopic(@RequestHeader(value = "Token") String token, @RequestBody TopicIdModel topicIdModel) {

        if (topicRestViewRepository.exists(topicIdModel.getId())) {
            TopicRestViewEntity topicEntity = topicRestViewRepository.findOne(topicIdModel.getId());
            TopicRestViewEntity topicRestViewEntity;


            if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(userRepository.findByToken(token).getIdUser(), topicEntity.getIdTopic())) != null) {
                topicRestViewEntity = (generateTopicList(1, topicEntity));
            } else {
                topicRestViewEntity = (generateTopicList(0, topicEntity));
            }
            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(true, "git gut", 1337, topicRestViewEntity);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        } else {
            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(false, "zjebalo sie", 100, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> addTopic(@RequestHeader(value = "Token") String token, @RequestBody NewTopicModel newTopicModel) {


        if (circleRestViewRepository.exists(newTopicModel.getId()) && newTopicModel.getName().length() > 2) {
            TopicEntity topicEntity = new TopicEntity();
            topicEntity.setCircleIdCircle(newTopicModel.getId());
            topicEntity.setName(newTopicModel.getName());
            topicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
            topicEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
            topicEntity.setUuid(newTopicModel.getUuid());
            this.addTopicService.saveAndFlush(topicEntity);

            SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
            subscribedTopicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
            subscribedTopicEntity.setTopicIdTopic(topicEntity.getIdTopic());
            this.addTopicSubscriptionService.saveAndFlush(subscribedTopicEntity);

            TopicRestViewEntity topicRestViewEntity = topicRestViewRepository.findOne(topicEntity.getIdTopic());
            topicRestViewEntity.setIsSub(1);

            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(true, "topic added and subscribed successfully", 1337, topicRestViewEntity);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        } else {
            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(false, "ERROR wrong ID or name have less than 3 chars", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> getLimitedTopic(@RequestHeader(value = "Token") String token, @RequestBody LimitTopicModel limitTopicModel) {

        if (circleRestViewRepository.exists(limitTopicModel.getId()) && limitTopicModel.getHowMany() > 0) {
            List<TopicRestViewEntity> topicEntityList = topicRestViewRepository.findAllByCircleIdCircleAndPublishDateIsLessThanEqualOrderByPublishDateDesc(limitTopicModel.getId(), limitTopicModel.getDate());
            List<TopicRestViewEntity> topicRestList = new ArrayList<>();


            for (TopicRestViewEntity i : topicEntityList) {
                if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(userRepository.findByToken(token).getIdUser(), i.getIdTopic())) != null) {
                    topicRestList.add(generateTopicList(1, i));
                } else {
                    topicRestList.add(generateTopicList(0, i));
                }
            }

            LimitedListGenerator<TopicRestViewEntity> topicRestViewEntityLimitedListGenerator = new LimitedListGenerator<>();

            SchemaRestList<TopicRestViewEntity> schemaRest = new SchemaRestList<>(true, "should work not sure tho", 1337, topicRestViewEntityLimitedListGenerator.limitedList(topicRestList, limitTopicModel.getHowMany()));

            if (!schemaRest.getData().isEmpty())
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            else {
                schemaRest.setErrorCode(101);
                schemaRest.setMessage("list is empty");
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            }


        } else {

            SchemaRestList<TopicRestViewEntity> schemaRest = new SchemaRestList<>(false, "Circle dosnt exist or howMany is less than 1", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/sub", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> subscribeTopic(@RequestHeader(value = "Token") String token, @RequestBody TopicSubbedModel topicSubbedModel) {

        if (circleRestViewRepository.exists(topicSubbedModel.getId())) {
            if (topicSubbedModel.isStatus()) {
                SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
                subscribedTopicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
                subscribedTopicEntity.setTopicIdTopic(topicSubbedModel.getId());
                this.addTopicSubscriptionService.saveAndFlush(subscribedTopicEntity);

                SchemaRest<SubscribedTopicEntity> schemaRest = new SchemaRest<>(true, "Subbed topic", 1337, subscribedTopicEntity);

                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            } else {
                SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
                subscribedTopicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
                subscribedTopicEntity.setTopicIdTopic(topicSubbedModel.getId());
                subscribedTopicRepository.delete(subscribedTopicEntity);

                SchemaRest<SubscribedTopicEntity> schemaRest = new SchemaRest<>(true, "UnSubbed topic", 1337, subscribedTopicEntity);


                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            }
        } else {
            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(false, "zjebalo sie", 100, null);
            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);

        }

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> searchCircle(@RequestHeader(value = "Token") String token, @RequestBody(required = false) TopicLookForModel lookForModel) {

        SchemaRestList<TopicRestViewEntity> schemaRestList;
        LimitedListGenerator<TopicRestViewEntity> listGenerator = new LimitedListGenerator<>();
        if (!lookForModel.getName().isEmpty())
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(topicRestViewRepository.findAllByNameContaining(lookForModel.getName()), lookForModel.getHowMany()));
        else
            schemaRestList = new SchemaRestList<>(false, "String is empty fix pl0x", 100, null);

        if (!schemaRestList.getData().isEmpty())
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        else {
            schemaRestList.setErrorCode(101);
            schemaRestList.setMessage("List is empty");
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);

        }
    }
}