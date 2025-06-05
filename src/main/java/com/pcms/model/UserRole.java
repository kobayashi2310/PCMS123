package com.pcms.model;

import lombok.Getter;

/**
 * ユーザーの役割を表す列挙型。
 * <p>
 * システム上のユーザーが「生徒」か「教師」かを区別するために使用される。
 * 各値はデータベースに保存される文字列値（小文字）と対応している。
 * </p>
 */
@Getter
public enum UserRole {

    /**
     * 生徒。データベース上では "student" として保存される。
     */
    STUDENT("student"),

    /**
     * 教師。データベース上では "teacher" として保存される。
     */
    TEACHER("teacher");

    /**
     * データベースとマッピングされる文字列表現。
     */
    private final String value;

    /**
     * 指定された文字列値を持つユーザー役割を生成する。
     *
     * @param value データベースなどで使用される文字列値
     */
    UserRole(String value) {
        this.value = value;
    }

    /**
     * 列挙値を対応する文字列として返す。
     *
     * @return 文字列形式のユーザー役割（例: "student", "teacher"）
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * 文字列から対応する {@code UserRole} を取得する。
     *
     * @param value "student" または "teacher" に一致する文字列
     * @return 対応する {@code UserRole} 値
     * @throws IllegalArgumentException 指定された値がどの役割にも一致しない場合
     */
    public static UserRole fromString(String value) {
        for (UserRole role : UserRole.values()) {
            if (role.value.equals(value.toLowerCase())) {
                return role;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }
}