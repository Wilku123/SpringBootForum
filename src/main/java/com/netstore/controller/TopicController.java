//package com.netstore.controller;
//
//
//import com.netstore.model.entity.CircleEntity;
//import com.netstore.model.entity.SubscribedTopicEntity;
//import com.netstore.model.entity.TopicEntity;
//import com.netstore.model.repository.TopicRepository;
//import com.netstore.model.repository.UserRepository;
//import com.netstore.model.service.AddTopicService;
//import com.netstore.model.service.AddTopicSubscriptionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
///**
// * Created by Master on 2017-05-20.
// */
//@org.springframework.stereotype.Controller
//@RequestMapping("/topic")
//public class TopicController {
//
//    private LocalDate todayLocalDate = LocalDate.now();
//    private java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
//    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//    @Autowired
//    private TopicRepository topicRepository;
//    @Autowired
//    private AddTopicService addTopicService;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private AddTopicSubscriptionService addTopicSubscriptionService;
//
//    @GetMapping("/{id}")
//    public String getForm(@PathVariable Integer id,Model model) {
//        model.addAttribute("topicEntity",new TopicEntity());
//        CircleEntity circleEntity = new CircleEntity();
//        circleEntity.setIdCircle(id);
//
//        List<TopicEntity> topicEntityList = topicRepository.findAllByCircleByCircleIdCircle(circleEntity);
//        List<String> nameList = new ArrayList<>();
//        List<Timestamp> publishDateList = new ArrayList<>();
//        List<Integer> topicId = new ArrayList<>();
//
//        for (TopicEntity i:topicEntityList) {
//            nameList.add(i.getName());
//            publishDateList.add(i.getPublishDate());
//            topicId.add(i.getIdTopic());
//        }
//        model.addAttribute("topicName", nameList);
//        model.addAttribute("topicPublishDate", publishDateList);
//        model.addAttribute("topicId",topicId);
//        model.addAttribute("currentCircle",circleEntity.getIdCircle());
//        return "topics";
//    }
//    @PostMapping("/{id}/proccessAdding")
//    public String proccess(@PathVariable Integer id, @ModelAttribute TopicEntity topicEntity, BindingResult result, Authentication authentication){
//        topicEntity.setCircleIdCircle(id);
//        topicEntity.setPublishDate(timestamp);
//        topicEntity.setUuid(UUID.randomUUID().toString());
//        topicEntity.setUserIdUser(userRepository.findByEmail(authentication.getName()).getIdUser());
//        this.addTopicService.saveAndFlush(topicEntity);
//        SubscribedTopicEntity subscribedTopicEntity = new SubscribedTopicEntity();
//        subscribedTopicEntity.setUserIdUser(userRepository.findByEmail(authentication.getName()).getIdUser());
//        subscribedTopicEntity.setTopicIdTopic(topicEntity.getIdTopic());
//        this.addTopicSubscriptionService.saveAndFlush(subscribedTopicEntity);
//        return "redirect:/topic/{id}";
//    }
//}
//
