package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.models.Movie;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Classe pour afficher les détails d'un film
 */
public class MovieActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private FloatingActionButton fabCast, fabCompany, fabReviews;
    private TextView tvRelease, tvOverview, tvGenres, tvBudget, tvRevenue;
    private Button btVideos, btRecom, btSimilar;
    private AppBarLayout ablPoster;
    private CollapsingToolbarLayout toolbarLayout;

    // Déclaration des variables
    private int movieId;
    private String url;

    // Déclaration d'un Film
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation des items du layout
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        ablPoster = (AppBarLayout) findViewById(R.id.app_bar);

        fabCast = (FloatingActionButton) findViewById(R.id.floating_cast);
        fabCompany = (FloatingActionButton) findViewById(R.id.floating_company);
        fabReviews = (FloatingActionButton) findViewById(R.id.floating_reviews);
        btVideos = (Button) findViewById(R.id.button_movie_video);
        btRecom = (Button) findViewById(R.id.button_movie_recom);
        btSimilar = (Button) findViewById(R.id.button_movie_similar);
        tvRelease = (TextView) findViewById(R.id.textView_release);
        tvOverview = (TextView) findViewById(R.id.textViewSummary);
        tvGenres = (TextView) findViewById(R.id.textView_GenreMovie);
        tvBudget = (TextView) findViewById(R.id.textView_BudgetMovie);
        tvRevenue = (TextView) findViewById(R.id.textView_revenueMovie);

        // Si nous avons bien des intent à récupérer
        if(getIntent().getExtras() != null){
            // Initialisation des variables en fonction des intent recu
            movieId = getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
            // Création de l'url
            url = String.format(Constant.URL_MOVIE, movieId);

            // Si l'utilisateur est bien connecté à internet
            if(Network.isNetworkAvailable(MovieActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(MovieActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la reponse en un film
                                movie = gson.fromJson(response, Movie.class);

                                // Modification des valeurs des items du Layout
                                toolbarLayout.setTitle(movie.getTitle());

                                tvRelease.setText("Release date : " + movie.getRelease_date());
                                tvOverview.setText(movie.getOverview());
                                String genres = "";
                                for (int i = 0 ; i < movie.getGenres().size() ; i++){
                                    genres += movie.getGenres().get(i).getName() + " / ";
                                }
                                tvGenres.setText("Type : " + genres);
                                NumberFormat format = NumberFormat.getInstance(java.util.Locale.FRENCH);
                                tvBudget.setText("Initial budget : " + format.format(movie.getBudget()) + "$");
                                tvRevenue.setText("Revenue : " +format.format(movie.getRevenue()) + "$");

                                // Gestion de l'appuie sur les boutons
                                fabCast.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent pour les crédits du film
                                        Intent it_credit = new Intent(MovieActivity.this, CreditMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);
                                        it_credit.putExtras(bundle);

                                        startActivity(it_credit);
                                    }
                                });

                                fabCompany.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent pour les companies du film
                                        Intent it_company = new Intent(MovieActivity.this, CompanyMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);
                                        it_company.putExtras(bundle);

                                        startActivity(it_company);
                                    }
                                });

                                fabReviews.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent pour les commentaires du film
                                        Intent it_reviews = new Intent(MovieActivity.this, ReviewsMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);
                                        it_reviews.putExtras(bundle);

                                        startActivity(it_reviews);
                                    }
                                });

                                btVideos.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent pour afficher les vidéos du film
                                        Intent it_videos = new Intent(MovieActivity.this, VideosActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);
                                        bundle.putString(Constant.INTENT_TYPE_VIDEO, "movie");
                                        it_videos.putExtras(bundle);

                                        startActivity(it_videos);
                                    }
                                });

                                btRecom.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent pour les recommendations par rapport au film
                                        Intent it_recom = new Intent(MovieActivity.this, RecomMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, 1);
                                        it_recom.putExtras(bundle);

                                        startActivity(it_recom);
                                    }
                                });

                                btSimilar.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent pour les films similaire au film
                                        Intent it_recom = new Intent(MovieActivity.this, SimilarMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, 1);
                                        it_recom.putExtras(bundle);

                                        startActivity(it_recom);
                                    }
                                });

                                // Si nous avons bien une URL pour l'image, alors nous la créons
                                if(movie.getBackdrop_path() != null){
                                    Picasso.with(MovieActivity.this).load(String.format(Constant.URL_IMAGE_500, movie.getBackdrop_path())).into(new Target() {
                                        @Override
                                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                ablPoster.setBackground(new BitmapDrawable(MovieActivity.this.getResources(), bitmap));
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

                                getWindow().setStatusBarColor(Color.TRANSPARENT);

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
