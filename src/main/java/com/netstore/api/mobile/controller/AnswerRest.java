package com.netstore.api.mobile.controller;

import com.netstore.model.API.mobile.SchemaRest;
import com.netstore.model.API.mobile.SchemaRestList;
import com.netstore.model.API.mobile.answer.*;
import com.netstore.model.entity.AnswerEntity;
import com.netstore.model.entity.EventEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddEventService;
import com.netstore.model.view.AnswerRestViewEntity;
import com.netstore.model.repository.rest.AnswerRestRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.service.AddAnswersService;
import com.netstore.model.view.UserRestViewEntity;
import com.netstore.utility.LimitedListGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Master on 2017-07-28.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/api/answer")
public class AnswerRest {

    @Autowired
    private AnswerRestRepository answerRepository;
    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private AddAnswersService addAnswersService;
    @Autowired
    private TopicRestViewRepository topicRestViewRepository;
    @Autowired
    private AddEventService addEventService;

    private AnswerWithAuthor generateAnswerList(int yours, AnswerRestViewEntity i) {

        Integer idAnswer = i.getIdAnswer();
        String content = i.getContent();
        Integer idTopic = i.getTopicIdTopic();
        Integer author = i.getUserIdUser();
        Timestamp publishDate = i.getPublishDate();
        String uuid = i.getUuid();

        AnswerWithAuthor answerEntity = new AnswerWithAuthor();

        answerEntity.setIdAnswer(idAnswer);
        answerEntity.setTopicIdTopic(idTopic);
        answerEntity.setContent(content);
        answerEntity.setUserIdUser(author);
        answerEntity.setPublishDate(publishDate);
        answerEntity.setUuid(uuid);
        answerEntity.setYours(yours);

        UserRestViewEntity userEntity;

        userEntity = userRestRepository.findByIdUser(answerEntity.getUserIdUser());
        answerEntity.setAuthor(userEntity);
        return answerEntity;
    }


    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> getOneAnswer(Authentication auth, @RequestBody AnswerIdModel answerIdModel) {

        if (answerRepository.exists(answerIdModel.getId())) {

            AnswerRestViewEntity answerEntity = answerRepository.findOne(answerIdModel.getId());
            AnswerWithAuthor answerWithAuthor;
            if (credentialsRepository.findByToken(auth.getName()).getUserIdUser() == answerEntity.getUserIdUser())
            {
                answerWithAuthor = generateAnswerList(1,answerEntity);
            }else {
                answerWithAuthor = generateAnswerList(0,answerEntity);
            }

            SchemaRest<AnswerWithAuthor> schemaRest = new SchemaRest<>(true, "git gut", 1337, answerWithAuthor);

            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        } else {
            SchemaRest<AnswerRest> schemaRest = new SchemaRest<>(false, "answer dosnt exist", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> addAnswer(Authentication auth, @RequestBody NewAnswerModel newAnswerModel) {

        if (topicRestViewRepository.exists(newAnswerModel.getId())) {
            if (newAnswerModel.getContent().length() > 5) {
                AnswerEntity answerEntity = new AnswerEntity();
                answerEntity.setTopicIdTopic(newAnswerModel.getId());
                answerEntity.setContent(newAnswerModel.getContent());
                answerEntity.setUserIdUser(credentialsRepository.findByToken(auth.getName()).getUserIdUser());
                answerEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
                answerEntity.setUuid(newAnswerModel.getUuid());
                this.addAnswersService.saveAndFlush(answerEntity);

                EventEntity eventEntity = new EventEntity();
                eventEntity.setType("Answer");
                eventEntity.setEntityId(answerEntity.getIdAnswer());
                eventEntity.setAuthorId(answerEntity.getUserIdUser());
                eventEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
                this.addEventService.saveAndFlush(eventEntity);

                AnswerRestViewEntity restViewEntity;
                restViewEntity = answerRepository.findOne(answerEntity.getIdAnswer());
                AnswerWithAuthor answerWithAuthor = generateAnswerList(1,restViewEntity);


                SchemaRest<AnswerWithAuthor> schemaRest = new SchemaRest<>(true, "git gut", 1337, answerWithAuthor);

                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            } else {
                SchemaRest<AnswerRestViewEntity> schemaRest = new SchemaRest<>(false, "content have less than 5 chars", 102, null);

                return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
            }
        } else {
            SchemaRest<AnswerWithAuthor> schemaRest = new SchemaRest<>(false, "ID dosnt exsist in DB", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/limitLess", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> getLimitedAnswerLessThan(Authentication auth, @RequestBody LimitAnswerModel limitAnswerModel) {

        if (topicRestViewRepository.exists(limitAnswerModel.getId())) {
            if (limitAnswerModel.getHowMany() > 0) {
                List<AnswerRestViewEntity> answerEntityList = answerRepository.findAllByTopicIdTopicAndPublishDateIsLessThanOrderByPublishDateDesc(limitAnswerModel.getId(), limitAnswerModel.getDate());
                List<AnswerWithAuthor> answerRestList = new ArrayList<>();

                for (AnswerRestViewEntity i : answerEntityList) {
                    if (i.getUserIdUser() == credentialsRepository.findByToken(auth.getName()).getUserIdUser()) {
                        answerRestList.add(generateAnswerList(1, i));
                    } else {
                        answerRestList.add(generateAnswerList(0, i));
                    }

                }
                LimitedListGenerator<AnswerWithAuthor> limitedListGenerator = new LimitedListGenerator<>();
                SchemaRestList<AnswerWithAuthor> schemaRestList = new SchemaRestList<>(true, "git gut", 1337, limitedListGenerator.limitedList(answerRestList, limitAnswerModel.getHowMany()));

                if (!schemaRestList.getData().isEmpty())
                    return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
                else {
                    schemaRestList.setErrorCode(103);
                    schemaRestList.setMessage("List Is empty");
                    return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
                }
            } else {
                SchemaRestList<AnswerRestViewEntity> schemaRest = new SchemaRestList<>(false, "howMany is less than 1", 102, null);
                return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
            }

        } else {
            SchemaRestList<AnswerRestViewEntity> schemaRest = new SchemaRestList<>(false, "Topic dosnt exist", 101, null);
            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/limitGreater", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> getLimitedAnswerGreaterThan(Authentication auth, @RequestBody LimitAnswerModel limitAnswerModel) {

        if (topicRestViewRepository.exists(limitAnswerModel.getId())) {
            if (limitAnswerModel.getHowMany() > 0) {
                List<AnswerRestViewEntity> answerEntityList = answerRepository.findAllByTopicIdTopicAndPublishDateIsGreaterThanOrderByPublishDateDesc(limitAnswerModel.getId(), limitAnswerModel.getDate());
                List<AnswerWithAuthor> answerRestList = new ArrayList<>();

                for (AnswerRestViewEntity i : answerEntityList) {
                    if (i.getUserIdUser() == credentialsRepository.findByToken(auth.getName()).getUserIdUser()) {
                        answerRestList.add(generateAnswerList(1, i));
                    } else {
                        answerRestList.add(generateAnswerList(0, i));
                    }

                }
                LimitedListGenerator<AnswerWithAuthor> limitedListGenerator = new LimitedListGenerator<>();
                SchemaRestList<AnswerWithAuthor> schemaRestList = new SchemaRestList<>(true, "git gut", 1337, limitedListGenerator.limitedList(answerRestList, limitAnswerModel.getHowMany()));

                if (!schemaRestList.getData().isEmpty())
                    return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
                else {
                    schemaRestList.setErrorCode(103);
                    schemaRestList.setMessage("List Is empty");
                    return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
                }
            } else {
                SchemaRestList<AnswerRestViewEntity> schemaRest = new SchemaRestList<>(false, "howMany is less than 1", 102, null);
                return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
            }

        } else {
            SchemaRestList<AnswerRestViewEntity> schemaRest = new SchemaRestList<>(false, "Topic dosnt exist", 101, null);
            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> searchCircle(Authentication auth, @RequestBody AnswerLookForModel lookForModel) {

//        SchemaRestList<AnswerRestViewEntity> schemaRestList;
//        LimitedListGenerator<AnswerRestViewEntity> listGenerator = new LimitedListGenerator<>();
//        if (!lookForModel.getContent().isEmpty())
//            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(answerRepository.findAllByContentContainingAndTopicIdTopic(lookForModel.getContent(),lookForModel.getId()), lookForModel.getHowMany()));
//        else
//            schemaRestList = new SchemaRestList<>(false, "String is empty fix pl0x", 102, null);
//
//        if (schemaRestList.getData()!=null)
//            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
//        else {
//            schemaRestList.setMessage("List empty");
//            schemaRestList.setErrorCode(103);
//            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
//
//        }


        SchemaRestList<AnswerWithAuthor> schemaRestList;
        LimitedListGenerator<AnswerWithAuthor> listGenerator = new LimitedListGenerator<>();
        List<AnswerWithAuthor> answerRestList = new ArrayList<>();
        List<AnswerRestViewEntity> answerRestViewEntities;



        if (!lookForModel.getContent().isEmpty()) {
            answerRestViewEntities = answerRepository.findAllByContentContainingAndTopicIdTopic(lookForModel.getContent(), lookForModel.getId());
            for (AnswerRestViewEntity i : answerRestViewEntities) {
                if (i.getUserIdUser() == credentialsRepository.findByToken(auth.getName()).getUserIdUser()) {
                    answerRestList.add(generateAnswerList(1, i));
                } else {
                    answerRestList.add(generateAnswerList(0, i));
                }

            }
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(answerRestList, lookForModel.getHowMany()));

        } else
            schemaRestList = new SchemaRestList<>(false, "String is empty fix pl0x", 102, null);

        if (schemaRestList.getData() != null)
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        else {
            schemaRestList.setMessage("List empty");
            schemaRestList.setErrorCode(103);
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);

        }

    }
}
