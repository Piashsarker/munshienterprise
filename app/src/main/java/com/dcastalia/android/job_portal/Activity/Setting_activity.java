package com.dcastalia.android.job_portal.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.dcastalia.android.job_portal.R;

/**
 * Created by imtiyaj-pc on 12/29/16.
 */
public class Setting_activity extends AppCompatActivity {
    int pos = -1;

    Context context;
        public static boolean isToastShown = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

           requestFullScreenWindow();
        // Show the Actionbar in the activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Setting");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting_activity_layout);



    }
    private void requestFullScreenWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


        @Override
        public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


        @Override
        public void onBackPressed() {
        super.onBackPressed();
        super.finish();
        Intent intent = new Intent(Setting_activity.this, MainActivity.class);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        startActivity(intent);

    }
    }
