package com.dcastalia.android.job_portal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.dcastalia.android.job_portal.Fragment.Edit_profile_Frag;
import com.dcastalia.android.job_portal.Fragment.Edit_profile_Frag_camera;
import com.dcastalia.android.job_portal.Fragment.RegFragment_1;
import com.dcastalia.android.job_portal.R;

public class MainActivity extends AppCompatActivity {


    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    int flag=0;
    final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
    int item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestFullScreenWindow();

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle("Register Now");

        /**
         *Setup the DrawerLayout and NavigationView
         */

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.replace(R.id.fragment_container, new RegFragment_1()).commit();


        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                mDrawerLayout.closeDrawers();
                //  toolbar.setTitle("Munshi Enterprise");
                // if (mDrawerLayout.isDrawerVisible(toolbar));


                if (menuItem.getItemId() == R.id.nav_profile ) {

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
                    fragmentTransaction.replace(R.id.fragment_container, new Edit_profile_Frag_camera()).commit();

                     setTitle("My Profile");


                }

                if (menuItem.getItemId() == R.id.nav_profile ){
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
                    fragmentTransaction.replace(R.id.fragment_container, new Edit_profile_Frag()).commit();

                    setTitle("My Profile");
                }

                if (menuItem.getItemId() == R.id.nav_job_opening) {

                    Intent intent = new Intent(MainActivity.this, JobOpening_activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);


                }

                if (menuItem.getItemId() == R.id.nav_myjobs) {
                    Intent intent = new Intent(MainActivity.this, Myjob_activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }

                if (menuItem.getItemId() == R.id.nav_fav_job) {
                    Intent intent = new Intent(MainActivity.this, Favourite_activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }

                if (menuItem.getItemId() == R.id.nav_help) {
                    Intent intent = new Intent(MainActivity.this, Help_activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }
                if (menuItem.getItemId() == R.id.nav_setting) {
                    Intent intent = new Intent(MainActivity.this, Setting_activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                }


                return false;
            }

        });




        /**
         * Setup Drawer Toggle of the Toolbar
         */

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);


        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
    private void requestFullScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent.getStringExtra("now") != null) {
            switch (intent.getStringExtra("now")) {
                case "details":
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RegFragment_1()).commit();
                    break;
            }
        }

    }
}


