package com.kraken.assesment.Utils;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static String API_KEY = "e72b4559ffdb15dbb9c5de5c94948f25";
    public static String BASE_URL = "https://api.themoviedb.org/3";
    public static String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    public static Map<String, Integer> genres = new HashMap<String, Integer>() {
        {
            put("Action", 28);
            put("Adventure", 12);
            put("Animation", 16);
            put("Comedy", 35);
            put("Crime", 80);
            put("Documentary", 99);
            put("Drama", 18);
            put("Family", 10751);
            put("Fantasy", 14);
            put("History", 36);
            put("Horror", 27);
            put("Music", 10402);
            put("Mystry", 9648);
            put("Romance", 10749);
            put("Science Fiction", 878);
            put("TV Movie", 10770);
            put("Thriller", 53);
            put("War", 10752);
            put("Western", 37);
        }
    };
}
