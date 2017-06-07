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
import com.matsdb.loicr.moviedb.ui.models.Crew;
import com.matsdb.loicr.moviedb.ui.utils.CircleTransform;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class ListCrewMovieAdapter extends ArrayAdapter<Crew> {

    private LayoutInflater inflater;
    private int crewId;

    public ListCrewMovieAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Crew> objects) {
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
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_crew);
            viewHolder.tvJob = (TextView) convertView.findViewById(R.id.textViewJob);
            viewHolder.tvPerson = (TextView) convertView.findViewById(R.id.textViewCrew);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Crew crew = getItem(position);

        viewHolder.tvPerson.setText(crew.getName());
        viewHolder.tvJob.setText(crew.getDepartment() + " - " + crew.getJob());

        if (crew.getProfile_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, crew.getProfile_path())).transform(new CircleTransform()).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvPerson, tvJob;
    }
}
