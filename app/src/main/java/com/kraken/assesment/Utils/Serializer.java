package com.kraken.assesment.Utils;

import com.google.gson.Gson;
import com.kraken.assesment.Models.Movie;

import java.util.Arrays;
import java.util.List;

public class Serializer {

    public static List<Movie> serializeMovies(String raw) {
        Gson gson = new Gson();
        Movie[] movies = gson.fromJson(raw, Movie[].class);
        return Arrays.asList(movies);
    }
}
