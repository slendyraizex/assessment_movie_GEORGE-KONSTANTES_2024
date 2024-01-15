package com.example.assessment_movie.DetailDomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CrewResponse {
    @SerializedName("crew")
    @Expose
    private List<Crew> crew;

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
