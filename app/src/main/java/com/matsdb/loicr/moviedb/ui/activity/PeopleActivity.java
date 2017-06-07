package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.adapter.ImageAdapter;
import com.matsdb.loicr.moviedb.ui.models.ObjectImage;
import com.matsdb.loicr.moviedb.ui.models.People;
import com.matsdb.loicr.moviedb.ui.models.PeopleImage;
import com.matsdb.loicr.moviedb.ui.models.PeopleMovie;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PeopleActivity extends AppCompatActivity {

    private TextView tvBornWhere, tvBiography, tvName;
    private ImageView ivPhoto;
    private RecyclerView rvMovies;

    private List<ObjectImage> images = new ArrayList<>();

    private int peopleId;
    private People people;
    private PeopleImage peopleImage;
    private PeopleMovie peopleMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        tvBiography = (TextView) findViewById(R.id.textView_biography);
        tvBornWhere = (TextView) findViewById(R.id.textView_bornWhere);
        tvName = (TextView) findViewById(R.id.textView_peopleName);

        rvMovies = (RecyclerView) findViewById(R.id.recyclerView_movie);

        ivPhoto = (ImageView) findViewById(R.id.image_people);

        if(getIntent().getExtras() != null){
            peopleId = getIntent().getExtras().getInt(Constant.INTENT_ID_PEOPLE);
            String url = String.format(Constant.URL_PEOPLE, peopleId);
            String url2 = String.format(Constant.URL_PEOPLE_IMAGE, peopleId);

            if(Network.isNetworkAvailable(PeopleActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(PeopleActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                people = gson.fromJson(response, People.class);

                                tvBornWhere.setText("Born the " + people.getBirthday() + " in " + people.getPlace_of_birth());
                                tvBiography.setText(people.getBiography());
                                tvName.setText(people.getName());

                                Picasso.with(PeopleActivity.this).load(String.format(Constant.URL_IMAGE, people.getProfile_path())).into(ivPhoto);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errors", error.toString());
                    }
                });

                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                peopleImage = gson.fromJson(response, PeopleImage.class);

                                for (int i = 0 ; i < peopleImage.getProfiles().size() ; i++){
                                    images.add(new ObjectImage(String.format(Constant.URL_IMAGE_500, peopleImage.getProfiles().get(i).getFile_path())));
                                }

                                rvMovies.setLayoutManager(new LinearLayoutManager(PeopleActivity.this, LinearLayoutManager.HORIZONTAL, true));
                                rvMovies.setAdapter(new ImageAdapter(images));


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errors", error.toString());
                    }
                });

                queue.add(stringRequest);
                queue.add(stringRequest1);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_people, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_credits:
                Intent it_credits = new Intent(PeopleActivity.this, PeopleCreditsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.INTENT_ID_PEOPLE, peopleId);
                it_credits.putExtras(bundle);
                startActivity(it_credits);
        }

        return super.onOptionsItemSelected(item);
    }

}
