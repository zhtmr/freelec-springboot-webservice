package com.susu.book.springboot.web;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Nginx 에서 8081, 8082 판단 기준
@RequiredArgsConstructor
@RestController
public class ProfileController {
  private final Environment env;

  @GetMapping("/profile")
  public List<String> profile(){
    // real, oauth, real-db 등의 현재 실행중인 프로필(.properties 파일). deploy.sh 에서 현재 활성화 시킨 profile : -Dspring.profiles.active=real \
    List<String> profiles = Arrays.asList(env.getActiveProfiles()); // asList : 배열을 List 형태로 가져옴. 원본 배열을 수정해도 List 바뀌고, 그 반대도 마찬가지. add는 안됨.
    List<String> realProfiles = Arrays.asList("real", "real1", "real2");
    // 삼항연산자
    //  String defaultProfile;
    //    if (profiles.isEmpty()) {
    //      defaultProfile = "default";
    //    } else {
    //      defaultProfile = profiles.get(0);
    //    }
    String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

    // ---- Stream API chain with loop 대신 아래처럼 사용가능
    //  for (String profile : profiles) {
    //      if (realProfiles.contains(profile)) {
    //        return profile;
    //      }
    //    }
    //    return defaultProfile;


    return profiles;
    // Stream은 자바 8부터 추가된 기능으로 "컬렉션, 배열등의 저장 요소를 하나씩 참조하며 함수형 인터페이스(람다식)를 적용하며 반복적으로 처리할 수 있도록 해주는 기능"이다.
//    return  profiles.stream()
//        .filter(realProfiles::contains)
//        .findAny()
//        .orElse(defaultProfile); // 최종연산을 끝낸뒤에도 객체가 비어있으면 defaultProfile

//    List<String> names = Arrays.asList("jeong", "pro", "jdk", "java");
//// 기존의 코딩 방식
//    long count = 0;
//    for (String name : names) {
//      if (name.contains("o")) {
//        count++;
//      }
//    }
//    System.out.println("Count : " + count); // 2
//
//// 스트림 이용한 방식
//    count = 0;
//    count = names.stream().filter(x -> x.contains("o")).count();
//    System.out.println("Count : " + count); // 2
  }
}
