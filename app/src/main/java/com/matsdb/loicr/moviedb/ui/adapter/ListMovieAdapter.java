package com.matsdb.loicr.moviedb.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.models.Movies;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 24/05/2017.
 */

public class ListMovieAdapter extends ArrayAdapter<Movies> {

    private LayoutInflater inflater;
    private int movieId;

    public ListMovieAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Movies> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(context);
        movieId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(movieId, null);

            viewHolder = new ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.textView_list_title);
            viewHolder.tvDateRelease = (TextView) convertView.findViewById(R.id.textView_list_dateRelease);
            viewHolder.ivMovie = (ImageView) convertView.findViewById(R.id.image_list_movie);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Movies movies = getItem(position);

        viewHolder.tvTitle.setText(movies.getTitle());
        viewHolder.tvDateRelease.setText(movies.getRelease_date());

        if(movies.getPoster_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, movies.getPoster_path())).into(viewHolder.ivMovie);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvTitle, tvDateRelease;
        ImageView ivMovie;
    }
}
