//package com.netstore.controller;
//
//import com.netstore.model.CircleEntity;
//import com.netstore.model.service.AddCircleService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.time.LocalDate;
//
//
///**
// * Created by Master on 2017-04-27.
// */
//@org.springframework.stereotype.Controller
//public class AddCircle {
//
//    @Autowired
//    AddCircleService addCircleService;
//
//
//    LocalDate todayLocalDate = LocalDate.now();
//
//    java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
////    @RequestMapping(method= RequestMethod.POST)
////    public String add(@ModelAttribute("circleForm")CircleEntity circleEntity, Model model) {
////
////        model.addAttribute("circleForm");
////        model.addAttribute("descriptionText");
////        return "";
////    }
//    @GetMapping("/addCircle")
//    public String getForm(Model model) {
//        model.addAttribute("circleEntity",new CircleEntity());
//        return "addCircle";
//    }
//    @PostMapping("/proccessAdding")
//    public String proccess(@Valid @ModelAttribute CircleEntity circleEntity, BindingResult result){
//        circleEntity.setPublishDate(sqlDate);
//        this.addCircleService.saveAndFlush(circleEntity);
//        return "redirect:/circles.html";
//    }
//}
