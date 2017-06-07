package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.matsdb.loicr.moviedb.ui.adapter.ListEpisodesAdapter;
import com.matsdb.loicr.moviedb.ui.models.Season;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe permettant de voir la liste des épisodes d'une saison d'une série
 */
public class EpisodesActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private ListView lvEpisodes;

    // Déclaration des variable nécessaires
    private int tvId, numSeason;

    // Déclaration d'une Saison
    private Season season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation des items du layout
        lvEpisodes = (ListView) findViewById(R.id.listView_episodes);

        // Si nous avons bien des Intents à récupérer
        if(getIntent().getExtras() != null){
            // Initialisation des variables en fonction des intents récupéré
            tvId = getIntent().getExtras().getInt(Constant.INTENT_ID_TV);
            numSeason = getIntent().getExtras().getInt(Constant.INTENT_NUM_SEASON);
            // Création de l'url permettant de récupérer la liste des épisodes
            String url = String.format(Constant.URL_TV_SEASON, tvId, numSeason);

            // Si le mobile est bien connecté à internet
            if (Network.isNetworkAvailable(EpisodesActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(EpisodesActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en une saison
                                season = gson.fromJson(response, Season.class);

                                // Modification des items du layout
                                getSupportActionBar().setTitle(season.getName());

                                // Transformation de la liste des épisode en fonction de l'adapter donné et des épisodes
                                lvEpisodes.setAdapter(new ListEpisodesAdapter(
                                        EpisodesActivity.this,
                                        R.layout.adapter_list_episodes,
                                        season.getEpisodes()
                                ));

                                // Gestion du click sur un item de la liste créée précédemment
                                lvEpisodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Creation d'un intent pour voir le détail de l'episode choisi
                                        Intent it_episode = new Intent(EpisodesActivity.this, EpisodeActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, tvId);
                                        bundle.putInt(Constant.INTENT_NUM_SEASON, numSeason);
                                        bundle.putInt(Constant.INTENT_NUM_EPISODE, season.getEpisodes().get(position).getEpisode_number());

                                        it_episode.putExtras(bundle);
                                        startActivity(it_episode);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seasons, menu);

        return super.onCreateOptionsMenu(menu);
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
            case R.id.action_credits:
                // Création de l'intent pour les crédit de la saison
                Intent it_credit = new Intent(EpisodesActivity.this, CreditTVActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.INTENT_ID_TV, tvId);
                bundle.putInt(Constant.INTENT_NUM_SEASON, numSeason);
                bundle.putInt(Constant.INTENT_NUM_EPISODE, 9999999);

                it_credit.putExtras(bundle);
                startActivity(it_credit);
                break;
            case R.id.action_video:
                // Création de l'intent pour les vidéos de la saison
                Intent it_videos = new Intent(EpisodesActivity.this, VideosActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putInt(Constant.INTENT_ID_TV, tvId);
                bundle1.putInt(Constant.INTENT_NUM_SEASON, numSeason);
                bundle1.putInt(Constant.INTENT_NUM_EPISODE, 9999999);
                bundle1.putString(Constant.INTENT_TYPE_VIDEO, "tv");

                it_videos.putExtras(bundle1);
                startActivity(it_videos);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
