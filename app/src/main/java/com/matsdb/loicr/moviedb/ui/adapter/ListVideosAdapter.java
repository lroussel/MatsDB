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
import com.matsdb.loicr.moviedb.ui.models.Video;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class ListVideosAdapter extends ArrayAdapter<Video> {

    private LayoutInflater inflater;
    private int videoId;

    public ListVideosAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Video> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(context);
        videoId= resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(videoId, null);

            viewHolder = new ViewHolder();
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.image_youtube);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.textView_videoName);
            viewHolder.tvQuality = (TextView) convertView.findViewById(R.id.textView_videoQuality);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Video video = getItem(position);

        viewHolder.tvTitle.setText(video.getName());
        viewHolder.tvQuality.setText(video.getSize() + "p");

        if(video.getKey() != null){
            Picasso.with(getContext()).load(String.format(Constant.URL_IMG_YOUTUBE, video.getKey())).into(viewHolder.ivPhoto);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView ivPhoto;
        TextView tvTitle, tvQuality;
    }
}
