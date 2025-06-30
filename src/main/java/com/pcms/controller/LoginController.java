package com.pcms.controller;

import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    /**
     * ログインページを表示するためのGETリクエストを処理します。
     * メールやエラーメッセージなど、ログインに関連するデータをHTTPセッションから取得し、
     * ログインページでレンダリングするためにモデルに追加します。
     * 取得後、これらの属性はセッションから削除されます。
     *
     * @param session 「LOGIN_EMAIL」や「LOGIN_ERROR」などの属性を取得するために使用されるHTTPセッション
     * @param model ビューのログインメールやエラーなどの属性を保存するために使用するモデル
     * @return レンダリングされるビューの名前、具体的には「public/login」
     */
    @GetMapping
    public String showLoginPage(HttpSession session, Model model) {
        String loginEmail = (String) session.getAttribute("LOGIN_EMAIL");
        String loginError = (String) session.getAttribute("LOGIN_ERROR");

        if (loginEmail != null) {
            model.addAttribute("loginEmail", loginEmail);
            session.removeAttribute("LOGIN_EMAIL");
        }
        if (loginError != null) {
            model.addAttribute("loginError", loginError);
            session.removeAttribute("LOGIN_ERROR");
        }

        return "public/login";
    }

    public static class LoginException extends RuntimeException {
        public LoginException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
