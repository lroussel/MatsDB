package com.matsdb.loicr.moviedb.ui.models;

import java.util.List;

/**
 * Created by loicr on 31/05/2017.
 */

public class PeopleImage {
    int id;
    List<Images> profiles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Images> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Images> profiles) {
        this.profiles = profiles;
    }
}
