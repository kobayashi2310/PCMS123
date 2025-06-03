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

    @PostMapping
    public String login(
            @RequestParam(required = false) String emailAddress,
            @RequestParam(required = false) String password,
            Model model
    ) {

        //log.info("Login attempt with email: {}", emailAddress);


        model.addAttribute("emailAddress", emailAddress);

        // 入力値の検証
        if (emailAddress == null || emailAddress.trim().isEmpty()
            || password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "メールアドレスかパスワードが未入力です");
            return "public/login";
        }

        try {
            String trimAddress = emailAddress.trim();
            String trimPassword = password.trim();

            if (!emailAddressCheck(trimAddress)) {
                model.addAttribute("error", "有効なメールアドレスを入力してください");
                return "public/login";
            }

            User authenticatedUser = loginService.login(trimAddress, trimPassword).orElse(null);

            if (authenticatedUser == null) {
                model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
                return "public/login";
            }

            sessionUser.setUser_id(authenticatedUser.getUser_id());
            sessionUser.setNumber(authenticatedUser.getNumber());
            sessionUser.setName(authenticatedUser.getName());
            sessionUser.setEmail(authenticatedUser.getEmail());
            sessionUser.setRole(authenticatedUser.getRole());


        } catch (Exception e) {
            //log.error("ログイン処理でエラーが発生:", e);
            throw new LoginException("システムエラーが発生", e);
        }

        return "redirect:/protected/mypage";
    }

    private boolean emailAddressCheck(String emailAddress) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(emailAddress).matches();
    }

    public static class LoginException extends RuntimeException {
        public LoginException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
