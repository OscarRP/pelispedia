package com.proyecto.sergioblanco.pelispedia.app.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 29/8/17.
 */

public class User {

    /**
     * Indicates URL to download profile image from firebase storage
     */
    private String downloadURL;

    /**
     * User email
     */
    private String email;

    /**
     * User password
     */
    private String password;

    /**
     * User nickname
     */
    private String nick;

    /**
     * User Id
     */
    private String userId;

    /**
     * Users profile image
     */
    @SerializedName("profileImage")
    private String profileImage;

    /**
     * User favorite films
     */
    private ArrayList<Film> favorites;

    public User(String userId, String nick, String email, String password) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.userId = userId;
    }

    public User(String email, String password, String nick, String userId, ArrayList<Film> favorites) {
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.userId = userId;
        this.favorites = favorites;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Film> getFavorites() {
        return favorites;
    }

    public void setFavorites(ArrayList<Film> favorites) {
        this.favorites = favorites;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
