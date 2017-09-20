package com.proyecto.sergioblanco.pelispedia.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 18/09/2017.
 */

public class SearchMovieAdapter extends PagerAdapter {

    /**
     * Select film listener
     */
    private AppInterfaces.ISelectFilm selectFilmListener;

    /**
     * Films list
     */
    private ArrayList<Film> films;

    /**
     * Context
     */
    private Context context;

    public SearchMovieAdapter (Context context, ArrayList<Film> films, AppInterfaces.ISelectFilm selectFilmListener) {
        this.context = context;
        this.films = films;
        this.selectFilmListener = selectFilmListener;
    }


    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup pager, final int position) {

        final View view;

        //inflate view
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.suggested_movies_item, null);

        //set views
        final TextView titleTV = view.findViewById(R.id.title_text_view);
        final ImageView poster = view.findViewById(R.id.poster_image_view);
        final TextView runtimeTV = view.findViewById(R.id.runtime_text_view);
        final TextView descriptionTV = view.findViewById(R.id.description_text_view);
        final RelativeLayout loadingLayout = view.findViewById(R.id.loading_layout);

        //set data to views
        titleTV.setText(films.get(position).getTitle());
        runtimeTV.setText(String.valueOf(films.get(position).getReleaseDate()));
        Glide.with(context).load(Constants.POSTER+films.get(position).getPosterPath()).into(poster);
        descriptionTV.setText(films.get(position).getOverview());

        //add view to viewpager
        pager.addView(view);

        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectFilmListener.selectFilm(films.get(position));
            }
        });

        return view;
    }

    /**
     * Method to delete viewpager view
     */
    @Override
    public void destroyItem(ViewGroup pager, int position, Object object) {
        pager.removeView((View) object);
    }
}
