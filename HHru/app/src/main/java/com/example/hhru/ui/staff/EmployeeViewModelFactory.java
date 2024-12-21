package com.example.hhru.ui.staff;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.domain.Repositories.FavoriteEmployeeRepository;
import com.example.hhru.ui.staff.EmployeeViewModel;

public class EmployeeViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final FavoriteEmployeeRepository favoriteEmployeeRepository;

    public EmployeeViewModelFactory(Application application, FavoriteEmployeeRepository favoriteEmployeeRepository) {
        this.application = application;
        this.favoriteEmployeeRepository = favoriteEmployeeRepository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EmployeeViewModel.class)) {
            return (T) new EmployeeViewModel(application, favoriteEmployeeRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
