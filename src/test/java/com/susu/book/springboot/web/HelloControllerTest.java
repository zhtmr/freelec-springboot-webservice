package com.susu.book.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class HelloControllerTest {


    @Autowired
    private MockMvc mvc;

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
