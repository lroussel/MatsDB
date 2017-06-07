package com.matsdb.loicr.moviedb.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.activity.MovieActivity;
import com.matsdb.loicr.moviedb.ui.adapter.ListMoviePeopleAdapter;
import com.matsdb.loicr.moviedb.ui.models.PeopleMovie;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Created by loicr on 31/05/2017.
 */

public class PeopleMovieFragment extends Fragment {

    public int movieId, peopleId;
    private PeopleMovie peopleMovie;

    private ListView lvPeopleMovie;

    private static final String ARG_SECTION_FRAGMENT = "section_number";

    public PeopleMovieFragment(){}

    public static PeopleMovieFragment newInstance(int sectionNumber) {

        Bundle args = new Bundle();

        PeopleMovieFragment fragment = new PeopleMovieFragment();
        args.putInt(ARG_SECTION_FRAGMENT, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_peoplemovie, container, false);

        lvPeopleMovie = (ListView) rootView.findViewById(R.id.listView_peopleMovie);

        if (getActivity().getIntent().getExtras() != null){
            peopleId = getActivity().getIntent().getExtras().getInt(Constant.INTENT_ID_PEOPLE);
            String url = String.format(Constant.URL_PEOPLE_MOVIE, peopleId);

            if (Network.isNetworkAvailable(getContext())){
                RequestQueue queue = Volley.newRequestQueue(getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                peopleMovie = gson.fromJson(response, PeopleMovie.class);

                                lvPeopleMovie.setAdapter(new ListMoviePeopleAdapter(
                                        getContext(),
                                        R.layout.adapter_list_peoplemovie,
                                        peopleMovie.getCast()
                                ));

                                lvPeopleMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent it_movie = new Intent(getContext(), MovieActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_MOVIE, peopleMovie.getCast().get(position).getId());
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

        return rootView;
    }
}
