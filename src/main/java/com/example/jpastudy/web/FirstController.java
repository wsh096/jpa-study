package com.example.jpastudy.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FirstController {

    //ex_01
    @RequestMapping(value = "/first-url", method = RequestMethod.GET)
    public void first() {
    }

    //ex_02
    @ResponseBody//Controller의 경우 해당 어노테이션을 붙여야 문자열을 반환
    @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
    public String helloworld() {
        return "Hello World";
    }

}
