package com.example.domain.Usecases;

import com.example.domain.Repositories.AuthRepository;

public class LoginUser {
    private final AuthRepository authRepository;

    public LoginUser(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.login(email, password, callback);
    }
}
