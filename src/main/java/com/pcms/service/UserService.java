package com.pcms.service;

import com.pcms.model.User;
import com.pcms.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String mail) {
        return userRepository.findByEmail(mail);
    }


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

}
