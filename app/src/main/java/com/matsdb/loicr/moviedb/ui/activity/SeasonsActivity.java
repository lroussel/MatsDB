package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.adapter.ListSeasonsAdapter;
import com.matsdb.loicr.moviedb.ui.models.TV;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe permettant l'affiche des saison d'une série
 */
public class SeasonsActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private ListView lvSeasons;

    // Déclaration d'une TV
    private TV tv;

    // Déclaration des variables
    private int tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Season");

        // Initialisation des items du layout
        lvSeasons = (ListView) findViewById(R.id.listView_seasons);

        // Si nous recevons des paramètres d'intent
        if (getIntent().getExtras() != null){
            // Initialisation des variables en fonction des paramètres d'intent
            tvId = getIntent().getExtras().getInt(Constant.INTENT_ID_TV);
            // Création de l'url
            String url = String.format(Constant.URL_TV, tvId);

            // Si nous sommes bien connecté à internet
            if(Network.isNetworkAvailable(SeasonsActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(SeasonsActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la classe TV
                                tv = gson.fromJson(response, TV.class);

                                // Initialisation de la listView en fonction de l'adapter et de la liste des saisons
                                lvSeasons.setAdapter(new ListSeasonsAdapter(
                                        SeasonsActivity.this,
                                        R.layout.adapter_list_seasons,
                                        tv.getSeasons()
                                ));

                                // Gestion de l'appuie sur une saison dans la liste
                                lvSeasons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Création de l'intent pour voir la liste des épisode de la saison choisie
                                        Intent it_episodes = new Intent(SeasonsActivity.this, EpisodesActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, tvId);
                                        bundle.putInt(Constant.INTENT_NUM_SEASON, tv.getSeasons().get(position).getSeason_number());

                                        it_episodes.putExtras(bundle);
                                        startActivity(it_episodes);
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
