package com.matsdb.loicr.moviedb.ui.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.models.Review;

import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class ListReviewAdapter extends ArrayAdapter<Review> {

    private LayoutInflater inflater;
    private int reviewId;

    public ListReviewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Review> objects) {
        super(context, resource, objects);

        inflater = LayoutInflater.from(context);
        reviewId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(reviewId, null);

            viewHolder = new ViewHolder();
            viewHolder.tvAuthor = (TextView) convertView.findViewById(R.id.textView_review_author);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.textView_review_content);

            convertView.setTag(position);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Review review = getItem(position);

        viewHolder.tvAuthor.setText(review.getAuthor());
        viewHolder.tvContent.setText(review.getContent());

        return convertView;
    }

    class ViewHolder {
        TextView tvAuthor, tvContent;
    }
}
