# jpa-study 100
#### 0000 SpringSecurity 모든 곳에 허용하기 위한 설정    
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
#### 0003~0004 RestController 활용한 매핑
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


