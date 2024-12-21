package com.example.rumireayudakovlesson9;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.data.Storage.MovieStorage;
import com.example.data.data.Storage.SharedPrefMovieStorage;
import com.example.data.data.repository.MovieRepositoryImpl;
import com.example.domain.domain.repository.MovieRepository;

//создание экз MainViewModel
// MainViewModel з от MovieRepository з от MovieStorage - для корректного отображения зависимостей
public class ViewModelFactory implements ViewModelProvider.Factory {
    private Context context;

    public ViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        MovieStorage sharedPrefMovieStorage = new SharedPrefMovieStorage(context);
        MovieRepository movieRepository = new MovieRepositoryImpl(sharedPrefMovieStorage);
        return (T) new MainViewModel(movieRepository);
    }
}
