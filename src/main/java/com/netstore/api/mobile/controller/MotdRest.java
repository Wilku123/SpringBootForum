package com.netstore.api.mobile.controller;

import com.netstore.model.API.mobile.SchemaRestList;
import com.netstore.model.API.mobile.motd.MOTD;
import com.netstore.model.entity.MotdEntity;
import com.netstore.model.repository.rest.MOTDRestRepository;
import com.netstore.model.repository.rest.UserRestRepository;
import com.netstore.model.view.UserRestViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MotdRest {

    @Autowired
    private MOTDRestRepository motdRestRepository;
    @Autowired
    private UserRestRepository userRestRepository;

    private MOTD fillMotd(MotdEntity i)
    {
        Integer idMotd = i.getIdMotd();
        String msg = i.getMessage();
        Integer idAuthor = i.getAuthorId();
        Timestamp timeStamp= i.getPublishDate();

        MOTD motd = new MOTD();
        motd.setIdMotd(idMotd);
        motd.setMessage(msg);
        motd.setTimestamp(timeStamp);


        UserRestViewEntity userEntity;
        userEntity =userRestRepository.findByIdUser(idAuthor);
        motd.setAuthor(userEntity);
        return motd;
    }

    @RequestMapping(value = "/motd", method = RequestMethod.GET)
    public ResponseEntity<SchemaRestList<MOTD>> getMotd()
    {
        List<MotdEntity> motdEntityList = motdRestRepository.findTop5ByOrderByPublishDateDesc();
        List<MOTD> motds = new ArrayList<>();
        for (MotdEntity i:motdEntityList) {
            motds.add(fillMotd(i));
        }

        SchemaRestList<MOTD> motdSchemaRestList = new SchemaRestList<>(true,"Wiadomość Dnia!",1337,motds) ;
        return new ResponseEntity<>(motdSchemaRestList, HttpStatus.OK);
    }
}
