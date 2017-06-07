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
import com.matsdb.loicr.moviedb.ui.models.SearchMovie;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe permettant l'affichage es films populaires du moment
 */
public class PopularMovieActivity extends AppCompatActivity {

    // Déclaration d'une searchMovie
    private SearchMovie movies;

    // Déclaration des items du layout
    private ListView lvPopular;
    private Button btPrevious, btNext;
    private TextView tvPagination;

    // Déclaration des variables
    private int numPage, nbPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Popular Movie");

        // Initialisation des items du layotu
        lvPopular = (ListView) findViewById(R.id.listView_popularMovie);
        btPrevious = (Button) findViewById(R.id.button_previous);
        btNext = (Button) findViewById(R.id.button_next);
        tvPagination = (TextView) findViewById(R.id.textView_Pagination);

        // Si nous obtenons des paramètres d'intent
        if(getIntent().getExtras() != null) {
            // Initialisation des variables en fonction des paramètres d'intent
            numPage = getIntent().getExtras().getInt(Constant.INTENT_NUM_PAGE);
            // Création de l'URL
            String url = String.format(Constant.URL_POPULAR_MOVIE, numPage);

            // Si nous sommes bien connecté à internet
            if (Network.isNetworkAvailable(PopularMovieActivity.this)) {
                RequestQueue queue = Volley.newRequestQueue(PopularMovieActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la calsse SearchMovie
                                movies = gson.fromJson(response, SearchMovie.class);

                                // Système de pagination
                                nbPages = movies.getTotal_pages();

                                tvPagination.setText(numPage + " / " + nbPages);

                                if(nbPages > 1 && numPage < nbPages) {
                                    btNext.setEnabled(true);
                                }
                                if(numPage > 1){
                                    btPrevious.setEnabled(true);
                                }

                                // Gestion de l'appuie pour aller à la page suivante
                                btNext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(PopularMovieActivity.this, PopularMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });
                                // Gestion de l'appuie pour aller à la page précédente
                                btPrevious.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(PopularMovieActivity.this, PopularMovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });

                                // Création de la liste en fonction d'un adapter et de la liste des films
                                lvPopular.setAdapter(new ListMovieAdapter(
                                        PopularMovieActivity.this,
                                        R.layout.adapter_list_search_movie,
                                        movies.getResults()
                                ));

                                // Gestion de l'appuie sur l'un des items de la liste
                                lvPopular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Creation de l'intent pour les détails du film choisi
                                        Intent it_movie = new Intent(PopularMovieActivity.this, MovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, movies.getResults().get(position).getId());

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
