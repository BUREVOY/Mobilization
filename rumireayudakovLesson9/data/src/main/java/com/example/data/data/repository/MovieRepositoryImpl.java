package com.example.data.data.repository;

import com.example.data.data.Storage.MovieStorage;
import com.example.domain.domain.Models.Movie;
import com.example.domain.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {
    private MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        return movieStorage.saveMovie(movie);
    }

    @Override
    public Movie getMovie() {
        return movieStorage.get();
    }
}
