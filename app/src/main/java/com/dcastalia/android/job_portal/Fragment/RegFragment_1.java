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
import android.widget.RadioButton;
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
import com.dcastalia.android.job_portal.SqliteDatabase.SQLiteHandler;
import com.dcastalia.android.job_portal.Util.VolleyCustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.android.volley.VolleyLog.d;


public class RegFragment_1 extends Fragment implements View.OnClickListener {
    protected static final String TAG = "RegFragment_1";

    Button btn_reg1;
    String catagory;
    EditText input_userName;
    EditText input_passport_nubmer;
    EditText input_phone;
    SharedPreferences sharedpreferences;
    private SQLiteHandler db;
    private String name , passport, phone;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.reg_fragment_1, container, false);

        btn_reg1 = (Button) view.findViewById(R.id.btn_reg1);

        db = new SQLiteHandler(getContext());


        input_userName = (EditText) view.findViewById(R.id.input_userName);
        input_passport_nubmer = (EditText) view.findViewById(R.id.input_passport_nubmer);
        input_phone = (EditText) view.findViewById(R.id.input_phone);



        btn_reg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = input_userName.getText().toString();
                passport = input_passport_nubmer.getText().toString();
                phone = input_phone.getText().toString();

                if (input_userName.getText().toString().length() == 0) {
                    input_userName.setError("Please enter your name");
                    input_userName.requestFocus();
                }
                else if (input_passport_nubmer.getText().toString().length() == 0) {
                    input_passport_nubmer.setError("Please enter your passport number");
                    input_passport_nubmer.requestFocus();
                }

                else   if (input_passport_nubmer.length()!=9)
                {
                    Toast.makeText(getContext(), "Invalid passport number!", Toast.LENGTH_LONG).show();

                }
                else  if (input_phone.getText().toString().length()!=11) {
                    input_phone.setError("Please enter your phone number");
                    input_phone.requestFocus();
                }

                else if (!isValidPhone(phone)) {
                    Toast.makeText(getContext(), "Phone number not valid!", Toast.LENGTH_LONG).show();

                }
                else if (catagory==null) {
                    Toast.makeText(getContext(), "Select Catagory!", Toast.LENGTH_LONG).show();

                }

                else {

                  //  dataSendToServer(name,passport,phone,catagory);
                    Toast.makeText(getContext(), "Registration success!", Toast.LENGTH_LONG).show();
                    radioClick(v);

                }

            }
        });

        /*
    radio button on click event listener
     */
        view.findViewById(R.id.radio_individual_bt).setOnClickListener(this);
        view.findViewById(R.id.radio_agent_bt).setOnClickListener(this);

        return view;
    }


    public void radioClick(View v) {
        boolean checked = ((RadioButton)v).isChecked();

        switch (v.getId()) {
            case R.id.radio_individual_bt:
                if (checked) {

                    btn_reg1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            name = input_userName.getText().toString();
                            passport = input_passport_nubmer.getText().toString();
                            phone = input_phone.getText().toString();

                            if (input_userName.getText().toString().length() == 0) {
                                input_userName.setError("Please enter your name");
                                input_userName.requestFocus();
                            }
                            else if (input_passport_nubmer.getText().toString().length() == 0) {
                                input_passport_nubmer.setError("Please enter your passport number");
                                input_passport_nubmer.requestFocus();
                            }

                            else   if (input_passport_nubmer.length()!=9)
                            {
                                Toast.makeText(getContext(), "Invalid passport number!", Toast.LENGTH_LONG).show();

                            }
                            else  if (input_phone.getText().toString().length() == 0) {
                                input_phone.setError("Please enter your phone number");
                                input_phone.requestFocus();
                            }

                            else if (!isValidPhone(phone)) {
                                Toast.makeText(getContext(), "Phone number not valid!", Toast.LENGTH_LONG).show();

                            }

                            else {

                                dataSendToServer(name,passport,phone,catagory);

                                long userID = db.addUser(name,phone);

                                if (userID > 0) {
                                    Toast.makeText(getContext(),
                                            "Registration Completed" + "\n" + "Your id is: " + userID, Toast.LENGTH_LONG)
                                            .show();
                                }


                            }


                        }
                    });
                    catagory = " ";
                }
                else {
                    Toast.makeText(getContext(), "Select catagory!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.radio_agent_bt:
                if (checked) {
                    btn_reg1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String name = input_userName.getText().toString();
                            String passport = input_passport_nubmer.getText().toString();
                            String phone = input_phone.getText().toString();

                            if (input_userName.getText().toString().length() == 0) {
                                input_userName.setError("Please enter your name");
                                input_userName.requestFocus();
                            }
                            else if (input_passport_nubmer.getText().toString().length() == 0) {
                                input_passport_nubmer.setError("Please enter your passport number");
                                input_passport_nubmer.requestFocus();
                            }

                            else   if (input_passport_nubmer.length()!=9)
                            {
                                Toast.makeText(getContext(), "Invalid passport number!", Toast.LENGTH_LONG).show();

                            }
                            else  if (input_phone.getText().toString().length() == 0) {
                                input_phone.setError("Please enter your phone number");
                                input_phone.requestFocus();
                            }

                            else if (!isValidPhone(phone)) {
                                Toast.makeText(getContext(), "Phone number not valid!", Toast.LENGTH_LONG).show();

                            }

                            else {

                                dataSendToServer(name,passport,phone,catagory);
                                long userID = db.addUser(name,phone);

                                if (userID > 0) {
                                    Toast.makeText(getContext(),
                                            "Registration Completed" + "\n" + "Your id is: " + userID, Toast.LENGTH_LONG)
                                            .show();
                                }
                                fragmentTransaction();
                            }
                        }
                    });

                    catagory = "agent";
                }

                break;
            default:
                break;
        }
    }

    public void fragmentTransaction(){
        Fragment fragment = new Set_pass_individual_frag();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
    radio button on click method
     */
    @Override
    public void onClick(View v) {
        radioClick(v);
    }

//    //Validate phone number--
//    private boolean isValidMobile(String input_phone) {
//        return android.util.Patterns.PHONE.matcher(input_phone).matches();
//    }

    public static boolean isValidPhone(String phone)
    {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    public void dataSendToServer(String name, String passport, String phone,String catagory) {

        String hitURL = "http://bestinbd.com/projects/web/munshi/restAPI/site/register";

        HashMap<String, String> params = new HashMap<>();
        params.put("name", name); //Items - Item 1 - name
        params.put("passport", passport); //Items - passport
        params.put("phone", phone); //Items - Item 3 - phone
        params.put("catagory", catagory); //Items - Item 3 - catagory


        VolleyCustomRequest postRequest = new VolleyCustomRequest(Request.Method.POST, hitURL, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Register Response: " + response.toString());

                        try {
                            int status = response.getInt("status");
                            if (status == 1) {

                                String id=response.getString("id");

                                sharedpreferences  = getContext().getSharedPreferences(
                                        "sharedPrefName", 0); // 0 for private mode

                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("key_name",id); // key_name is the name through which you can retrieve it later.
                                editor.commit();

                                Toast.makeText(getContext(),
                                        response.getString("message"), Toast.LENGTH_SHORT).show();
                                fragmentTransaction();
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