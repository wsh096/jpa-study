package com.example.jpastudy.service;

import com.example.jpastudy.exception.AlreadyDeletedException;
import com.example.jpastudy.exception.NoticeNotFoundException;
import com.example.jpastudy.model.NoticeInput;
import com.example.jpastudy.model.entity.Notice;
import com.example.jpastudy.model.repository.NoticeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 강의와의 다른 점. 해당 부분을 컨트롤러에 바로 지정해 만들었지만, 이런 비즈니스 서비스 내용은 Service 로 나누는 것이 실무적인 관점이기 때문에 해당 로직을 나누어
 * 작성. 물론 아래의 경우도 정적인 값만 가지고 잇는 경우기 때문에 유의미하지는 않음. 또한 생성자의 경우 final 로 선언하고 @RequiredArgsConstructor
 * 활용해 생성한 형태가 강의와의 차이점.
 */
@Service
@RequiredArgsConstructor
public class NoticeService {

    //private final NoticeInput noticeInput;//이렇게 넣어주면 안 됨.
    private final NoticeRepository noticeRepository;

    /**
     * ex_07 의 값을 만들기 위한 서비스입니다.
     */
    public NoticeInput notice() {
        NoticeInput noticeInput = new NoticeInput();
        noticeInput.setTitle("공지사항 제목");
        noticeInput.setDescription("공지사항 내용");

        return noticeInput;
    }

    /**
     * ex_08 의 값을 만들기 위한 서비스입니다.
     */
    public List<NoticeInput> noticeList() {
        List<NoticeInput> noticeInputList = new ArrayList<>();
        NoticeInput noticeInput1 = new NoticeInput();

        noticeInput1.setTitle("공지사항입니다.");
        noticeInput1.setDescription("공지사항내용입니다.");

        noticeInputList.add(noticeInput1);
        /**
         * builder 의 활용
         * builder 의 경우, 생성자를 통해서 해당 값을 생성하는 것과 같다.
         * 그렇기 때문에 setId() 이런 형태로 값이 들어가는 게 아니라(내가 해당된 오류를 시도 했었다...ㅎ)
         * 바로 값을 넣어 주면 된다! 초기화를 한다는 생각으로 this.id = 2; 이런 느낌!
         */
        NoticeInput noticeInput2 = NoticeInput.builder()

            .title("두 번째 공지사항입니다.")
            .description("두 번째 공지사항내용입니다.")

            .build();

        noticeInputList.add(noticeInput2);
        return noticeInputList;
    }

    /**
     * 강의와 다른점 강의는 전부 선언하고 변수를 반환 나는 바로 반환. 즉 강의 List<NoticeInput> notice = new ArrayList<>();
     * return notice; 나 return new ArrayList<>(); 해당 형태를 활용한다.
     * <p>
     * 실수로 부터 배우기! 잘못된 표현 return List<NoticeInput> notice; //해당 형태는 인터페이스 추상 메서드여서 객체화 인스턴스를 생성할 수
     * 없음.
     * <p>
     * Null Vs. List<>().isEmpty() == true return null; 아래처럼 반환 Keep-Alive: timeout=60 Connection:
     * keep-alive
     * <p>
     * <Response body is empty> <- 응답 자체가 비었다고 나오는 것! 차이!!
     * <p>
     * return new ArrayList<>(); 아래처럼 반환 Keep-Alive: timeout=60 Connection: keep-alive
     * <p>
     * []<- 목표한 값(빈 리스트 반환)
     * <p>
     * `작성 목적` 데이터가 Null 인 경우 방어 코드를 작성해서 해결
     */
    public List<NoticeInput> noticeListNull() {
        return null;
        // return new ArrayList<>();
    }

    //ex_11
    public NoticeInput addNotice(String title, String description) {
        return NoticeInput.builder()
            .title(title)
            .description(description).build();

    }

    //ex_12
    public NoticeInput addNoticeAbstract(NoticeInput noticeInput) {

        return noticeInput;
    }

    //ex_13
    public NoticeInput addNoticeShowJson(NoticeInput noticeInput) {

        return noticeInput;
    }

    //ex_14
    public Notice addNoticeShowDB(NoticeInput noticeInput) {
        return getSaveNotice(noticeInput);
    }

    private Notice getSaveNotice(NoticeInput noticeInput) {
        return noticeRepository.save(Notice.builder().title(noticeInput.getTitle())
            .description(noticeInput.getDescription())
            .regDate(LocalDateTime.now()).build());

    }

    //ex_15
    public Notice addWatchLike(NoticeInput noticeInput) {
        return getSaveNoticeWatchLike(noticeInput);
    }

    private Notice getSaveNoticeWatchLike(NoticeInput noticeInput) {
        return noticeRepository.save(Notice.builder().title(noticeInput.getTitle())
            .description(noticeInput.getDescription()).watch(0L).likes(0L)
            .regDate(LocalDateTime.now()).build());
    }

    //ex_16
    public Notice showNotice(Long id) {
        return noticeRepository.findById(id).orElse(null);

        //return noticeRepository.findById(id).orElse(null);//좋은 방법은 아님.
        //isPresent가 더 적합한 예제가 맞음. 다만, 현재의 예시에서는 null의 반환을 가정할 수 있기에 위와 같이 작성.
        //null의 경우는 방어코드로 nullpointException 발생이 더 주요!
    }

    //ex_17
    public Notice updateNotice(Long id, NoticeInput noticeInput) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            notice.get().setTitle(noticeInput.getTitle());
            notice.get().setDescription(noticeInput.getDescription());
            notice.get().setUpdateDate(LocalDateTime.now());
            noticeRepository.save(notice.get());
            return notice.get();
        }
        return null;
    }

    //ex_18
    public void updateNoticeError(Long id, NoticeInput noticeInput) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new NoticeNotFoundException("찾고자 하는 공지사항의 글이 존재하지 않습니다."));

        notice.setTitle(noticeInput.getTitle());
        notice.setDescription(noticeInput.getDescription());
        noticeRepository.save(notice);
    }

    //ex_19 하하, 18번에서 이미 다 했네 :) 날짜까지 수정하는게 19번의 예제!
    public void updateNoticeDate(Long id, NoticeInput noticeInput) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new NoticeNotFoundException("찾고자 하는 공지사항의 글이 존재하지 않습니다."));

        notice.setTitle(noticeInput.getTitle());
        notice.setDescription(noticeInput.getDescription());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }

    //ex_20
    public void NoticeWatch(Long id) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new NoticeNotFoundException("찾고자 하는 공지사항의 글이 존재하지 않습니다."));
        notice.setWatch(notice.getWatch() + 1);
        noticeRepository.save(notice);
    }

    //ex_21
    public void NoticeDelete(Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            noticeRepository.delete(notice.get());
        }
    }

    //ex_22
    public void NoticeDeleteThrow(Long id) {
        noticeRepository.delete(noticeRepository.findById(id)
            .orElseThrow(() -> new NoticeNotFoundException("삭제하고자 하는 글이 없습니다.")));
    }
    //ex_23
    public void NoticeDeleteFlag(Long id) {
        int i = 0;
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(() -> new NoticeNotFoundException("삭제하고자 하는 글이 없습니다."));
            if (notice.isDeleted()) {
                throw new AlreadyDeletedException("이미 삭제된 글입니다.");
            }
        notice.setDeleted(true);
        notice.setDeletedDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }
}

