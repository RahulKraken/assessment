package com.kraken.assesment.Models;

import java.io.Serializable;

public class Movie implements Serializable {

    public String title, overview, release_date, backdrop_path, poster_path, homepage, original_language;
    public int vote_count, id, runtime;
    public double vote_average, popularity;
    public boolean video, adult;
    public int[] genre_ids;

    public Movie(String title, double vote_average) {
        this.title = title;
        this.vote_average = vote_average;
    }
}
