package com.susu.book.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index"; // src/main/resources/templates/index.mustache로 View Resolver가 처리함.
    }

    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; // posts-save.mustache

    }
}
