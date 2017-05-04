package com.netstore.controller;

import com.netstore.model.repository.CircleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Master on 2017-04-27.
 */
@org.springframework.stereotype.Controller
public class CircleController {
    @Autowired
    private CircleRepository circleRepository;

    @RequestMapping("/greeting")
    public String greeting(Model model) {
        model.addAttribute("circleDescription",circleRepository.getByDescription());
        model.addAttribute("circleName", circleRepository.getByName());
        model.addAttribute("circlePublishDate", circleRepository.getByPublishDate());
        return "greeting";
    }
}
