# jpa-study 100
### 알아두기_강의와의 차이점.
    강의의 경우, Controller 패키지를 최초 빌드하는 동안에
    Service 명에 따라 생성하고 있다. 
    하지만 실무적인 관점에서 Controller와 같은 명칭으로 나누어 만들었고
    나의 경우는 컨트롤러를 가장 밑에 패키지로 분류해 관리하고자 'web'이라는 이름으로 관리
    마찬가지로, config 패키지로 설정 파일 역시 관리 중

#### 0000 SpringSecurity 모든 곳에 허용하기 위한 설정    
___
```agsl
    package com.example.jpastudy.config;    

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        //Security 를 모든 곳에서 허용하기 위한 설정.
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().anyRequest().permitAll();
        }
    }
```
#### 0001~0002 Controller 활용한 매핑
___
```agsl
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
    
        //ex_02 문자열 반환 Controller의 경우 @ResponseBody Anotation 필수
        @ResponseBody//Controller의 경우 해당 어노테이션을 붙여야 문자열을 반환
        @RequestMapping(value = "/hello-world", method = RequestMethod.GET)
        public String helloworld() {
            return "Hello World";
        }
    }
```
#### 0003~0005 RestController 활용한 매핑
___
```agsl
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
```    
#### 0006-
```agsl
package com.example.jpastudy.web;
    
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
    
    //ex_06
    @RestController
    public class ApiNoticeController {
    @GetMapping("/api/notice")
    public String noticeString(){
    return "공지사항입니다.";
    }
    //ex_07
    /**
    * String 으로 받을 시 , text 를 기본으로 하고 다른 객체로 받을 시 application/json 이 기본값
    *  @GetMapping(value = "/api/noticeInput2",produces = "application/json;charset=UTF-8")
    *  위와 같이 명시적으로 String json 타입으로 바꿔 줄 수 있지만, 이렇게 해도 String 자체의 형태를
    *  보여주기 때문에 Json 형태로 만들어지는 것은 아님.
    */
    @GetMapping(value = "/api/noticeInput2")
    public Notice noticeString2() {
    return noticeService.notice();
        }
    }   
    //ex_08
        
    /**
    * 게시판의 추상화한 모델, 복수형태 데이터 리턴. 2개 이상의 데이터 가져오기
    */
    @GetMapping(value = "/api/notice3")
    public List<Notice> noticeInputList() {
        return noticeService.noticeInputList();
    }
    //ex_09
    /**
     * Null 반환 정확히는 빈배열의 반환 서비스 단에 관련한 차이 자세한 설명 있음.
     */
    @GetMapping(value = "/api/notice4")
    public List<Notice> noticeList_Null() {
        return noticeService.noticeListNull();
    }
```
#### ex07-ex10 을 위한 모델 클래스(Component)와 Service Class
___
```agsl
//모델 클래스(Component)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString //해당 어노테이션이 있어야, 주소값이 아니라 문자열 값으로 반환
@Component // 해당 클래스의 빈 주입
//@Entity db 연결한 것이 아니기 때문에 해당 어노테이션은 시기상조,
public class Notice {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime regDate;
}
```

```agsl
//Service Class
package com.example.jpastudy.service;

import com.example.jpastudy.model.NoticeInput;
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
    public List<Notice> noticeInputList(){
        List<Notice> noticeInputList = new ArrayList<>();
        Notice noticeInput1 = new Notice();
        noticeInput1.setId(1L);
        noticeInput1.setTitle("공지사항입니다.");
        noticeInput1.setDescription("공지사항내용입니다.");
        noticeInput1.setRegDate(LocalDateTime.of(2023,4,26,0,0));
        noticeInputList.add(noticeInput1);
        /**
         * builder 의 활용
         * builder 의 경우, 생성자를 통해서 해당 값을 생성하는 것과 같다.
         * 그렇기 때문에 setId() 이런 형태로 값이 들어가는 게 아니라(내가 해당된 오류를 시도 했었다...ㅎ)
         * 바로 값을 넣어 주면 된다! 초기화를 한다는 생각으로 this.id = 2; 이런 느낌!
         */
        Notice noticeInput2 = Notice.builder()
                        .id(2L)
                        .title("두 번째 공지사항입니다.")
                        .description("두 번째 공지사항내용입니다.")
                        .regDate(LocalDateTime.of(2023, 4, 26, 0, 0))
                        .build();

        noticeInputList.add(noticeInput2);
    return noticeInputList;
    }
}
  /**
     * 강의와 다른점 강의는 전부 선언하고 변수를 반환 나는 바로 반환.
     * 즉 강의 List<Notice> notice = new ArrayList<>();
     *          return notice;
     * 나 return new ArrayList<>(); 해당 형태를 활용한다.
     *
     * 실수로 부터 배우기!
     * 잘못된 표현
     * return List<Notice> notice; //해당 형태는 인터페이스 추상 메서드여서 객체화 인스턴스를 생성할 수 없음.
     *
     * Null Vs. List<>().isEmpty() == true
     *  return null;
     *  아래처럼 반환
     *Keep-Alive: timeout=60
     * Connection: keep-alive
     *
     * <Response body is empty> <- 응답 자체가 비었다고 나오는 것! 차이!!
     *
     *  return new ArrayList<>();
     *  아래처럼 반환
     *  Keep-Alive: timeout=60
     * Connection: keep-alive
     *
     * []<- 목표한 값(빈 리스트 반환)
     * 
     * `작성 목적`
     * 데이터가 Null 인 경우 방어 코드를 작성해서 해결
     */
    public List<Notice> noticeListNull(){
       //return null;
        return new ArrayList<>();
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
```
#### ex11- ex13 을 위한 Controller (post) Service 추가, Config 추가. 스크래치 파일.   
---

