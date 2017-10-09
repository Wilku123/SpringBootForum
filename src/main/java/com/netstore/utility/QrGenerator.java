package com.netstore.utility;

import net.glxn.qrgen.javase.QRCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayOutputStream;

/**
 * Created by Master on 2017-07-16.
 */
@org.springframework.stereotype.Controller
public class QrGenerator {

    @RequestMapping(value = "/qrcode/{name}/{id}/{action}",method = RequestMethod.GET)
    public ResponseEntity<byte[]> qr(@PathVariable Integer id, @PathVariable String name, @PathVariable String action){
        ByteArrayOutputStream stream = QRCode.from("DMTAPP;"+name+";"+action+";"+id.toString()).stream();

        byte[] bytes = stream.toByteArray();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]> (bytes, headers, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/qrcode/{token}",method = RequestMethod.GET)
    public ResponseEntity<byte[]> registerQr(@PathVariable String token){
        ByteArrayOutputStream stream = QRCode.from(token).stream();

        byte[] bytes = stream.toByteArray();

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<byte[]> (bytes, headers, HttpStatus.CREATED);
    }
}
