package com.example.domain.Usecases;

import com.example.domain.Repositories.AuthRepository;

public class RegisterUser {
    private final AuthRepository authRepository;

    public RegisterUser(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void execute(String email, String password, AuthRepository.AuthCallback callback) {
        authRepository.register(email, password, callback);
    }
}
