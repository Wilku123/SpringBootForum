package com.netstore.controller;

import com.netstore.model.entity.CircleEntity;
import com.netstore.model.view.CircleRestViewEntity;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddCircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Master on 2017-04-27.
 */
@org.springframework.stereotype.Controller
public class CircleController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CircleRestViewRepository circleRepository;
    @Autowired
    private AddCircleService addCircleService;

    LocalDate todayLocalDate = LocalDate.now();
    //java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    //Date date = new Date(timestamp.getTime());



    @RequestMapping("/circles")
    public String greeting(Model model) {

        List<CircleRestViewEntity> circleEntityList = circleRepository.findAll();
        List<String> descList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        List<String> publishDateList = new ArrayList<>();
        List<String> userNameList = new ArrayList<>();
        List<String> userLastNameList = new ArrayList<>();
        List<String> isSubbedList = new ArrayList<>();
        List<Long> countSubbedList = new ArrayList<>();
        List<Long> countTopicList = new ArrayList<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm yyyy.MM.dd");

        for (CircleRestViewEntity i:circleEntityList) {
            idList.add(i.getIdCircle());
            descList.add(i.getDescription());
            nameList.add(i.getName());
            publishDateList.add(simpleDateFormat.format(new Date(i.getPublishDate().getTime())));
            userNameList.add(userRepository.findOne(i.getUserIdUser()).getName());
            userLastNameList.add(userRepository.findOne(i.getUserIdUser()).getLastName());
            isSubbedList.add("true"); //TODO Modify to Session variable
            countSubbedList.add(i.getCountSubbed());
            countTopicList.add(i.getCountTopic());
        }

        model.addAttribute("circleDescription",descList);
        model.addAttribute("circleName", nameList);
        model.addAttribute("circlePublishDate", publishDateList);
        model.addAttribute("circleId",idList);
        model.addAttribute("circleCreatorName",userNameList);
        model.addAttribute("circleCreatorLastName",userLastNameList);
        model.addAttribute("circleIsSubbed",isSubbedList);
        model.addAttribute("circleCountSubbed",countSubbedList);
        model.addAttribute("circleTopicCount",countTopicList);
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
        circleEntity.setUuid(UUID.randomUUID().toString());
        circleEntity.setUserIdUser(1); // TODO current user from session
        this.addCircleService.saveAndFlush(circleEntity);
        return "redirect:/circles";
    }
}
