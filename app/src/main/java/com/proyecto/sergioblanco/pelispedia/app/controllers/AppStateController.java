package com.proyecto.sergioblanco.pelispedia.app.controllers;

/**
 * Created by Sergio Blanco on 25/08/2017.
 */

public class AppStateController {

    /**
     * Singleton
     */
    private static AppStateController controller;

    /**
     * Application State
     */
    private static int state;

    /**
     * Method to create controller
     */
    public static AppStateController getInstance() {
        //check if is created
        if(controller==null){
            //create controller
            controller = new AppStateController();
        }

        return controller;
    }

    /**
     * Method to get App state
     */
    public static int getState() {
        return state;
    }

    /**
     * Method to set App state
     */
    public static void setState(int state) {
        AppStateController.state = state;
    }
}
