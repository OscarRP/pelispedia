package com.proyecto.sergioblanco.pelispedia.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.proyecto.sergioblanco.pelispedia.R;
import com.proyecto.sergioblanco.pelispedia.app.models.Film;
import com.proyecto.sergioblanco.pelispedia.app.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Sergio Blanco on 30/08/2017.
 */

public class UpcomingAdapter extends BaseAdapter {

    /**
     * ViewHolder
     */
    private UpcomingAdapter.ViewHolder viewHolder;

    /**
     * Layout inflager
     */
    private LayoutInflater inflater;

    /**
     * Context
     */
    private Context context;

    /**
     * User favorites film
     */
    private ArrayList<Film> bests;

    public UpcomingAdapter(Context context, ArrayList<Film> bests) {
        this.context = context;
        this.bests = bests;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bests.size();
    }

    @Override
    public Object getItem(int i) {
        return bests.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.upcoming_item, null);

            viewHolder = new UpcomingAdapter.ViewHolder();

            //getviews
            viewHolder.poster = view.findViewById(R.id.poster_image_view);
            viewHolder.title = view.findViewById(R.id.title_text_view);
            viewHolder.upcoming = view.findViewById(R.id.date_text_view);

            //set tag
            view.setTag(viewHolder);
        } else {
            //get holder
            viewHolder = (UpcomingAdapter.ViewHolder)view.getTag();
        }

        //set info
        viewHolder.title.setText(bests.get(position).getTitle());
        viewHolder.upcoming.setText(String.valueOf(bests.get(position).getReleaseDate()));
        Glide.with(context).load(Constants.POSTER+bests.get(position).getPosterPath()).into(viewHolder.poster);

        return view;
    }

    /**
     * Viewholder class
     */
    public class ViewHolder {
        //film poster
        private ImageView poster;
        //film title
        private TextView title;
        //film upcoming date
        private TextView upcoming;

    }
}
