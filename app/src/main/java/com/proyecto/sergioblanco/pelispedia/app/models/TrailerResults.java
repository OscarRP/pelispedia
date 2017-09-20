package com.proyecto.sergioblanco.pelispedia.app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public class TrailerResults implements Serializable {

    /**
     * Trailers list
     */
    @SerializedName("results")
    private ArrayList<Trailer> trailers;

    public TrailerResults() {
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }
}
