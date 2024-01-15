package com.example.assessment_movie.DetailDomain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CastResponse {
    @SerializedName("cast") // replace "actors" with the actual key in the JSON response
    @Expose
    private List<Cast> actors;


    public List<Cast> getActors() {
        return actors;
    }

    public void setActors(List<Cast> actors) {
        this.actors = actors;
    }
}
