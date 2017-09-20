package com.proyecto.sergioblanco.pelispedia.app.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.interfaces.AppInterfaces;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;


import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 28/08/2017.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.FilmsViewHolder> {

    /**
     * Select film listener
     */
    private AppInterfaces.ISelectFilm selectFilmListener;

    /**
     * ViewHolder
     */
    private FilmsViewHolder viewHolder;

    /**
     * Films list
     */
    private ArrayList<Film> films;

    /**
     * Context
     */
    private Context context;

    public CategoriesAdapter(Context context, ArrayList<Film> films, AppInterfaces.ISelectFilm selectFilmListener) {
        super();

        this.films = films;
        this.context = context;
        this.selectFilmListener = selectFilmListener;
    }

    @Override
    public FilmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        //init view holder
        return new FilmsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmsViewHolder holder, final int position) {

        //set info
        holder.title.setText(films.get(position).getTitle());
        Glide.with(context).load(Constants.POSTER+films.get(position).getPosterPath()).into(holder.poster);

        //set onclick listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Film film = films.get(position);
                selectFilmListener.selectFilm(film);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * ViewHolder class
     */
    public static class FilmsViewHolder extends RecyclerView.ViewHolder {
        private ImageView poster;
        private TextView title;

        public FilmsViewHolder(View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title_text_view);
            poster = (ImageView)itemView.findViewById(R.id.poster_image_view);
        }
    }
}

