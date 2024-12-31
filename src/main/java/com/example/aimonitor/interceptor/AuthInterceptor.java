package com.example.aimonitor.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        // 假设我们简单的验证 token 是否为 "fake-jwt-token-for-demo"
        if (token != null && token.equals("Bearer " + "fake-jwt-token-for-demo")) {
            return true;  // 通过验证
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Unauthorized");
            return false;  // 拦截请求
        }
    }
}
