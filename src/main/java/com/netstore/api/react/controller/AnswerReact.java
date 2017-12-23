package com.netstore.api.react.controller;

import com.netstore.api.mobile.controller.AnswerRest;
import com.netstore.model.API.mobile.answer.AnswerWithAuthor;
import com.netstore.model.API.react.ReactStatus;
import com.netstore.model.entity.AnswerEntity;
import com.netstore.model.entity.EventEntity;
import com.netstore.model.entity.TopicEntity;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.repository.rest.AnswerRestRepository;
import com.netstore.model.repository.rest.TopicRestViewRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddAnswersService;
import com.netstore.model.service.AddEventService;
import com.netstore.model.view.AnswerRestViewEntity;
import com.netstore.utility.LimitedListGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
public class AnswerReact {

    @Autowired
    private AnswerRestRepository answerRepository;
    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private AddAnswersService addAnswersService;
    @Autowired
    private TopicRestViewRepository topicRestViewRepository;
    @Autowired
    private AddEventService addEventService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRest answerRest;


    @RequestMapping(value = "/getAnswers", method = RequestMethod.POST)
    public ResponseEntity<List<AnswerWithAuthor>> getAnswers(Authentication auth, @RequestBody AnswerEntity answerEntity) {

//        if (topicRestViewRepository.exists(topicEntity.getIdTopic())) {
                List<AnswerRestViewEntity> answerEntityList = answerRepository.findAllByTopicIdTopicAndPublishDateIsLessThanOrderByPublishDate(answerEntity.getTopicIdTopic(), new Timestamp(System.currentTimeMillis()));
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
    @RequestMapping(value = "/addAnswer", method = RequestMethod.POST)
    public ResponseEntity<?> addAnswer(Authentication auth, @RequestBody AnswerEntity newAnswerModel) {

        ReactStatus reactStatus = new ReactStatus();
        if (topicRestViewRepository.exists(newAnswerModel.getTopicIdTopic())) {
            if (newAnswerModel.getContent().length() > 2
                    && newAnswerModel.getContent().length() < 250) {
                AnswerEntity answerEntity = new AnswerEntity();
                answerEntity.setTopicIdTopic(newAnswerModel.getTopicIdTopic());
                answerEntity.setContent(newAnswerModel.getContent());
                answerEntity.setUserIdUser(userRepository.findByEmail(auth.getName()).getIdUser());
                answerEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
                answerEntity.setUuid(UUID.randomUUID().toString());
                this.addAnswersService.saveAndFlush(answerEntity);

                EventEntity eventEntity = new EventEntity();
                eventEntity.setType("Answer");
                eventEntity.setEntityId(answerEntity.getIdAnswer());
                eventEntity.setAuthorId(answerEntity.getUserIdUser());
                eventEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
                this.addEventService.saveAndFlush(eventEntity);

                AnswerRestViewEntity restViewEntity;
                restViewEntity = answerRepository.findOne(answerEntity.getIdAnswer());
                AnswerWithAuthor answerWithAuthor = answerRest.generateAnswerList(1,restViewEntity);


                return new ResponseEntity<>(answerWithAuthor, HttpStatus.OK);
            } else {
                reactStatus.setStatus(false);
                return new ResponseEntity<>(reactStatus, HttpStatus.BAD_REQUEST);
            }
        } else {

            return new ResponseEntity<>(reactStatus, HttpStatus.BAD_REQUEST);
        }

    }
}
