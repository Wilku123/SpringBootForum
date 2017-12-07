package com.netstore.api.mobile.controller;

import com.netstore.model.API.mobile.SchemaRest;
import com.netstore.model.API.mobile.SchemaRestList;
import com.netstore.model.API.mobile.circle.*;
import com.netstore.model.entity.CircleEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.view.CircleRestViewEntity;
import com.netstore.model.entity.SubscribedCircleEntity;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.service.AddCircleService;
import com.netstore.model.service.AddCircleSubscriptionService;
import com.netstore.model.view.UserRestViewEntity;
import com.netstore.utility.LimitedListGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private CredentialsRepository credentialsRepository;
    @Autowired
    private AddCircleService addCircleService;
    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private AddCircleSubscriptionService addCircleSubscriptionService;

    public CircleWithAuthor generateCircleList(int sub, CircleRestViewEntity i) {

        Integer idCircle = i.getIdCircle();
        String name = i.getName();
        String description = i.getDescription();
        Integer author = i.getUserIdUser();
        Long countSubb = i.getCountSubbed();
        Integer isSub = sub;
        Long countTopic = i.getCountTopic();
        Timestamp publishDate = i.getPublishDate();

        CircleWithAuthor circleRestViewEntity = new CircleWithAuthor();

        circleRestViewEntity.setIdCircle(idCircle);
        circleRestViewEntity.setName(name);
        circleRestViewEntity.setDescription(description);
        circleRestViewEntity.setUserIdUser(author);
        circleRestViewEntity.setCountSubbed(countSubb);
        circleRestViewEntity.setIsSub(isSub);
        circleRestViewEntity.setCountTopic(countTopic);
        circleRestViewEntity.setPublishDate(publishDate);
        circleRestViewEntity.setUuid(i.getUuid());

        UserRestViewEntity userEntity;

        userEntity = userRestRepository.findByIdUser(circleRestViewEntity.getUserIdUser());
        circleRestViewEntity.setAuthor(userEntity);
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> addCircle(Authentication auth, @RequestBody NewCircleModel newCircleModel) {


        if (newCircleModel.getName().length() > 2 && newCircleModel.getDescription().length() > 10) {
            CircleEntity circleEntity = new CircleEntity();

            circleEntity.setName(newCircleModel.getName());
            circleEntity.setDescription(newCircleModel.getDescription());
            circleEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
            circleEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
            circleEntity.setUuid(newCircleModel.getUuid());
            this.addCircleService.saveAndFlush(circleEntity);

            SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
            subscribedCircleEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
            subscribedCircleEntity.setCircleIdCircle(circleEntity.getIdCircle());
            this.addCircleSubscriptionService.saveAndFlush(subscribedCircleEntity);


            CircleRestViewEntity circleRestViewEntity;
            circleRestViewEntity = circleRestViewRepository.findOne(circleEntity.getIdCircle());
            CircleWithAuthor circleWithAuthor = generateCircleList(1,circleRestViewEntity);


            SchemaRest<CircleWithAuthor> schemaRest = new SchemaRest<>(true, "Added circle and subbed successfully", 1337, circleWithAuthor);


            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        } else {
            SchemaRest<CircleWithAuthor> schemaRest = new SchemaRest<>(false, "name should be longer than 2chars and description than 10chars", 102, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> getLimitedCircle(Authentication auth , @RequestBody LimitCircleModel limit) {

        if (limit.getHowMany() > 0) {
            List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAllByPublishDateIsLessThanOrderByPublishDateDesc(limit.getDate());
            List<CircleWithAuthor> circleRestList = new ArrayList<>();

            for (CircleRestViewEntity i : circleEntityList) {
                if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getIdCircle())) != null) {
                    circleRestList.add(generateCircleList(1, i));
                } else {
                    circleRestList.add(generateCircleList(0, i));
                }
            }
            LimitedListGenerator<CircleWithAuthor> listGenerator = new LimitedListGenerator<>();

            SchemaRestList<CircleWithAuthor> schemaRest = new SchemaRestList<>(true, "git gut", 1337, listGenerator.limitedList(circleRestList,limit.getHowMany()));

            if (!schemaRest.getData().isEmpty())
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            else {
                schemaRest.setErrorCode(103);
                schemaRest.setMessage("List empty");
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);

            }

        } else {
            SchemaRestList<CircleWithAuthor> schemaRest = new SchemaRestList<>(false, "howMany is less than 1", 102, null);
            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);

        }

    }

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> getOneCircle(Authentication auth, @RequestBody CircleIdModel oneById) {

        if (circleRestViewRepository.exists(oneById.getId())) {

            CircleRestViewEntity circleEntity = circleRestViewRepository.findOne(oneById.getId());
            CircleWithAuthor entityWithAuthor;



            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), circleEntity.getIdCircle())) != null) {
                entityWithAuthor = (generateCircleList(1, circleEntity));
            } else {
                entityWithAuthor = (generateCircleList(0, circleEntity));
            }

            SchemaRest<CircleWithAuthor> schemaRest = new SchemaRest<>(true, "git gut", 1337, entityWithAuthor);


            return new ResponseEntity<>(schemaRest, HttpStatus.OK);

        } else {

            SchemaRest<CircleWithAuthor> schemaRest = new SchemaRest<>(false, "ID dosnt exists in DB", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);

        }
    }

    //-------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/all", method = RequestMethod.POST)
    public ResponseEntity<List<CircleRestViewEntity>> getAllCircles() {

        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAll();
        List<CircleRestViewEntity> circleRestList = new ArrayList<>();


        return new ResponseEntity<>(circleEntityList, HttpStatus.OK);
    }

    //-------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/sub", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> subscribeCircle(Authentication auth, @RequestBody CircleSubbedModel subById) {

        if (circleRestViewRepository.exists(subById.getId())) {

            if (subById.isStatus()) {
                SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
                subscribedCircleEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                subscribedCircleEntity.setCircleIdCircle(subById.getId());
                this.addCircleSubscriptionService.saveAndFlush(subscribedCircleEntity);
                SchemaRest<SubscribedCircleEntity> schemaRest = new SchemaRest<>(true, "Subbed circle", 1337, subscribedCircleEntity);
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            } else {
                SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
                subscribedCircleEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                subscribedCircleEntity.setCircleIdCircle(subById.getId());
                subscribedCircleRepository.delete(subscribedCircleEntity);
                SchemaRest<SubscribedCircleEntity> schemaRest = new SchemaRest<>(true, "UnSubbed circle", 1337, subscribedCircleEntity);
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            }
        } else {
            SchemaRest<SubscribedCircleEntity> schemaRest = new SchemaRest<>(false, "Circle id dosnt exist", 101, null);
            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        }
    }



    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> searchCircle(Authentication auth, @RequestBody CircleLookForModel lookForModel) {

        SchemaRestList<CircleWithAuthor> schemaRestList;
        LimitedListGenerator<CircleWithAuthor> listGenerator = new LimitedListGenerator<>();
        List<CircleWithAuthor> circleRestList = new ArrayList<>();
        List<CircleRestViewEntity> circleRestViewEntities;


        if (!lookForModel.getName().isEmpty() && lookForModel.getDescription().isEmpty()) {
            circleRestViewEntities = circleRestViewRepository.findAllByNameContaining(lookForModel.getName());

            for (CircleRestViewEntity i : circleRestViewEntities) {
                if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getIdCircle())) != null) {
                    circleRestList.add(generateCircleList(1, i));
                } else {
                    circleRestList.add(generateCircleList(0, i));
                }
            }

            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(circleRestList, lookForModel.getHowMany()));
        }
        else if (lookForModel.getName().isEmpty() && !lookForModel.getDescription().isEmpty()) {
            circleRestViewEntities = circleRestViewRepository.findAllByDescriptionContaining(lookForModel.getDescription());
            for (CircleRestViewEntity i : circleRestViewEntities) {
                if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getIdCircle())) != null) {
                    circleRestList.add(generateCircleList(1, i));
                } else {
                    circleRestList.add(generateCircleList(0, i));
                }
            }
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(circleRestList, lookForModel.getHowMany()));
        }
        else if (!lookForModel.getName().isEmpty() && !lookForModel.getDescription().isEmpty()) {
            circleRestViewEntities = circleRestViewRepository.findAllByNameContainingOrDescriptionContaining(lookForModel.getName(), lookForModel.getDescription());
            for (CircleRestViewEntity i : circleRestViewEntities) {
                if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getIdCircle())) != null) {
                    circleRestList.add(generateCircleList(1, i));
                } else {
                    circleRestList.add(generateCircleList(0, i));
                }
            }
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(circleRestList, lookForModel.getHowMany()));
        }
        else
            schemaRestList = new SchemaRestList<>(false, "Strings are empty fix pl0x", 102, null);

        if (schemaRestList.getData()!=null)
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        else {
            schemaRestList.setMessage("List is empty");
            schemaRestList.setErrorCode(103);
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/subCircle", method = RequestMethod.GET)
    public ResponseEntity<SchemaRestList> getLimitedCircle(Authentication auth) {


            List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAll();
            List<CircleWithAuthor> circleRestList = new ArrayList<>();

            for (CircleRestViewEntity i : circleEntityList) {
                if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getIdCircle())) != null) {
                    circleRestList.add(generateCircleList(1, i));
                }
            }

            SchemaRestList<CircleWithAuthor> schemaRest = new SchemaRestList<>(true, "git gut", 1337, circleRestList);

            if (!schemaRest.getData().isEmpty())
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            else {
                schemaRest.setErrorCode(103);
                schemaRest.setMessage("Circle Subbed List is empty");
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            }



    }
}
