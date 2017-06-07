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
import com.matsdb.loicr.moviedb.ui.models.Cast;
import com.matsdb.loicr.moviedb.ui.utils.CircleTransform;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class ListCastTVAdapter extends ArrayAdapter<Cast> {

    private LayoutInflater inflater;
    private int castId;

    public ListCastTVAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Cast> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(context);
        castId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(castId, null);

            viewHolder = new ViewHolder();
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_cast);
            viewHolder.tvPerson = (TextView) convertView.findViewById(R.id.textViewPerson);
            viewHolder.tvCharacter = (TextView) convertView.findViewById(R.id.textViewCharacter);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Cast cast = getItem(position);

        viewHolder.tvPerson.setText("Actor Name : " + cast.getName());
        viewHolder.tvCharacter.setText("Character Name : " + cast.getCharacter());

        if(cast.getProfile_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, cast.getProfile_path())).transform(new CircleTransform()).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvPerson, tvCharacter;
    }
}
