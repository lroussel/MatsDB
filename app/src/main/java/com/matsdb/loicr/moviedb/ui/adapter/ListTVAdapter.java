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
import com.matsdb.loicr.moviedb.ui.models.TVs;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 24/05/2017.
 */

public class ListTVAdapter extends ArrayAdapter<TVs> {

    private LayoutInflater inflater;
    private int movieId;

    public ListTVAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TVs> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(context);
        movieId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListTVAdapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(movieId, null);

            viewHolder = new ListTVAdapter.ViewHolder();
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.textView_list_title);
            viewHolder.tvDateRelease = (TextView) convertView.findViewById(R.id.textView_list_dateRelease);
            viewHolder.ivMovie = (ImageView) convertView.findViewById(R.id.image_list_movie);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ListTVAdapter.ViewHolder) convertView.getTag();
        }

        TVs tvs = getItem(position);

        viewHolder.tvTitle.setText(tvs.getName());
        viewHolder.tvDateRelease.setText(tvs.getFirst_air_date());

        if(tvs.getPoster_path() != null) {
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, tvs.getPoster_path())).into(viewHolder.ivMovie);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvTitle, tvDateRelease;
        ImageView ivMovie;
    }
}
