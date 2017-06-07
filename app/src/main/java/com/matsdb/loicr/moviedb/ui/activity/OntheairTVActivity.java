package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.adapter.ListTVAdapter;
import com.matsdb.loicr.moviedb.ui.models.SearchTV;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe pour l'affichage des séries en cours d'affichag
 */
public class OntheairTVActivity extends AppCompatActivity {

    // Déclaration d'une recherche de série
    private SearchTV tv;

    // Déclaration des items du layout
    private ListView lvUpcomming;
    private Button btPrevious, btNext;
    private TextView tvPagination;

    // Déclaration des variables
    private int numPage, nbPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ontheair_tv);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("On the air");

        // Initialisation des items du layout
        lvUpcomming = (ListView) findViewById(R.id.listView_ontheairTV);
        btPrevious = (Button) findViewById(R.id.button_previous);
        btNext = (Button) findViewById(R.id.button_next);
        tvPagination = (TextView) findViewById(R.id.textView_Pagination);

        // Si nous recevons bien des paramètres d'intent
        if(getIntent().getExtras() != null) {
            // Initialisation des variables en fonction des paramètres d'intent
            numPage = getIntent().getExtras().getInt(Constant.INTENT_NUM_PAGE);
            // Création de l'url
            String url = String.format(Constant.URL_ONTHEAIR_TV, numPage);

            // Si nous sommes bien connecté à Internet
            if (Network.isNetworkAvailable(OntheairTVActivity.this)) {
                RequestQueue queue = Volley.newRequestQueue(OntheairTVActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en une classe de searchTV
                                tv = gson.fromJson(response, SearchTV.class);

                                // Récupèration du nombre de page pour la pagination
                                nbPages = tv.getTotal_pages();

                                tvPagination.setText(numPage + " / " + nbPages);

                                if(nbPages > 1 && numPage < nbPages) {
                                    // Si le numéro de page actuel est entre 1 et le nombre de pages
                                    // total, on active le bouton next
                                    btNext.setEnabled(true);
                                }
                                if(numPage > 1){
                                    // Si le numéro de page actuel est plus grand que 1
                                    // on active le bouton previous
                                    btPrevious.setEnabled(true);
                                }

                                // Gestion de l'appuie sur un des boutons
                                btNext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent de recherche avec la même URL mais
                                        // pour la page suivante
                                        Intent it_search = new Intent(OntheairTVActivity.this, OntheairTVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });

                                btPrevious.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        // Création de l'intent de recherche avec la même URL mais
                                        // pour la page précédente
                                        Intent it_search = new Intent(OntheairTVActivity.this, OntheairTVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });

                                // Création de la listeView en fonction de l'adapter et des résultats
                                // récupérés
                                lvUpcomming.setAdapter(new ListTVAdapter(
                                        OntheairTVActivity.this,
                                        R.layout.adapter_list_search_movie,
                                        tv.getResults()
                                ));

                                // Gestion de l'appuie sur un des items de la liste
                                lvUpcomming.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Création de l'intent pour afficher le détail de la série
                                        Intent it_movie = new Intent(OntheairTVActivity.this, TVActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_TV, tv.getResults().get(position).getId());

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
    }

    /**
     * Gestion des boutons du menu de l'affichage
     * @param item
     * @return
     */
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
