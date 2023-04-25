# jpa-study 100
### 알아두기_강의와의 차이점.
    강의의 경우, Controller 패키지를 최초 빌드하는 동안에
    Service 명에 따라 생성하고 있다. 
    하지만 실무적인 관점에서 Controller와 같은 명칭으로 나누어 만들었고
    나의 경우는 컨트롤러를 가장 밑에 패키지로 분류해 관리하고자 'web'이라는 이름으로 관리
    마찬가지로, config 패키지로 설정 파일 역시 관리 중

#### 0000 SpringSecurity 모든 곳에 허용하기 위한 설정    
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
#### 0001~0002 Controller 활용한 매핑
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
#### 0003~0005 RestController 활용한 매핑
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
#### 0006-
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

