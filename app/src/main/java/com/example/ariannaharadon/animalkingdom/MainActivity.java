package com.example.ariannaharadon.animalkingdom;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends TitleActivity {
    //Intent started from Title Activity

    //Establish variables for navigation drawer and SoundPool variables
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mAnimalTitles;

    private SoundPool mySounds;
    private int soundDog, soundCat, soundHorse, soundBird;
    boolean plays = false;
    boolean loaded = false;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //New instance of AudioManager is created to control volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //Establish volume variables
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        //Create SoundPool object to manage audio
        mySounds = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        //Listens for when loading of sound is complete, returns true when sound is completely loaded
        mySounds.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool mySounds, int sampleId, int status) {
                loaded = true;
            }

        });
        //Load sounds with context, resourceId, and priority parameters
        soundDog = mySounds.load(this, R.raw.dog, 1); //1
        soundCat = mySounds.load(this, R.raw.cat, 1); //2
        soundHorse = mySounds.load(this, R.raw.horse, 1); //3
        soundBird = mySounds.load(this, R.raw.bird, 1); //4

        //Establish navigation drawer's main title and selection titles using a string array
        mTitle = mDrawerTitle = getTitle();
        mAnimalTitles = getResources().getStringArray(R.array.dogs_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        //Establish shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        //Set up the drawer's list view with animal items and clicklistener
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mAnimalTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        //Enable ActionBar app icon to toggle nav drawer
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        if (getActionBar() != null)
            getActionBar().setHomeButtonEnabled(true);

        //ActionBarDrawerToggle ties together DrawerLayout and ActionBar
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); //calls onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    //Inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Action bar up icon open or closes the drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;
    }

    //Click listener determines which sound to play, based on what is clicked in the nav drawer
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            if (loaded && !plays) {
                if (position == 0) {
                    mySounds.play(soundCat, 1, 1, 1, 0, 1f);
                }else if(position  == 1){
                    mySounds.play(soundHorse, 1, 1, 1, 0, 1f);
                }else if(position  == 2){
                    mySounds.play(soundBird, 1, 1, 1, 0, 1f);
                }else if (position  == 3){
                    mySounds.play(soundDog, 1, 1, 1, 0, 1f);
                }
            }
        }
    }

    //Selects fragment (which animal) to display
    private void selectItem(int position) {
        //Updates the main content by replacing fragments
        Fragment fragment = new AnimalFragment();
        Bundle args = new Bundle();
        args.putInt(AnimalFragment.ARG_DOG_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        //Update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mAnimalTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }


    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        if (getActionBar() != null)
            getActionBar().setTitle(mTitle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync toggle state after onRestoreInstanceState
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass configuration change to the drawer toggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}