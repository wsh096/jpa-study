package com.example.jpastudy.web;

import com.example.jpastudy.model.Notice;
import com.example.jpastudy.service.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    //ex_10
    /**
     * 게시글 개수에 대한 반환
     * 실제에서는 query 활용한 수를 조회하는 방식으로 진행함 그 때도 Query 카운트를 활용!
     * String 을 반환하나, int 반환하나 실질적인 http 에서는 String 해당하는 값.
     * 다만, 코드의 가독성과 직관적인 코드를 위해 이를 구분해 주는 것이 좋다
     */
    @GetMapping(value = "/api/count")
    public int noticeCount() {
        return 10;
    }

    //ex_11
    /**
     * GetMapping _ PostMapping
     * Restful 은, URI 들어가는 방식에 따라 구분되기 때문에 주소가 같더라도 방식이 다르면 다른 값.
     * @RequestParam 을 통해 넣어줄 값에 관한 구체적선언 가능.
     */
    @PostMapping(value = "/api/notice")
    public Notice addNotice(@RequestParam String title, @RequestParam String description) {
        return noticeService.addNotice(title, description);
    }
}
