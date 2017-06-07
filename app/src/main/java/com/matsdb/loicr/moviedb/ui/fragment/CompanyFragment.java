package com.matsdb.loicr.moviedb.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.models.Movie;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loicr on 30/05/2017.
 */

public class CompanyFragment extends Fragment {

    private ListView lvCompanies;
    private Movie movie;
    private int movieId;
    private List<String> companies;

    private static final String ARG_SECTION_FRAGMENT = "section_number";

    public CompanyFragment(){}

    public static CompanyFragment newInstance(int sectionNumber) {

        Bundle args = new Bundle();

        CompanyFragment fragment = new CompanyFragment();
        args.putInt(ARG_SECTION_FRAGMENT, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_company, container, false);

        lvCompanies = (ListView) rootView.findViewById(R.id.listView_companiess);
        companies = new ArrayList<>();

        if(getActivity().getIntent().getExtras() != null){
            movieId = getActivity().getIntent().getExtras().getInt(Constant.INTENT_ID_MOVIE);
            String url = String.format(Constant.URL_MOVIE, movieId);

            if(Network.isNetworkAvailable(getContext())){
                RequestQueue queue = Volley.newRequestQueue(getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                movie = gson.fromJson(response, Movie.class);

                                for (int i = 0 ; i < movie.getProduction_companies().size() ; i++){
                                    companies.add(movie.getProduction_companies().get(i).getName());
                                }

                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, companies);
                                lvCompanies.setAdapter(adapter);

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
