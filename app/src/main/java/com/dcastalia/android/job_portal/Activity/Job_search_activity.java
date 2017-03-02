package com.dcastalia.android.job_portal.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.dcastalia.android.job_portal.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nusrat-pc on 12/18/16.
 */
public class Job_search_activity extends AppCompatActivity {
    Button btn_search_now;
    Context context;
    public static boolean isToastShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //For Full Screen view----------------------
        requestFullScreenWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_search_layout);
        toolbarSetup();
        // Spinner element
        Spinner spinner_location = (Spinner) findViewById(R.id.spinner_location);
        Spinner spinner_salary = (Spinner) findViewById(R.id.spinner_salary);
        // Spinner click listener
        // Spinner Drop down elements

        List<String> location = new ArrayList<String>();
        location.add("Dhaka");
        location.add("Chittagong");
        location.add("Rajshahi");
        location.add("Dinajpur");

        List<String> salary = new ArrayList<String>();
        salary.add("Negotiable");
        salary.add("40-50");
        salary.add("30-40");
        salary.add("20-30");


        // Creating adapter for spinner_salary
        ArrayAdapter<String> salaryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, salary);

        // Drop down layout style
        salaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_salary.setAdapter(salaryAdapter);


        btn_search_now=(Button)findViewById(R.id.btn_search_now);

        btn_search_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Job_search_activity.this,Job_details_activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_in, R.anim.left_out);

            }
        });



    }

    private void toolbarSetup() {
        // Show the Actionbar in the activity
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Job Opening");
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
        Intent intent = new Intent(Job_search_activity.this, JobOpening_activity.class);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        startActivity(intent);


    }
}
