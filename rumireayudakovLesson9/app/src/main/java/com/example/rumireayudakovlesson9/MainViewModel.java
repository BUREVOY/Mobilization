package com.example.rumireayudakovlesson9;

import androidx.lifecycle.ViewModel;

import com.example.domain.domain.Models.Movie;
import com.example.domain.domain.repository.MovieRepository;
import com.example.domain.domain.usecases.GetFavoriteFilmUseCase;
import com.example.domain.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean saveMovie(Movie movie) {
        return new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
    }

    public Movie getMovie() {
        return new GetFavoriteFilmUseCase(movieRepository).execute();
    }
}
