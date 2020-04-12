package com.susu.book.springboot.web;

import com.susu.book.springboot.config.auth.LoginUser;
import com.susu.book.springboot.config.auth.dto.SessionUser;
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
//    private final HttpSession httpSession; -> 어노테이션 기반으로 바꾸면 필요없음

    // 목록 보기
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        // postsService.findAllDesc() 로 가져온 결과를 posts로 index.mustache에 전달 -> view에서 {{#posts}}로 조회
        model.addAttribute("posts",postsService.findAllDesc());
        // 로그인 성공시 세션에 "user" 키로 저장된 값 가져오기
//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); -> 메소드 파라미터로 바로 세션 가져오기때문에 필요없음
        // 값이 있으면 model에 userName을 담아서감. 값이 없으면 모델에 아무런 값이 없으므로 로그인 버튼이 보이게됨.
        if(user != null){
            model.addAttribute("userName", user.getName()); // ** BUG : 윈도우에서 특이하게 username 으로 명명할 시 로컬 시스템 환경변수와 중복됨
            System.out.println(user.getName());
        }
        return "index"; // src/main/resources/templates/index.mustache로 View Resolver가 처리함
    }

    // 저장하기
    @GetMapping("/posts/save")
    public String postsSave(){
        return "posts-save"; // posts-save.mustache
    }

    // 수정하기
    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto); // {{post.id}}, {{post.title}}, {{post.author}}

        return "posts-update";
    }


}
