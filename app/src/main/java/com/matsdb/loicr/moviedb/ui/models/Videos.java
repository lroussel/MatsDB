package com.matsdb.loicr.moviedb.ui.models;

import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class Videos {
    int id;
    List<Video> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
