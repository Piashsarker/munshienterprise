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

/**
 * Created by shahimtiyaj-pc on 12/15/16.
 */
public class ResetPassFrag extends Fragment {
    protected static final String TAG = "ResetPassFrag";

    Button btn_resend_reset_code;
    Button btn_change_pass_confirm;


    EditText input_reset_code;
    EditText input_new_password;
    EditText input_confirm_password;
    EditText input_phone;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.reset_pass_layout, container, false);

        input_reset_code = (EditText) view.findViewById(R.id.input_reset_code);
        input_new_password = (EditText) view.findViewById(R.id.input_new_password);
        input_confirm_password = (EditText) view.findViewById(R.id.input_confirm_password);
        input_phone = (EditText) view.findViewById(R.id.phone_no);

        btn_resend_reset_code=(Button)view.findViewById(R.id.btn_resend_reset_code);
        btn_change_pass_confirm=(Button)view.findViewById(R.id.btn_change_pass_confirm);


        btn_resend_reset_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fragmentTransaction();
                Toast.makeText(getContext(),"Send Request for reset code",Toast.LENGTH_LONG).show();
            }
        });

        btn_change_pass_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = input_reset_code.getText().toString();
                String password = input_new_password.getText().toString();
                String phone = input_phone.getText().toString();

                dataSendToServer(code, password,phone);


            }
        });


        return view;
    }

    public void fragmentTransaction(){
        Fragment fragment = new RecoverPassFrag();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void dataSendToServer(String code, String password,String phone) {


        String hitURL = "http://bestinbd.com/projects/web/munshi/restAPI/site/reset_password";

        HashMap<String, String> params = new HashMap<>();
        params.put("code", code); //Items - Item 2  new password
        params.put("password", password); //Items - Item 2  new password
        params.put("phone", phone); //Items - Item 1 - resetcode




        VolleyCustomRequest postRequest = new VolleyCustomRequest(Request.Method.POST, hitURL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Register Response password change: " + response.toString());

                        try {
                            int status = response.getInt("status");
                            if (status == 1) {


                                Toast.makeText(getContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getContext(),"Your password has been changed",Toast.LENGTH_LONG).show();
                                jumpToLoginFragment();
                            }
                            if(status==0){
                                Toast.makeText(getContext(), "Error ! Password Not Changed", Toast.LENGTH_SHORT).show();
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

    private void jumpToLoginFragment() {
        Fragment fragment = new SignInFragment_1();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.right_in, R.anim.right_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
