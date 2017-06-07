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

public class EpisodesActivity extends AppCompatActivity {

    private ListView lvEpisodes;

    private int tvId, numSeason;

    private Season season;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvEpisodes = (ListView) findViewById(R.id.listView_episodes);

        if(getIntent().getExtras() != null){
            tvId = getIntent().getExtras().getInt(Constant.INTENT_ID_TV);
            numSeason = getIntent().getExtras().getInt(Constant.INTENT_NUM_SEASON);
            String url = String.format(Constant.URL_TV_SEASON, tvId, numSeason);

            if (Network.isNetworkAvailable(EpisodesActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(EpisodesActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                season = gson.fromJson(response, Season.class);

                                getSupportActionBar().setTitle(season.getName());

                                lvEpisodes.setAdapter(new ListEpisodesAdapter(
                                        EpisodesActivity.this,
                                        R.layout.adapter_list_episodes,
                                        season.getEpisodes()
                                ));

                                lvEpisodes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_credits:
                Intent it_credit = new Intent(EpisodesActivity.this, CreditTVActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.INTENT_ID_TV, tvId);
                bundle.putInt(Constant.INTENT_NUM_SEASON, numSeason);
                bundle.putInt(Constant.INTENT_NUM_EPISODE, 9999999);

                it_credit.putExtras(bundle);
                startActivity(it_credit);
                break;
            case R.id.action_video:
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
