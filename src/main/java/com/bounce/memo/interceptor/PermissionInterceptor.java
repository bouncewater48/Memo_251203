package com.bounce.memo.interceptor;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request
            , HttpServletResponse response
            , Object handler) throws IOException {

        HttpSession session = request.getSession();
         Long userId = (Long)session.getAttribute("userId");
//         Long : null도 확인 가능한 상태

        // /memo/list
        String uri = request.getRequestURI();
        // 로그인이 안된 상태에서 메모와 관련된 페이지 접근을 막는다
        if(userId == null) {
            // /memo 로 시작하는 요청 url인 경우
            if(uri.startsWith("/memo")) {

                // 현재 접근하는 요청을 막고, 로그인 페이지로 리다이렉트
                // 리다이렉트로 넘길 코드를 먼저 구성한 다음, 막는 기능을 구현해야함
                response.sendRedirect("/user/login");

                return false;
            }
        } else {
            // 로그인이 안된 경우 로그인 화원가입 관련 페이지 접근 막기
            if(uri.startsWith("/user")) {

                response.sendRedirect("/memo/list");
                return false;
            }
        }
        return true;
    }

}
