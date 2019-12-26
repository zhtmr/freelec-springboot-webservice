package com.susu.book.springboot.web.dto;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class HelloResponseDtoTest {
    @Test
    public void 롬복_기능_테스트(){
        //given
        String name="test";
        int amount=1000;

        //when
        HelloResponseDto dto=new HelloResponseDto(name,amount);

        //then
        assertThat(dto.getName()).isEqualTo(name); // assertj 를 import해야 추가 라이브러리 필요없고, 자동완성이 정확함
        assertThat(dto.getAmount()).isEqualTo(amount);
    }
}
