package com.netstore.api;

import com.netstore.model.API.SchemaRest;
import com.netstore.model.API.SchemaRestList;
import com.netstore.model.API.circle.*;
import com.netstore.model.CircleEntity;
import com.netstore.model.CircleRestViewEntity;
import com.netstore.model.SubscribedCircleEntity;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddCircleService;
import com.netstore.service.AddCircleSubscriptionService;
import com.netstore.utility.LimitedListGenerator;
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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> addCircle(@RequestHeader(value = "Token") String token, @RequestBody NewCircleModel newCircleModel) {

        if (newCircleModel.getName().length() > 2 && newCircleModel.getDescription().length() > 10) {
            CircleEntity circleEntity = new CircleEntity();
            circleEntity.setName(newCircleModel.getName());
            circleEntity.setDescription(newCircleModel.getDescription());
            circleEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
            circleEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
            circleEntity.setUuid(newCircleModel.getUuid());
            this.addCircleService.saveAndFlush(circleEntity);

            SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
            subscribedCircleEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
            subscribedCircleEntity.setCircleIdCircle(circleEntity.getIdCircle());
            this.addCircleSubscriptionService.saveAndFlush(subscribedCircleEntity);


            CircleRestViewEntity circleRestViewEntity;
            circleRestViewEntity = circleRestViewRepository.findOne(circleEntity.getIdCircle());
            circleRestViewEntity.setIsSub(1);
            SchemaRest<CircleRestViewEntity> schemaRest = new SchemaRest<>(true, "Added circle and subbed successfully", 1337, circleRestViewEntity);


            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        } else {
            SchemaRest<CircleRestViewEntity> schemaRest = new SchemaRest<>(false, "name should be longer than 2chars and description than 10chars", 100, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> getLimitedCircle(@RequestHeader(value = "Token") String token, @RequestBody LimitCircleModel limit) {

        if (limit.getHowMany() > 0) {
            List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAllByPublishDateIsLessThanEqualOrderByPublishDateDesc(limit.getDate());
            List<CircleRestViewEntity> circleRestList = new ArrayList<>();

            for (CircleRestViewEntity i : circleEntityList) {
                if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRepository.findByToken(token).getIdUser(), i.getIdCircle())) != null) {
                    circleRestList.add(generateCircleList(1, i));
                } else {
                    circleRestList.add(generateCircleList(0, i));
                }
            }
            SchemaRestList<CircleRestViewEntity> schemaRest = new SchemaRestList<>(true, "git gut", 1337, circleRestList);

            if (schemaRest.getData().isEmpty())
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            else {
                schemaRest.setErrorCode(101);
                schemaRest.setMessage("List is empty");
                return new ResponseEntity<>(schemaRest, HttpStatus.OK);

            }

        } else {
            SchemaRestList<CircleRestViewEntity> schemaRest = new SchemaRestList<>(false, "zjebalo sie", 1337, null);
            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);

        }

    }

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> getOneCircle(@RequestHeader(value = "Token") String token, @RequestBody CircleIdModel oneById) {

        if (circleRestViewRepository.exists(oneById.getId())) {

            CircleRestViewEntity circleEntity = circleRestViewRepository.findOne(oneById.getId());
            CircleRestViewEntity circleRest;


            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRepository.findByToken(token).getIdUser(), circleEntity.getIdCircle())) != null) {
                circleRest = (generateCircleList(1, circleEntity));
            } else {
                circleRest = (generateCircleList(0, circleEntity));
            }

            SchemaRest<CircleRestViewEntity> schemaRest = new SchemaRest<>(true, "git gut", 1337, circleRest);


            return new ResponseEntity<>(schemaRest, HttpStatus.OK);

        } else {

            SchemaRest<CircleRestViewEntity> schemaRest = new SchemaRest<>(false, "zjebalo sie", 1337, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);

        }
    }

    //-------------------------------------------------------------------------------------------------------------
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

    //-------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/sub", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> subscribeCircle(@RequestHeader(value = "Token") String token, @RequestBody CircleSubbedModel subById) {

        if (circleRestViewRepository.exists(subById.getId())) {

            if (subById.isStatus()) {
                SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
                subscribedCircleEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
                subscribedCircleEntity.setCircleIdCircle(subById.getId());
                this.addCircleSubscriptionService.saveAndFlush(subscribedCircleEntity);

                SchemaRest<SubscribedCircleEntity> schemaRest = new SchemaRest<>(true, "Subbed circle", 1337, subscribedCircleEntity);


                return new ResponseEntity<>(schemaRest, HttpStatus.OK);

            } else {
                SubscribedCircleEntity subscribedCircleEntity = new SubscribedCircleEntity();
                subscribedCircleEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
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
    public ResponseEntity<SchemaRestList> searchCircle(@RequestHeader(value = "Token") String token, @RequestBody(required = false) CircleLookForModel lookForModel) {

        SchemaRestList<CircleRestViewEntity> schemaRestList;
        LimitedListGenerator<CircleRestViewEntity> listGenerator = new LimitedListGenerator<>();
        if (!lookForModel.getName().isEmpty() && lookForModel.getDescription().isEmpty())
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(circleRestViewRepository.findAllByNameContaining(lookForModel.getName()), lookForModel.getHowMany()));
        else if (lookForModel.getName().isEmpty() && !lookForModel.getDescription().isEmpty())
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(circleRestViewRepository.findAllByDescriptionContaining(lookForModel.getDescription()), lookForModel.getHowMany()));
        else if (!lookForModel.getName().isEmpty() && !lookForModel.getDescription().isEmpty())
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(circleRestViewRepository.findAllByNameContainingAndDescriptionContaining(lookForModel.getName(), lookForModel.getDescription()), lookForModel.getHowMany()));
        else
            schemaRestList = new SchemaRestList<>(false, "Strings are empty fix pl0x", 100, null);

        if (!schemaRestList.getData().isEmpty())
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        else {
            schemaRestList.setMessage("List is empty");
            schemaRestList.setErrorCode(101);
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        }


    }
}
