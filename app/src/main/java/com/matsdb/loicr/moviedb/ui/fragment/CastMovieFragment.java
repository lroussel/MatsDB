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
import com.matsdb.loicr.moviedb.ui.activity.PeopleActivity;
import com.matsdb.loicr.moviedb.ui.adapter.ListCastMovieAdapter;
import com.matsdb.loicr.moviedb.ui.models.MovieCredits;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Created by loicr on 30/05/2017.
 */

public class CastMovieFragment extends Fragment {

    private MovieCredits credits;

    private ListView lvCastMovie;
    private int movieId;

    private static final String ARG_SECTION_FRAGMENT = "section_number";

    public CastMovieFragment() {
    }

    public static CastMovieFragment newInstance(int sectionNumber) {
        CastMovieFragment fragment = new CastMovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_FRAGMENT, sectionNumber);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_castmovie, container, false);

        lvCastMovie = (ListView) rootView.findViewById(R.id.listView_castMovie);

        if(getActivity().getIntent().getExtras() != null){
            movieId = getActivity().getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
            String url = String.format(Constant.URL_CREDIT_MOVIE, movieId);

            if(Network.isNetworkAvailable(getContext())){
                RequestQueue queue = Volley.newRequestQueue(getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Gson gson = new Gson();

                                credits = gson.fromJson(response, MovieCredits.class);

                                lvCastMovie.setAdapter(new ListCastMovieAdapter(
                                        getContext(),
                                        R.layout.adapter_list_castmovie,
                                        credits.getCast()
                                ));

                                lvCastMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent it_people = new Intent(getContext(), PeopleActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_PEOPLE, credits.getCast().get(position).getId());

                                        it_people.putExtras(bundle);
                                        startActivity(it_people);
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
