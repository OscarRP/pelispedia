package com.proyecto.sergioblanco.pelispedia.app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public class Trailer implements Serializable {

    /**
     * Youtube key
     */
    @SerializedName("key")
    private String key;

    public Trailer(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
