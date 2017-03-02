package com.dcastalia.android.job_portal.Fragment;

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


public class SignInFragment_1 extends Fragment {
    protected static final String TAG = "SignInFragment_1";

    Button btn_forgot_pass;
    Button btn_signIn;

    EditText input_phone;
    EditText input_password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sign_in_fragment_1, container, false);

        input_phone = (EditText) view.findViewById(R.id.input_phone);
        input_password = (EditText) view.findViewById(R.id.input_password);

        btn_forgot_pass=(Button)view.findViewById(R.id.btn_forgot_pass);
        btn_signIn=(Button)view.findViewById(R.id.btn_signIn);

        btn_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            fragmentTransaction();
            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = input_phone.getText().toString();
                String password = input_password.getText().toString();
                dataSendToServer( phone, password);


            }
        });

        return view;
    }

    private void fragmentTransaction() {

        Fragment fragment = new RecoverPassFrag();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public void dataSendToServer(String phone, String password) {


        String hitURL = "http://bestinbd.com/projects/web/munshi/restAPI/site/login";

        HashMap<String, String> params = new HashMap<>();
        params.put("phone", phone); //Items - Item 1 - phone
        params.put("password", password); //Items 2-password


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
                            if(status==0){
                                Toast.makeText(getContext(), "Phone or Password Not Valid", Toast.LENGTH_SHORT).show();
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
