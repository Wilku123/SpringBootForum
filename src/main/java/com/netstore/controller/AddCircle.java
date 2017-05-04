package com.netstore.controller;

import com.netstore.model.CircleEntity;
import com.netstore.model.repository.CircleRepository;
import com.netstore.model.repository.service.AddCircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.Date;
import java.time.LocalDate;

import static java.lang.Math.toIntExact;


/**
 * Created by Master on 2017-04-27.
 */
@org.springframework.stereotype.Controller
public class AddCircle {

    @Autowired
    AddCircleService addCircleService;


    LocalDate todayLocalDate = LocalDate.now();

    java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
//    @RequestMapping(method= RequestMethod.POST)
//    public String add(@ModelAttribute("circleForm")CircleEntity circleEntity, Model model) {
//
//        model.addAttribute("circleForm");
//        model.addAttribute("descriptionText");
//        return "";
//    }
    @RequestMapping("/processAddCircleForm")
    public String proccess(){
        CircleEntity circleEntity = new CircleEntity();
        circleEntity.setName("Topkek");
        circleEntity.setDescription("descriptionText");
        circleEntity.setPublishDate(sqlDate);

        this.addCircleService.saveAndFlush(circleEntity);
        return "redirect:/greeting.html";
    }
}
