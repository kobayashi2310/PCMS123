package com.pcms.service;

import com.pcms.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * {@link UserDetailsService} インターフェースのカスタム実装。
 * 提供されたメールアドレスに基づいてユーザー固有のデータを読み込みます。
 * このサービスは {@link UserService} と統合してユーザー情報を取得します。
 * <br/>
 * このクラスには、
 * アプリケーションでユーザー認証を制御するために使用される
 * Spring 管理コンポーネントであることを示す {@link Service} アノテーションが付けられています。
 * また、{@link RequiredArgsConstructor} アノテーションを利用して、最終フィールドのコンストラクターを自動的に生成します。
 * <br/>
 * このクラスの主な役割は以下のとおりです。<br/>
 * - メールアドレスでユーザーを読み込み、そのユーザー情報を Spring Security で必要な {@link UserDetails} オブジェクトに変換する。<br/>
 * - 指定されたメールアドレスを持つユーザーが見つからない場合は例外をスローする。
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService loginService;

    /**
     * 指定されたメールアドレスでユーザーの詳細を読み込みます。
     * このメソッドは、メールアドレスを一意の識別子として基になるデータソースからユーザー情報を取得し、
     * それをSpring Securityに適したUserDetailsオブジェクトに変換します。
     * また、ユーザーが見つからない場合は適切な例外がスローされるようにします。
     *
     * @param email ロードするユーザーの電子メールアドレス。主識別子として使用されます。
     * @return 指定されたメールに関連付けられたユーザーを表す {@link UserDetails} オブジェクト
     * @throws UsernameNotFoundException 指定されたメールアドレスを持つユーザーが存在しない場合
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return loginService.findByEmail(email)
                .map(this::toUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * {@link User} オブジェクトを、Spring Security が認証および認可の目的で使用する {@link UserDetails} オブジェクトに変換します。
     *
     * @param user {@link UserDetails} に変換するユーザー
     * @return 提供されたユーザーから得られたSpring Security互換の情報を含む{@link UserDetails}オブジェクト
     */
    private UserDetails toUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name()) // EnumからROLEを生成
                .build();
    }
}

