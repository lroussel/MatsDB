package com.matsdb.loicr.moviedb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.activity.ImageViewActivity;
import com.matsdb.loicr.moviedb.ui.models.ObjectImage;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

/**
 * Created by loicr on 31/05/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivCardView;

    public MyViewHolder(View itemView) {
        super(itemView);

        ivCardView = (ImageView) itemView.findViewById(R.id.image_cardView);
    }

    public void bind(final ObjectImage objectImage){
        Picasso.with(ivCardView.getContext()).load(objectImage.getUrl()).into(ivCardView);

        ivCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_fullscreen = new Intent(ivCardView.getContext(), ImageViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTENT_URL_IMAGE_FULLSCREEN, objectImage.getUrl());

                it_fullscreen.putExtras(bundle);
                ivCardView.getContext().startActivity(it_fullscreen);
            }
        });
    }
}
