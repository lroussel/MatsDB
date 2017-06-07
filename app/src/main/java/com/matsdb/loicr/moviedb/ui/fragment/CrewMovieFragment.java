package com.matsdb.loicr.moviedb.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.adapter.ListCrewMovieAdapter;
import com.matsdb.loicr.moviedb.ui.models.MovieCredits;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Created by loicr on 30/05/2017.
 */

public class CrewMovieFragment extends Fragment {

    private ListView lvCrewMovie;
    private int movieId;

    private MovieCredits credits;

    private static final String ARG_SECTION_FRAGMENT = "section_number";

    public CrewMovieFragment(){}

    public static CrewMovieFragment newInstance(int sectionNumber) {
        Bundle args = new Bundle();
        CrewMovieFragment fragment = new CrewMovieFragment();
        args.putInt(ARG_SECTION_FRAGMENT, sectionNumber);

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_crewmovie, container, false);

        lvCrewMovie = (ListView) rootView.findViewById(R.id.listView_crewmovie);

        if(getActivity().getIntent().getExtras() != null){
            movieId = getActivity().getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
            String url = String.format(Constant.URL_CREDIT_MOVIE, movieId);

            if (Network.isNetworkAvailable(getContext())){
                RequestQueue queue = Volley.newRequestQueue(getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                credits = gson.fromJson(response, MovieCredits.class);

                                lvCrewMovie.setAdapter(new ListCrewMovieAdapter(
                                        getContext(),
                                        R.layout.adapter_list_crewmovie,
                                        credits.getCrew()
                                ));

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
