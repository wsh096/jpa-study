package com.example.jpastudy.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 알아두기. 강의와의 차이점. 강의에서는 @Data 어노테이션을 통해 손쉽게 구현을 했지만 실무에서 Data를 쓰는 경우는 거의 없으므로 ToString 일괄 적용의 문제 해당
 * 부분을 반영하여 별도의 Getter, Setter 어노테이션을 사용
 * + ToString 어노테이션의 활용 해당 어노테이션이 있으면, 값 반환에 있어서 주소값이 아닌 해당 값이 가지고 있는 문자열 값이 반환된다.
 * + Component 어노테이션을 활용해 아래의 클래스를 사용할 수 있게 @Bean 주입
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString //해당 어노테이션이 있어야, 주소값이 아니라 문자열 값으로 반환
@Component // 해당 클래스의 빈 주입
//@Entity db 연결한 것이 아니기 때문에 해당 어노테이션은 시기상조,
public class NoticeInput {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)

    private String title;
    private String description;
}
