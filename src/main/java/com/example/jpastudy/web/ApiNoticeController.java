package com.example.jpastudy.web;

import com.example.jpastudy.model.NoticeInput;
import com.example.jpastudy.model.entity.Notice;
import com.example.jpastudy.service.NoticeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public NoticeInput noticeString2() {
        return noticeService.notice();
    }

    //ex_08
    /**
     * 게시판의 추상화한 모델, 복수형태 데이터 리턴. 2개 이상의 데이터 가져오기
     */
    @GetMapping(value = "/api/notice3")
    public List<NoticeInput> noticeList() {
        return noticeService.noticeList();
    }

    //ex_09
    /**
     * Null 반환 정확히는 빈배열의 반환 서비스 단에 관련한 차이 자세한 설명 있음.
     */
    @GetMapping(value = "/api/notice4")
    public List<NoticeInput> noticeList_Null() {
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
    public NoticeInput addNotice(@RequestParam String title, @RequestParam String description) {
        return noticeService.addNotice(title, description);
    }

    /**
     * ex_12
     * 공지사항 모델을 추상화해서 가져온다는 것의 의미
     * 11의 경우 이미 생성되어 있는 값을 수정하는 형태지만,
     * 12번의 경우 비어 있는 것을 post 로 전달해줘서 셋팅하는 연습
     * 큰 차이는 없음
     */
    @PostMapping(value = "/api/notice2")
    public NoticeInput addNoticeAbstract(NoticeInput noticeInput){
        return noticeService.addNoticeAbstract(noticeInput);
    }

    /**
     * ex_13
     * @RequestBody로 형태로 스프링에서 받아서 매핑을 진행
     * 이러한 형태로 매핑해줘야 Json 형태로 값을 받을 수 있음
     */
    @PostMapping(value = "/api/notice3")
    public NoticeInput addNoticeShowJson(@RequestBody NoticeInput noticeInput){
        return noticeService.addNoticeShowJson(noticeInput);
    }
    /**
     * ex_14
     * 실제 db에서 확인인 가능한 형태로 제작
     */
    @PostMapping(value = "/api/notice4")
    public Notice addNoticeShowDB(@RequestBody NoticeInput noticeInput){
        return noticeService.addNoticeShowDB(noticeInput);
    }
    /**
     * ex_15
     * 공지사항 등록일은 현재시간을 저장, 공지사항 조회수와 좋아요 수는 초기값을 0으로 설정하기.
     */
    @PostMapping(value = "/api/notice5")
    public Notice addWatchLike(@RequestBody NoticeInput noticeInput){
        return noticeService.addWatchLike(noticeInput);
    }
    /**
     * ex_16
     * 공지사항을 조회하는 기능 없으면 null 반환 실제에서는 예외를 넘기도록 해야함
     */
    @GetMapping("/api/notice/{id}")
    public Notice showNotice(@PathVariable Long id){
        return noticeService.showNotice(id);
    }
}
