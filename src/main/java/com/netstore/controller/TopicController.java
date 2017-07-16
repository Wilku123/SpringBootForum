package com.netstore.controller;


import com.netstore.model.CircleEntity;
import com.netstore.model.TopicEntity;
import com.netstore.model.repository.TopicRepository;
import com.netstore.service.AddTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 2017-05-20.
 */
@org.springframework.stereotype.Controller
@RequestMapping("/topic")
public class TopicController {

    LocalDate todayLocalDate = LocalDate.now();
    java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    AddTopicService addTopicService;
    @GetMapping("/{id}")
    public String getForm(@PathVariable Integer id,Model model) {
        model.addAttribute("topicEntity",new TopicEntity());
        CircleEntity circleEntity = new CircleEntity();
        circleEntity.setIdCircle(id);

        List<TopicEntity> topicEntityList = topicRepository.findAllByCircleByCircleIdCircle(circleEntity);
        List<String> nameList = new ArrayList<>();
        List<Timestamp> publishDateList = new ArrayList<>();
        List<Integer> topicId = new ArrayList<>();

        for (TopicEntity i:topicEntityList) {
            nameList.add(i.getName());
            publishDateList.add(i.getPublishDate());
            topicId.add(i.getIdTopic());
        }
        model.addAttribute("topicName", nameList);
        model.addAttribute("topicPublishDate", publishDateList);
        model.addAttribute("topicId",topicId);
        model.addAttribute("currentCircle",circleEntity.getIdCircle());
        return "topics";
    }
    @PostMapping("/{id}/proccessAdding")
    public String proccess(@PathVariable Integer id, @ModelAttribute TopicEntity topicEntity, BindingResult result){
        topicEntity.setCircleIdCircle(id);
        topicEntity.setPublishDate(timestamp);
        topicEntity.setUserIdUser(1); //TODO set to current user from session
        this.addTopicService.saveAndFlush(topicEntity);
        return "redirect:/topic/{id}";
    }
}

