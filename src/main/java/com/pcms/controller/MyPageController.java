package com.pcms.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @GetMapping
    public String showMyPage(Model model, HttpSession session) {
        String loginMessage = (String) session.getAttribute("loginMessage");
        if (loginMessage != null) {
            model.addAttribute("loginMessage", loginMessage);
            session.removeAttribute("loginMessage");
        }
        return "protected/mypage";
    }

}
