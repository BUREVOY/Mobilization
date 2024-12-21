package com.example.domain.domain.usecases;

import com.example.domain.domain.Models.Movie;
import com.example.domain.domain.repository.MovieRepository;

public class SaveMovieToFavoriteUseCase {
    private MovieRepository movieRepository;
    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    public boolean execute(Movie movie)
    {
        return movieRepository.saveMovie(movie);
    }
}
