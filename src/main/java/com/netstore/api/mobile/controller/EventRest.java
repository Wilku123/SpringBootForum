package com.netstore.api.mobile.controller;

import com.netstore.model.API.mobile.SchemaRestList;
import com.netstore.model.API.mobile.answer.AnswerWithAuthor;
import com.netstore.model.API.mobile.circle.CircleWithAuthor;
import com.netstore.model.API.mobile.events.EventWithEntityAndUser;
import com.netstore.model.API.mobile.topic.TopicWithAuthor;
import com.netstore.model.entity.EventEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.repository.SubscribedTopicRepository;
import com.netstore.model.repository.rest.*;
import com.netstore.model.view.AnswerRestViewEntity;
import com.netstore.model.view.CircleRestViewEntity;
import com.netstore.model.view.TopicRestViewEntity;
import com.netstore.model.view.UserRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EventRest {

    @Autowired
    private EventRestRepository eventRestRepository;
    @Autowired
    private AnswerRestRepository answerRestRepository;
    @Autowired
    private TopicRestViewRepository topicRestViewRepository;
    @Autowired
    private CircleRestViewRepository circleRestViewRepository;
    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private SubscribedTopicRepository subscribedTopicRepository;
    @Autowired
    private SubscribedCircleRepository subscribedCircleRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;

    private EventWithEntityAndUser fillEventEntityAuthor(EventEntity i, Authentication auth) {
        Integer idEvent = i.getIdEvent();
        String type = i.getType();
        Integer idEntity = i.getEntityId();
        Integer idAuthor = i.getAuthorId();
        Timestamp timestamp = i.getPublishDate();
        UserRestViewEntity userRestViewEntity;
        userRestViewEntity = userRestRepository.findByIdUser(idAuthor);


//        if (type.equals("Answer")) {
//            EventWithEntityAndUser<AnswerWithAuthor> entityAndUser =new EventWithEntityAndUser<>();
//            AnswerRestViewEntity answerRestViewEntity=answerRestRepository.findByIdAnswer(idEntity);
//            AnswerWithAuthor answerWithAuthor = new AnswerWithAuthor();
//            answerWithAuthor.setIdAnswer(answerRestViewEntity.getIdAnswer());
//            answerWithAuthor.setContent(answerRestViewEntity.getContent());
//            answerWithAuthor.setPublishDate(answerRestViewEntity.getPublishDate());
//            answerWithAuthor.setUserIdUser(answerRestViewEntity.getUserIdUser());
//            answerWithAuthor.setTopicIdTopic(answerRestViewEntity.getTopicIdTopic());
//            answerWithAuthor.setUuid(answerRestViewEntity.getUuid());
//            answerWithAuthor.setAuthor(userRestViewEntity);
//
//            if (i.getAuthorId() == credentialsRepository.findByToken(auth.getName()).getUserIdUser()) {
//                answerWithAuthor.setYours(1);
//            } else {
//                answerWithAuthor.setYours(0);
//            }
//
//            entityAndUser.setData(answerWithAuthor);
//
//            entityAndUser.setIdEvent(idEvent);
//            entityAndUser.setType(type);
//            entityAndUser.setIdAuthor(idAuthor);
//            entityAndUser.setPublishDate(timestamp);
//            return entityAndUser;
//
//        }else
        if (type.equals("Topic")) {
            EventWithEntityAndUser<TopicWithAuthor> entityAndUser = new EventWithEntityAndUser<>();
            TopicRestViewEntity topicRestViewEntity = topicRestViewRepository.findByIdTopic(idEntity);

            TopicWithAuthor topicWithAuthor = new TopicWithAuthor();
            topicWithAuthor.setIdTopic(topicRestViewEntity.getIdTopic());
            topicWithAuthor.setName(topicRestViewEntity.getName());
            topicWithAuthor.setPublishDate(topicRestViewEntity.getPublishDate());
            topicWithAuthor.setUserIdUser(topicRestViewEntity.getUserIdUser());
            topicWithAuthor.setCircleIdCircle(topicRestViewEntity.getCircleIdCircle());
            topicWithAuthor.setUuid(topicRestViewEntity.getUuid());
            topicWithAuthor.setDescription(topicRestViewEntity.getDescription());
            topicWithAuthor.setAuthor(userRestViewEntity);
            topicWithAuthor.setCountAnswer(topicRestViewEntity.getCountAnswer());
            topicWithAuthor.setCountSubbed(topicRestViewEntity.getCountSubbed());

            if ((subscribedTopicRepository.findByUserIdUserAndTopicIdTopic(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), topicRestViewEntity.getIdTopic())) != null) {
                topicWithAuthor.setIsSub(1);
            } else {
                topicWithAuthor.setIsSub(0);
            }

            entityAndUser.setData(topicWithAuthor);
            entityAndUser.setIdEvent(idEvent);
            entityAndUser.setType(type);
            entityAndUser.setIdAuthor(idAuthor);
            entityAndUser.setPublishDate(timestamp);
            return entityAndUser;
        } if(type.equals("Circle")) {
            EventWithEntityAndUser<CircleWithAuthor> entityAndUser = new EventWithEntityAndUser<>();
            CircleRestViewEntity circleRestViewEntity = circleRestViewRepository.findByIdCircle(idEntity);

            CircleWithAuthor circleWithAuthor = new CircleWithAuthor();

            circleWithAuthor.setIdCircle(circleRestViewEntity.getIdCircle());
            circleWithAuthor.setName(circleRestViewEntity.getName());
            circleWithAuthor.setPublishDate(circleRestViewEntity.getPublishDate());
            circleWithAuthor.setDescription(circleRestViewEntity.getDescription());
            circleWithAuthor.setUserIdUser(circleRestViewEntity.getUserIdUser());
            circleWithAuthor.setUuid(circleRestViewEntity.getUuid());
            circleWithAuthor.setCountSubbed(circleRestViewEntity.getCountSubbed());
            circleWithAuthor.setCountTopic(circleRestViewEntity.getCountTopic());
            circleWithAuthor.setAuthor(userRestViewEntity);


            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(credentialsRepository.findByToken(auth.getName()).getUserIdUser(), i.getEntityId())) != null) {
                circleWithAuthor.setIsSub(1);
            } else {
                circleWithAuthor.setIsSub(0);
            }


            entityAndUser.setData(circleWithAuthor);
            entityAndUser.setIdEvent(idEvent);
            entityAndUser.setType(type);
            entityAndUser.setPublishDate(timestamp);
            entityAndUser.setIdAuthor(idAuthor);
            return entityAndUser;
        }
        else {
            return null;
        }


    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public ResponseEntity<SchemaRestList<EventWithEntityAndUser>> getEvents(Authentication authentication) {
        List<EventEntity> eventList = eventRestRepository.findTop30ByOrderByPublishDateDesc();
        List<EventWithEntityAndUser> eventWithEntityAndUserList = new ArrayList<>();
        for (EventEntity i : eventList) {
            if(!i.getType().equals("Answer"))
            eventWithEntityAndUserList.add(fillEventEntityAuthor(i, authentication));
        }

        SchemaRestList<EventWithEntityAndUser> schemaRestList = new SchemaRestList<>(true, "Wydarzenia", 1337, eventWithEntityAndUserList);
        return new ResponseEntity<>(schemaRestList, HttpStatus.OK);
    }
}
