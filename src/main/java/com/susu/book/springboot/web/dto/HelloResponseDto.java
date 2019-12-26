package com.susu.book.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor   // gradle 5 부터 바뀜 --> gradle 4로 설정해야됨
public class HelloResponseDto {
    private final String name;
    private final int amount;
}
