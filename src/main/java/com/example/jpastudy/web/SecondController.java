package com.example.jpastudy.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SecondController {

    @GetMapping("/hello-spring")
    public String hellospring() {
        return "Hello Spring";
    }
}
