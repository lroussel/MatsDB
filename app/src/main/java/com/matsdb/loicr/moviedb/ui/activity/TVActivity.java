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
import com.matsdb.loicr.moviedb.ui.models.TV;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Classe permettant d'afficher le détail d'une sére TV
 */
public class TVActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private FloatingActionButton fabCredits, fabSeasons, fabVideos;
    private TextView tvReleaseTV, tvByTV, tvSummaryTV, tvGenreTV, tvNumberTV;
    private AppBarLayout ablPoster;
    private CollapsingToolbarLayout toolbarLayout;

    // Déclaration des variables
    private int tvId;

    // Déclaration d'une TV
    private TV tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation des items du layout
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        ablPoster = (AppBarLayout) findViewById(R.id.app_bar);

        tvReleaseTV = (TextView) findViewById(R.id.textView_releaseTV);
        tvByTV = (TextView) findViewById(R.id.textView_byTV);
        tvSummaryTV = (TextView) findViewById(R.id.textView_summaryTV);
        tvGenreTV = (TextView) findViewById(R.id.textView_GenreTV);
        tvNumberTV = (TextView) findViewById(R.id.textView_numberTV);

        fabCredits = (FloatingActionButton) findViewById(R.id.floating_cast);
        fabSeasons = (FloatingActionButton) findViewById(R.id.floating_seasons);
        fabVideos = (FloatingActionButton) findViewById(R.id.floating_videos);

        // Si nous recevons des paramètres d'intent
        if (getIntent().getExtras() != null){
            // Initialisation des variables en fonction des paramètres d'intent
            tvId = getIntent().getExtras().getInt(Constant.INTENT_ID_TV);
            // Création de l'url
            String url = String.format(Constant.URL_TV, tvId);

            // Si nous sommes bien connecté à internet
            if(Network.isNetworkAvailable(TVActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(TVActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Trasnformation de la réponse en format de la classe TV
                                tv = gson.fromJson(response, TV.class);

                                String createdBy = "";
                                String genres = "";

                                for (int i = 0 ; i < tv.getCreated_by().size() ; i++ ){
                                    createdBy += tv.getCreated_by().get(i).getName() + " & ";
                                }
                                for (int j = 0 ; j < tv.getGenres().size() ; j ++){
                                    genres += tv.getGenres().get(j).getName() + " / ";
                                }

                                // Modification des valeurs des items du layout
                                toolbarLayout.setTitle(tv.getName());

                                tvReleaseTV.setText("Created : " + tv.getFirst_air_date());
                                tvByTV.setText("By " + createdBy);
                                tvSummaryTV.setText(tv.getOverview());
                                tvGenreTV.setText("Type : " + genres);
                                tvNumberTV.setText(tv.getNumber_of_seasons() + " season - " + tv.getNumber_of_episodes() + " episode");

                                if (tv.getBackdrop_path() != null){
                                    Picasso.with(TVActivity.this).load(String.format(Constant.URL_IMAGE_500, tv.getBackdrop_path())).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            ablPoster.setBackground(new BitmapDrawable(TVActivity.this.getResources(), bitmap));
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

                                getWindow().setStatusBarColor(Color.TRANSPARENT);

                                // Gestion de l'appuie sur le bouton pour afficher les crédit de la série
                                fabCredits.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_credit = new Intent(TVActivity.this, CreditTVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, tvId);
                                        bundle.putInt(Constant.INTENT_NUM_SEASON, 999);
                                        bundle.putInt(Constant.INTENT_NUM_EPISODE, 9999999);
                                        it_credit.putExtras(bundle);

                                        startActivity(it_credit);
                                    }
                                });

                                // Gestion de l'appuie sur le bouton pour afficher les vidéos de la série
                                fabVideos.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_videos = new Intent(TVActivity.this, VideosActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString(Constant.INTENT_TYPE_VIDEO, "tv");
                                        bundle.putInt(Constant.INTENT_ID_TV, tvId);
                                        it_videos.putExtras(bundle);
                                        startActivity(it_videos);
                                    }
                                });

                                // Gestion de l'appuie sur le bouton pour afficher les saisons de la série
                                fabSeasons.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_seasons = new Intent(TVActivity.this, SeasonsActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, tvId);
                                        it_seasons.putExtras(bundle);
                                        startActivity(it_seasons);
                                    }
                                });

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

    /**
     * Gestion des boutons du menu de l'affichage
     * @param item
     * @return
     */
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
