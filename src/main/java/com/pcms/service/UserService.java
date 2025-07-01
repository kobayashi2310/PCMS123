package com.pcms.service;

import com.pcms.dataaccess.mapper.ReservationMapper;
import com.pcms.dto.mypage.history.ReservationHistoryDto;
import com.pcms.model.User;
import com.pcms.dataaccess.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * UserService クラスは、ユーザーに関連するビジネスロジックを処理するサービス層のコンポーネントです。
 * ユーザーの認証、パスワード変更、および予約履歴の取得機能を提供します。
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ReservationMapper reservationMapper;

    /**
     * 電子メール アドレスでユーザーを取得します。
     *
     * @param mail 取得するユーザーのメールアドレス
     * @return 見つかった場合はユーザーを含む Optional、見つからない場合は空の Optional
     */
    public Optional<User> findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }

    /**
     * 現在のパスワードを検証し、新しいパスワードに更新します。
     * パスワードの一致や更新結果を確認し、適切な例外をスローします。
     *
     * @param email ユーザーのメールアドレス
     * @param currentPassword 現在のパスワード
     * @param newPassword 新しいパスワード
     * @param newPasswordCheck 確認用の新しいパスワード
     * @throws IllegalArgumentException 新しいパスワードと確認用パスワードが不一致の場合、または現在のパスワードが正しくない場合
     * @throws UsernameNotFoundException 指定されたメールアドレスに該当するユーザーが存在しない場合
     * @throws IllegalStateException パスワードの更新に失敗した場合
     */
    @Transactional
    public void changePassword(
            String email,
            String currentPassword,
            String newPassword,
            String newPasswordCheck)
    {
        if (!newPassword.equals(newPasswordCheck)) {
            throw new IllegalArgumentException("パスワードのが一致しません");
        }

        User user = findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("現在のパスワードが正しくありません");
        }

        String encodedPassword = passwordEncoder.encode(newPassword);
        int result = userRepository.changePassword(email, encodedPassword);

        if (result != 1) {
            throw new IllegalStateException("パスワードの更新に失敗しました");
        }

    }

    /**
     * ユーザーのメールアドレスに基づいて、そのユーザーの予約履歴を取得します。
     *
     * @param email 予約履歴を取得するユーザーのメールアドレス
     * @return ユーザーの予約履歴を含む {@code ReservationHistoryDto} オブジェクトのリスト
     * @throws UsernameNotFoundException 提供された電子メール アドレスに関連付けられたユーザーが見つからない場合
     */
    public List<ReservationHistoryDto> getUserHistory(String email) {

        int userId = findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: "
                        + email.replace("@", " @")))
                .getUser_id();

        return reservationMapper.findUserHistory(userId);

    }

}
