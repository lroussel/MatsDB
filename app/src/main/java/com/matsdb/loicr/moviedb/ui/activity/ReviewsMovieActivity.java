package com.matsdb.loicr.moviedb.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.models.Reviews;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ReviewsMovieActivity extends AppCompatActivity {

    private ListView lvReviews;
    private int movieId;
    private List<String> reviewsList;

    private Reviews reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_movie);

        getSupportActionBar().setTitle("Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvReviews = (ListView) findViewById(R.id.listView_reviews);
        reviewsList = new ArrayList<>();

        // TODO : Récuperer les reviews du film selon idMovie
        if(getIntent().getExtras() != null){
            movieId = getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
            String url = String.format(Constant.URL_REVIEWS_MOVIE, movieId);

            if(Network.isNetworkAvailable(ReviewsMovieActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(ReviewsMovieActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                reviews = gson.fromJson(response, Reviews.class);

                                for (int i = 0 ; i < reviews.getResults().size() ; i++){
                                    reviewsList.add(reviews.getResults().get(i).getAuthor() + " : " + reviews.getResults().get(i).getContent());
                                }

                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReviewsMovieActivity.this, android.R.layout.simple_list_item_1, reviewsList);
                                lvReviews.setAdapter(adapter);

                                /*lvReviews.setAdapter(new ListReviewAdapter(
                                        ReviewsMovieActivity.this,
                                        R.layout.adapter_list_reviewmovie,
                                        reviews.getResults()
                                ));*/

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errors", error.toString());
                    }
                });

                queue.add(stringRequest);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
