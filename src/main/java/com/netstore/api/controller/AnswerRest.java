package com.netstore.api.controller;

import com.netstore.model.API.SchemaRest;
import com.netstore.model.API.SchemaRestList;
import com.netstore.model.API.answer.AnswerIdModel;
import com.netstore.model.API.answer.AnswerLookForModel;
import com.netstore.model.API.answer.LimitAnswerModel;
import com.netstore.model.API.answer.NewAnswerModel;
import com.netstore.model.entity.AnswerEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.rest.UserRestRepository;
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

    private AnswerRestViewEntity generateAnswerList(int yours, AnswerRestViewEntity i) {

        Integer idAnswer = i.getIdAnswer();
        String content = i.getContent();
        Integer idTopic = i.getTopicIdTopic();
        Integer author = i.getUserIdUser();
        Timestamp publishDate = i.getPublishDate();
        String uuid = i.getUuid();


        AnswerRestViewEntity answerEntity = new AnswerRestViewEntity();

        answerEntity.setIdAnswer(idAnswer);
        answerEntity.setTopicIdTopic(idTopic);
        answerEntity.setContent(content);
        answerEntity.setUserIdUser(author);
        answerEntity.setPublishDate(publishDate);
        answerEntity.setUuid(uuid);
        answerEntity.setYours(yours);

        return answerEntity;
    }


    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> getOneAnswer(Authentication auth, @RequestBody AnswerIdModel answerIdModel) {

        if (answerRepository.exists(answerIdModel.getId())) {

            AnswerRestViewEntity answerEntity = answerRepository.findOne(answerIdModel.getId());

            SchemaRest<AnswerRestViewEntity> schemaRest = new SchemaRest<>(true, "git gut", 1337, answerEntity);

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
                AnswerRestViewEntity restViewEntity;
                restViewEntity = answerRepository.findOne(answerEntity.getIdAnswer());
                restViewEntity.setYours(1);

                SchemaRest<AnswerRestViewEntity> schemaRest = new SchemaRest<>(true, "git gut", 1337, restViewEntity);

                return new ResponseEntity<>(schemaRest, HttpStatus.OK);
            } else {
                SchemaRest<AnswerRestViewEntity> schemaRest = new SchemaRest<>(false, "content have less than 5 chars", 102, null);

                return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
            }
        } else {
            SchemaRest<AnswerRestViewEntity> schemaRest = new SchemaRest<>(false, "ID dosnt exsist in DB", 101, null);

            return new ResponseEntity<>(schemaRest, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/limitLess", method = RequestMethod.POST)
    public ResponseEntity<SchemaRestList> getLimitedAnswerLessThan(Authentication auth, @RequestBody LimitAnswerModel limitAnswerModel) {

        if (topicRestViewRepository.exists(limitAnswerModel.getId())) {
            if (limitAnswerModel.getHowMany() > 0) {
                List<AnswerRestViewEntity> answerEntityList = answerRepository.findAllByTopicIdTopicAndPublishDateIsLessThanEqualOrderByPublishDateDesc(limitAnswerModel.getId(), limitAnswerModel.getDate());
                List<AnswerRestViewEntity> answerRestList = new ArrayList<>();

                for (AnswerRestViewEntity i : answerEntityList) {
                    if (i.getUserIdUser() == credentialsRepository.findByToken(auth.getName()).getUserIdUser()) {
                        answerRestList.add(generateAnswerList(1, i));
                    } else {
                        answerRestList.add(generateAnswerList(0, i));
                    }

                }
                LimitedListGenerator<AnswerRestViewEntity> limitedListGenerator = new LimitedListGenerator<>();
                SchemaRestList<AnswerRestViewEntity> schemaRestList = new SchemaRestList<>(true, "git gut", 1337, limitedListGenerator.limitedList(answerRestList, limitAnswerModel.getHowMany()));

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
                List<AnswerRestViewEntity> answerEntityList = answerRepository.findAllByTopicIdTopicAndPublishDateIsGreaterThanEqualOrderByPublishDateDesc(limitAnswerModel.getId(), limitAnswerModel.getDate());
                List<AnswerRestViewEntity> answerRestList = new ArrayList<>();

                for (AnswerRestViewEntity i : answerEntityList) {
                    if (i.getUserIdUser() == credentialsRepository.findByToken(auth.getName()).getUserIdUser()) {
                        answerRestList.add(generateAnswerList(1, i));
                    } else {
                        answerRestList.add(generateAnswerList(0, i));
                    }

                }
                LimitedListGenerator<AnswerRestViewEntity> limitedListGenerator = new LimitedListGenerator<>();
                SchemaRestList<AnswerRestViewEntity> schemaRestList = new SchemaRestList<>(true, "git gut", 1337, limitedListGenerator.limitedList(answerRestList, limitAnswerModel.getHowMany()));

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

        SchemaRestList<AnswerRestViewEntity> schemaRestList;
        LimitedListGenerator<AnswerRestViewEntity> listGenerator = new LimitedListGenerator<>();
        if (!lookForModel.getContent().isEmpty())
            schemaRestList = new SchemaRestList<>(true, "", 1337, listGenerator.limitedList(answerRepository.findAllByContentContainingAndTopicIdTopic(lookForModel.getContent(),lookForModel.getId()), lookForModel.getHowMany()));
        else
            schemaRestList = new SchemaRestList<>(false, "String is empty fix pl0x", 102, null);

        if (schemaRestList.getData()!=null)
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
        else {
            schemaRestList.setMessage("List empty");
            schemaRestList.setErrorCode(103);
            return new ResponseEntity<>(schemaRestList, HttpStatus.OK);

        }

    }
}
