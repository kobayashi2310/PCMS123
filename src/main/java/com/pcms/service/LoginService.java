package com.pcms.service;

import com.pcms.model.User;
import com.pcms.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final LoginRepository loginRepository;

    public Optional<User> login(String emailAddress, String password) {
        return loginRepository.login(emailAddress, password);
    }

}
