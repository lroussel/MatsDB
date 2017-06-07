package com.matsdb.loicr.moviedb.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.utils.Constant;
import com.squareup.picasso.Picasso;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView ivFullscreen;
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivFullscreen = (ImageView) findViewById(R.id.image_fullscreen);

        if (getIntent().getExtras() != null){
            urlImage = getIntent().getExtras().getString(Constant.INTENT_URL_IMAGE_FULLSCREEN);

            Picasso.with(ImageViewActivity.this).load(urlImage).into(ivFullscreen);
        }
    }

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
