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

public class SeasonsActivity extends AppCompatActivity {

    private ListView lvSeasons;

    private TV tv;

    private int tvId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Season");

        lvSeasons = (ListView) findViewById(R.id.listView_seasons);

        if (getIntent().getExtras() != null){
            tvId = getIntent().getExtras().getInt(Constant.INTENT_ID_TV);
            String url = String.format(Constant.URL_TV, tvId);

            if(Network.isNetworkAvailable(SeasonsActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(SeasonsActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                tv = gson.fromJson(response, TV.class);

                                lvSeasons.setAdapter(new ListSeasonsAdapter(
                                        SeasonsActivity.this,
                                        R.layout.adapter_list_seasons,
                                        tv.getSeasons()
                                ));

                                lvSeasons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
