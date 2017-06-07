package com.matsdb.loicr.moviedb.ui.models;

import java.util.List;

/**
 * Created by loicr on 31/05/2017.
 */

public class PeopleTV {
    int id;
    List<PeopleTVCast> cast;
    List<PeopleTVCrew> crew;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PeopleTVCast> getCast() {
        return cast;
    }

    public void setCast(List<PeopleTVCast> cast) {
        this.cast = cast;
    }

    public List<PeopleTVCrew> getCrew() {
        return crew;
    }

    public void setCrew(List<PeopleTVCrew> crew) {
        this.crew = crew;
    }
}
