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
import com.matsdb.loicr.moviedb.ui.models.Seasons;
import com.matsdb.loicr.moviedb.ui.utils.CircleTransform;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class ListSeasonsAdapter extends ArrayAdapter<Seasons> {

    private LayoutInflater inflater;
    private int crewId;

    public ListSeasonsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Seasons> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(getContext());
        crewId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(crewId, null);

            viewHolder = new ViewHolder();
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_season);
            viewHolder.tvNbEp = (TextView) convertView.findViewById(R.id.textView_nbEpisode);
            viewHolder.tvRelease = (TextView) convertView.findViewById(R.id.textView_daterelease);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Seasons season = getItem(position);

        viewHolder.tvRelease.setText(season.getAir_date());
        viewHolder.tvNbEp.setText("Season " + season.getSeason_number() + " - " + season.getEpisode_count() + " episode");

        if (season.getPoster_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, season.getPoster_path())).transform(new CircleTransform()).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvRelease, tvNbEp;
    }
}
