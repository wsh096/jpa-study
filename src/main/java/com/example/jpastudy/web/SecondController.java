package com.example.jpastudy.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondController {
    //ex_03
    //@RequestMapping("/hello-spring")//Get이 default기 때문에 따로 쓰지 않아도 기능하지만 효율적인 방식은 아님
    @RequestMapping(value = "/hello-spring", method = RequestMethod.GET)
    public String hellospring() {
        return "Hello Spring";
    }
    //ex_04
    @GetMapping("/hello-rest")
    public String helloRest(){
        return "Hello Rest";
    }
    //ex_05
    @GetMapping("/api/helloworld")
    public String apiHelloWorld(){
        return "Hello Rest API";
    }
}
