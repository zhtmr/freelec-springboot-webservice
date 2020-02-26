package com.susu.book.springboot.web;

import com.susu.book.springboot.service.PostsService;
import com.susu.book.springboot.web.dto.PostsResponseDto;
import com.susu.book.springboot.web.dto.PostsSaveRequestDto;
import com.susu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 등록
    // 값을 Dto로 받아서 sevice의 save 메소드로 전달
    //      [  @RequestBody : HTML -> java
    //         @ResponseBody : java -> HTML  ]
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    // 수정
    @PutMapping("/api/v1/posts/{id}") // {id} -> @PathVariable 매핑됨
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id,requestDto);
    }

    // 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}
