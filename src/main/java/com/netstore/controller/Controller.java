package com.netstore.controller;

import com.netstore.model.repository.CircleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    private CircleRepository circleRepository;



    @RequestMapping("/login")
        public String login(){
            return "login";
        }
    @RequestMapping("/logout")
    public String logout(){
            return "logout";
    }
    @RequestMapping("/")
    public  String index(){
        return "index";
    }
    @RequestMapping("/addCircle")
    public  String addCircle(){
        return "addCircle";
    }


}
