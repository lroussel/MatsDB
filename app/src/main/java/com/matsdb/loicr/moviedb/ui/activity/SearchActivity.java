package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.adapter.ListMovieAdapter;
import com.matsdb.loicr.moviedb.ui.adapter.ListPersonAdapter;
import com.matsdb.loicr.moviedb.ui.adapter.ListTVAdapter;
import com.matsdb.loicr.moviedb.ui.models.SearchMovie;
import com.matsdb.loicr.moviedb.ui.models.SearchPerson;
import com.matsdb.loicr.moviedb.ui.models.SearchTV;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

public class SearchActivity extends AppCompatActivity {

    private ListView lvSearch;
    private String url, search, type;
    private SearchMovie movies;
    private SearchTV tvs;
    private SearchPerson persons;
    private int numPage, nbPages;
    private Button btPrevious, btNext;
    private TextView tvPagination;
    private ProgressBar pbSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lvSearch = (ListView) findViewById(R.id.listView_search);
        btPrevious = (Button) findViewById(R.id.button_previous);
        btNext = (Button) findViewById(R.id.button_next);
        tvPagination = (TextView) findViewById(R.id.textView_Pagination);
        pbSearch = (ProgressBar) findViewById(R.id.progressBar_search);

        if(getIntent().getExtras() != null){
            search = getIntent().getExtras().getString(Constant.INTENT_SEARCH);
            type = getIntent().getExtras().getString(Constant.INTENT_TYPE_SEARCH);
            numPage = getIntent().getExtras().getInt(Constant.INTENT_NUM_PAGE);
            url = String.format(Constant.URL_SEARCH, type, search, numPage);

            getSupportActionBar().setTitle("\"" + search + "\"");

            if(Network.isNetworkAvailable(SearchActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(SearchActivity.this);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                switch (type){
                                    case "movie":
                                        movies = gson.fromJson(response, SearchMovie.class);

                                        lvSearch.setAdapter(new ListMovieAdapter(
                                                SearchActivity.this,
                                                R.layout.adapter_list_search_movie,
                                                movies.getResults()
                                        ));

                                        nbPages = movies.getTotal_pages();

                                        tvPagination.setText(numPage + " / " + nbPages);

                                        if(nbPages > 1 && numPage < nbPages) {
                                            btNext.setEnabled(true);
                                        }
                                        if(numPage > 1){
                                            btPrevious.setEnabled(true);
                                        }

                                        btNext.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent it_search = new Intent(SearchActivity.this, SearchActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString(Constant.INTENT_SEARCH, search);
                                                bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);
                                                bundle.putString(Constant.INTENT_TYPE_SEARCH, type);

                                                it_search.putExtras(bundle);
                                                startActivity(it_search);
                                                finish();
                                            }
                                        });
                                        btPrevious.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent it_search = new Intent(SearchActivity.this, SearchActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString(Constant.INTENT_SEARCH, search);
                                                bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);
                                                bundle.putString(Constant.INTENT_TYPE_SEARCH, type);

                                                it_search.putExtras(bundle);
                                                startActivity(it_search);
                                                finish();
                                            }
                                        });

                                        pbSearch.setVisibility(View.GONE);

                                        lvSearch.setOnItemClickListener(new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent it_movie = new Intent(SearchActivity.this, MovieActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt(Constant.INTENT_ID_MOVIE, movies.getResults().get(position).getId());

                                                it_movie.putExtras(bundle);
                                                startActivity(it_movie);
                                            }
                                        });

                                        break;
                                    case "tv":
                                        tvs = gson.fromJson(response, SearchTV.class);

                                        lvSearch.setAdapter(new ListTVAdapter(
                                                SearchActivity.this,
                                                R.layout.adapter_list_search_movie,
                                                tvs.getResults()
                                        ));

                                        nbPages = tvs.getTotal_pages();

                                        tvPagination.setText(numPage + " / " + nbPages);

                                        if(nbPages > 1 && numPage < nbPages) {
                                            btNext.setEnabled(true);
                                        }
                                        if(numPage > 1){
                                            btPrevious.setEnabled(true);
                                        }

                                        btNext.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent it_search = new Intent(SearchActivity.this, SearchActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString(Constant.INTENT_SEARCH, search);
                                                bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);
                                                bundle.putString(Constant.INTENT_TYPE_SEARCH, type);

                                                it_search.putExtras(bundle);
                                                startActivity(it_search);
                                                finish();
                                            }
                                        });
                                        btPrevious.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent it_search = new Intent(SearchActivity.this, SearchActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString(Constant.INTENT_SEARCH, search);
                                                bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);
                                                bundle.putString(Constant.INTENT_TYPE_SEARCH, type);

                                                it_search.putExtras(bundle);
                                                startActivity(it_search);
                                                finish();
                                            }
                                        });

                                        pbSearch.setVisibility(View.GONE);

                                        lvSearch.setOnItemClickListener(new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent it_tv = new Intent(SearchActivity.this, TVActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt(Constant.INTENT_ID_TV, tvs.getResults().get(position).getId());
                                                it_tv.putExtras(bundle);

                                                startActivity(it_tv);
                                            }
                                        });

                                        break;
                                    case "person":
                                        persons = gson.fromJson(response, SearchPerson.class);

                                        lvSearch.setAdapter(new ListPersonAdapter(
                                                SearchActivity.this,
                                                R.layout.adapter_list_search_person,
                                                persons.getResults()
                                        ));

                                        nbPages = persons.getTotal_pages();

                                        tvPagination.setText(numPage + " / " + nbPages);

                                        if(nbPages > 1 && numPage < nbPages) {
                                            btNext.setEnabled(true);
                                        }
                                        if(numPage > 1){
                                            btPrevious.setEnabled(true);
                                        }

                                        btNext.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent it_search = new Intent(SearchActivity.this, SearchActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString(Constant.INTENT_SEARCH, search);
                                                bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);
                                                bundle.putString(Constant.INTENT_TYPE_SEARCH, type);

                                                it_search.putExtras(bundle);
                                                startActivity(it_search);
                                                finish();
                                            }
                                        });
                                        btPrevious.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent it_search = new Intent(SearchActivity.this, SearchActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putString(Constant.INTENT_SEARCH, search);
                                                bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);
                                                bundle.putString(Constant.INTENT_TYPE_SEARCH, type);

                                                it_search.putExtras(bundle);
                                                startActivity(it_search);
                                                finish();
                                            }
                                        });

                                        pbSearch.setVisibility(View.GONE);

                                        lvSearch.setOnItemClickListener(new OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                Intent it_person = new Intent(SearchActivity.this, PeopleActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt(Constant.INTENT_ID_PEOPLE, persons.getResults().get(position).getId());

                                                it_person.putExtras(bundle);
                                                startActivity(it_person);
                                            }
                                        });
                                        break;
                                }

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
        getMenuInflater().inflate(R.menu.menu_search, menu);

        if(type.equals("person")){
            menu.removeItem(R.id.action_onTheAir);
            menu.removeItem(R.id.action_topRated);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_popular:
                // TODO : Selon type de recherche, envoie vers intent popular
                if(type.equals("movie")){
                    Intent it_search = new Intent(SearchActivity.this, PopularMovieActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    it_search.putExtras(bundle);
                    startActivity(it_search);
                }else if(type.equals("tv")){
                    Intent it_search = new Intent(SearchActivity.this, PopularTVActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    it_search.putExtras(bundle);
                    startActivity(it_search);
                }else if(type.equals("person")){
                    Intent it_search = new Intent(SearchActivity.this, PopularPersonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    it_search.putExtras(bundle);
                    startActivity(it_search);
                }
                break;
            case R.id.action_topRated:
                // TODO : Selon type de recherche, envoie vers intent topRated
                if(type.equals("movie")){
                    Intent it_search = new Intent(SearchActivity.this, TopRatedMovieActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    it_search.putExtras(bundle);
                    startActivity(it_search);
                }else if(type.equals("tv")){
                    Intent it_search = new Intent(SearchActivity.this, TopRatedTVActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    it_search.putExtras(bundle);
                    startActivity(it_search);
                }
                break;
            case R.id.action_onTheAir:
                if(type.equals("movie")){
                    Intent it_search = new Intent(SearchActivity.this, UpcommingMovieActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    it_search.putExtras(bundle);
                    startActivity(it_search);
                }else if(type.equals("tv")){
                    Intent it_search = new Intent(SearchActivity.this, OntheairTVActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    it_search.putExtras(bundle);
                    startActivity(it_search);
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
