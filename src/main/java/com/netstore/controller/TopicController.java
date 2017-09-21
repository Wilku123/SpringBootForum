package com.netstore.controller;


import com.netstore.model.entity.CircleEntity;
import com.netstore.model.entity.TopicEntity;
import com.netstore.model.repository.TopicRepository;
import com.netstore.service.AddTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Master on 2017-05-20.
 */
@org.springframework.stereotype.Controller
@RequestMapping("/topic")
public class TopicController {

    private LocalDate todayLocalDate = LocalDate.now();
    private java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private AddTopicService addTopicService;
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
        topicEntity.setUuid(UUID.randomUUID().toString());
        topicEntity.setUserIdUser(1); //TODO set to current user from session
        this.addTopicService.saveAndFlush(topicEntity);
        return "redirect:/topic/{id}";
    }
}

