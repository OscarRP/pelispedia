package com.proyecto.sergioblanco.pelispedia.app.interfaces;

import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.models.FilmResults;
import com.proyecto.sergioblanco.pelispedia.app.models.TrailerResults;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public interface ApiInterfaces {

    //get films by category
    @GET(Constants.ENDPOINTS.GET_FILMS_BY_GENRE+"{id}"+Constants.ENDPOINTS.GET_FILMS_BY_GENRE_B)
    Call<FilmResults> getFilmsByGenre(@Path("id") int genreId);

    //get film trailers
    @GET(Constants.ENDPOINTS.GET_FILM +"{id}"+Constants.ENDPOINTS.GET_TRAILERS_B)
    Call<TrailerResults> getTrailers(@Path("id") int filmId);

    //get film details
    @GET(Constants.ENDPOINTS.GET_FILM +"{id}"+Constants.ENDPOINTS.GET_FILM_DETAIL)
    Call<Film> getDetails(@Path("id") int filmId);

    //get upcoming films
    @GET(Constants.ENDPOINTS.GET_UPCOMING)
    Call<FilmResults> getUpcoming();

    //get best films
    @GET(Constants.ENDPOINTS.GET_BEST)
    Call<FilmResults> getBest();

    //search movie
    @GET(Constants.ENDPOINTS.SEARCH_MOVIE)
    Call<FilmResults> searchMovie(@Query("query") String movieTitle);

}
