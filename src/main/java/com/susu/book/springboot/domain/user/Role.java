package com.susu.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
//열거형 상수의 값이 불규칙적인 경우
//      1. 열거형 상수의 이름 옆에 원하는 값을 괄호()와 함께 적는다.
//
//      2. 지정된 값을 저장할 수 있는 인스턴스 변수와 생성자를 새로 추가한다.
//
//      주의! 열거형 상수를 모두 정의한 후 다른 멤버들을 추가한다.
//
//      열거형 상수의 마지막에 ' ; '을 잊지 않도록 주의한다.
//
//      열거형의 생성자는 제어자가 묵시적으로 private 이다.
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반사용자");

    private final String key;
    private final String title;
}
