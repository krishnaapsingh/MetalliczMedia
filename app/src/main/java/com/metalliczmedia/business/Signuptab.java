package com.metalliczmedia.business;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.Map;

import connection.ConnectionProvider;

import static com.metalliczmedia.business.Logintab.latitude;
import static com.metalliczmedia.business.Logintab.longitude;


/**
 * A simple {@link Fragment} subclass.
 */
public class Signuptab extends Fragment {


    public Signuptab() {
        // Required empty public constructor
    }

    EditText name, phone, username, password;
    Button signup;
    Dialog d;
    private static final int MAX_TIMEOUT_MS = 30000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_signuptab, container, false);
        username = (EditText) rootView.findViewById(R.id.user);
        password = (EditText) rootView.findViewById(R.id.pass);
        name = (EditText) rootView.findViewById(R.id.name);
        phone = (EditText) rootView.findViewById(R.id.phone);
        signup = (Button) rootView.findViewById(R.id.signup);
        d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.custom_dialog3);
        d.setCancelable(false);
        WebView wv = (WebView) d.findViewById(R.id.dialogwebview1);
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.loadUrl("file:///android_asset/demo.html");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup.setClickable(false);
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String names = name.getText().toString();
                String ph = phone.getText().toString();
                d.show();
                if (user.equalsIgnoreCase("") || pass.equalsIgnoreCase("") || names.equalsIgnoreCase("") || ph.equalsIgnoreCase("")) {
                    d.hide();
                    signup.setClickable(true);
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter all the values", Toast.LENGTH_LONG).show();
                } else if (ph.trim().length() < 10 || ph.trim().length() > 10) {
                    d.hide();
                    signup.setClickable(true);
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter correct phone number", Toast.LENGTH_LONG).show();

                } else {

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Map<String, String> params = new Hashtable<String, String>();
                                params.put("username", user);
                                params.put("name", names);
                                params.put("phone", ph);
                                params.put("password", pass);

                                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                                CustomRequest3 customRequest1 = new CustomRequest3(Request.Method.POST, UtilsApp.REGISTER_URL,
                                        params, this.createImageSuccessListner3(), this.createImageErrorListner3());
                                customRequest1.setRetryPolicy(new DefaultRetryPolicy(MAX_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                customRequest1.setShouldCache(false);
                                requestQueue.add(customRequest1);

                            } catch (Exception e) {
                                Log.e("Signup Error", e.toString());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        d.hide();
                                        signup.setClickable(true);
                                    }
                                });
                            }
                        }

                        private Response.Listener<JSONObject> createImageSuccessListner3() {
                            return new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    d.dismiss();
                                    try {
                                        int statuscode = response.getJSONObject("status").getInt("statusCode");
                                        if (statuscode == 409) {
                                            Toast.makeText(getContext(), "username exist", Toast.LENGTH_LONG).show();
                                            System.out.println("record updated");
                                        }
                                        else if(statuscode==201)
                                        {
                                            Toast.makeText(getContext(),"signup proess",Toast.LENGTH_LONG).show();
                                            Intent in = new Intent(getContext(), BeforeLogin.class);
                                            //in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);
                                            getActivity().finish();


                                        }


                                    } catch (JSONException ex) {
                                    }

                                }
                            };
                        }

                        private Response.ErrorListener createImageErrorListner3() {
                            return new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    d.dismiss();
                                    Log.d("SEARCH GARAGE", "Error " + volleyError.getMessage());
                                    Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            };
                        }



                    });
                    t.start();
                }
            }
        });

        return rootView;
    }






}




