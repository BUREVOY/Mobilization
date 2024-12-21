package com.example.domain.Repositories;

public interface AuthRepository {
    void login(String email, String password, AuthCallback callback);
    void register(String email, String password, AuthCallback callback);

    interface AuthCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }
}
