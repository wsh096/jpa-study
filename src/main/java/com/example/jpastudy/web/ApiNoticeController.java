package com.example.jpastudy.web;

import com.example.jpastudy.model.Notice;
import com.example.jpastudy.service.NoticeService;
import java.util.List;
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
     * 관련 내용 이슈 정리. 아래 링크 참고. https://github.com/wsh096/jpa-study/issues/3
     */
    @GetMapping(value = "/api/notice2")
    public Notice noticeString2() {
        return noticeService.notice();
    }

    //ex_08
    /**
     * 게시판의 추상화한 모델, 복수형태 데이터 리턴. 2개 이상의 데이터 가져오기
     */
    @GetMapping(value = "/api/notice3")
    public List<Notice> noticeList() {
        return noticeService.noticeList();
    }

    //ex_09
    /**
     * Null 반환 정확히는 빈배열의 반환 서비스 단에 관련한 차이 자세한 설명 있음.
     */
    @GetMapping(value = "/api/notice4")
    public List<Notice> noticeList_Null() {
        return noticeService.noticeListNull();
    }
}
