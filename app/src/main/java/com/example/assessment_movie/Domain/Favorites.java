package com.example.assessment_movie.Domain;

import java.util.ArrayList;
import java.util.List;

public class Favorites {
    private static Favorites instance = null;
    private List<Result> favoriteMovies;

    private Favorites() {
        favoriteMovies = new ArrayList<>();
    }

    public static Favorites getInstance() {
        if (instance == null) {
            instance = new Favorites();
        }
        return instance;
    }

    public void addMovie(Result movie) {
        favoriteMovies.add(movie);
    }

    public void removeMovie(Result movie) {
        favoriteMovies.remove(movie);
    }

    public boolean isFavorite(Result movie) {
        return favoriteMovies.contains(movie);
    }

    public List<Result> getFavoriteMovies() {
        return favoriteMovies;
    }
}
