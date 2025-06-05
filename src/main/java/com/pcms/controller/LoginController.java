package com.pcms.controller;

import com.pcms.model.User;
import com.pcms.service.LoginService;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Slf4j
@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final User sessionUser;
    private final LoginService loginService;

    @GetMapping
    public String showLogin() {
        return "public/login";
    }

    public static class LoginException extends RuntimeException {
        public LoginException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
