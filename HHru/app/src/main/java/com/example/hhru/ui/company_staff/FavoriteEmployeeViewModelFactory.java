package com.example.hhru.ui.company_staff;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.domain.Repositories.FavoriteEmployeeRepository;

public class FavoriteEmployeeViewModelFactory implements ViewModelProvider.Factory {
    private final FavoriteEmployeeRepository favoriteEmployeeRepository;

    public FavoriteEmployeeViewModelFactory(FavoriteEmployeeRepository favoriteEmployeeRepository) {
        this.favoriteEmployeeRepository = favoriteEmployeeRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(FavoriteEmployeeViewModel.class)) {
            return (T) new FavoriteEmployeeViewModel(favoriteEmployeeRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

