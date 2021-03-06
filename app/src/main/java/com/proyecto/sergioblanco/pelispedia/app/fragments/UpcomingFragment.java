package com.proyecto.sergioblanco.pelispedia.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.activities.HomeActivity;
import com.proyecto.sergioblanco.pelispedia.app.adapter.GridAdapter;
import com.proyecto.sergioblanco.pelispedia.app.adapter.UpcomingAdapter;
import com.proyecto.sergioblanco.pelispedia.app.controllers.DataController;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.models.User;
import com.proyecto.sergioblanco.pelispedia.app.utils.Utils;

import java.util.ArrayList;

public class UpcomingFragment extends Fragment {

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
    private UpcomingAdapter adapter;

    /**
     * Grid view
     */
    private GridView gridView;

    /**
     * Upcoming films
     */
    private ArrayList<Film> upcoming;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

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
        ((HomeActivity)getActivity()).setToolbarTitle(getString(R.string.upcoming));

        //init utils
        utils = new Utils();

        //get upcoming films
        dataController.getUpcoming(new AppInterfaces.IGetFilms() {
            @Override
            public void getFilms(ArrayList<Film> films) {
                upcoming = films;

                //create adapter
                adapter = new UpcomingAdapter(getContext(), upcoming);
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
                utils.goToDetail(getActivity(), upcoming.get(i));
            }
        });

    }

}
