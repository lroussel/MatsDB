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
import com.matsdb.loicr.moviedb.ui.models.Persons;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 24/05/2017.
 */

public class ListPersonAdapter extends ArrayAdapter<Persons> {

    private LayoutInflater inflater;
    private int movieId;

    public ListPersonAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Persons> objects) {
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
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.textView_list_name);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_list_person);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Persons persons = getItem(position);

        viewHolder.tvName.setText(persons.getName());

        if(persons.getProfile_path() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMAGE, persons.getProfile_path())).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        TextView tvName;
        ImageView ivPhoto;
    }
}