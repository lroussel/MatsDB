package com.matsdb.loicr.moviedb.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.utils.Constant;

/**
 * L'activité principale pour la gestion de la recherche
 */
public class MainActivity extends AppCompatActivity {

    // Déclaration des items du Layout
    private EditText etSearch;
    private Button btSearch;
    private Spinner spTypeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation des items du Layout
        etSearch = (EditText) findViewById(R.id.editText_search);
        btSearch = (Button) findViewById(R.id.button_search);
        spTypeSearch = (Spinner) findViewById(R.id.spinner_typeSearch);

        // Création de la liste déroulante du type de recherche avec un tableau de string
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_search, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeSearch.setAdapter(adapter);

        // Gestion de l'appui sur le bouton de recherche du clavier
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    // Création de l'intent pour afficher la recherche
                    Intent it_search = new Intent(MainActivity.this, SearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.INTENT_SEARCH, etSearch.getText().toString());
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                    // Tri en fonction du choix de la liste déroulante / choix du type de recherche
                    if(spTypeSearch.getSelectedItem().equals("Movie")){
                        bundle.putString(Constant.INTENT_TYPE_SEARCH, "movie");
                    }else if (spTypeSearch.getSelectedItem().equals("TV Show")){
                        bundle.putString(Constant.INTENT_TYPE_SEARCH, "tv");
                    }else if (spTypeSearch.getSelectedItem().equals("Actor")){
                        bundle.putString(Constant.INTENT_TYPE_SEARCH, "person");
                    }

                    it_search.putExtras(bundle);
                    startActivity(it_search);

                    handled = true;
                }

                return handled;
            }
        });

        // Gestion de l'appui du bouton de recherche
        btSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Création de l'intent pour afficher la recherche
                Intent it_search = new Intent(MainActivity.this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTENT_SEARCH, etSearch.getText().toString());
                bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

                // Tri en fonction du choix de la liste déroulante / choix du type de recherche
                if(spTypeSearch.getSelectedItem().equals("Movie")){
                    bundle.putString(Constant.INTENT_TYPE_SEARCH, "movie");
                }else if (spTypeSearch.getSelectedItem().equals("TV Show")){
                    bundle.putString(Constant.INTENT_TYPE_SEARCH, "tv");
                }else if (spTypeSearch.getSelectedItem().equals("Actor")){
                    bundle.putString(Constant.INTENT_TYPE_SEARCH, "person");
                }

                it_search.putExtras(bundle);
                startActivity(it_search);
            }
        });

    }
}
