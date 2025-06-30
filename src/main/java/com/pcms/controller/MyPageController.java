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

    /**
     * 「/mypage」エンドポイントへのGETリクエストを処理します。
     * セッション内にログインメッセージが存在する場合はモデルに追加し、
     * セッションから削除します。mypageテンプレートのビュー名を返します。
     *
     * @param model   ビューに属性を追加するために使用されるモデルオブジェクト
     * @param session セッション属性を取得するために使用されるHttpSessionオブジェクト
     * @return レンダリングするビューの名前、具体的には「protected/mypage」
     */
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

    /**
     * ユーザーのパスワード変更リクエストを処理します。
     * 現在のパスワードを検証し、有効な場合は新しいパスワードに更新します。
     * 成功した場合はマイページにリダイレクトされ、
     * そうでない場合はパスワード変更ページにエラーが表示されます。
     *
     * @param currentPassword   検証するユーザーの現在のパスワード
     * @param newPassword       ユーザーが設定する新しいパスワード
     * @param newPasswordCheck  新しいパスワードが一致するかどうかを確認します
     * @param session           現在ログインしているユーザーの電子メールを取得するために使用される HttpSession オブジェクト
     * @param redirectAttributes リダイレクト間でメッセージまたはエラーを渡すための RedirectAttributes オブジェクト
     * @return 成功または失敗に応じて、マイページまたはパスワード変更ページへのリダイレクト文字列
     */
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
