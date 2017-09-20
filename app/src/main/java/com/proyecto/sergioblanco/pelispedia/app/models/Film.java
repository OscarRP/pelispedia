package com.proyecto.sergioblanco.pelispedia.app.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public class Film implements Serializable {

    /**
     * Film id
     */
    @SerializedName("id")
    private int id;

    /**
     * Trailers
     */
    private ArrayList<Trailer> trailers;

    /**
     * Release date
     */
    @SerializedName("release_date")
    private String releaseDate;

    /**
     * Film runtime
     */
    @SerializedName("runtime")
    private int runtime;

    /**
     * Vote Average
     */
    @SerializedName("vote_average")
    private Double voteAverage;

    /**
     * Film title
     */
    @SerializedName("title")
    private String title;

    /**
     * Film poster
     */
    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("posterPath")
    private String poster;

    /**
     * Film overview
     */
    @SerializedName("overview")
    private String overview;

    public Film(String title, String poster_path) {
        this.title = title;
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return poster_path;
    }

    public void setPosterPath(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(ArrayList<Trailer> trailers) {
        this.trailers = trailers;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
