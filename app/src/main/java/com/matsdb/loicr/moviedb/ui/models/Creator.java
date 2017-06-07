package com.matsdb.loicr.moviedb.ui.models;

/**
 * Created by loicr on 01/06/2017.
 */

public class Creator {
    int id;
    String name, profile_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }
}
