package com.dcastalia.android.job_portal.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.dcastalia.android.job_portal.R;

/**
 * Created by nusrat-pc on 12/29/16.
 */
public class Help_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestFullScreenWindow();
        setContentView(R.layout.help_activity_layout);
        toolbarSetup();


    }

    private void toolbarSetup() {
        // Show the Actionbar in the activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Help");
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
        Intent intent = new Intent(Help_activity.this, MainActivity.class);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        startActivity(intent);


    }
}
