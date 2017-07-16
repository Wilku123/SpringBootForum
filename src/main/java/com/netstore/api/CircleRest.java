package com.netstore.api;

import com.netstore.model.TopicEntity;
import com.netstore.model.repository.CircleRepository;
import com.netstore.model.repository.TopicRepository;
import com.netstore.model.repository.UserSubCircle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Master on 2017-04-27.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class CircleRest {

    @Autowired
    UserSubCircle userSubCircle;
    @Autowired
    CircleRepository circleRepository;
    @Autowired
    TopicRepository topicRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TopicEntity>> listTopics(){
        return new ResponseEntity<List<TopicEntity>>(topicRepository.findAll(), HttpStatus.OK);
    }
    @RequestMapping(value = "/kek",method = RequestMethod.GET)
    public ResponseEntity<Integer> howManySubs(){
        return new ResponseEntity<Integer>(userSubCircle.getCount(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TopicEntity> getParty(@PathVariable Integer id) {
            return new ResponseEntity<>(topicRepository.findOne(id), HttpStatus.OK);

    }

}
