package com.netstore.api.mobile.controller;

import com.netstore.model.API.mobile.SchemaRest;
import com.netstore.model.API.mobile.register.CredentialsWithUser;
import com.netstore.model.API.mobile.register.RegisterNewQr;
import com.netstore.model.API.mobile.register.ValidateQr;
import com.netstore.model.entity.CredentialsEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.service.AddCredentialsService;
import com.netstore.model.view.UserRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Master on 2017-09-23.
 */
@RestController
public class RegisterQrRest {

    @Autowired
    private UserRestRepository userRestRepository;

    @Autowired
    private AddCredentialsService addCredentialsService;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @RequestMapping("/registerrest")
    public ResponseEntity<SchemaRest> registerDevice(@RequestBody RegisterNewQr registerQrRest) {

        if (credentialsRepository.findByToken(registerQrRest.getToken())!=null) {
            CredentialsEntity credentialsEntity = credentialsRepository.findByToken(registerQrRest.getToken());
            credentialsEntity.setPin(registerQrRest.getPin());
            this.addCredentialsService.saveAndFlush(credentialsEntity);
            CredentialsWithUser credentialsWithUser = new CredentialsWithUser();
            credentialsWithUser.setIdCredentials(credentialsEntity.getIdCredentials());
            credentialsWithUser.setPin(credentialsEntity.getPin());
            credentialsWithUser.setToken(credentialsEntity.getToken());
            credentialsWithUser.setUser(userRestRepository.findByIdUser(credentialsEntity.getUserIdUser()));
            SchemaRest<CredentialsWithUser> credentialsEntitySchemaRest = new SchemaRest<>(true,"Registered new device",1337,credentialsWithUser);
            return new ResponseEntity<>(credentialsEntitySchemaRest,HttpStatus.OK);
        }
        else
        {
            SchemaRest<CredentialsEntity> credentialsEntitySchemaRest = new SchemaRest<>(false,"User dosnt exists",101,null);
            return new ResponseEntity<>(credentialsEntitySchemaRest,HttpStatus.BAD_REQUEST);

        }

    }
    @RequestMapping("/validateToken")
    public ResponseEntity<SchemaRest> validateQr(@RequestBody ValidateQr validateQr)
    {
        if (credentialsRepository.findByToken(validateQr.getToken())!= null)
        {
            SchemaRest<CredentialsEntity> credentialsEntitySchemaRest = new SchemaRest<>(true,"Qr is git gut",1337,null);
            return new ResponseEntity<>(credentialsEntitySchemaRest,HttpStatus.OK);
        }
        else
        {
            SchemaRest<CredentialsEntity> credentialsEntitySchemaRest = new SchemaRest<>(false,"QR taken from lays dafuq",101,null);
            return new ResponseEntity<>(credentialsEntitySchemaRest,HttpStatus.OK);
        }
    }
    @RequestMapping("/validatePin")
    public ResponseEntity<SchemaRest> validatePin(@RequestBody CredentialsEntity credentialsEntity)
    {

        if (credentialsRepository.findByTokenAndPin(credentialsEntity.getToken(),credentialsEntity.getPin())!=null)
        {
            UserRestViewEntity userRestViewEntity = userRestRepository.findByIdUser(credentialsRepository.findByTokenAndPin(credentialsEntity.getToken(),credentialsEntity.getPin()).getUserIdUser());
            SchemaRest<UserRestViewEntity> restViewEntitySchemaRest =new SchemaRest<>(true,"Pin poprawny",1337,userRestViewEntity);
            return new ResponseEntity<>(restViewEntitySchemaRest,HttpStatus.OK);
        }else {
            SchemaRest<UserRestViewEntity> restViewEntitySchemaRest = new SchemaRest<>(false, "Pin nie poprawny", 101, null);
            return new ResponseEntity<>(restViewEntitySchemaRest,HttpStatus.OK);
        }
    }
}
