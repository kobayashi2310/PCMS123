package com.pcms.model;

import lombok.*;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;

/**
 * ユーザー情報を表すモデルクラス。
 * <p>
 * 学籍番号・氏名・メールアドレス・パスワード・ユーザー区分（生徒／教師）などを保持する。
 * </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SessionScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class User implements Serializable {

    @java.io.Serial
    private static final long serialVersionUID = 1L;

    /**
     * ユーザーの一意な識別子（主キー）。
     */
    private int user_id;

    /**
     * 学籍番号または職員番号
     */
    private String number;

    /**
     * ユーザーの名前。
     */
    private String name;

    /**
     * ハッシュ化されたパスワード。
     */
    private String password;

    /**
     * ユーザーのメールアドレス。こちらも一意制約がかかっている。
     */
    private String email;

    /**
     * ユーザーの役割（生徒または教師）。
     */
    private UserRole role;

    public User(int user_id, String number, String name, String password, String email, String role) {
        this(user_id, number, name, password, email, UserRole.fromString(role));
    }
}