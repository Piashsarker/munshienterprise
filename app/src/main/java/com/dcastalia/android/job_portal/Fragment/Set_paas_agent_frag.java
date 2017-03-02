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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.dcastalia.android.job_portal.Controller.AppController;
import com.dcastalia.android.job_portal.Model.Agent;
import com.dcastalia.android.job_portal.R;
import com.dcastalia.android.job_portal.Util.VolleyCustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.R.layout.simple_spinner_dropdown_item;
import static com.android.volley.VolleyLog.d;

/**
 * Created by nusrat-pc on 12/21/16.
 */
public class Set_paas_agent_frag extends Fragment {
    // Log tag
    protected static final String TAG = "Set_paas_agent_frag";
    SharedPreferences sharedpreferences;

    // Job opening  json url
    private static final String URL = " http://bestinbd.com/projects/web/munshi/restAPI/site/joblist";


    Button btn_reg3;
    EditText input_password;
    EditText input_confirm_password;
    EditText input_agent_id;

    private Spinner spinner;
    // array list for spinner adapter
    private ArrayList<Agent> agentList;
    ArrayAdapter<String> dataAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.set_pass_agent_layout, container, false);

        //Initializing Spinner
        spinner = (Spinner)view.findViewById(R.id.spinner_agent);

//        agentList = new ArrayList<Agent>();
//
//        // spinner item select listener
//        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getActivity());
//
//        List<String> lables = new ArrayList<String>();
//
//        for (int i = 0; i < agentList.size(); i++) {
//            lables.add(agentList.get(i).getName());
//        }
//
//         // Spinner adapter
//       spinner.setAdapter(new ArrayAdapter<String>(getActivity(),
//                      simple_spinner_dropdown_item,
//               lables));
//
//

        //or-----------------------------------------------

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");

        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        // input_agent_id = (EditText) view.findViewById(R.id.input_agent_id);
        input_password = (EditText) view.findViewById(R.id.input_password);
        input_confirm_password = (EditText) view.findViewById(R.id.input_confirm_password);

        btn_reg3=(Button)view.findViewById(R.id.btn_reg3);

        btn_reg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = input_password.getText().toString();
                String repassword = input_confirm_password.getText().toString();
                String id1 = sharedpreferences.getString("key_name","defaultvalue");

//                if (input_agent_id.getText().toString().length() == 0) {
//                    input_agent_id.setError("Agent Number Empty");
//                    input_agent_id.requestFocus();
//                }

                 if (input_password.getText().toString().length() == 0) {
                    input_password.setError("Please enter password");
                    input_password.requestFocus();
                }

               else if(input_password.getText().toString().length()<8 &&!isValidPassword(input_password.getText().toString())){
                    Toast.makeText(getContext(), "Please enter strong password", Toast.LENGTH_LONG).show();

                }


                else  if (input_confirm_password.getText().toString().length() == 0) {
                    input_confirm_password.setError("Please confirm your password");
                    input_confirm_password.requestFocus();
                }


                else if (!checkPassWordAndConfirmPassword(password, repassword)) {

                    Toast.makeText(getContext(), "Password don't match", Toast.LENGTH_LONG).show();
                }

                else {

                     dataSendToServer(id1,password);

                     Fragment fragment = new VarifyPhoneFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
                    fragmentTransaction.replace(R.id.fragment_container, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });

        return view;
    }



    // Creating volley request obj getting agent id
    JsonArrayRequest agentReq = new JsonArrayRequest(URL,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, "Json response: " + response.toString());


                    // Parsing json
                    for (int i = 0; i < response.length(); i++) {
                        try {

                            JSONObject obj = response.getJSONObject(i);

                            Agent agent = new Agent();

                            //jobs.setAvailable_job(obj.getString("total_job"));
                            //txt.setText(obj.getString("total_job"));

                            agent.setId(obj.getInt("id"));
                            agent.setName(obj.getString("name"));

                            // adding agent id  to agent array

                            agentList.add(agent);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // notifying list adapter about data changes
                    // so that it renders the list view with updated data

                    dataAdapter.notifyDataSetChanged();

                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            volleyError.printStackTrace();

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



    //Check password and repassword is write or wrong
    public boolean checkPassWordAndConfirmPassword(String password, String repassword) {
        boolean pstatus = false;
        if (repassword != null && password != null) {
            if (password.equals(repassword)) {
                pstatus = true;
            }
        }
        return pstatus;
    }

    //Valid password----------
    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

   //Sending data to the server post req
    public void dataSendToServer(String id, String password) {

        sharedpreferences = getContext().getSharedPreferences(
                "sharedPrefName", 0);

        String id1 = sharedpreferences.getString("key_name","defaultvalue");

        String hitURL = "http://bestinbd.com/projects/web/munshi/restAPI/site/register";

        HashMap<String, String> params = new HashMap<>();
        params.put("key_name", id); //Items - Item 1 - name
        params.put("password", password); //Items - Item 3 - pass


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
