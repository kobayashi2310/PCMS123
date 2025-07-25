package com.pcms.config;

import com.pcms.handler.config.CustomAuthenticationFailureHandler;
import com.pcms.service.CustomUserDetails;
import com.pcms.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomAuthenticationFailureHandler failureHandler;

    /**
     * アプリケーションのセキュリティフィルターチェーンを設定します。
     * これには、リクエストの承認、ログインフォームのカスタマイズ、ログアウト処理の設定が含まれます。
     *
     * @param http セキュリティを構成するために使用される {@link HttpSecurity} オブジェクト
     * @return アプリケーションに構成された {@link SecurityFilterChain}
     * @throws Exception セキュリティ構成の構築中にエラーが発生した場合
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // /mypage/*と/reservation.*　はログインが必要。それ以外は公開
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()
                        .requestMatchers("/mypage/**", "/reservation/**").authenticated()
                        .anyRequest().permitAll()
                )
                // ログインフォームの設定
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/mypage", true)
                        .successHandler((req, res, auth) -> {
                            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
                            String email = userDetails.getUsername();
                            String name = userDetails.getName();
                            req.getSession().setAttribute("LOGIN_EMAIL", email);
                            req.getSession().setAttribute("LOGIN_NAME", name);
                            req.getSession().setAttribute("loginMessage", "ログインに成功しました");
                            res.sendRedirect("/mypage");
                        })
                        .failureUrl("/login?error")
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                // ログアウトの設定
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            request.getSession().setAttribute("logoutMessage", "ログアウトしました");
                            response.sendRedirect("/");
                        })
                );

        http.userDetailsService(userDetailsService);
        return http.build();
    }

}