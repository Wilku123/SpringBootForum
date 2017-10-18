package com.netstore.api.controller;

import com.netstore.model.API.SchemaRest;
import com.netstore.model.API.register.RegisterNewQr;
import com.netstore.model.API.register.ValidateQr;
import com.netstore.model.entity.CredentialsEntity;
import com.netstore.model.repository.CredentialsRepository;
import com.netstore.model.repository.UserRepository;
import com.netstore.model.service.AddCredentialsService;
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
    private UserRepository userRepository;

    @Autowired
    private AddCredentialsService addCredentialsService;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @RequestMapping("/registerrest")
    public ResponseEntity<SchemaRest> addAnswer(@RequestBody RegisterNewQr registerQrRest) {

        if (credentialsRepository.findByToken(registerQrRest.getToken())!=null) {
            CredentialsEntity credentialsEntity = credentialsRepository.findByToken(registerQrRest.getToken());
            credentialsEntity.setPin(registerQrRest.getPin());
            this.addCredentialsService.saveAndFlush(credentialsEntity);
            SchemaRest<CredentialsEntity> credentialsEntitySchemaRest = new SchemaRest<>(true,"Registered new device",1337,credentialsEntity);
            return new ResponseEntity<>(credentialsEntitySchemaRest,HttpStatus.OK);
        }
        else
        {
            SchemaRest<CredentialsEntity> credentialsEntitySchemaRest = new SchemaRest<>(false,"User dosnt exists",101,null);
            return new ResponseEntity<>(credentialsEntitySchemaRest,HttpStatus.BAD_REQUEST);

        }

    }
    @RequestMapping("/validateToken")
    public ResponseEntity<SchemaRest> validate(@RequestBody ValidateQr validateQr)
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
}
