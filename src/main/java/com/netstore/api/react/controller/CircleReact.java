package com.netstore.api.react.controller;

import com.netstore.api.mobile.controller.CircleRest;
import com.netstore.model.API.mobile.SchemaRestList;
import com.netstore.model.API.mobile.circle.CircleWithAuthor;
import com.netstore.model.API.mobile.circle.LimitCircleModel;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddCircleService;
import com.netstore.model.service.AddCircleSubscriptionService;
import com.netstore.model.view.CircleRestViewEntity;
import com.netstore.model.view.UserRestViewEntity;
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

@RestController
@RequestMapping("/react/main")
public class CircleReact {

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
    @Autowired
    private CircleRest circleRest;




    @RequestMapping(value = "/getCircle", method = RequestMethod.POST)
    public ResponseEntity<List<CircleWithAuthor>> getLimitedCircle(Authentication auth) {


        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAllByPublishDateIsLessThanOrderByPublishDateDesc(new Timestamp(System.currentTimeMillis()));
        List<CircleWithAuthor> circleRestList = new ArrayList<>();

        for (CircleRestViewEntity i : circleEntityList) {
            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRestRepository.findByEmail(auth.getName()).getIdUser(), i.getIdCircle())) != null) {
                circleRestList.add(circleRest.generateCircleList(1, i));
            } else {
                circleRestList.add(circleRest.generateCircleList(0, i));
            }
        }
        //LimitedListGenerator<CircleWithAuthor> listGenerator = new LimitedListGenerator<>();


        return new ResponseEntity<>(circleRestList, HttpStatus.OK);


    }
}