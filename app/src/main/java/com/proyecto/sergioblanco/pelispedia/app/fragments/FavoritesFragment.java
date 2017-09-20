package com.proyecto.sergioblanco.pelispedia.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DatabaseError;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.activities.HomeActivity;
import com.proyecto.sergioblanco.pelispedia.app.adapter.GridAdapter;
import com.proyecto.sergioblanco.pelispedia.app.controllers.DataController;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.models.User;
import com.proyecto.sergioblanco.pelispedia.app.session.Session;
import com.proyecto.sergioblanco.pelispedia.app.utils.Utils;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    /**
     * Data controller
     */
    private DataController dataController;

    /**
     * Utils
     */
    private Utils utils;

    /**
     * Favorites Adapter
     */
    private GridAdapter adapter;

    /**
     * Grid view
     */
    private GridView gridView;

    /**
     * User favorite films
     */
    private ArrayList<Film> favorites;

    /**
     * Current user
     */
    private User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        getViews(view);
        getInfo();
        setListeners();

        return view;
    }

    /**
     * Method to get views
     */
    private void getViews(View view) {
        gridView = (GridView)view.findViewById(R.id.grid_view);

    }

    /**
     * Method to get info
     */
    private void getInfo() {

        //init utils
        utils = new Utils();

        //init data controller
        dataController = new DataController();

        //set title
        ((HomeActivity)getActivity()).setToolbarTitle(getString(R.string.favourites));

        //get user from session
        user = Session.getInstance().getUser();

        //get favorites from database
        dataController.loadUser(user.getNick(), new AppInterfaces.ILoadUser() {
            @Override
            public void loadUser(User userLoaded) {
                user = userLoaded;

                //get user favorites
                if (user.getFavorites() != null) {
                    favorites = user.getFavorites();

                    //create adapter
                    adapter = new GridAdapter(getContext(), favorites);
                    //set adapter
                    gridView.setAdapter(adapter);
                } else {
                    //show no favorites layout

                }
            }

            @Override
            public void error(DatabaseError error) {

            }
        });
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //go to detail film
                utils.goToDetail(getActivity(), favorites.get(i));
            }
        });

    }
}
