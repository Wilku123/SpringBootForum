package com.netstore.api.react.controller;

import com.netstore.api.mobile.controller.CircleRest;
import com.netstore.model.API.mobile.SchemaRestList;
import com.netstore.model.API.mobile.circle.CircleWithAuthor;
import com.netstore.model.entity.CredentialsEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.SubscribedCircleRepository;
import com.netstore.model.repository.rest.CircleRestViewRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.view.CircleRestViewEntity;
import com.netstore.model.view.UserRestViewEntity;
import net.glxn.qrgen.javase.QRCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/react/main")
public class UserActions {

    @Autowired
    private UserRestRepository userRestRepository;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private CircleRestViewRepository circleRestViewRepository;
    @Autowired
    private SubscribedCircleRepository subscribedCircleRepository;
    @Autowired
    private CircleRest circleRest;

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

    @RequestMapping(value = "/subbedCircle", method = RequestMethod.POST)
    public ResponseEntity<List<CircleWithAuthor>> getLimitedCircle(Authentication auth) {


        List<CircleRestViewEntity> circleEntityList = circleRestViewRepository.findAll();
        List<CircleWithAuthor> circleRestList = new ArrayList<>();

        for (CircleRestViewEntity i : circleEntityList) {
            if ((subscribedCircleRepository.findByUserIdUserAndCircleIdCircle(userRestRepository.findByEmail(auth.getName()).getIdUser(), i.getIdCircle())) != null) {
                circleRestList.add(circleRest.generateCircleList(1, i));
            }
        }
        return new ResponseEntity<>(circleRestList, HttpStatus.OK);
    }


}
