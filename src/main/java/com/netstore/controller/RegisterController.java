package com.netstore.controller;

import com.netstore.model.entity.RoleEntity;
import com.netstore.model.entity.UserEntity;
import com.netstore.model.repository.RoleRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.service.AddRoleService;
import com.netstore.service.AddUserService;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Master on 2017-09-16.
 */
@org.springframework.stereotype.Controller
public class RegisterController {

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

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "RegisterNewQr";
    }

    @PostMapping("/register")
    @Transactional
    public String processRegister(@ModelAttribute UserEntity userEntity, BindingResult result) {


        if (userRepository.findFirstByEmail(userEntity.getEmail())!=null) {
            return "redirect:/RegisterNewQr?error=email";

        } else {
            String token = UUID.randomUUID().toString();

            String pass = userEntity.getPassword();
            userEntity.setPassword(bCryptPasswordEncoder.encode(pass));
            userEntity.setActive((byte) 0);
            userEntity.setActiveToken(token);
            this.userService.saveAndFlush(userEntity);
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

            return "redirect:/RegisterNewQr?RegisterNewQr=true";
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
}
