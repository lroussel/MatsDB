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

/**
 * Classe pour les détails d'un/une acteur/Actrice
 */
public class PeopleActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private TextView tvBornWhere, tvBiography, tvName;
    private ImageView ivPhoto;
    private RecyclerView rvMovies;

    // Déclarations des variables
    private List<ObjectImage> images = new ArrayList<>();
    private int peopleId;

    // Déclaration d'un People, d'un PeopleImage et d'un PeopleMovie
    private People people;
    private PeopleImage peopleImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // Initialisation des items du layout
        tvBiography = (TextView) findViewById(R.id.textView_biography);
        tvBornWhere = (TextView) findViewById(R.id.textView_bornWhere);
        tvName = (TextView) findViewById(R.id.textView_peopleName);

        rvMovies = (RecyclerView) findViewById(R.id.recyclerView_movie);

        ivPhoto = (ImageView) findViewById(R.id.image_people);

        // SI nous récupérons bien des paramètres venant de l'intent
        if(getIntent().getExtras() != null){
            // Initialisation des variables en fonction des paramètres d'intent
            peopleId = getIntent().getExtras().getInt(Constant.INTENT_ID_PEOPLE);
            // Création des urls
            String url = String.format(Constant.URL_PEOPLE, peopleId);
            String url2 = String.format(Constant.URL_PEOPLE_IMAGE, peopleId);

            // Si nous sommes bien connectés à internet
            if(Network.isNetworkAvailable(PeopleActivity.this)){
                RequestQueue queue = Volley.newRequestQueue(PeopleActivity.this);

                // Création d'une première requête
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la classe People
                                people = gson.fromJson(response, People.class);

                                // Modification des valeurs des items du layout
                                tvBornWhere.setText("Born the " + people.getBirthday() + " in " + people.getPlace_of_birth());
                                tvBiography.setText(people.getBiography());
                                tvName.setText(people.getName());

                                // Si nous avons bien une url pour l'image de l'acteur/actrice
                                if(people.getProfile_path() != null) {
                                    Picasso.with(PeopleActivity.this).load(String.format(Constant.URL_IMAGE, people.getProfile_path())).into(ivPhoto);
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("errors", error.toString());
                    }
                });

                // Création de la deuxième requête
                StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Gson gson = new Gson();

                                // Transformation de la réponse en format de la classe PeopleImage
                                peopleImage = gson.fromJson(response, PeopleImage.class);

                                // Ajout des images dans la liste
                                for (int i = 0 ; i < peopleImage.getProfiles().size() ; i++){
                                    images.add(new ObjectImage(String.format(Constant.URL_IMAGE_500, peopleImage.getProfiles().get(i).getFile_path())));
                                }

                                // Transformation du RecyclerVie selon la liste
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
