package com.example.jpastudy.service;

import com.example.jpastudy.model.Notice;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 강의와의 다른 점. 해당 부분을 컨트롤러에 바로 지정해 만들었지만,
 * 이런 비즈니스 서비스 내용은 Service 로 나누는 것이 실무적인 관점이기 때문에
 * 해당 로직을 나누어 작성. 물론 아래의 경우도 정적인 값만 가지고 잇는 경우기 때문에 유의미하지는 않음.
 * 또한 생성자의 경우 final 로 선언하고 @RequiredArgsConstructor 활용해 생성한 형태가 강의와의 차이점.
 */
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final Notice notice;

    /**
     * ex_07 의 값을 만들기 위한 서비스입니다.
     */
    public Notice notice(){
       notice.setId(1L);
       notice.setTitle("공지사항 제목");
       notice.setDescription("공지사항 내용");
       notice.setRegDate(LocalDateTime.of(2023,4,25,0,0));
       return notice;
    }
    /**
     * ex_08 의 값을 만들기 위한 서비스입니다.
     */
    public List<Notice> noticeList(){
        List<Notice> noticeList = new ArrayList<>();
        Notice notice1 = new Notice();
        notice1.setId(1L);
        notice1.setTitle("공지사항입니다.");
        notice1.setDescription("공지사항내용입니다.");
        notice1.setRegDate(LocalDateTime.of(2023,4,26,0,0));
        noticeList.add(notice1);
        /**
         * builder 의 활용
         * builder 의 경우, 생성자를 통해서 해당 값을 생성하는 것과 같다.
         * 그렇기 때문에 setId() 이런 형태로 값이 들어가는 게 아니라(내가 해당된 오류를 시도 했었다...ㅎ)
         * 바로 값을 넣어 주면 된다! 초기화를 한다는 생각으로 this.id = 2; 이런 느낌!
         */
        Notice notice2 = Notice.builder()
                        .id(2L)
                        .title("두 번째 공지사항입니다.")
                        .description("두 번째 공지사항내용입니다.")
                        .regDate(LocalDateTime.of(2023, 4, 26, 0, 0))
                        .build();

        noticeList.add(notice2);
    return noticeList;
    }
}
