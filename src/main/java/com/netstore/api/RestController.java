package com.netstore.api;

import com.netstore.model.entity.UserEntity;
import com.netstore.model.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Master on 2017-04-22.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    LoginRepository loginRepository;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greet")
    public Greet greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greet(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping(path="/user", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>>  listUser(){

        return new ResponseEntity<List<UserEntity>>(loginRepository.findAll(), HttpStatus.OK);
    }

//    @RequestMapping(path="/user/{id}", method = RequestMethod.GET)
//    public ResponseEntity<UserEntity>  listUser(@PathVariable(value = "id") String id){
//        return new ResponseEntity<UserEntity>(loginRepository.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null), HttpStatus.OK);
//
//    }

//    @RequestMapping(path="/user", method = RequestMethod.POST)
//    public ResponseEntity<String>  listUser(@RequestBody User user){
//        return new ResponseEntity<String>("18", HttpStatus.OK);
//    }

}
