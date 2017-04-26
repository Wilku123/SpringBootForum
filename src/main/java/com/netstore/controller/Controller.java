package com.netstore.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

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



}
