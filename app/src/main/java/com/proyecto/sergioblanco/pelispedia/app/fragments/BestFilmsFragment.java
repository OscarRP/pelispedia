package com.proyecto.sergioblanco.pelispedia.app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.activities.HomeActivity;
import com.proyecto.sergioblanco.pelispedia.app.adapter.BestFilmsAdapter;
import com.proyecto.sergioblanco.pelispedia.app.adapter.GridAdapter;
import com.proyecto.sergioblanco.pelispedia.app.controllers.DataController;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.models.User;
import com.proyecto.sergioblanco.pelispedia.app.utils.Utils;

import java.util.ArrayList;


public class BestFilmsFragment extends Fragment {

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
    private BestFilmsAdapter adapter;

    /**
     * Grid view
     */
    private GridView gridView;

    /**
     * Best films
     */
    private ArrayList<Film> bestFilms;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_best_films, container, false);

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

        //init data controller
        dataController = new DataController();

        //set title
        ((HomeActivity)getActivity()).setToolbarTitle(getString(R.string.top_rated));

        //init utils
        utils = new Utils();

        //get best films
        dataController.getBest(new AppInterfaces.IGetFilms() {
            @Override
            public void getFilms(ArrayList<Film> films) {
                bestFilms = films;
                //create adapter
                adapter = new BestFilmsAdapter(getContext(), films);
                //set adapter
                gridView.setAdapter(adapter);
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
                utils.goToDetail(getActivity(), bestFilms.get(i));

            }
        });

    }
}
