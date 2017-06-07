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
import com.matsdb.loicr.moviedb.ui.models.PeopleMovieCast;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 31/05/2017.
 */

public class ListMoviePeopleAdapter extends ArrayAdapter<PeopleMovieCast> {

    private LayoutInflater inflater;
    private int movieId;

    public ListMoviePeopleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PeopleMovieCast> objects) {
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
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_personMovie);
            viewHolder.tvMovie = (TextView) convertView.findViewById(R.id.textView_nameMovie);
            viewHolder.tvPerson = (TextView) convertView.findViewById(R.id.textView_personMovie);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PeopleMovieCast peopleMovie = getItem(position);

        viewHolder.tvPerson.setText(peopleMovie.getCharacter());
        viewHolder.tvMovie.setText("in " + peopleMovie.getTitle() + ", " + peopleMovie.getRelease_date());
        if(peopleMovie.getPoster_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, peopleMovie.getPoster_path())).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvPerson, tvMovie;
    }
}
