package com.proyecto.sergioblanco.pelispedia.app.utils;

/**
 * Created by Sergio Blanco on 25/08/2017.
 */

public class Constants {

    /**
     * Defines time incremental
     */
    public static final int INCREMENTAL_TIME = 1000;

    /**
     * Time showing splash activity
     */
    public static final int SPLASH_TIME = 2000;

    /**
     * Indicates users nick that is logged in
     */
    public static final String NICK = "nick";

    /**
     * Application states interface
     */
    public interface APLICATION_STATES {
        public static final int SPLASH_STATE = 0;
        public static final int REGISTER_STATE = SPLASH_STATE + 1;
        public static final int LOGIN_STATE = REGISTER_STATE + 1;
        public static final int HOME_STATE = LOGIN_STATE + 1;
        public static final int DETAIL_STATE = HOME_STATE + 1;
    }

    /**
     * Home states to control fragemnt navigation
     */
    public interface HOME_STATES {
        public static final int HOME_STATE = 0;
        public static final int DETAIL_STATE = HOME_STATE + 1;
        public static final int UPCOMING_STATE = DETAIL_STATE + 1;
        public static final int BEST_STATE = UPCOMING_STATE + 1;
        public static final int FAVORITES_STATE = BEST_STATE + 1;
        public static final int PROFILE_STATE = FAVORITES_STATE + 1;
    }

    /**
     * Indicates if user has been logged in
     */
    public static final String USER_LOGGED = "user_loged";

    /**
     * Indicates if navigation must go from register to login or home
     */
    public static final String GO_TO_LOGIN = "go_to_login";

    /**
     * Indicates if navigation must go to detail fragment
     */
    public static final String GO_TO_DETAIL = "go_to_detail";

    /**
     * Indicates if navigation must go from register to register or home
     */
    public static final String GO_TO_REGISTER = "go_to_register";

    /**
     * Request code to select image from gallery
     */
    public static final int SELECT_GALLERY = 10;

    /**
     * Request code to select camera
     */
    public static final int SELECT_CAMERA = 20;

    /**
     * Directory to save photos
     */
    public static final String PHOTO_DIRECTORY = "myshopping/profile/photos/";

    /**
     * Api Key
     */
    public static final String API_KEY = "8e36c0b10ea97b689bf3793930212885";

    /**
     * TheMovieDB URL
     */
    public static final  String MOVIEDB_URL = "https://api.themoviedb.org/3/";

    /**
     * Poster URL Base
     */
    public static final String POSTER = "https://image.tmdb.org/t/p/w500";

    /**
     * Youtube URL Base for trailers
     */
    public static final String TRAILER = "https://www.youtube.com/watch?v=";

    /**
     * Endpoints
     */
    public interface ENDPOINTS {
        public static final String GET_FILMS_BY_GENRE = "genre/";
        public static final String GET_FILMS_BY_GENRE_B = "/movies?api_key="+API_KEY+"&language=es&include_adult=false&sort_by=created_at.asc";
        public static final String GET_FILM = "https://api.themoviedb.org/3/movie/";
        public static final String GET_TRAILERS_B = "/videos?api_key="+API_KEY+"&language=es";
        public static final String GET_FILM_DETAIL = "?api_key="+API_KEY+"&language=ES";
        public static final String GET_UPCOMING = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=es&page=1";
        public static final String GET_BEST = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY+"&language=es&page=1";
        public static final String SEARCH_MOVIE = "search/movie?api_key="+API_KEY+"&language=es&query=";
    }

    /**
     * Genre IDs
     */
    public interface GENRES {
        public static final int ACTION = 28;
        public static final int MYSTERY = 9648;
        public static final int COMEDY = 35;
        public static final int HORROR = 27;
        public static final int FICTION = 878;
    }

    /**
     * Film poster URL
     */
    public static final String SERIE_POSTER = "https://image.tmdb.org/t/p/w500/";

    /**
     * Film params
     */
    public static final String FILM = "film";

    /**
     * Google Api key (Youtube)
     */
    public static final String GOOGLE_API = "AIzaSyDcSp3XJ2yGa-wANyv6pI62wWPrf8cFLaM";

    /**
     * Mins
     */
    public static final String MINS = " mins";

}
