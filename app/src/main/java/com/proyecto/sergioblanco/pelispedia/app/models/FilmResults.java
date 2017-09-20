package com.proyecto.sergioblanco.pelispedia.app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public class FilmResults implements Serializable{

    @SerializedName("results")
    private ArrayList<Film> films;

    public FilmResults() {
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }
}
