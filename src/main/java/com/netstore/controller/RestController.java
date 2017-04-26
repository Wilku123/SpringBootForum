package com.netstore.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Master on 2017-04-22.
 */
@org.springframework.web.bind.annotation.RestController
public class RestController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greet")
    public Greet greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greet(counter.incrementAndGet(),
                String.format(template, name));
    }
}
