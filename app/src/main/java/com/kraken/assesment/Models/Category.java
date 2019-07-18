package com.kraken.assesment.Models;

import java.io.Serializable;

public class Category implements Serializable {

    public String title;

    public Category() {
    }

    public Category(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
