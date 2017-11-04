package com.netstore.api.controller;

import com.netstore.model.API.EntityWithAuthor;
import com.netstore.model.API.SchemaRest;
import com.netstore.model.API.SchemaRestList;
import com.netstore.model.API.topic.*;
import com.netstore.model.entity.SubscribedTopicEntity;
import com.netstore.model.entity.TopicEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.view.TopicRestViewEntity;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.SubscribedTopicRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.service.AddTopicService;
import com.netstore.model.service.AddTopicSubscriptionService;
import com.netstore.model.view.UserRestViewEntity;
import com.netstore.utility.LimitedListGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
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
    UserRestRepository userRestRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private AddTopicService addTopicService;
    @Autowired
    private AddTopicSubscriptionService addTopicSubscriptionService;
    @Autowired
    private CircleRestViewRepository circleRestViewRepository;


    private EntityWithAuthor<TopicRestViewEntity> generateTopicList(int sub, TopicRestViewEntity i) {

        Integer idTopic = i.getIdTopic();
        String name = i.getName();
        Integer author = i.getUserIdUser();
        Integer isSub = sub;
        Timestamp publishDate = i.getPublishDate();
        Integer circleId = i.getCircleIdCircle();

        TopicRestViewEntity topicRestViewEntity = new TopicRestViewEntity();

        topicRestViewEntity.setIdTopic(idTopic);
        topicRestViewEntity.setName(name);
        topicRestViewEntity.setDescription(i.getDescription());
        topicRestViewEntity.setUserIdUser(author);
        topicRestViewEntity.setIsSub(isSub);
        topicRestViewEntity.setPublishDate(publishDate);
        topicRestViewEntity.setCircleIdCircle(circleId);
        topicRestViewEntity.setUuid(i.getUuid());
        topicRestViewEntity.setCountSubbed(i.getCountSubbed());
        topicRestViewEntity.setCountAnswer(i.getCountAnswer());

        UserRestViewEntity userRestViewEntity = new UserRestViewEntity();
        userRestViewEntity = userRestRepository.findByIdUser(topicRestViewEntity.getUserIdUser());
        EntityWithAuthor<TopicRestViewEntity> entityWithAuthor = new EntityWithAuthor<>(topicRestViewEntity,userRestViewEntity);

        return entityWithAuthor;
    }


    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> getOneTopic(Authentication auth, @RequestBody TopicIdModel topicIdModel) {

        if (topicRestViewRepository.exists(topicIdModel.getId())) {
            TopicRestViewEntity topicEntity = topicRestViewRepository.findOne(topicIdModel.getId());
            UserRestViewEntity userRestViewEntity = userRestRepository.findByIdUser(topicEntity.getUserIdUser());
            EntityWithAuthor<TopicRestViewEntity> topicRestViewEntity = new EntityWithAuthor<>(topicEntity,userRestViewEntity);



            if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), topicEntity.getIdTopic())) != null) {
                topicRestViewEntity = (generateTopicList(1, topicEntity));
            } else {
                topicRestViewEntity = (generateTopicList(0, topicEntity));
            }
            SchemaRest<EntityWithAuthor<TopicRestViewEntity>> schemaRest = new SchemaRest<>(true, "git gut", 1337, topicRestViewEntity);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        } else {
            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(false, "ID dosnt exists in DB", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> addTopic(Authentication auth, @RequestBody NewTopicModel newTopicModel) {


        if (circleRestViewRepository.exists(newTopicModel.getId())) {

            if (newTopicModel.getName().length() > 2 && newTopicModel.getDescription().length() > 5) {
                TopicEntity topicEntity = new TopicEntity();
                topicEntity.setCircleIdCircle(newTopicModel.getId());
                topicEntity.setName(newTopicModel.getName());
                topicEntity.setDescription(newTopicModel.getDescription());
                topicEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                topicEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
                topicEntity.setUuid(newTopicModel.getUuid());
                this.addTopicService.saveAndFlush(topicEntity);

                SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
                subscribedTopicEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                subscribedTopicEntity.setTopicIdTopic(topicEntity.getIdTopic());
                this.addTopicSubscriptionService.saveAndFlush(subscribedTopicEntity);

                TopicRestViewEntity topicRestViewEntity = topicRestViewRepository.findOne(topicEntity.getIdTopic());
                topicRestViewEntity.setIsSub(1);

                UserRestViewEntity userRestViewEntity = userRestRepository.findByIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                EntityWithAuthor<TopicRestViewEntity> entityWithAuthor = new EntityWithAuthor<>(topicRestViewEntity,userRestViewEntity);

                SchemaRest<EntityWithAuthor<TopicRestViewEntity>> schemaRest = new SchemaRest<>(true, "topic added and subscribed successfully", 1337, entityWithAuthor);

                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            } else {
                SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(false, "name have less than 3 chars,description have less than 5 chars", 102, null);

                return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
            }
        } else {
            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(false, "ID dosnt exists in DB", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> getLimitedTopic(Authentication auth, @RequestBody LimitTopicModel limitTopicModel) {

        if (circleRestViewRepository.exists(limitTopicModel.getId())) {
            if (limitTopicModel.getHowMany() > 0) {
                List<TopicRestViewEntity> topicEntityList = topicRestViewRepository.findAllByCircleIdCircleAndPublishDateIsLessThanEqualOrderByPublishDateDesc(limitTopicModel.getId(), limitTopicModel.getDate());
                List<EntityWithAuthor<TopicRestViewEntity>> topicRestList = new ArrayList<>();


                for (TopicRestViewEntity i : topicEntityList) {
                    if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getIdTopic())) != null) {
                        topicRestList.add(generateTopicList(1, i));
                    } else {
                        topicRestList.add(generateTopicList(0, i));
                    }
                }

                LimitedListGenerator<EntityWithAuthor<TopicRestViewEntity>> topicRestViewEntityLimitedListGenerator = new LimitedListGenerator<>();

                SchemaRestList<EntityWithAuthor<TopicRestViewEntity>> schemaRest = new SchemaRestList<>(true, "should work not sure tho", 1337, topicRestViewEntityLimitedListGenerator.limitedList(topicRestList, limitTopicModel.getHowMany()));

                if (!schemaRest.getData().isEmpty())
                    return new ResponseEntity<>(schemaRest, HttpStatus.OK);
                else {
                    schemaRest.setErrorCode(103);
                    schemaRest.setMessage("list is empty");
                    return new ResponseEntity<>(schemaRest, HttpStatus.OK);
                }


            } else {

                SchemaRestList<TopicRestViewEntity> schemaRest = new SchemaRestList<>(false, "howMany is less than 1", 102, null);

                return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
            }
        } else {
            SchemaRestList<TopicRestViewEntity> schemaRest = new SchemaRestList<>(false, "ID dosnt exists in DB", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/sub", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> subscribeTopic(Authentication auth, @RequestBody TopicSubbedModel topicSubbedModel) {

        if (topicRestViewRepository.exists(topicSubbedModel.getId())) {
            if (topicSubbedModel.isStatus()) {
                SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
                subscribedTopicEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                subscribedTopicEntity.setTopicIdTopic(topicSubbedModel.getId());
                this.addTopicSubscriptionService.saveAndFlush(subscribedTopicEntity);

                SchemaRest<SubscribedTopicEntity> schemaRest = new SchemaRest<>(true, "Subbed topic", 1337, subscribedTopicEntity);

                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            } else {
                SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
                subscribedTopicEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                subscribedTopicEntity.setTopicIdTopic(topicSubbedModel.getId());
                subscribedTopicRepository.delete(subscribedTopicEntity);

                SchemaRest<SubscribedTopicEntity> schemaRest = new SchemaRest<>(true, "UnSubbed topic", 1337, subscribedTopicEntity);


                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            }
        } else {
            SchemaRest<TopicRestViewEntity> schemaRest = new SchemaRest<>(false, "ID dosnt exists in DB", 101, null);
            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);

        }

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> searchCircle(Authentication auth, @RequestBody TopicLookForModel lookForModel) {

        SchemaRestList<EntityWithAuthor<TopicRestViewEntity>> schemaRestList;
        LimitedListGenerator<EntityWithAuthor<TopicRestViewEntity>> listGenerator = new LimitedListGenerator<>();
        List<TopicRestViewEntity> topicRestViewEntity = new ArrayList<>();
        List<EntityWithAuthor<TopicRestViewEntity>> entityWithAuthors = new ArrayList<>();
        if (!lookForModel.getName().isEmpty()) {
            topicRestViewEntity= topicRestViewRepository.findAllByNameContainingAndCircleIdCircle(lookForModel.getName(), lookForModel.getId());
            for (TopicRestViewEntity i : topicRestViewEntity) {
                if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getIdTopic())) != null) {
                    entityWithAuthors.add(generateTopicList(1, i));
                } else {
                    entityWithAuthors.add(generateTopicList(0, i));
                }
            }
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(entityWithAuthors, lookForModel.getHowMany()));
        }
        else
            schemaRestList = new SchemaRestList<>(false, "String is empty fix pl0x", 102, null);

        if (schemaRestList.getData()!=null)
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        else {
            schemaRestList.setErrorCode(103);
            schemaRestList.setMessage("List is empty");
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);

        }
    }
}