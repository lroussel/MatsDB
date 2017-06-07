package com.matsdb.loicr.moviedb.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.MyViewHolder;
import com.matsdb.loicr.moviedb.ui.models.ObjectImage;

import java.util.List;

/**
 * Created by loicr on 31/05/2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<ObjectImage> list;

    public ImageAdapter(List<ObjectImage> list){
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_card_images, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ObjectImage objectImage = list.get(position);
        holder.bind(objectImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
