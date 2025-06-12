package com.pcms.controller;

import com.pcms.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MyPageController {

    private final UserService userService;

    @GetMapping
    public String showMyPage(Model model, HttpSession session) {
        String loginMessage = (String) session.getAttribute("loginMessage");
        if (loginMessage != null) {
            model.addAttribute("loginMessage", loginMessage);
            session.removeAttribute("loginMessage");
        }
        return "protected/mypage";
    }

    @GetMapping("/passwordChange")
    public String showPasswordChange() {
        return "protected/changePass";
    }

    @PostMapping("/passwordChange")
    public String changePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String newPasswordCheck,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        String email = (String) session.getAttribute("LOGIN_EMAIL");
        System.out.println(email);

        try {
            userService.changePassword(email, currentPassword, newPassword, newPasswordCheck);
            redirectAttributes.addFlashAttribute("message", "パスワードを変更しました");
            return "redirect:/mypage";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/mypage/passwordChange";
        }
    }


}
