package com.netstore.api;

import com.netstore.model.AnswerEntity;
import com.netstore.model.repository.AnswerRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddAnswersService;
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
@RequestMapping(value = "/api/answer")
public class AnswerRest {

    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddAnswersService addAnswersService;

    @RequestMapping(value = "/one", method = RequestMethod.POST)
    public ResponseEntity<AnswerEntity> getOneAnswer(@RequestHeader(value = "Token") String token, @RequestParam(value = "id") Integer id) {

        AnswerEntity answerEntity = answerRepository.findOne(id);

        return new ResponseEntity<>(answerEntity, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<String> addAnswer(@RequestHeader(value = "Token") String token, @RequestParam(value = "content") String content, @RequestParam(value = "topicId") Integer topicId) {

        AnswerEntity topicEntity = new AnswerEntity();
        topicEntity.setTopicIdTopic(topicId);
        topicEntity.setContent(content);
        topicEntity.setUserIdUser(userRepository.findByToken(token).getIdUser());
        topicEntity.setPublishDate(new Timestamp(System.currentTimeMillis()));
        this.addAnswersService.saveAndFlush(topicEntity);

        return new ResponseEntity<>("Added Topic", HttpStatus.OK);
    }

    @RequestMapping(value = "/limit", method = RequestMethod.POST)
    public ResponseEntity<List<AnswerEntity>> getLimitedAnswer(@RequestHeader(value = "Token") String token, @RequestParam(value = "limit") Integer limit, @RequestParam(value = "topicId") Integer topicId) {

        List<AnswerEntity> answerEntityList = answerRepository.findAllByTopicIdTopic(topicId);

        if (answerEntityList.size() < limit)
        {
            return new ResponseEntity<>(answerEntityList.subList(answerEntityList.size(), answerEntityList.size()), HttpStatus.OK);
        }
        else if (limit < 0)
        {
            return new ResponseEntity<>(answerEntityList.subList(0, 0), HttpStatus.OK);
        }
        else if (answerEntityList.size() < limit + 10)
        {
            return new ResponseEntity<>(answerEntityList.subList(limit, answerEntityList.size()), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(answerEntityList.subList(limit, limit + 10), HttpStatus.OK);
        }
    }
}
