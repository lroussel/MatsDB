package com.matsdb.loicr.moviedb.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.matsdb.loicr.moviedb.R;
import com.matsdb.loicr.moviedb.ui.fragment.PeopleMovieFragment;
import com.matsdb.loicr.moviedb.ui.fragment.PeopleTVFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant l'affichage des fragments pour les crédits d'un people
 */
public class PeopleCreditsActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_credits);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filmography");

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(mSectionsPagerAdapter);

        setViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_people_credits, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    public void setViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Ajout des fragments
        adapter.addFragment(new PeopleMovieFragment(), "Film");
        adapter.addFragment(new PeopleTVFragment(), "TV Shows");
        viewPager.setAdapter(adapter);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        // Liste des fragments et de leurs titre
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        /**
         * Constructeur
         * @param fm
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Retourne un fragment en fonction de la position
         * @param position
         * @return
         */
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        /**
         * Retourne le nombre de fragments dans la liste
         * @return
         */
        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * Ajoute un fragment dans la liste
         * @param fragment
         * @param title
         */
        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        /**
         * Retourne le titre d'un fragment en fonction de la position
         * @param position
         * @return
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
