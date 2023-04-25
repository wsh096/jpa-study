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
     * 관련 내용 이슈 정리. 아래 링크 참고.
     * https://github.com/wsh096/jpa-study/issues/3
     */
    @GetMapping(value = "/api/notice2")
    public Notice noticeString2() {
        return noticeService.notice();
    }
}
