package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.matsdb.loicr.moviedb.ui.adapter.ListTVAdapter;
import com.matsdb.loicr.moviedb.ui.models.SearchTV;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe permettant l'affichage de la liste des séries les mieux notées
 */
public class TopRatedTVActivity extends AppCompatActivity {

    // Déclaration d'un TV
    private SearchTV tv;

    // Déclaration des variables
    private int numPage, nbPages;

    // Déclaration des items du layout
    private ListView lvTopRated;
    private Button btPrevious, btNext;
    private TextView tvPagination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_rated_tv);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Best TV Shows");

        // Initialisation des items du layout
        lvTopRated = (ListView) findViewById(R.id.listView_TopRatedTV);
        btPrevious = (Button) findViewById(R.id.button_previous);
        btNext = (Button) findViewById(R.id.button_next);
        tvPagination = (TextView) findViewById(R.id.textView_Pagination);

        // Si nous recevons des paramères de l'intent
        if(getIntent().getExtras() != null) {
            //Initialisation des variables en fonction des paramètres de l'intent
            numPage = getIntent().getExtras().getInt(Constant.INTENT_NUM_PAGE);
            // Création de l'url
            String url = String.format(Constant.URL_TOPRATED_TV, numPage);

            // Si nous sommes bien connecté à internet
            if (Network.isNetworkAvailable(TopRatedTVActivity.this)) {
                RequestQueue queue = Volley.newRequestQueue(TopRatedTVActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la classe SearchTV
                                tv = gson.fromJson(response, SearchTV.class);

                                // Gestion de la pagination
                                nbPages = tv.getTotal_pages();

                                tvPagination.setText(numPage + " / " + nbPages);

                                if(nbPages > 1 && numPage < nbPages) {
                                    btNext.setEnabled(true);
                                }
                                if(numPage > 1){
                                    btPrevious.setEnabled(true);
                                }

                                // Gestion de la redirection vers la page suivante
                                btNext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(TopRatedTVActivity.this, TopRatedTVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });
                                // Gestion de la redirection vers la page précédente
                                btPrevious.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(TopRatedTVActivity.this, TopRatedTVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });

                                // Initialisation de la listView en fonction de l'adapter et de la liste des séries
                                lvTopRated.setAdapter(new ListTVAdapter(
                                        TopRatedTVActivity.this,
                                        R.layout.adapter_list_search_movie,
                                        tv.getResults()
                                ));

                                // Gestion de l'appuie sur un item de la liste des séries
                                lvTopRated.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Création de l'intent pour voir le détail de la série choisie
                                        Intent it_movie = new Intent(TopRatedTVActivity.this, TVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, tv.getResults().get(position).getId());

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
