package com.example.jpastudy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping
public class FirstController {

    @GetMapping("/first-url")
    public void first() {

    }
    @ResponseBody//Controller의 경우 해당 어노테이션을 붙여야 문자열을 반환
    @GetMapping("/helloworld")
    public String helloworld() {
        return "Hello World";
    }

}
