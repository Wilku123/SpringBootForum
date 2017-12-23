package com.netstore.api.react.controller;

import com.netstore.model.API.react.ReactStatus;
import com.netstore.model.entity.UserEntity;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.view.UserRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/react/main")
public class UserReact {

    @Autowired
    private UserRestRepository userRepository;

    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    public ResponseEntity<UserRestViewEntity> getOneAuthor(Authentication auth, @RequestBody UserEntity userEntity)
    {

            UserRestViewEntity userRestViewEntity =userRepository.findByIdUser(userEntity.getIdUser());
            return new ResponseEntity<>(userRestViewEntity, HttpStatus.OK);


    }
}
