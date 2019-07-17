package com.kraken.assesment.Utils;

import com.google.gson.Gson;
import com.kraken.assesment.Models.Movie;
import com.kraken.assesment.Models.Review;

import java.util.Arrays;
import java.util.List;

public class Serializer {

    public static List<Movie> serializeMovies(String raw) {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(raw, Movie[].class));
    }

    public static Movie serializeMovie(String raw) {
        Gson gson = new Gson();
        return gson.fromJson(raw, Movie.class);
    }

    public static List<Review> serializeReviews(String raw) {
        Gson gson = new Gson();
        return Arrays.asList(gson.fromJson(raw, Review[].class));
    }
}
