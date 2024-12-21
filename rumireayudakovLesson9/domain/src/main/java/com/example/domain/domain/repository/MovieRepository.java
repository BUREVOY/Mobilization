package com.example.domain.domain.repository;

import com.example.domain.domain.Models.Movie;

public interface MovieRepository {
    public boolean saveMovie(Movie movie);
    public Movie getMovie();
}
