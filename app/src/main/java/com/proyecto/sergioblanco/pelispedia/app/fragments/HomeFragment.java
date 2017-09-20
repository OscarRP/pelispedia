package com.proyecto.sergioblanco.pelispedia.app.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.activities.HomeActivity;
import com.proyecto.sergioblanco.pelispedia.app.adapter.CategoriesAdapter;
import com.proyecto.sergioblanco.pelispedia.app.adapter.SearchMovieAdapter;
import com.proyecto.sergioblanco.pelispedia.app.controllers.DataController;
import com.proyecto.sergioblanco.pelispedia.app.controllers.NavigationController;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.models.FilmResults;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;
import com.proyecto.sergioblanco.pelispedia.app.utils.Utils;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {

    /**
     * Utils
     */
    private Utils utils;

    /**
     * Show matches dialog
     */
    private Dialog matchesDialog;

    /**
     * Search Movie Adapter
     */
    private SearchMovieAdapter searchMovieAdapter;

    /**
     * Matched series list
     */
    private ArrayList<FilmResults> matches;

    /**
     * Dialog select button
     */
    private Button accept;

    /**
     * Dialog viewpager
     */
    private ViewPager viewPager;

    /**
     * Viewpager layout
     */
    private LinearLayout viewPagerLayout;

    /**
     * No results layout
     */
    private LinearLayout noResultsLayout;

    /**
     * Circle page indicator
     */
    private CircleIndicator indicator;


    /**
     * Category adapter
     */
    private CategoriesAdapter actionAdapter;

    /**
     * Category adapter
     */
    private CategoriesAdapter mysteryAdapter;
    /**
     * Category adapter
     */
    private CategoriesAdapter comedyAdapter;
    /**
     * Category adapter
     */
    private CategoriesAdapter horrorAdapter;
    /**
     * Category adapter
     */
    private CategoriesAdapter fictionAdapter;

    /**
     * Navigation Controller
     */
    private NavigationController navigationController;

    /**
     * Data controller
     */
    private DataController dataController;

    /**
     * Action films list
     */
    private ArrayList<Film> actionList;

    /**
     * Adventure films list
     */
    private ArrayList<Film> mysteryList;

    /**
     * Comedy films list
     */
    private ArrayList<Film> comedyList;

    /**
     * Terror films list
     */
    private ArrayList<Film> terrorList;

    /**
     * Science Fiction films list
     */
    private ArrayList<Film> fictionList;

    /**
     * Action category listview
     */
    private RecyclerView actionLV;

    /**
     * Adventure category listview
     */
    private RecyclerView mysteryLV;

    /**
     * Comedy category listview
     */
    private RecyclerView comedyLV;

    /**
     * Terror category listview
     */
    private RecyclerView terrorLV;

    /**
     * Search button
     */
    private ImageButton searchButton;

    /**
     * Search Edit text
     */
    private EditText searchET;

    /**
     * Science fiction category listview
     */
    private RecyclerView fictionLV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        getViews(view);
        setInfo();
        setListeners();

        return view;
    }

    /**
     * Method to get views
     */
    private void getViews(View view) {
        actionLV = (RecyclerView) view.findViewById(R.id.action_list_view);
        mysteryLV = (RecyclerView)view.findViewById(R.id.mystery_list_view);
        comedyLV = (RecyclerView)view.findViewById(R.id.comedy_list_view);
        terrorLV = (RecyclerView)view.findViewById(R.id.terror_list_view);
        fictionLV = (RecyclerView)view.findViewById(R.id.science_fiction);
        searchButton = (ImageButton)view.findViewById(R.id.search_button);
        searchET = (EditText)view.findViewById(R.id.search_edit_text);
    }

    /**
     * Method to set initial info
     */
    private void setInfo() {
        //set options menu
        setHasOptionsMenu(true);

        //init utils
        utils = new Utils();

        //init data and navigation controllers
        navigationController = new NavigationController();
        dataController = new DataController();

        //set title
        ((HomeActivity)getActivity()).setToolbarTitle(getString(R.string.home));

        //set horizontal listviews
        LinearLayoutManager actionLM = new LinearLayoutManager(getActivity());
        actionLM.setOrientation(LinearLayoutManager.HORIZONTAL);
        actionLV.setLayoutManager(actionLM);

        LinearLayoutManager adventurLM = new LinearLayoutManager(getActivity());
        adventurLM.setOrientation(LinearLayoutManager.HORIZONTAL);
        mysteryLV.setLayoutManager(adventurLM);

        LinearLayoutManager comedyLM = new LinearLayoutManager(getActivity());
        comedyLM.setOrientation(LinearLayoutManager.HORIZONTAL);
        comedyLV.setLayoutManager(comedyLM);

        final LinearLayoutManager horrorLM = new LinearLayoutManager(getActivity());
        horrorLM.setOrientation(LinearLayoutManager.HORIZONTAL);
        terrorLV.setLayoutManager(horrorLM);

        LinearLayoutManager fictionLM = new LinearLayoutManager(getActivity());
        fictionLM.setOrientation(LinearLayoutManager.HORIZONTAL);
        fictionLV.setLayoutManager(fictionLM);

        //get categories info
        dataController.getFilmsByGenre(Constants.GENRES.ACTION, new AppInterfaces.IGetFilms() {
            @Override
            public void getFilms(ArrayList<Film> actionFilms) {
                actionList = actionFilms;
                actionAdapter = new CategoriesAdapter(getContext(), actionList, new AppInterfaces.ISelectFilm() {
                    @Override
                    public void selectFilm(Film film) {
                        utils.goToDetail(getActivity(), film);
                    }
                });
                actionLV.setHasFixedSize(true);
                actionLV.setAdapter(actionAdapter);
            }
        });

        dataController.getFilmsByGenre(Constants.GENRES.MYSTERY, new AppInterfaces.IGetFilms() {
            @Override
            public void getFilms(ArrayList<Film> mysteryFilms) {
                mysteryList = mysteryFilms;
                mysteryAdapter = new CategoriesAdapter(getContext(), mysteryList, new AppInterfaces.ISelectFilm() {
                    @Override
                    public void selectFilm(Film film) {
                        utils.goToDetail(getActivity(), film);                    }
                });
                mysteryLV.setHasFixedSize(true);
                mysteryLV.setAdapter(mysteryAdapter);
            }
        });

        dataController.getFilmsByGenre(Constants.GENRES.COMEDY, new AppInterfaces.IGetFilms() {
            @Override
            public void getFilms(ArrayList<Film> comedyFilms) {
                comedyList = comedyFilms;
                comedyAdapter = new CategoriesAdapter(getContext(), comedyList, new AppInterfaces.ISelectFilm() {
                    @Override
                    public void selectFilm(Film film) {
                        utils.goToDetail(getActivity(), film);
                    }
                });
                comedyLV.setHasFixedSize(true);
                comedyLV.setAdapter(comedyAdapter);
            }
        });

        dataController.getFilmsByGenre(Constants.GENRES.HORROR, new AppInterfaces.IGetFilms() {
            @Override
            public void getFilms(ArrayList<Film> horrorFilms) {
                terrorList = horrorFilms;
                horrorAdapter = new CategoriesAdapter(getContext(), terrorList, new AppInterfaces.ISelectFilm() {
                    @Override
                    public void selectFilm(Film film) {
                        utils.goToDetail(getActivity(), film);
                    }
                });
                terrorLV.setHasFixedSize(true);
                terrorLV.setAdapter(horrorAdapter);
            }
        });

        dataController.getFilmsByGenre(Constants.GENRES.FICTION, new AppInterfaces.IGetFilms() {
            @Override
            public void getFilms(ArrayList<Film> films) {
                fictionList = films;
                fictionAdapter = new CategoriesAdapter(getContext(), fictionList, new AppInterfaces.ISelectFilm() {
                    @Override
                    public void selectFilm(Film film) {
                        utils.goToDetail(getActivity(), film);
                    }
                });
                fictionLV.setHasFixedSize(true);
                fictionLV.setAdapter(fictionAdapter);
            }
        });

        //Hide Keyboard
        fictionLV.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(fictionLV.getWindowToken(), 0);
    }

    /**
     * Method to set listeners
     */
    private void setListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (!searchET.getText().toString().isEmpty()) {
                    //init matches array
                    matches = new ArrayList<>();

                    //dialog configuration
                    matchesDialog = new Dialog(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
                    matchesDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    matchesDialog.setCancelable(true);
                    matchesDialog.setContentView(R.layout.search_films_dialog);

                    //set dialog views
                    accept = matchesDialog.findViewById(R.id.accept_button);
                    viewPager = matchesDialog.findViewById(R.id.viewpager);
                    viewPagerLayout = matchesDialog.findViewById(R.id.viewpager_layout);
                    noResultsLayout = matchesDialog.findViewById(R.id.no_results_layout);
                    indicator = matchesDialog.findViewById(R.id.indicator);

                    //search matches
                    dataController.searchMovie(searchET.getText().toString(), new AppInterfaces.IGetMovie() {
                        @Override
                        public void searchMovie(ArrayList<Film> films) {
                            if (films == null || films.size() == 0) {
                                //show no results layout
                                viewPagerLayout.setVisibility(View.GONE);
                                noResultsLayout.setVisibility(View.VISIBLE);
                                accept.setText(getString(R.string.accept));
                            } else {
                                //set viewpager info
                                searchMovieAdapter = new SearchMovieAdapter(getContext(), films, new AppInterfaces.ISelectFilm() {
                                    @Override
                                    public void selectFilm(Film film) {
                                        //go to detail Activity
                                        utils.goToDetail(getActivity(), film);
                                    }
                                });
                                viewPager.setAdapter(searchMovieAdapter);
                                indicator.setViewPager(viewPager);
                            }
                        }
                    });

                    //Dialog listeners
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            matchesDialog.dismiss();
                        }
                    });

                    //show dialog
                    matchesDialog.show();
                }
            }
        });

    }

    /**
     * Method to create Options Menu
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu
        getActivity().getMenuInflater().inflate(R.menu.home, menu);
    }

    /**
     * Method called when user select an option from Options Menu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.close_session) {
            utils.closeSession(getActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
