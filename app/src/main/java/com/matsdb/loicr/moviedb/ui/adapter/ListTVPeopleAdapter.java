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
import com.matsdb.loicr.moviedb.ui.models.PeopleTVCast;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 31/05/2017.
 */

public class ListTVPeopleAdapter  extends ArrayAdapter<PeopleTVCast> {

    private LayoutInflater inflater;
    private int movieId;

    public ListTVPeopleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<PeopleTVCast> objects) {
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
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_personTV);
            viewHolder.tvMovie = (TextView) convertView.findViewById(R.id.textView_nameTV);
            viewHolder.tvPerson = (TextView) convertView.findViewById(R.id.textView_personTV);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PeopleTVCast peopleTV = getItem(position);

        viewHolder.tvPerson.setText(peopleTV.getCharacter());
        viewHolder.tvMovie.setText("in " + peopleTV.getName() + ", " + peopleTV.getFirst_air_date());
        if(peopleTV.getPoster_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, peopleTV.getPoster_path())).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvPerson, tvMovie;
    }
}
