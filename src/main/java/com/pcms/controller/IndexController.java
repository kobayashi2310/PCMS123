package com.pcms.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(Model model, HttpSession session) {
        String loginMessage = (String) session.getAttribute("logoutMessage");
        if (loginMessage != null) {
            model.addAttribute("logoutMessage", loginMessage);
            session.removeAttribute("logoutMessage");
        }
        return "index";
    }

}
