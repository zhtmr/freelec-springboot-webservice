package com.susu.book.springboot.web;

import com.susu.book.springboot.service.PostsService;
import com.susu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        // postsService.findAllDesc() 로 가져온 결과를 posts로 index.mustache에 전달 {{#posts}}로 조회
        model.addAttribute("posts",postsService.findAllDesc());
        return "index"; // src/main/resources/templates/index.mustache로 View Resolver가 처리함
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; // posts-save.mustache
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto); // {{post.id}}, {{post.title}}, {{post.author}}

        return "posts-update";
    }


}
