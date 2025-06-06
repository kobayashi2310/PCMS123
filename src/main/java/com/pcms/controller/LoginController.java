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
