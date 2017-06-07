package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.matsdb.loicr.moviedb.ui.adapter.ListVideosAdapter;
import com.matsdb.loicr.moviedb.ui.models.Videos;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe permettant l'affichage des vidéos
 */
public class VideosActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private ListView lvVideos;

    // Déclaration des variables
    private int movieId, numEpisode, numSeason;
    private String url = "";

    // Déclaration d'une video
    private Videos videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Video");

        // Initialisation des items du layout
        lvVideos = (ListView) findViewById(R.id.listView_videos);

        // Si nous recevons des paramètres d'intent
        if(getIntent().getExtras() != null){
            String type = getIntent().getExtras().getString(Constant.INTENT_TYPE_VIDEO);
            // Le lien sera différent pour un film ou pour une série
            if(type.equals("movie")) {
                // Si c'est un film
                movieId = getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
                url = String.format(Constant.URL_VIDEO_MOVIE, movieId);
            }else if(type.equals("tv")){
                // Si c'est une série
                movieId = getIntent().getExtras().getInt(Constant.INTENT_ID_TV);
                numSeason = getIntent().getExtras().getInt(Constant.INTENT_NUM_SEASON);
                numEpisode = getIntent().getExtras().getInt(Constant.INTENT_NUM_EPISODE);

                if (numEpisode == 9999999 && numSeason == 999){ // Si pas de saison ou d'episode renseigné, juste credit de la serie
                    url = String.format(Constant.URL_TV_SEASON_VIDEO, movieId);
                }else if (numSeason != 999 && numEpisode == 9999999){ // si pas d'episode renseigne mais saison renseigné, credit d'une saison
                    url = String.format(Constant.URL_TV_SEASON_VIDEO, movieId, numSeason);
                }else { // Si episode et saison renseigné, credit d'un episode
                    url = String.format(Constant.URL_TV_EPISODE_VIDEO, movieId, numSeason, numEpisode);
                }
            }

            // Si nous sommes bien connectés à internet
            if (Network.isNetworkAvailable(VideosActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(VideosActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la classe VIdeos
                                videos = gson.fromJson(response, Videos.class);

                                // Initialisation de la listView en fonction de l'adapter et de la liste des vidéos
                                lvVideos.setAdapter(new ListVideosAdapter(
                                        VideosActivity.this,
                                        R.layout.adapter_list_videos,
                                        videos.getResults()
                                ));

                                // Gestion de l'appuie sur l'une des vidéos de la listView
                                lvVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Ouvre la vidéo sur youtube
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videos.getResults().get(position).getKey())));
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
