package com.pcms.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    /**
     * ルートURLへのGETリクエストを処理し、必要な属性を持つモデルを準備します。
     * セッションに「logoutMessage」属性が見つかった場合は、
     * それをモデルに追加し、セッションから削除します。
     *
     * @param model ビューをレンダリングするために属性が追加されるモデル
     * @param session ログアウト関連の属性が取得されるHTTPセッション
     * @return レンダリングされるビューの名前。この場合は「index」
     */
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
