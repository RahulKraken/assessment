package com.kraken.assesment.Utils;

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
}
