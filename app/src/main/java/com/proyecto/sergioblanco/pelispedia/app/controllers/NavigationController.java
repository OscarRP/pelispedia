package com.proyecto.sergioblanco.pelispedia.app.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.activities.DetailActivity;
import com.proyecto.sergioblanco.pelispedia.app.activities.HomeActivity;
import com.proyecto.sergioblanco.pelispedia.app.activities.LoginActivity;
import com.proyecto.sergioblanco.pelispedia.app.activities.RegisterActivity;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;

/**
 * Created by Sergio Blanco on 25/08/2017.
 */

public class NavigationController {

    /**
     * Fragment Manager
     */
    private FragmentManager fragmentManager;

    /**
     * check if user is logged in
     */
    private boolean isLogged;

    /**
     * go to login
     */
    private boolean goToLogin;

    /**
     * go to detail
     */
    private boolean goToDetail;

    /**
     * Home State controller
     */
    private HomeStateController homeStateController;

    /**
     * App state controller
     */
    private AppStateController controller;



    public NavigationController() {
        //init controllers
        controller = AppStateController.getInstance();
        homeStateController = HomeStateController.getInstance();
    }

    /**
     * Method to change activity
     */
    public void changeActivity(Activity activity, Bundle params) {
        //Create intent
        Intent intent;

        switch (controller.getState()) {

            case Constants.APLICATION_STATES.SPLASH_STATE:
                //close activity
                activity.finish();

                isLogged = params.getBoolean(Constants.USER_LOGGED);
                //set intent
                if (isLogged) {
                    intent = new Intent(activity, HomeActivity.class);
                    //change controller state
                    controller.setState(Constants.APLICATION_STATES.HOME_STATE);
                } else {
                    intent = new Intent(activity, RegisterActivity.class);
                    //change controller state
                    controller.setState(Constants.APLICATION_STATES.REGISTER_STATE);
                }

                //check parms
                if (params != null) {
                    //add paramas
                    intent.putExtras(params);
                }

                //start activity
                activity.startActivity(intent);

                break;

            case Constants.APLICATION_STATES.REGISTER_STATE:
                //check where to go
                goToLogin = params.getBoolean(Constants.GO_TO_LOGIN);
                if (goToLogin) {
                    intent = new Intent(activity, LoginActivity.class);
                    controller.setState(Constants.APLICATION_STATES.LOGIN_STATE);
                } else {
                    intent = new Intent(activity, HomeActivity.class);
                    controller.setState(Constants.APLICATION_STATES.HOME_STATE);
                }

                //start activity
                activity.startActivity(intent);

                break;

            case Constants.APLICATION_STATES.LOGIN_STATE:
                //check where to go
                boolean goToRegister = params.getBoolean(Constants.GO_TO_REGISTER);
                if (goToRegister) {
                    intent = new Intent(activity, RegisterActivity.class);
                    controller.setState(Constants.APLICATION_STATES.REGISTER_STATE);
                } else {
                    intent = new Intent(activity, HomeActivity.class);
                    controller.setState(Constants.APLICATION_STATES.HOME_STATE);
                }

                //start activity
                activity.startActivity(intent);

                break;
            case Constants.APLICATION_STATES.HOME_STATE:
                //check where to go
                if (params != null) {
                    goToLogin = params.getBoolean(Constants.GO_TO_LOGIN);
                    goToDetail = params.getBoolean(Constants.GO_TO_DETAIL);

                    if (goToLogin) {
                        intent = new Intent(activity, LoginActivity.class);
                        controller.setState(Constants.APLICATION_STATES.LOGIN_STATE);

                        //start activity
                        activity.startActivity(intent);

                    } else if (goToDetail) {
                        intent = new Intent(activity, DetailActivity.class);
                        intent.putExtras(params);
                        controller.setState(Constants.APLICATION_STATES.DETAIL_STATE);
                        //start activity
                        activity.startActivity(intent);
                    } else {
                        //open Home Activity again
                        intent = new Intent(activity, HomeActivity.class);
                        controller.setState(Constants.APLICATION_STATES.HOME_STATE);
                        //start activity
                        activity.startActivity(intent);
                    }

                }
                break;
            case Constants.APLICATION_STATES.DETAIL_STATE:
                //go to home activity
                intent = new Intent(activity, HomeActivity.class);
                controller.setState(Constants.APLICATION_STATES.HOME_STATE);

                //start activity
                activity.startActivity(intent);
                break;
        }
    }

        /**
         * Method to change fragment
         */
    public void changeFragment(Activity activity, Fragment fragment, Bundle params, int homeState) {

        //check params
        if (params != null) {
            fragment.setArguments(params);
        }
        //change fragment
        fragmentManager = ((AppCompatActivity) activity).getSupportFragmentManager();

        if (homeStateController.getState() != homeState) {
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out).replace(R.id.fragment_container, fragment).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
        //set home state
        homeStateController.setState(homeState);
    }

    /**
     * Method to init navigation at home
     */
    public void initNavigation(Activity activity, Fragment fragment) {
        //change fragment
        fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.fragment_in, R.anim.fragment_out).replace(R.id.fragment_container, fragment).commit();

        //set home state
        homeStateController.setState(Constants.HOME_STATES.HOME_STATE);
    }

    /**
     * Method to change fragment
     */
    public void backNavigation (Activity activity, Fragment fragment, Bundle params, int homeState) {

        if (homeStateController.getState() != homeState) {
            //check params
            if (params != null) {
                fragment.setArguments(params);
            }
            //change fragment
            fragmentManager = ((AppCompatActivity)activity).getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.back_fragment_in, R.anim.back_fragment_out).replace(R.id.fragment_container, fragment).commit();

            //set home state
            homeStateController.setState(homeState);
        }
    }

    /**
     * Method to get home state
     */
    public int getHomeState () {
        return homeStateController.getState();
    }
}
