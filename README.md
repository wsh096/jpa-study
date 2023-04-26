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
    *  @GetMapping(value = "/api/notice2",produces = "application/json;charset=UTF-8")
    *  위와 같이 명시적으로 String json 타입으로 바꿔 줄 수 있지만, 이렇게 해도 String 자체의 형태를
    *  보여주기 때문에 Json 형태로 만들어지는 것은 아님.
    */
    @GetMapping(value = "/api/notice2")
    public Notice noticeString2() {
    return noticeService.notice();
        }
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
```
#### ex07- 을 위한 모델 클래스(Component)와 Service Class
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

```
    