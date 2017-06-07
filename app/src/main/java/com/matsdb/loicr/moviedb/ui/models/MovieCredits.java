package com.matsdb.loicr.moviedb.ui.models;

import java.util.List;

/**
 * Created by loicr on 24/05/2017.
 */

public class MovieCredits {
    int id;
    List<Cast> cast;
    List<Crew> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
