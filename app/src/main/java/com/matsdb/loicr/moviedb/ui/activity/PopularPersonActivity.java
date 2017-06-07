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
import com.matsdb.loicr.moviedb.ui.adapter.ListPersonAdapter;
import com.matsdb.loicr.moviedb.ui.models.SearchPerson;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.matsdb.loicr.moviedb.ui.utils.Network;
import com.google.gson.Gson;

/**
 * Classe pour afficher la liste des people populaire du moment
 */
public class PopularPersonActivity extends AppCompatActivity {

    // Déclaration d'une SearchPerson
    private SearchPerson person;

    // Déclaration des items du layout
    private Button btPrevious, btNext;
    private TextView tvPagination;
    private ListView lvPopular;

    // Déclaration des variables
    private int numPage, nbPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_person);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Popular person");

        // Initialisation des items du layout
        lvPopular = (ListView) findViewById(R.id.listView_popularPerson);
        btPrevious = (Button) findViewById(R.id.button_previous);
        btNext = (Button) findViewById(R.id.button_next);
        tvPagination = (TextView) findViewById(R.id.textView_Pagination);

        // Si nous obtenons des paramètres d'intent
        if(getIntent().getExtras() != null) {
            // Initialisation des variables en fonction des paramètres d'intent
            numPage = getIntent().getExtras().getInt(Constant.INTENT_NUM_PAGE);
            // Création de l'url
            String url = String.format(Constant.URL_POPULAR_PERSON, numPage);

            // Si nous sommes bien connecté à internet
            if (Network.isNetworkAvailable(PopularPersonActivity.this)) {
                RequestQueue queue = Volley.newRequestQueue(PopularPersonActivity.this);

                // Création de la requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformaiton de la réponse en format de la classe SearchPerson
                                person = gson.fromJson(response, SearchPerson.class);


                                // Gestion de la pagination
                                nbPages = person.getTotal_pages();

                                tvPagination.setText(numPage + " / " + nbPages);

                                if(nbPages > 1 && numPage < nbPages) {
                                    btNext.setEnabled(true);
                                }
                                if(numPage > 1){
                                    btPrevious.setEnabled(true);
                                }

                                // Gestion pour aller vers la page suivante
                                btNext.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(PopularPersonActivity.this, PopularPersonActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage + 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });
                                // Gestion pour aller vers la page précédente
                                btPrevious.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent it_search = new Intent(PopularPersonActivity.this, PopularPersonActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_NUM_PAGE, numPage - 1);

                                        it_search.putExtras(bundle);
                                        startActivity(it_search);
                                        finish();
                                    }
                                });

                                // Initialisation de la listeView en fonction de l'adapter et de la liste des personnes
                                lvPopular.setAdapter(new ListPersonAdapter(
                                        PopularPersonActivity.this,
                                        R.layout.adapter_list_search_person,
                                        person.getResults()
                                ));

                                // Gestion de l'appuie sur un des items de la liste
                                lvPopular.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        // Création de l'intent pour aller vers le détails de la personne choisi
                                        Intent it_movie = new Intent(PopularPersonActivity.this, PeopleActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putInt(Constant.INTENT_ID_PEOPLE, person.getResults().get(position).getId());

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
