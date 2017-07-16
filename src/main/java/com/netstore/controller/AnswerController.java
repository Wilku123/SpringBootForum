package com.netstore.controller;

import com.netstore.model.AnswerEntity;
import com.netstore.model.TopicEntity;
import com.netstore.model.UserEntity;
import com.netstore.model.repository.AnswerRepository;
import com.netstore.model.repository.TopicRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master on 2017-05-21.
 */
@org.springframework.stereotype.Controller
@RequestMapping("/topic")
public class AnswerController {

    @Autowired
    private AddAnswersService addAnswersService;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;

    LocalDate todayLocalDate = LocalDate.now();

    java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @GetMapping("/{name}/{id}")
    public String answers(@PathVariable Integer id, Model model) {
        model.addAttribute("answerEntity",new AnswerEntity());
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setIdTopic(id);
        List<AnswerEntity> answerEntityList = answerRepository.findAllByTopicByTopicIdTopic(topicEntity);
        List<String> contentList = new ArrayList<>();
        List<String> userList = new ArrayList<>();
        List<Timestamp> publishDateList = new ArrayList<>();

        List<Integer> answerId = new ArrayList<>();
        for (AnswerEntity i:answerEntityList) {
            answerId.add(i.getIdAnswer());
            contentList.add(i.getContent());
            publishDateList.add(i.getPublishDate());
            UserEntity userEntity = new UserEntity();
            userEntity.setIdUser(i.getUserIdUser());
            userList.add(userRepository.findAllByIdUser(userEntity.getIdUser()).getName());

        }
        model.addAttribute("answerContent", contentList);
        model.addAttribute("answerPublishDate", publishDateList);
        model.addAttribute("userList",userList);
        model.addAttribute("topicId",id);
        model.addAttribute("idAnswer",answerId);
        return "answers";
    }
    @PostMapping("{name}/{id}/proccessAddingAnswer")
    public String proccess(@PathVariable Integer id, @ModelAttribute AnswerEntity answerEntity, BindingResult result){
        answerEntity.setTopicIdTopic(id);
        answerEntity.setPublishDate(timestamp);
        answerEntity.setUserIdUser(1);
        this.addAnswersService.saveAndFlush(answerEntity);
        return "redirect:/topic/{name}/{id}";
    }
}
