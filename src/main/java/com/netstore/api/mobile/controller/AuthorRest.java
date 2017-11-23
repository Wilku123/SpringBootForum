package com.netstore.api.mobile.controller;

import com.netstore.model.API.SchemaRest;
import com.netstore.model.API.user.UserIdModel;
import com.netstore.model.view.UserRestViewEntity;
import com.netstore.model.repository.rest.UserRestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Master on 2017-09-06.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/author")
public class AuthorRest {

    @Autowired
    private UserRestRepository userRepository;

    @RequestMapping(value = "/one",method = RequestMethod.POST)
    public ResponseEntity<SchemaRest> getOneAuthor(Authentication auth, @RequestBody UserIdModel userIdModel)
    {
        if (userRepository.exists(userIdModel.getId()))
        {
            SchemaRest<UserRestViewEntity> schemaRest = new SchemaRest<>(true,"git gut",1337,userRepository.findByIdUser(userIdModel.getId()));
            return new ResponseEntity<>(schemaRest, HttpStatus.OK);
        }
        else
        {
            SchemaRest<UserRestViewEntity> schemaRest = new SchemaRest<>(false,"Author dosnt exist",101,null);
            return new ResponseEntity<>(schemaRest,HttpStatus.BAD_REQUEST);
        }
    }
}
