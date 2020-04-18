package com.susu.book.springboot.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.susu.book.springboot.domain.posts.Posts;
import com.susu.book.springboot.domain.posts.PostsRepository;
import com.susu.book.springboot.web.dto.PostsSaveRequestDto;
import com.susu.book.springboot.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    // 아래 @WithMockUser 를 사용하기 위해선 MockMvc가 필요하다. MockMvc 타입 객체 담을 필드.
    private MockMvc mvc;

    // MockMvcBuilder로 부터 MockMvc 객체를 얻어온다.
    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // build.gradle 에 추가한 spring-security-test 로 부터 static import 할수있다.
                .build();
    } // @AutoConfigureMockMvc 를 사용해서 setup() 을 자동으로 구성해주는 내용이 https://stackoverflow.com/questions/48589893/autowire-mockmvc-spring-data-rest 에 나오긴 하지만,
    // 완벽하지 않은듯.

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    // 글 작성 등록이 되는지 테스트함.
    @Test
    @WithMockUser(roles = "USER") // 모의 사용자. 테스트 시 스프링 시큐리티 302 에러 방지용 권한 부여. @WithMockUser는 MockMvc에서만 작동한다.
    public void Posts_등록된다() throws Exception {
        //given
        String title="title";
        String content="content";
        PostsSaveRequestDto requestDto=PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url="http://localhost:" + port + "/api/v1/posts";

        //when (주소, 전달할 것, 반환 타입)
//        ResponseEntity<Long> responseEntity=restTemplate.postForEntity(url,requestDto,Long.class);
        mvc.perform(post(url)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK); // 상태가 200인가
//        assertThat(responseEntity.getBody()).isGreaterThan(0L); // 생성된 PK가 0보다 큰가

        List<Posts> all = postsRepository.findAll(); // h2 db에 저장된 모든값 불러오기
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    // 수정이 되는지 테스트
    @Test
    @WithMockUser(roles = "USER")
    public void Posts_수정된다() throws Exception{
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }
}