`Config 설정`
```agsl
package com.example.jpastudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //Security 를 모든 곳에서 허용하기 위한 설정.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin(); // ex11을 위한 추가 설정 외부의 값을 받아오기 위한 설정
        http.authorizeRequests().anyRequest().permitAll();
    }
}
```
`Controller`
```agsl
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
    
      /**
     * ex_12
     * 공지사항 모델을 추상화해서 가져온다는 것의 의미
     * 11의 경우 이미 생성되어 있는 값을 수정하는 형태지만,
     * 12번의 경우 비어 있는 것을 post 로 전달해줘서 셋팅하는 연습
     * 큰 차이는 없음
     */
    @PostMapping(value = "/api/noticeInput2")
    public Notice addNoticeAbstract(Notice notice){
        return noticeService.addNoticeAbstract(notice);
    }
    
    /**
     * ex_13
     * @RequestBody로 형태로 스프링에서 받아서 매핑을 진행
     * 이러한 형태로 매핑해줘야 Json 형태로 값을 받을 수 있음
     * 관련정리(https://github.com/wsh096/jpa-study/issues/6)
     */
    @PostMapping(value = "/api/notice3")
    public Notice addNoticeShowJson(@RequestBody Notice notice){
        return noticeService.addNoticeShowJson(notice);
    }
```
`Service`
```agsl
//ex_11
    public Notice addNotice(String title, String description) {
        return notice.builder()
            .id(1L)
            .title(title)
            .description(description)
            .regDate(LocalDateTime.of(2023, 4, 26, 0, 0)).build();
    }
    
//ex_12
    public Notice addNoticeAbstract(Notice notice) {
        notice.setId(3L);
        notice.setRegDate(LocalDateTime.of(2023,04,12,12,12));
    return notice;
    }
//ex_13
    public Notice addNoticeShowJson(Notice notice) {
        notice.setId(3L);
        notice.setRegDate(LocalDateTime.now());
        return notice;
    }
    
```
`스크래치 파일`
```agsl
### 게시글 제목과 내용 더하기 ex_11
POST http://localhost:8080/api/notice/
Content-Type: application/x-www-form-urlencoded

title=제목1&description=내용1

### 게시글 입력 추상 형태 ex_12
POST http://localhost:8080/api/noticeInput2
Content-Type: application/x-www-form-urlencoded

title=제목1&description=내용1

### 게시글 입력 show json type ex_13
POST http://localhost:8080/api/notice3
Content-Type: application/json

{
"title" : "새로운 제목",
"description" : "새로운 내용"
}
```

