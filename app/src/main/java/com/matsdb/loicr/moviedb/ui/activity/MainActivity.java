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

public class MainActivity extends AppCompatActivity {

    private EditText etSearch;
    private Button btSearch;
    private Spinner spTypeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.editText_search);
        btSearch = (Button) findViewById(R.id.button_search);
        spTypeSearch = (Spinner) findViewById(R.id.spinner_typeSearch);

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    Intent it_search = new Intent(MainActivity.this, SearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.INTENT_SEARCH, etSearch.getText().toString());
                    bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type_search, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTypeSearch.setAdapter(adapter);

        btSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it_search = new Intent(MainActivity.this, SearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.INTENT_SEARCH, etSearch.getText().toString());
                bundle.putInt(Constant.INTENT_NUM_PAGE, 1);

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
