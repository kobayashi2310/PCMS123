package com.pcms.handler.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * このクラスは、{@link AuthenticationFailureHandler} インターフェースのカスタム実装として機能します。
 * Spring Security アプリケーションで認証の試行が失敗した場合の動作を処理します。
 * <br/>
 * このクラスは、ユーザーが入力したメールアドレスなど、
 * 失敗したログイン試行の詳細を取得し、HTTPセッションに保存します。
 * 失敗した場合は、適切なエラーメッセージを表示し、エラーインジケーター付きのログインページにユーザーをリダイレクトします。
 * <br/>
 * 機能： <br/>
 * - 失敗したログインリクエストからメールアドレスとパスワードを抽出します。<br/>
 * - ログインフォームで再利用できるように、メールアドレスをHTTPセッションに保存します。<br/>
 * - 詳細なエラーメッセージをセッションに保存します。<br/>
 * - メールアドレスとパスワードの両方が空の場合、入力漏れのエラーメッセージを設定します。<br/>
 * - メールアドレスまたはパスワードが無効な場合、資格情報が正しくないことを示す一般的なエラーメッセージを設定します。<br/>
 * - ログイン失敗を示すために、ユーザーを「/login?error」URLにリダイレクトします。
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 認証試行が失敗した場合に実行されるアクションを処理します。
     * このメソッドは、失敗したログインリクエストからメールアドレスとパスワードを取得し、ログインフォームで再利用するためにメールアドレスをセッションに保存します。
     * また、検証結果に基づいて詳細なエラーメッセージをセッションに設定します。最後に、エラーインジケーター付きのログインページにユーザーをリダイレクトします。
     *
     * @param request  認証試行に関連付けられた{@link HttpServletRequest}（メールアドレスやパスワードなどのユーザー入力を含む）
     * @param response ログインページへのリダイレクトを返すために使用される {@link HttpServletResponse}
     * @param exception 認証失敗の原因を表す {@link AuthenticationException}
     * @throws IOException リダイレクト中に入力または出力例外が発生した場合
     */
    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException {

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
