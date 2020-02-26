package com.susu.book.springboot.web;

import com.susu.book.springboot.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model){
        // postsService.findAllDesc() 로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts",postsService.findAllDesc());
        return "index"; // src/main/resources/templates/index.mustache로 View Resolver가 처리함
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; // posts-save.mustache
    }


}
