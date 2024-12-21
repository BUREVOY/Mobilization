package com.example.hhru;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.domain.Usecases.LoginUser;
import com.example.domain.Usecases.RegisterUser;

public class AuthViewModelFactory implements ViewModelProvider.Factory {

    private final LoginUser loginUser;
    private final RegisterUser registerUser;

    public AuthViewModelFactory(LoginUser loginUser, RegisterUser registerUser) {
        this.loginUser = loginUser;
        this.registerUser = registerUser;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new AuthViewModel(loginUser, registerUser);
    }
}
