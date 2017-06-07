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

/**
 * Classe permettant d'afficher la liste des commentaires
 */
public class ReviewsMovieActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private ListView lvReviews;

    // Déclaration d'une reviews
    private Reviews reviews;

    // Déclaration des variables
    private int movieId;
    private List<String> reviewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews_movie);

        getSupportActionBar().setTitle("Reviews");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation des items du layout
        lvReviews = (ListView) findViewById(R.id.listView_reviews);
        reviewsList = new ArrayList<>();

        // Si nous recevons des paramètres d'intent
        if(getIntent().getExtras() != null){
            // Initialisation des variables en fonction des paramètres d'intent
            movieId = getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
            // Création de l'url
            String url = String.format(Constant.URL_REVIEWS_MOVIE, movieId);

            // Si nous sommes bien connecté à internet
            if(Network.isNetworkAvailable(ReviewsMovieActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(ReviewsMovieActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la classe Reviews
                                reviews = gson.fromJson(response, Reviews.class);

                                // Ajout des reviews dans la liste
                                for (int i = 0 ; i < reviews.getResults().size() ; i++){
                                    reviewsList.add(reviews.getResults().get(i).getAuthor() + " : " + reviews.getResults().get(i).getContent());
                                }

                                // Création de l'affichage de la liste
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ReviewsMovieActivity.this, android.R.layout.simple_list_item_1, reviewsList);
                                lvReviews.setAdapter(adapter);

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
