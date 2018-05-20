package com.netstore.api.react.controller;

import com.netstore.model.API.react.ReactStatus;
import com.netstore.model.API.react.user.OldAndNewPassword;
import com.netstore.model.entity.CredentialsEntity;
import com.netstore.model.entity.UserEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddCredentialsService;
import com.netstore.model.service.AddUserService;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
@RequestMapping("/react/main")
public class UserActionsReact {

    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddUserService addUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private AddCredentialsService addCredentialsService;


    @RequestMapping("/qr")
    public ResponseEntity<byte[]> getTokenForQr(Authentication authentication) {
        String token = credentialsRepository.findByUserIdUser(userRestRepository.findByEmail(authentication.getName()).getIdUser()).getToken();
        ByteArrayOutputStream stream = QRCode.from(token).stream();

        byte[] bytes = stream.toByteArray();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(bytes, headers, HttpStatus.CREATED);
//        return new ResponseEntity<>(credentialsRepository.findByUserIdUser(userRestRepository.findByEmail(authentication.getName()).getIdUser()), HttpStatus.OK);
    }

    @RequestMapping("/qrRegistered")
    public ResponseEntity<ReactStatus> checkIfRegistered(Authentication authentication) {
        ReactStatus reactStatus = new ReactStatus();
        if (credentialsRepository.findByUserIdUser(userRepository.findByEmail(authentication.getName()).getIdUser()).getPin() != null) {
            reactStatus.setStatus(true);
            return new ResponseEntity<>(reactStatus, HttpStatus.OK);
        } else {
            reactStatus.setStatus(false);
            return new ResponseEntity<>(reactStatus, HttpStatus.OK);
        }
    }

    @RequestMapping("/unRegister")
    public ResponseEntity<ReactStatus> unRegisterDevice(Authentication authentication) {
        ReactStatus reactStatus = new ReactStatus();
        CredentialsEntity credentialsEntity = credentialsRepository.findByUserIdUser(userRepository.findByEmail(authentication.getName()).getIdUser());
        credentialsEntity.setPin(null);
        this.addCredentialsService.saveAndFlush(credentialsEntity);
        reactStatus.setStatus(false);
        return new ResponseEntity<>(reactStatus, HttpStatus.OK);

    }

    @RequestMapping(value = "/saveAvatar", method = RequestMethod.POST)
    public ResponseEntity<ReactStatus> saveAvatar(Authentication authentication, @RequestBody UserEntity userEntity) {
        if (userEntity.getAvatar() == (null)) {
            userEntity.setAvatar("nops");
        }

        UserEntity user = userRepository.findByEmail(authentication.getName());
        user.setAvatar(userEntity.getAvatar());
        this.addUserService.saveAndFlush(user);

        ReactStatus reactStatus = new ReactStatus();
        reactStatus.setStatus(true);
        return new ResponseEntity<>(reactStatus, HttpStatus.OK);
//        return new ResponseEntity<>(credentialsRepository.findByUserIdUser(userRestRepository.findByEmail(authentication.getName()).getIdUser()), HttpStatus.OK);
    }

    @PostMapping("/changePass")
    @Transactional
    public ResponseEntity<ReactStatus> changePass(Authentication authentication, @RequestBody OldAndNewPassword oldAndNewPassword) {

        String oldPass = oldAndNewPassword.getOldPass();
        ReactStatus reactStatus = new ReactStatus();


        if (bCryptPasswordEncoder.matches(oldPass, userRepository.findByEmail(authentication.getName()).getPassword())) {

            UserEntity userEntity = userRepository.findByEmail(authentication.getName());
            userEntity.setPassword(bCryptPasswordEncoder.encode(oldAndNewPassword.getNewPass()));
            this.addUserService.saveAndFlush(userEntity);

            reactStatus.setStatus(true);
            return new ResponseEntity<>(reactStatus, HttpStatus.OK);
        } else {
            reactStatus.setStatus(false);
            return new ResponseEntity<>(reactStatus, HttpStatus.OK);
        }
    }


}
