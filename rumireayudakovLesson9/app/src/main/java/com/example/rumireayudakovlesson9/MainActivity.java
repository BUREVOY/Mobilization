package com.example.rumireayudakovlesson9;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.data.Storage.MovieStorage;
import com.example.data.data.Storage.SharedPrefMovieStorage;
import com.example.data.data.repository.MovieRepositoryImpl;
import com.example.domain.domain.Models.Movie;
import com.example.domain.domain.repository.MovieRepository;
import com.example.domain.domain.usecases.GetFavoriteFilmUseCase;
import com.example.domain.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainActivity extends AppCompatActivity { // не от Activity чтобы viewmodel работал

    private MainViewModel vm;
    private MovieRepository movieRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация MovieStorage
        MovieStorage movieStorage = new SharedPrefMovieStorage(this);

        // Инициализация репозитория
        movieRepository = new MovieRepositoryImpl(movieStorage);

        // Получаем элементы интерфейса
        EditText editTextMovie = findViewById(R.id.editTextMovie);
        TextView textViewMovie = findViewById(R.id.textViewMovie);

        // Устанавливаем слушатель для кнопки "Сохранить фильм"
        findViewById(R.id.buttonSaveMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String movieName = editTextMovie.getText().toString();
                Boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(new Movie(2, movieName));
                textViewMovie.setText(String.format("Save result: %s", result));
            }
        });

        // Устанавливаем слушатель для кнопки "Получить фильм"
        findViewById(R.id.buttonGetMovie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
                textViewMovie.setText(String.format("Movie name: %s", movie.getName()));
            }
        });

        Log.d(MainActivity.class.getSimpleName(), "MainActivity created");
        vm = new ViewModelProvider(this, new ViewModelFactory(this)).get(MainViewModel.class);
    }
}
