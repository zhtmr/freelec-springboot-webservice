package com.susu.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaAuditing 가 삭제됨. // JPA Auditing 활성화 -> 최소 하나의 @Entity 클래스가 필요함.
// HelloControllerTest 는 @WebMvcTest 이고,  Controller layer 에서만 실행되기 때문에 이 @EnableJpaAuditing 어노테이션은 에러발생시킴.
// 따라서 @EnableJpaAuditing 을 config 패키지에 분리시킨다.
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);

    }

}
