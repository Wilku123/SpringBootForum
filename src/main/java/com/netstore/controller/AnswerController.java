//package com.netstore.controller;
//
//import com.netstore.model.entity.AnswerEntity;
//import com.netstore.model.entity.TopicEntity;
//import com.netstore.model.entity.UserEntity;
//import com.netstore.model.repository.AnswerRepository;
//import com.netstore.model.repository.TopicRepository;
//import com.netstore.model.repository.UserRepository;
//import com.netstore.model.service.AddAnswersService;
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
// * Created by Master on 2017-05-21.
// */
//@org.springframework.stereotype.Controller
//@RequestMapping("/topic")
//public class AnswerController {
//
//    @Autowired
//    private AddAnswersService addAnswersService;
//    @Autowired
//    private AnswerRepository answerRepository;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private TopicRepository topicRepository;
//
//    LocalDate todayLocalDate = LocalDate.now();
//
//    java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
//    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//    @GetMapping("/{name}/{id}")
//    public String answers(@PathVariable Integer id, Model model) {
//        model.addAttribute("answerEntity",new AnswerEntity());
//        TopicEntity topicEntity = new TopicEntity();
//        topicEntity.setIdTopic(id);
//        List<AnswerEntity> answerEntityList = answerRepository.findAllByTopicByTopicIdTopic(topicEntity);
//        List<String> contentList = new ArrayList<>();
//        List<String> userList = new ArrayList<>();
//        List<Timestamp> publishDateList = new ArrayList<>();
//
//        List<Integer> answerId = new ArrayList<>();
//        for (AnswerEntity i:answerEntityList) {
//            answerId.add(i.getIdAnswer());
//            contentList.add(i.getContent());
//            publishDateList.add(i.getPublishDate());
//            UserEntity userEntity = new UserEntity();
//            userEntity.setIdUser(i.getUserIdUser());
//            userList.add(userRepository.findAllByIdUser(userEntity.getIdUser()).getName());
//
//        }
//        model.addAttribute("answerContent", contentList);
//        model.addAttribute("answerPublishDate", publishDateList);
//        model.addAttribute("userList",userList);
//        model.addAttribute("topicId",id);
//        model.addAttribute("idAnswer",answerId);
//        return "answers";
//    }
//    @PostMapping("{name}/{id}/proccessAddingAnswer")
//    public String proccess(@PathVariable Integer id, @ModelAttribute AnswerEntity answerEntity, BindingResult result, Authentication authentication){
//        answerEntity.setTopicIdTopic(id);
////        answerEntity.setTopicByTopicIdTopic();
//        answerEntity.setPublishDate(timestamp);
//        answerEntity.setUuid(UUID.randomUUID().toString());
//        answerEntity.setUserIdUser(userRepository.findByEmail(authentication.getName()).getIdUser());
//        this.addAnswersService.saveAndFlush(answerEntity);
//        return "redirect:/topic/{name}/{id}";
//    }
//}
