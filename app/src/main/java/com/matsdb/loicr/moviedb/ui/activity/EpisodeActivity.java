package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.models.Episode;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class EpisodeActivity extends AppCompatActivity {

    private TextView tvRelease, tvNumEp, tvSummary;
    private AppBarLayout ablPoster;
    private CollapsingToolbarLayout toolbarLayout;
    private FloatingActionButton fabCredit, fabVideos;

    private int tvId, numSeason, numEp;

    private Episode episode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        ablPoster = (AppBarLayout) findViewById(R.id.app_bar);

        tvRelease = (TextView) findViewById(R.id.textView_releaseEp);
        tvNumEp = (TextView) findViewById(R.id.textView_nbEpisode);
        tvSummary = (TextView) findViewById(R.id.textView_summaryEp);

        fabCredit = (FloatingActionButton) findViewById(R.id.floating_cast);
        fabVideos = (FloatingActionButton) findViewById(R.id.floating_videos);

        if(getIntent().getExtras() != null){
            tvId = getIntent().getExtras().getInt(Constant.INTENT_ID_TV);
            numSeason = getIntent().getExtras().getInt(Constant.INTENT_NUM_SEASON);
            numEp = getIntent().getExtras().getInt(Constant.INTENT_NUM_EPISODE);
            String url = String.format(Constant.URL_TV_EPISODE, tvId, numSeason, numEp);

            if (Network.isNetworkAvailable(EpisodeActivity.this)){
                RequestQueue requestQueue = Volley.newRequestQueue(EpisodeActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                episode = gson.fromJson(response, Episode.class);

                                toolbarLayout.setTitle(episode.getName());

                                tvRelease.setText("Release date " + episode.getAir_date());
                                tvNumEp.setText("Saison " + episode.getSeason_number() + " - Episode " + episode.getEpisode_number());
                                tvSummary.setText(episode.getOverview());

                                if(episode.getStill_path() != null){
                                    Picasso.with(EpisodeActivity.this).load(String.format(Constant.URL_IMAGE_500, episode.getStill_path())).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                ablPoster.setBackground(new BitmapDrawable(EpisodeActivity.this.getResources(), bitmap));
                                            }
                                        }

                                        @Override
                                        public void onBitmapFailed(Drawable errorDrawable) {
                                            Log.d("TAG", "FAILED");
                                        }

                                        @Override
                                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                                            Log.d("TAG", "on prepare load");
                                        }
                                    });
                                }

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                                }

                                fabCredit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_credit = new Intent(EpisodeActivity.this, CreditTVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, tvId);
                                        bundle.putInt(Constant.INTENT_NUM_SEASON, numSeason);
                                        bundle.putInt(Constant.INTENT_NUM_EPISODE, numEp);

                                        it_credit.putExtras(bundle);
                                        startActivity(it_credit);
                                    }
                                });

                                fabVideos.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_videos = new Intent(EpisodeActivity.this, VideosActivity.class);
                                        Bundle bundle1 = new Bundle();
                                        bundle1.putInt(Constant.INTENT_ID_TV, tvId);
                                        bundle1.putInt(Constant.INTENT_NUM_SEASON, numSeason);
                                        bundle1.putInt(Constant.INTENT_NUM_EPISODE, numEp);
                                        bundle1.putString(Constant.INTENT_TYPE_VIDEO, "tv");

                                        it_videos.putExtras(bundle1);
                                        startActivity(it_videos);
                                    }
                                });

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errors", error.toString());
                    }
                });

                requestQueue.add(stringRequest);
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
