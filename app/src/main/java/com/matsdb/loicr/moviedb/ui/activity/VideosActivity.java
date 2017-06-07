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

public class VideosActivity extends AppCompatActivity {

    private ListView lvVideos;
    private int movieId, numEpisode, numSeason;
    private String url = "";

    private Videos videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Video");

        lvVideos = (ListView) findViewById(R.id.listView_videos);

        if(getIntent().getExtras() != null){
            String type = getIntent().getExtras().getString(Constant.INTENT_TYPE_VIDEO);
            if(type.equals("movie")) {
                movieId = getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
                url = String.format(Constant.URL_VIDEO_MOVIE, movieId);
            }else if(type.equals("tv")){
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

            if (Network.isNetworkAvailable(VideosActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(VideosActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                videos = gson.fromJson(response, Videos.class);

                                lvVideos.setAdapter(new ListVideosAdapter(
                                        VideosActivity.this,
                                        R.layout.adapter_list_videos,
                                        videos.getResults()
                                ));

                                lvVideos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
