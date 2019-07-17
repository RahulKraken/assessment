package com.kraken.assesment.Utils;

import com.kraken.assesment.Models.Movie;

public class URLBuilder {

    public static String movieWithGenre(int genre) {
        return Constants.BASE_URL + "/discover/movie?api_key=" + Constants.API_KEY + "&language=en-US&with_genre=" + genre;
    }

    public static String movieListWithGenre(int genre, int page) {
        return Constants.BASE_URL + "/discover/movie?api_key=" + Constants.API_KEY + "&language=en-US&with_genre=" + genre + "&page=" + page;
    }

    public static String imageUrl(String path) {
        return Constants.IMAGE_BASE_URL + path;
    }

    public static String getMovie(int id) {
        return Constants.BASE_URL + "/movie/" + id + "?api_key=" + Constants.API_KEY;
    }

    public static String getReviews(int id) {
        return Constants.BASE_URL + "/movie/" + id + "/reviews?api_key=" + Constants.API_KEY;
    }

    public static String searchMovie(String key) {
        return Constants.BASE_URL + "/search/movie?query=" + key + "&api_key=" + Constants.API_KEY;
    }
}
