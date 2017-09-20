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
 * Created by Sergio Blanco on 29/8/17.
 */

public class GridAdapter extends BaseAdapter {

    /**
     * ViewHolder
     */
    private ViewHolder viewHolder;

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
    private ArrayList<Film> favorites;

    public GridAdapter(Context context, ArrayList<Film> favorites) {
        this.context = context;
        this.favorites = favorites;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return favorites.size();
    }

    @Override
    public Object getItem(int i) {
        return favorites.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.category_item, null);

            viewHolder = new ViewHolder();

            //getviews
            viewHolder.poster = view.findViewById(R.id.poster_image_view);
            viewHolder.title = view.findViewById(R.id.title_text_view);

            //set tag
            view.setTag(viewHolder);
        } else {
            //get holder
            viewHolder = (ViewHolder)view.getTag();
        }

        //set info
        viewHolder.title.setText(favorites.get(position).getTitle());
        Glide.with(context).load(Constants.POSTER+favorites.get(position).getPoster()).into(viewHolder.poster);

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

    }
}
