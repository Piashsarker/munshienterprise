package com.dcastalia.android.job_portal.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.dcastalia.android.job_portal.Controller.AppController;
import com.dcastalia.android.job_portal.R;
import com.dcastalia.android.job_portal.Util.VolleyCustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by imtiyaj-pc on 12/18/16.
 */
public class Job_details_activity extends AppCompatActivity{
    int pos = -1;

    Button btn_apply;
    Button btn_like;
    String jobId, title , position , coutry,company ,vacancy, experince, salary , expireDate , age , gender , jobNature,jobDescription,jobRequirement ;
    TextView tvJobTitle , tvJobPosition , tvJobLocation, tvCompany , tvVacancy, tvExperince, tvSalary , tvExpireDate , tvAge , tvGender , tvJobNature, tvJobDescription,tvJobRequirement;
    public static boolean isToastShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestFullScreenWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_details_layout);
        setupToolbar();
        btn_apply=(Button)findViewById(R.id.btn_apply);

        btn_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                postApplyToServer();

            }
        });

        btn_like=(Button)findViewById(R.id.btn_like);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"Added to the favourite list!",Toast.LENGTH_LONG).show();

            }
        });


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                jobId = null;
                title= null;
                position = null;
                coutry = null;
                company = null;
                vacancy = null ;
                experince = null ;
                salary = null;
                expireDate = null ;
                age = null ;
                gender = null ;
                jobNature = null;
                jobDescription = null;
                jobRequirement = null;
            } else {
                jobId = extras.getString("job_id");
                title= extras.getString("job_title");
                position = extras.getString("job_position");
                coutry = extras.getString("country");
                vacancy = extras.getString("vacancy");
                company = extras.getString("company");
                experince = extras.getString("experience");
                salary = extras.getString("salary");
                expireDate = extras.getString("expire_date");
                age = extras.getString("age");
                gender = extras.getString("gender");
                jobNature = extras.getString("job_nature");
                jobDescription = extras.getString("job_description");
                jobRequirement = extras.getString("job_requirement");

                setView(company, title, position,coutry,vacancy,experince,salary,expireDate,age,gender,jobNature,jobDescription,jobRequirement);
            }
        } else {
           title= (String) savedInstanceState.getSerializable("job_title");
           position= (String) savedInstanceState.getSerializable("job_position");
           coutry= (String) savedInstanceState.getSerializable("country");

        }




    }

    private void postApplyToServer() {
        SharedPreferences shared = getSharedPreferences("sharedPrefName", MODE_PRIVATE);
        String personId = (shared.getString("key_name", null));
        String hitURL = "http://bestinbd.com/projects/web/munshi/restAPI/site/apply_on_job";
        HashMap<String, String> params = new HashMap<>();
        params.put("job_id", jobId ); //Items - Item 1 - name
        params.put("person_id", personId); //Items - passport



        VolleyCustomRequest postRequest = new VolleyCustomRequest(Request.Method.POST, hitURL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            int status = response.getInt("status");
                            if (status == 1) {

                                Toast.makeText(Job_details_activity.this, "Apply Successfull", Toast.LENGTH_SHORT).show();


                            }
                            if (status==0){
                                Toast.makeText(Job_details_activity.this, "Can Not Apply Right Now", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        if (volleyError instanceof NetworkError) {
                            Toast.makeText(Job_details_activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ServerError) {
                            Toast.makeText(Job_details_activity.this, "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (volleyError instanceof AuthFailureError) {
                            Toast.makeText(Job_details_activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();


                        } else if (volleyError instanceof ParseError) {
                            Toast.makeText(Job_details_activity.this, "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(Job_details_activity.this, "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();

                        } else if (volleyError instanceof TimeoutError) {
                            Toast.makeText(Job_details_activity.this, "Connection TimeOut! Please check your internet connection", Toast.LENGTH_SHORT).show();

                        }


                    }
                });


        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(postRequest);

    }

    private void setView(String company, String title, String position, String coutry, String vacancy, String experince, String salary, String expireDate, String age, String gender, String jobNature, String jobDescription, String jobRequirement) {
        tvJobTitle = (TextView) findViewById(R.id.txt_title);
        tvJobPosition = (TextView) findViewById(R.id.txt_job_position);
        tvJobLocation = (TextView) findViewById(R.id.txt_job_location);
        tvCompany = (TextView) findViewById(R.id.txt_company_name);
        tvVacancy = (TextView) findViewById(R.id.txt_job_vacancy);
        tvExperince = (TextView) findViewById(R.id.txt_experience);
        tvSalary = (TextView) findViewById(R.id.txt_salary);
        tvExpireDate = (TextView) findViewById(R.id.txt_expire_date);
        tvAge = (TextView) findViewById(R.id.txt_age);
        tvGender = (TextView) findViewById(R.id.txt_gender);
        tvJobNature = (TextView) findViewById(R.id.txt_job_nature);
        tvJobDescription = (TextView) findViewById(R.id.txt_job_description);
        tvJobRequirement = (TextView) findViewById(R.id.txt_job_requirement);

        tvJobTitle.setText(title);
        tvJobPosition.setText(position);
        tvJobLocation.setText(coutry);
        tvCompany.setText(company);
        tvVacancy.setText(vacancy);
        tvExperince.setText(experince);
        tvSalary.setText(salary);
        tvAge.setText(age);
        tvGender.setText(gender);
        tvJobNature.setText(jobNature);
        tvJobDescription.setText(jobDescription);
        tvJobRequirement.setText(jobRequirement);
    }

    private void setupToolbar() {
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0b0b0b")));
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(true);
        setTitle("Job Details");
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
        Intent intent = new Intent(Job_details_activity.this, JobOpening_activity.class);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        startActivity(intent);


    }


}
