package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.adapter.ListMovieAdapter;
import com.matsdb.loicr.moviedb.ui.models.Similar;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe permettant l'affichage des film similaire
 */
public class SimilarMovieActivity extends AppCompatActivity {

    // Déclaration d'un Similar
    private Similar similar;

    // Déclaration des variables
    private int movieId, numPage, nbPages;

    // Déclaration des items du layout
    private ListView lvSimilar;
    private Button btPrevious, btNext;
    private TextView tvPagination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_movie);

        getSupportActionBar().setTitle("Similar Movie");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation des items du layout
        lvSimilar = (ListView) findViewById(R.id.listView_similarMovie);
        btPrevious = (Button) findViewById(R.id.button_previous);
        btNext = (Button) findViewById(R.id.button_next);
        tvPagination = (TextView) findViewById(R.id.textView_Pagination);

        // Si nous recevons des paramètres d'intent
        if(getIntent().getExtras() != null){
            // Initialisation des variables en fonction des paramètres d'intent
            numPage = getIntent().getExtras().getInt(Constant.INTENT_NUM_PAGE);
            movieId = getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
            // Création de l'url
            String url = String.format(Constant.URL_SIMILAR_MOVIE, movieId, numPage);

            // Si nous sommes bien connecté à internet
            if(Network.isNetworkAvailable(SimilarMovieActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(SimilarMovieActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la classe Similar
                                similar = gson.fromJson(response, Similar.class);

                                // Gestion de la pagination
                                nbPages = similar.getTotal_pages();

                                tvPagination.setText(numPage + " / " + nbPages);

                                if(nbPages > 1 && numPage < nbPages) {
                                    btNext.setEnabled(true);
                                }
                                if(numPage > 1){
                                    btPrevious.setEnabled(true);
                                }

                                // Gestion de redirection vers la page suivante
                                btNext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(SimilarMovieActivity.this, SimilarMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });
                                // Gestion de redirection vers la page précédente
                                btPrevious.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(SimilarMovieActivity.this, SimilarMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movieId);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });

                                // Initialisation de la listView en fonction de l'adapter et de la liste des résultats
                                lvSimilar.setAdapter(new ListMovieAdapter(
                                        SimilarMovieActivity.this,
                                        R.layout.adapter_list_search_movie,
                                        similar.getResults()
                                ));

                                // Gestion de l'appuie sur un film de la liste
                                lvSimilar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Création de l'intent pour afficher le détail du film choisi
                                        Intent it_movie = new Intent(SimilarMovieActivity.this, MovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, similar.getResults().get(position).getId());

                                        it_movie.putExtras(bundle);
                                        startActivity(it_movie);
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
