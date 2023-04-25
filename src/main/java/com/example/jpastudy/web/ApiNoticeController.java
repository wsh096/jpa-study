package com.example.jpastudy.web;

import com.example.jpastudy.model.Notice;
import com.example.jpastudy.service.NoticeService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ApiNoticeController {
    private final NoticeService noticeService;
    //ex_06
    @GetMapping("/api/notice")
    public String noticeString() {
        return "공지사항입니다.";
    }

    //ex_07
    /**
     * String 으로 받을 시 , text 를 기본으로 하고 다른 객체로 받을 시 application/json 이 기본값
     *  @GetMapping(value = "/api/notice2",produces = "application/json;charset=UTF-8")
     *  위와 같이 명시적으로 String json 타입으로 바꿔 줄 수 있지만, 이렇게 해도 String 자체의 형태를
     *  보여주기 때문에 Json 형태로 만들어지는 것은 아님.
     */
    @GetMapping(value = "/api/notice2",produces = "application/json;charset=UTF-8")
    public Notice noticeString2() {
        return noticeService.notice();
    }
}
