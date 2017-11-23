package com.netstore.api.react.controller;

import com.netstore.model.API.react.entry.CheckUser;
import com.netstore.model.API.react.entry.ReactStatus;
import com.netstore.model.entity.CredentialsEntity;
import com.netstore.model.entity.RoleEntity;
import com.netstore.model.entity.UserEntity;
import com.netstore.model.repository.RoleRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.service.AddCredentialsService;
import com.netstore.model.service.AddRoleService;
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

import java.util.UUID;

@RestController
@RequestMapping("/react")
public class Register {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AddRoleService addRoleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddUserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AddCredentialsService addCredentialsService;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<ReactStatus> processRegister(@RequestBody UserEntity userEntity) {
        ReactStatus reactStatus = new ReactStatus();
        if (userRepository.findFirstByEmail(userEntity.getEmail())!=null) {

            reactStatus.setStatus(true);
            return new ResponseEntity<>(reactStatus, HttpStatus.OK);

        }
        else
        {
            reactStatus.setStatus(false);

            String token = UUID.randomUUID().toString();

            String pass = userEntity.getPassword();
            userEntity.setPassword(bCryptPasswordEncoder.encode(pass));
            userEntity.setActive((byte) 0);
            userEntity.setActiveToken(token);
            userEntity.setAvatar("");
            this.userService.saveAndFlush(userEntity);

            CredentialsEntity credentialsEntity = new CredentialsEntity();
            credentialsEntity.setUserIdUser(userEntity.getIdUser());
            credentialsEntity.setToken(UUID.randomUUID().toString());
            this.addCredentialsService.saveAndFlush(credentialsEntity);

            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setRole("USER");
            roleEntity.setUserByUserIdUser(userEntity);
            this.addRoleService.saveAndFlush(roleEntity);


            Email email = new EmailBuilder()
                    .from("Nopowcy forumowcy", "dejmitogroup@gmail.com")
                    .to((userEntity.getName() + " " + userEntity.getLastName()), (userEntity.getEmail()))// (userEntity.getEmail()+"@us.edu.pl")
                    .cc("Halo halo", "dejmitogroup@gmail.com")
                    .subject("Rejestracja na forum DejMiTo")
                    .text("no halo czymaj linksa http://37.233.102.142:8080/activate?token=" + token)
                    .build();
            new Mailer("smtp.gmail.com", 587, "dejmitogroup@gmail.com", "haslo123", TransportStrategy.SMTP_TLS).sendMail(email);

            return new ResponseEntity<>(reactStatus, HttpStatus.OK);


        }


    }
    @GetMapping("/activate")
    public String activate(@RequestParam(value = "token") String token) {
        if (userRepository.findByActiveToken(token)!=null && userRepository.findByActiveToken(token).getActive()!=1) {
            UserEntity userEntity = new UserEntity();
            userEntity= userRepository.findByActiveToken(token);
            userEntity.setActive((byte)1);
            this.userService.saveAndFlush(userEntity);
            return "redirect:/?activate=true";
        } else {
            return "redirect:/?activate=false";
        }
    }
    @PostMapping("/userExists")
    public ResponseEntity<ReactStatus> userExists(@RequestBody CheckUser checkUser){
        ReactStatus reactStatus = new ReactStatus();
        if (userRepository.findFirstByEmail(checkUser.getEmail())!=null){
            reactStatus.setStatus(true);
            return new ResponseEntity<>(reactStatus,HttpStatus.OK);}
        else{
            reactStatus.setStatus(false);
            return new ResponseEntity<>(reactStatus,HttpStatus.OK);
        }
    }
}
