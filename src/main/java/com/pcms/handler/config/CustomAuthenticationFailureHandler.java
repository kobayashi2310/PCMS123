package com.pcms.handler.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException {

        String email = request.getParameter("email");
        request.getSession().setAttribute("LOGIN_EMAIL", email);
        String password = request.getParameter("password");
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.getSession().setAttribute("LOGIN_ERROR", "メールアドレスまたはパスワードが入力されていません。");
        } else {
            request.getSession().setAttribute("LOGIN_ERROR", "メールアドレスまたはパスワードが正しくありません。");
        }
        response.sendRedirect("/login?error");
    }
}
