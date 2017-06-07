package com.matsdb.loicr.moviedb.ui.models;

/**
 * Created by loicr on 31/05/2017.
 */

public class ObjectMovie {
    String text, url;

    public ObjectMovie(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
