package com.proyecto.sergioblanco.pelispedia.app.interfaces;

import android.net.Uri;

import com.google.firebase.database.DatabaseError;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.models.FilmResults;
import com.proyecto.sergioblanco.pelispedia.app.models.Trailer;
import com.proyecto.sergioblanco.pelispedia.app.models.User;

import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public class AppInterfaces {

    /**
     * Interface to load user
     */
    public interface ILoadUser {
        void loadUser (User userLoaded);
        void error (DatabaseError error);
    }

    /**
     * Interface to get films
     */
    public interface IGetFilms {
        public abstract void getFilms(ArrayList<Film> films);
    }

    /**
     * Interface to search movie
     */
    public interface IGetMovie {
        public abstract void searchMovie(ArrayList<Film> films);
    }

    /**
     * Interface to select a film to go to details
     */
    public interface ISelectFilm {
        public abstract void selectFilm(Film film);
    }

    /**
     * Interface to get film trailers
     */
    public interface IGetTrailers {
        public abstract void getTrailers(ArrayList<Trailer> trailers);
    }

    /**
     * Interface to set image with glide in imageview
     */
    public interface ISetImge {
        public abstract void setImage();
    }

    /**
     * Interface to add profile image
     */
    public interface IAddImage{
        public abstract void addImage(String userPhotoUrl);
    }

    /**
     * Interface to remove profile image
     */
    public interface IRemoveImage {
        public abstract void removeImage();
    }

    /**
     * Interface to upload image
     */
    public interface IUploadImage {
        public abstract void uploadIMage(Uri uri);
        public abstract void error(Exception exception);
    }

    /**
     * Interface to change password
     */
    public interface IChangePassword {
        public abstract void changePassword();
    }
}
