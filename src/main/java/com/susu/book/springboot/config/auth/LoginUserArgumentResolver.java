package com.susu.book.springboot.config.auth;

import com.susu.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        /*  클래스 타입이 아니라 왜 커스텀 어노테이션 타입으로 바인딩 하는가?
        *
        *   어노테이션 존재유무 판단 없이 특정 클래스가 메소드에서 선언되어있으면 다 리졸버의 대상으로 보도록 구성하게 되면
        *   어노테이션 체크 없이 해당 클래스가 선언만 되어있으면 무조건 값이 바인딩 되도록 되어있으면 메소드간 호출에서도 매번 세션 사용자값이 사용되기 때문.
        *
        *   ex)
        *
            Controller {
               public void a (LoginUser loginUser) {
                   loginUser.updateLoginTime();
                   service.save(loginUser);
               }
            }

            Service {
                   save (LoginUser loginUser) {
                      ~~~
                  }
            }

            리졸버에서 LoginUser 만 있으면 무조건 현재 세션에 있는 값을 반환하기 때문입니다.
            개발자 입장에선 컨트롤러 접근할때마다 로그인 시간을 갱신하는 코드를 작성했는데, 서비스가 받질 못하는 케이스가 생기는 것이죠.

            상황에 따라 세션에 있는 값을 사용하기도, 혹은 호출하는 메소드에서 넣어준 값을 사용하기도 해야하는데,
            위 방식은 선택지가 아예 사라지게 됩니다
         */
        // 파라미터에 LoginUser 어노테이션이 붙어 있는가?
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        // 파라미터 타입이 SessionUser 인가?
        /* if(parameter.getParameterType == LoginUser.class) {return true} */
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        // 둘다 참일 경우 true 반환
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달할 객체 : session 객체
        return httpSession.getAttribute("user");
    }
}
