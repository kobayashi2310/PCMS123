package com.pcms.model;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
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

    /**
     * デフォルトコンストラクタ。
     */
    public User() {}

    public User(int user_id, String number, String name, String password, String email, String role) {
        this(user_id, number, name, password, email, UserRole.fromString(role));
    }

    /**
     * すべてのフィールドを指定するコンストラクタ。
     *
     * @param user_id ユーザーID
     * @param number 学籍番号または職員番号
     * @param name 氏名
     * @param password ハッシュ化されたパスワード
     * @param email メールアドレス
     * @param role ユーザーの役割（生徒または教師）
     */
    public User(int user_id, String number, String name, String password, String email, UserRole role) {
        this.user_id = user_id;
        this.number = number;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = UserRole.fromString(role);
    }
}