#### ex14-15 DB 연결, repository, .yml 설정(issue 확인)
---
+ `@component` 어노테이션 private final 로 선언한 값의 빈을 주입해주는 역할을 수행하는 것으로 확인. 
+ 하지만 해당 어노테이션의 접근은 적절한 방식이 아니기에, form이나 entity의 불변성 접근 불가의 원칙에 따라 사용하지 않음.
+ ex14_관련한 yml 설정 등의 기본 내용 이슈에 정리(https://github.com/wsh096/jpa-study/issues/7)
`Entity`

```agsl
package com.example.jpastudy.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notice")
//@Component // 해당 어노테이션이 없으면 실행이 되지 않음 이유는 service에 불필요하게 final 선언해서 그랬던 것!
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private LocalDateTime regDate;
    private long watch;
    //like 는 H2의 예약어기 때문에 생성이 안됨!
    private long likes;//자세한 내용은 issue
}

```
`repository`
```agsl
package com.example.jpastudy.model.repository;

import com.example.jpastudy.model.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {

}

```
`Controller`
```agsl
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
     * 엔티티에 예약어를 쓰지 말자!(https://github.com/wsh096/jpa-study/issues/8)
     */
    @PostMapping(value = "/api/notice5")
    public Notice addWatchLike(@RequestBody NoticeInput noticeInput){
        return noticeService.addWatchLike(noticeInput);
    }
```
`Service`
```agsl
 //ex_14
    public Notice addNoticeShowDB(NoticeInput noticeInput) {
        return getSaveNotice(noticeInput);
    }

    private Notice getSaveNotice(NoticeInput noticeInput) {
        Notice notice =
        Notice.builder().title(noticeInput.getTitle())
            .description(noticeInput.getDescription())
            .regDate(LocalDateTime.now()).build();
        noticeRepository.save(notice);
        return notice;
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
```

#### ex16- , Controller, Service
---

`Controller`
```agsl
    /**
    * ex_16
    * 공지사항을 조회하는 기능 없으면 null 반환 실제에서는 예외를 넘기도록 해야함
    * DB sql 문제 issue 에 정리 및 해결 (https://github.com/wsh096/jpa-study/issues/9)
     */
    */
    @GetMapping("/api/notice/{id}")
    public Notice showNotice(@PathVariable Long id){
    return noticeService.showNotice(id);
    }

    /**
     * ex_17
     * 공지사항을 업데이트, DB update_date 추가, @RequestBody 로 값 받기.
     * 추가 학습 내용 이슈 10에 정리(https://github.com/wsh096/jpa-study/issues/10)
     */
    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id,@RequestBody NoticeInput noticeInput){
        noticeService.updateNotice(id,noticeInput);
    }
    /**
     * ex_18
     * 17 + 예외 반환!
     * 10번 이슈의 닫는 내용으로 해당 내용 추가. 강의와 다른 점. 강의는 
     * 여기에 해당 ExceptionHandler 메서드를 추가해 사용했다.
     * 하지만, 나는 별도의 클래스로 보냈고, 이렇게 하면, @RestControllerAdvice로 해당 설정을 전역으로 만들었다.
     */
    @PutMapping("/api/noticeError/{id}")
    public void updateNoticeError(@PathVariable Long id,@RequestBody NoticeInput noticeInput){
       noticeService.updateNoticeError(id,noticeInput);
    }
    /**
     * ex_19(그런데 이미 18에서 다 했음. 오류였던 것. 18은 제목과 내용만 바꾸는 것이었다!
     * 날짜를 변경
     */
    @PutMapping("/api/noticeDate/{id}")
    public void updateNoticeDate(@PathVariable Long id,@RequestBody NoticeInput noticeInput){
        noticeService.updateNoticeDate(id,noticeInput);
    }
```

`Service`
```agsl
    //ex_16
        public Notice showNotice(Long id) {
            return noticeRepository.findById(id).orElse(null);
            
            //return noticeRepository.findById(id).orElse(null);//좋은 방법은 아님.
            //isPresent가 더 적합한 예제가 맞음. 다만, 현재의 예시에서는 null의 반환을 가정할 수 있기에 위와 같이 작성.
            //null의 경우는 방어코드로 nullpointException 발생이 더 주요!
        }
    
       //ex_17
    public void updateNotice(Long id, NoticeInput noticeInput) {
        Optional<Notice> notice = noticeRepository.findById(id);
        if(notice.isPresent()){
            notice.get().setTitle(noticeInput.getTitle());
            notice.get().setDescription(noticeInput.getDescription());
            notice.get().setUpdateDate(LocalDateTime.now());
            noticeRepository.save(notice.get());
        }else{
            System.out.println("찾고자 하는 id가 없습니다. 비정상적인 접근입니다.");
        }
    }
    
    //ex_18
    public void updateNoticeError(Long id, NoticeInput noticeInput) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(()->new NoticeNotFoundException("찾고자 하는 공지사항의 글이 존재하지 않습니다."));
       
        notice.setTitle(noticeInput.getTitle());
        notice.setDescription(noticeInput.getDescription());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }
     //ex_19 하하, 18번에서 이미 다 했네 :) 날짜까지 수정하는게 19번의 예제!
    public void updateNoticeDate(Long id, NoticeInput noticeInput) {
        Notice notice = noticeRepository.findById(id)
            .orElseThrow(()->new NoticeNotFoundException("찾고자 하는 공지사항의 글이 존재하지 않습니다."));

        notice.setTitle(noticeInput.getTitle());
        notice.setDescription(noticeInput.getDescription());
        notice.setUpdateDate(LocalDateTime.now());
        noticeRepository.save(notice);
    }
```