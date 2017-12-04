//package com.netstore.controller;
//
//import com.netstore.model.entity.UserEntity;
//import com.netstore.model.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.*;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
///**
// * Created by Master on 2017-09-22.
// */
//@org.springframework.stereotype.Controller
//public class NavbarController {
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/navbar")
//    public String getUserName(Model model, Authentication authentication) {
//        UserEntity userEntity = userRepository.findFirstByEmail(authentication.getName());
//        //String nameAndLastName = userEntity.getName()+" "+ userEntity.getLastName();
//        model.addAttribute("userInfo",userEntity.getName() +" "+ userEntity.getLastName());
//        return "fragments/navbar :: navbar";
//    }
//}
