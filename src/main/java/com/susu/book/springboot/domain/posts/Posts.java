package com.susu.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor // 기본생성자
@Entity // 테이블 posts와 매칭
public class Posts {

    @Id // 해당 테이블의 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK생성 규칙 : auto increment
    private Long id;

    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    @Builder // 디자인 패턴 : 빌더 패턴
    public Posts(String title, String content, String author){
        this.title=title;
        this.content=content;
        this.author=author;
    }


}
