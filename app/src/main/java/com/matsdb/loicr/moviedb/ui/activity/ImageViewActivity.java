package com.matsdb.loicr.moviedb.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

/**
 * Classe pour l'affichage d'une image en plein écran
 */
public class ImageViewActivity extends AppCompatActivity {

    // Déclaration des items du layout
    private ImageView ivFullscreen;

    // Déclaration de l'url de l'image
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialisation de l'item du layout
        ivFullscreen = (ImageView) findViewById(R.id.image_fullscreen);

        // Si nous avons bien un intent
        if (getIntent().getExtras() != null){
            // Initialisation de l'url de l'image
            urlImage = getIntent().getExtras().getString(Constant.INTENT_URL_IMAGE_FULLSCREEN);

            // Insertion de l'image dans l'ImageView du Layout
            Picasso.with(ImageViewActivity.this).load(urlImage).into(ivFullscreen);
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
