package com.example.hhru;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.Repositories.AuthRepository;
import com.example.domain.Usecases.LoginUser;
import com.example.domain.Usecases.RegisterUser;

public class AuthViewModel extends ViewModel {

    private final LoginUser loginUser;
    private final RegisterUser registerUser;

    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public AuthViewModel(LoginUser loginUser, RegisterUser registerUser) {
        this.loginUser = loginUser;
        this.registerUser = registerUser;
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    // Метод для логина
    public void login(String email, String password) {
        // Обработка логики входа пользователя
        loginUser.execute(email, password, new AuthRepository.AuthCallback(){
            @Override
            public void onSuccess() {
                loginSuccess.postValue(true);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue(error);
            }
        });
    }

    // Метод для регистрации
    public void register(String email, String password) {
        // Обработка логики регистрации пользователя
        registerUser.execute(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess() {
                loginSuccess.postValue(true);
            }

            @Override
            public void onFailure(String error) {
                errorMessage.postValue(error);
            }
        });
    }
}

