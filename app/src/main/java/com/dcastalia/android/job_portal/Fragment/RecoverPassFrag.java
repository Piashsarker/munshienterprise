package com.dcastalia.android.job_portal.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import static com.android.volley.VolleyLog.d;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecoverPassFrag extends Fragment {
    protected static final String TAG = "RecoverPassFrag";
    SharedPreferences sharedpreferences;

    Button btn_recover_pass;
    EditText input_email_recover_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recover_pass_layout, container, false);
       // input_email_recover_password = (EditText) view.findViewById(R.id.input_email_recover_password);

        sharedpreferences = getContext().getSharedPreferences(
                "sharedPrefName", 0);
        String id = sharedpreferences.getString("key_name","defaultvalue");

        btn_recover_pass=(Button)view.findViewById(R.id.btn_recover_pass);

        btn_recover_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String phone = input_email_recover_password.getText().toString();
                String id = sharedpreferences.getString("key_name","defaultvalue");

                dataSendToServer(id);

                Fragment fragment = new ResetPassFrag();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


    public void dataSendToServer(String id) {

//        sharedpreferences = getContext().getSharedPreferences(
//                "sharedPrefName", 0);

        String hitURL = "http://bestinbd.com/projects/web/munshi/restAPI/site/send_verification_code";

        HashMap<String, String> params = new HashMap<>();

        params.put("id", id); //Item varify code
       // params.put("phone", phone); //Items - Item 1 - phone

        VolleyCustomRequest postRequest = new VolleyCustomRequest(Request.Method.POST, hitURL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Register Response: " + response.toString());

                        try {
                            int status = response.getInt("status");
                            if (status == 1) {

                                Toast.makeText(getContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        d(TAG, "Error: " + volleyError.getMessage());

                        if (volleyError instanceof NetworkError) {
                            Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                        } else if (volleyError instanceof ServerError) {
                            Toast.makeText(getContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (volleyError instanceof AuthFailureError) {
                            Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();


                        } else if (volleyError instanceof ParseError) {
                            Toast.makeText(getContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                        } else if (volleyError instanceof NoConnectionError) {
                            Toast.makeText(getContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();

                        } else if (volleyError instanceof TimeoutError) {
                            Toast.makeText(getContext(), "Connection TimeOut! Please check your internet connection", Toast.LENGTH_SHORT).show();

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

}
