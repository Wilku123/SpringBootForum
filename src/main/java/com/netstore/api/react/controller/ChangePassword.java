package com.netstore.api.react.controller;

import com.netstore.model.API.react.entry.ReactStatus;
import com.netstore.model.entity.UserEntity;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.service.AddUserService;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/react")
public class ChangePassword {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private AddUserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/forgotPassword")
    public ResponseEntity<ReactStatus> sendMailforReset(@RequestBody UserEntity userEntity) {

        Email mail = new EmailBuilder()
                .from("Nopowcy forumowcy", "dejmitogroup@gmail.com")
                .to((""), (userEntity.getEmail()))// (userEntity.getEmail()+"@us.edu.pl")
                .cc("Halo halo", "dejmitogroup@gmail.com")
                .subject("Resetowanie Hasła")
                .text("By zresetować hasło proszę wejsc w link http://37.233.102.142:8080/resetPass?token=" + userRepository.findByEmail(userEntity.getEmail()).getActiveToken())
                .build();
        new Mailer("smtp.gmail.com", 587, "dejmitogroup@gmail.com", "haslo123", TransportStrategy.SMTP_TLS).sendMail(mail);

        ReactStatus reactStatus = new ReactStatus();
        reactStatus.setStatus(true);
        return new ResponseEntity<>(reactStatus, HttpStatus.OK);
    }
    @PostMapping("/changePass")
    @Transactional
    public ResponseEntity<ReactStatus> changePass(@RequestBody UserEntity resetPasswordUserEntity) {


        UserEntity userEntity = userRepository.findByActiveToken(resetPasswordUserEntity.getActiveToken());
        userEntity.setPassword(bCryptPasswordEncoder.encode(resetPasswordUserEntity.getPassword()));
        this.userService.saveAndFlush(userEntity);
        ReactStatus reactStatus = new ReactStatus();
        reactStatus.setStatus(true);
        return new ResponseEntity<>(reactStatus, HttpStatus.OK);
    }

}
