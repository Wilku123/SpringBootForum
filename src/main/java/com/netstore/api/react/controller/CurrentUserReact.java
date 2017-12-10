package com.netstore.api.react.controller;

import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.view.UserRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/react/main")
public class CurrentUserReact {
    @Autowired
    private UserRestRepository userRestRepository;

    @PostMapping("/showUser")
    public ResponseEntity<UserRestViewEntity> showCurrentUser (Authentication authentication)
    {

        return new ResponseEntity<>(userRestRepository.findByEmail(authentication.getName()), HttpStatus.OK);
    }
}
