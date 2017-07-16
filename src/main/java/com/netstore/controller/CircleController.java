package com.netstore.controller;

import com.netstore.model.CircleEntity;
import com.netstore.model.UserEntity;
import com.netstore.model.repository.CircleRepository;
import com.netstore.service.AddCircleService;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 2017-04-27.
 */
@org.springframework.stereotype.Controller
public class CircleController {

    @Autowired
    private CircleRepository circleRepository;
    @Autowired
    private AddCircleService addCircleService;

    LocalDate todayLocalDate = LocalDate.now();
    //java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    //Date date = new Date(timestamp.getTime());



    @RequestMapping("/circles")
    public String greeting(Model model) {

        List<CircleEntity> circleEntityList = circleRepository.findAll();
        List<String> descList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        List<String> publishDateList = new ArrayList<>();
        List<String> userNameList = new ArrayList<>();
        List<String> userLastNameList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm yyyy.MM.dd");

        for (CircleEntity i:circleEntityList) {
            idList.add(i.getIdCircle());
            descList.add(i.getDescription());
            nameList.add(i.getName());
            publishDateList.add(simpleDateFormat.format(new Date(i.getPublishDate().getTime())));
            userNameList.add(i.getUserByUserIdUser().getName());
            userLastNameList.add(i.getUserByUserIdUser().getLastName());
        }

        model.addAttribute("circleDescription",descList);
        model.addAttribute("circleName", nameList);
        model.addAttribute("circlePublishDate", publishDateList);
        model.addAttribute("circleId",idList);
        model.addAttribute("circleCreatorName",userNameList);
        model.addAttribute("circleCreatorLastName",userLastNameList);
        //model.addAttribute("qrCodeCircle",);

        return "circles";
    }
    @GetMapping("/addCircle")
    public String getForm(Model model) {
        model.addAttribute("circleEntity",new CircleEntity());
        return "addCircle";
    }
    @PostMapping("/proccessAdding")
    public String proccess(@Valid @ModelAttribute CircleEntity circleEntity, BindingResult result){
        circleEntity.setPublishDate(timestamp);
        circleEntity.setUserIdUser(1); // TODO current user from session
        this.addCircleService.saveAndFlush(circleEntity);
        return "redirect:/circles";
    }
}