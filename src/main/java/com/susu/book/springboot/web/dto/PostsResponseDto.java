package com.susu.book.springboot.web.dto;

import com.susu.book.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;

    // 응답 Dto
    // entity는 controller로 나오면 안되기때문에 entity를 받아서 Dto로 반환
    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
