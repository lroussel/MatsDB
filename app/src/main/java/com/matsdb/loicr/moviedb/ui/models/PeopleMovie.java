package com.matsdb.loicr.moviedb.ui.models;

import java.util.List;

/**
 * Created by loicr on 31/05/2017.
 */

public class PeopleMovie {
    int id;
    List<PeopleMovieCast> cast;
    List<PeopleMovieCrew> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PeopleMovieCast> getCast() {
        return cast;
    }

    public void setCast(List<PeopleMovieCast> cast) {
        this.cast = cast;
    }

    public List<PeopleMovieCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<PeopleMovieCrew> crew) {
        this.crew = crew;
    }
}
