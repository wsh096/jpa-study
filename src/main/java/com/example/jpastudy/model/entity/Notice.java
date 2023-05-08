package com.example.jpastudy.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
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
    private LocalDateTime updateDate;
    private long watch;
    //like 는 H2의 예약어기 때문에 생성이 안됨!
    private long likes;
    private boolean deleted;
    private LocalDateTime deletedDate;


}
