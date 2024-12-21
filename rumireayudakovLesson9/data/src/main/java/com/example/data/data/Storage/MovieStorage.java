package com.example.data.data.Storage;

import com.example.domain.domain.Models.Movie;

public interface MovieStorage {
    boolean saveMovie(Movie movie);
    Movie get();
}
