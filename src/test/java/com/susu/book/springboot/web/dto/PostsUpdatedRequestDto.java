package com.susu.book.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdatedRequestDto {
    private String title;
    private String content;

    @Builder
    public PostsUpdatedRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
