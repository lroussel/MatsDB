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
import com.matsdb.loicr.moviedb.ui.activity.TVActivity;
import com.matsdb.loicr.moviedb.ui.adapter.ListTVPeopleAdapter;
import com.matsdb.loicr.moviedb.ui.models.PeopleTV;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Created by loicr on 31/05/2017.
 */

public class PeopleTVFragment extends Fragment {

    public int movieId, peopleId;
    private PeopleTV peopleTV;

    private ListView lvPeopleTV;

    private static final String ARG_SECTION_FRAGMENT = "section_number";

    public PeopleTVFragment(){}

    public static PeopleTVFragment newInstance(int sectionNumber) {

        Bundle args = new Bundle();

        PeopleTVFragment fragment = new PeopleTVFragment();
        args.putInt(ARG_SECTION_FRAGMENT, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_peopletv, container, false);

        lvPeopleTV = (ListView) rootView.findViewById(R.id.listView_peopleTV);

        if (getActivity().getIntent().getExtras() != null){
            peopleId = getActivity().getIntent().getExtras().getInt(Constant.INTENT_ID_PEOPLE);
            String url = String.format(Constant.URL_PEOPLE_TV, peopleId);

            if (Network.isNetworkAvailable(getContext())){
                RequestQueue queue = Volley.newRequestQueue(getContext());

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                peopleTV = gson.fromJson(response, PeopleTV.class);

                                lvPeopleTV.setAdapter(new ListTVPeopleAdapter(
                                        getContext(),
                                        R.layout.adapter_list_peopletv,
                                        peopleTV.getCast()
                                ));

                                lvPeopleTV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent it_tv = new Intent(getContext(), TVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, peopleTV.getCast().get(position).getId());
                                        it_tv.putExtras(bundle);
                                        startActivity(it_tv);
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
