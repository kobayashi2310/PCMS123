package com.pcms.handler.config;

import com.pcms.controller.LoginController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * アプリケーション内のコントローラーによってスローされた例外をキャプチャして処理するためのグローバル例外ハンドラーです。
 * このクラスには {@link ControllerAdvice} アノテーションが付けられており、すべてのコントローラーにグローバルに適用できます。
 * <br/>
 * 責任：
 * - 特定の例外を一元的に処理します <br/>
 * - 例外を適切な HTTP レスポンスまたはビューにマッピングします <br/>
 * <br/>
 * 現在の実装:
 * - {@link LoginController.LoginException} 型の例外をキャプチャします <br/>
 * - エラーの詳細を表示するための {@link ModelAndView} オブジェクトを構築し、「error」ビューに送信します
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * アプリケーション内でスローされた {@link LoginController.LoginException} 型の例外を処理します。
     * このメソッドは、エラーの詳細を含む {@link ModelAndView} オブジェクトを構築して返します。このオブジェクトは、「エラー」ビューに表示されます。
     *
     * @param e キャッチされた {@link LoginController.LoginException} （例外の詳細を含む）
     * @return 属性が「error」、「status」、ビュー名が「error」に設定された {@link ModelAndView} オブジェクト
     */
    @ExceptionHandler(LoginController.LoginException.class)
    public ModelAndView loginException(LoginController.LoginException e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("error", e.getMessage());
        mav.addObject("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        mav.setViewName("error");  // error.htmlのパス
        mav.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        return mav;
    }

}
