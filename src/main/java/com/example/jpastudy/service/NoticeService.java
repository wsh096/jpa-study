package com.example.jpastudy.service;

import com.example.jpastudy.model.Notice;
import java.time.LocalDateTime;
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
    public Notice notice(){
       notice.setId(1L);
       notice.setTitle("공지사항 제목");
       notice.setDescription("공지사항 내용");
       notice.setRegDate(LocalDateTime.of(2023,4,25,0,0));
       return notice;
    }
}
