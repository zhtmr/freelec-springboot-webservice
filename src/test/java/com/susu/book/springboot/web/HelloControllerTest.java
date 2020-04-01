package com.susu.book.springboot.web;

import com.susu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// @WebMvcTest 는 @Service를 읽지 못하기 때문에 Test 에러가 난다. secure = false 옵션으로 spring security 를 타지 않고
// 테스트를 할 수 있지만 현재 deprecated 된 상태이므로 쓰지 않는다. 대신 필터로 scan 할 대상에서 SecurityConfig.class 를 제외시킨다.
// ASSIGNABLE_TYPE : 대상 구성 요소가 할당 (확장 / 구현) 될 수있는 클래스 (또는 인터페이스) (public class SecurityConfig extends WebSecurityConfigurerAdapter {})
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)})
public class HelloControllerTest {


    @Autowired
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello="hello";

        /*
        *  여기서 get()이 static import 안돼서 고생했음
        *
        *   1. sdkman 으로 java 버전 바꾸고 -> project structure.. 에서 버전 바꿔봤음
        *   2. ide 설정에서 File –>> Settings –>> Editor –>> General –>> Auto Import –>> 해당 두개의 부분을 체크처리한다.
                - Add unambi...
                - Optimize import...
            3. get만 치고 import하는게 아니라 get()까지 치고 import 한다. (java버전 바꾸니까 여기 표시되는 import 내용이 달라졋음)

        * */

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴된다() throws Exception {
        String name="hello";
        int amount=1000;

        /*
         *  1. /hello/dto로 get요청
            2. "name", "amount" 파라미터 검증
            3. name, amount,와 같은지 테스트
         */

        mvc.perform(
                get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(name)))
                .andExpect(jsonPath("$.amount",is(amount)));

    }

}
