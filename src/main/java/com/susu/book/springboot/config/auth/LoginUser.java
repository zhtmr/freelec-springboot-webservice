package com.susu.book.springboot.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 메소드 파라미터로만 쓸수 있는 어노테이션
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser { // @interface : LoginUser 라는 이름을 가진 어노테이션
}
