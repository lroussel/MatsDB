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
import com.matsdb.loicr.moviedb.ui.models.Episode;
import com.matsdb.loicr.moviedb.ui.utils.CircleTransform;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class ListEpisodesAdapter extends ArrayAdapter<Episode> {

    private LayoutInflater inflater;
    private int crewId;

    public ListEpisodesAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Episode> objects) {
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
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_episode);
            viewHolder.tvNomEp = (TextView) convertView.findViewById(R.id.textView_nomEp);
            viewHolder.tvRelease = (TextView) convertView.findViewById(R.id.textView_dateEp);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Episode episode = getItem(position);

        viewHolder.tvRelease.setText("Air Date : " + episode.getAir_date());
        viewHolder.tvNomEp.setText(episode.getName());

        if (episode.getStill_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, episode.getStill_path())).transform(new CircleTransform()).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvRelease, tvNomEp;
    }
}
