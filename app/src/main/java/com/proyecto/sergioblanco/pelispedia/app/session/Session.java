package com.proyecto.sergioblanco.pelispedia.app.session;

import com.proyecto.sergioblanco.pelispedia.app.models.User;

/**
 * Created by Sergio Blanco on 29/8/17.
 */

public class Session {

    /**
     * User session singleton
     */
    private static Session session;

    /**
     * User session
     */
    private User user;

    /**
     * Method to create singleton
     */
    public static Session getInstance() {
        //check if session is created
        if (session == null) {
            //create session
            session = new Session();
        }
        //return sessions
        return session;
    }

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        Session.session = session;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
