package com.metalliczmedia.business;


import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import connection.ConnectionProvider;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class Logintab extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static String loggedin;
  static  String latitude;
  static  String longitude;
    SharedPreferences sharedpreferences;
    Connection con;
    public static String loginuser;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Lat = "LAT";

    public static final String Longitude = "LNG";

    public Logintab() {
        // Required empty public constructor
    }

    EditText username, password;
    Button signin;
    Dialog d, d1;
    TextView forgot;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private PendingResult<LocationSettingsResult> result;
    private LocationSettingsRequest.Builder builder;
    public static Location mLastLocation;
    private final int REQUEST_LOCATION = 200;
    private final int REQUEST_CHECK_SETTINGS = 300;
    ProgressDialog pDialog;
    ProgressDialog mDialog;
    private static final int MAX_TIMEOUT_MS = 30000;
    String user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_logintab, container, false);
        username = (EditText) rootView.findViewById(R.id.usernamet);
        password = (EditText) rootView.findViewById(R.id.passwordt);
        forgot = (TextView) rootView.findViewById(R.id.forgotpass);
        d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mDialog = new ProgressDialog(getActivity());
        mDialog.setMessage("Please wait...");
        mDialog.setCanceledOnTouchOutside(false);
       /* pDialog  = new ProgressDialog(getActivity());
        pDialog.setMessage("Fetching...");*/

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.custom_dialog3);
        d.setCancelable(false);
        WebView wv = (WebView) d.findViewById(R.id.dialogwebview1);
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.loadUrl("file:///android_asset/demo.html");
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d1 = new Dialog(getActivity());
                d1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d1.setContentView(R.layout.dialogpassword);

                EditText user = (EditText) d1.findViewById(R.id.forgotuser);
                EditText phone = (EditText) d1.findViewById(R.id.forgotphone);
                Button b = (Button) d1.findViewById(R.id.forgot);

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String u = user.getText().toString();
                        String ph = phone.getText().toString();
                        if (u.equalsIgnoreCase("") || ph.equalsIgnoreCase("")) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "Please enter the fields", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    d.show();
                                }
                            });
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        con = ConnectionProvider.getCon();
                                        Statement st = con.createStatement();
                                        ResultSet rs = st.executeQuery("select * from login where username='" + u + "' and phone='" + ph + "' and auth='y'");
                                        if (rs.next()) {
                                            String msg = "Your%20Account%20Details%20are%20:%20Name%20:%20" + rs.getString(1) + "%20Username%20:%20" + rs.getString(2) + "%20Password%20:%20" + rs.getString(3);
                                            String s = "https://api.clicksend.com/http/v2/send.php?method=http&username=cooltoon007&key=963E21F5-6C5B-594C-EC5D-AAE1A4094DF0&to=+91" + rs.getString(4) + "&message=" + msg;
                                            try {

                                                HttpClient client = new DefaultHttpClient();
                                                HttpParams httpParameters = client.getParams();
                                                HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
                                                HttpConnectionParams.setSoTimeout(httpParameters, 5000);
                                                HttpConnectionParams.setTcpNoDelay(httpParameters, true);
                                                HttpGet request1 = new HttpGet();
                                                request1.setURI(new URI(s));
                                                HttpResponse response1 = client.execute(request1);


                                                Intent in = new Intent(getActivity().getApplicationContext(), BeforeLogin.class);
                                               // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                getActivity().startActivity(in);
                                               // getActivity().finish();
                                               // saveDataOnServer();
                                            } catch (Exception e) {
                                                Log.e("MSG Not Sent", e.toString());
                                                getActivity().runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        d.hide();
                                                    }
                                                });
                                            }


                                        } else {
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(getContext().getApplicationContext(), "No such account exists", Toast.LENGTH_LONG).show();
                                                    d.hide();
                                                }
                                            });
                                        }
                                    } catch (Exception e) {
                                        Log.e("TEJJJ", e.toString());
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                d.hide();
                                            }
                                        });
                                    }
                                }
                            });
                            t.start();
                        }
                    }
                });

                d1.show();
            }

        });
        signin = (Button) rootView.findViewById(R.id.signint);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check() == 1) {
                    mDialog.show();
                    LoginUser();
                }
               /* signin.setClickable(false);
                String user = username.getText().toString();
                String pass = password.getText().toString();
                d.show();*/
              /*  Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Connection con = ConnectionProvider.getCon();
                            PreparedStatement ps = con.prepareStatement("select auth,type from login where username=? and pass=?");
                            ps.setString(1, user);
                            ps.setString(2, pass);
                            ResultSet rs = ps.executeQuery();
                            if (rs.next()) {

                                if (rs.getString(1).equalsIgnoreCase("y")) {
                                    saveDataOnServer(user,con,"yes");
                                    if (rs.getString(2).equalsIgnoreCase("client")) {
                                        if (user.equalsIgnoreCase("oppo")) {
                                            Intent in = new Intent(getActivity().getApplicationContext(), OppoHome.class);
                                            ClientHome.Client = user;
                                           // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);
                                            //getActivity().finish();
                                        } else {
                                            Intent in = new Intent(getActivity().getApplicationContext(), ClientHome.class);
                                            ClientHome.Client = user;
                                           // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(in);
                                           // getActivity().finish();
                                        }

                                    } else {
                                        Intent in = new Intent(getActivity().getApplicationContext(), Clients.class);
                                        loggedin = user;
                                      //  in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(in);
                                      //  getActivity().finish();
                                    }
                                } else {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            d.hide();
                                            Toast.makeText(getActivity().getApplicationContext(), "Your account is currently under authentication process. Please try after some time !", Toast.LENGTH_LONG).show();
                                            signin.setClickable(true);
                                        }
                                    });
                                }
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        d.hide();
                                        Toast.makeText(getActivity().getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                                        signin.setClickable(true);
                                    }
                                });
                            }
                        } catch (Exception e) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    d.hide();
                                    Toast.makeText(getActivity().getApplicationContext(), "Unable to connect to the server !", Toast.LENGTH_LONG).show();
                                    signin.setClickable(true);
                                }
                            });
                        }

                    }
                });
                t.start();*/
            }
        });

        return rootView;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
      //  saveDataOnServer(ClientHome.Client,con,"no");
    }

   /* @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = createLocationRequest();
        builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates mState = result.getLocationSettingsStates();
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        } else {

                            if (mLastLocation != null) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                latitude=""+mLastLocation.getLatitude();
                                longitude=""+ mLastLocation.getLongitude();

                                editor.putLong("Latitude", Double.doubleToLongBits(mLastLocation.getLatitude()));
                                editor.putLong("Longitude", Double.doubleToLongBits(mLastLocation.getLongitude()));

//                                editor.putString(Lat, String.valueOf(mLastLocation.getLatitude()));
//                                editor.putString(Longitude,  String.valueOf(mLastLocation.getLongitude()));

                                editor.commit();

                          //      Toast.makeText(getActivity(), "lat" + mLastLocation.getLatitude() + "lng" + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();


//                                Thread t = new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        try {
//                                            Connection con = ConnectionProvider.getCon();
//                                            String sqlUpdate = "UPDATE login SET latitude = ? , longitude=? WHERE username = ?";
//                                            PreparedStatement statement1 = con.prepareStatement(sqlUpdate);
//                                            statement1.setString(1, latitude);
//                                            statement1.setString(2, longitude);
//                                            statement1.setString(3, "luv");
//                                            int i = statement1.executeUpdate();
//                                            if (i != 0) {
//
//                                                Toast.makeText(getContext(),"Record Updated",Toast.LENGTH_LONG).show();
//                                                System.out.println("record updated");
//                                               *//* json.put("code", "200");
//                                                json.put("message", "Latitude " + latitude + " longitude " + longitude + " is updated for user "
//                                                        + username);
//                                                PrintWriter pw = response.getWriter();
//                                                pw.print(json.toString());
//                                                pw.close();
//                                                *//*
//                                            }
//                                        } catch (Exception e) {
//                                            getActivity().runOnUiThread(new Runnable() {
//                                                @Override
//                                                public void run() {
//                                                    d.hide();
//                                                    Toast.makeText(getActivity().getApplicationContext(), "Unable to connect to the server !", Toast.LENGTH_LONG).show();
//                                                    signin.setClickable(true);
//                                                }
//                                            });
//                                        }
//                                    }
//
//                                });
//
//
//
//                                t.start();

//                                latitudePosition.setText(String.valueOf(mLastLocation.getLatitude()));
//                                longitudePosition.setText(String.valueOf(mLastLocation.getLongitude()));
                                // getAddressFromLocation(mLastLocation, getApplicationContext(), new DealerDetails.GeoCoderHandler());
                            }
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }*/


//    public void setDataOnServer() {
//
//        Map<String, String> params = new Hashtable<String, String>();
//
//        params.put("user_id","");
//        params.put("lat","");
//        params.put("lng","");
//
//
//        RequestQueue requestQueueSpinner = Volley
//                .newRequestQueue(getActivity());
//        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST,
//                "url", params,
//                this.createMyReqSuccessListenerStallDescp(),
//                this.createRequestErrorListener());
//
//        int socketTimeout = 30000;// 60 seconds - change
//
//        requestQueueSpinner.add(jsObjRequest);
//    }
//
//    private Response.Listener<JSONArray> createMyReqSuccessListenerStallDescp() {
//        return new Response.Listener<JSONArray>() {
//
//            //   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onResponse(JSONArray response) {
//                // TODO Auto-generated method stub
//                pDialog.hide();
//                Log.e("response", String.valueOf(response));
//                try {
////                    String status = response.getString("status");
////                    if (status.equalsIgnoreCase("true")) {
//
////                        jsonArray = response.getJSONArray("data");
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject jo = response.getJSONObject(i);
//                        Log.e("ids", jo.getString("id"));
////                        images = new Images();
////                        images.setImage_Id(jo.getString("id"));
////                        images.setImage_url(jo.getString("img_url"));
////                        images.setImage_cattype(jo.getString("category_id"));
////
////                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
////                        StrictMode.setThreadPolicy(policy);
////                        saveImage(jo.getString("img_url"));
////                        list1.add(images);
////
////                        setInRecyclerview();
//                    }
////                    } else {
////                        Toast.makeText(getApplicationContext(),
////                                "No data found",
////                                Toast.LENGTH_SHORT).show();
////                    }
//
//                } catch (JSONException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//
//            }
//        };
//    }
//
//
//    private Response.ErrorListener createRequestErrorListener() {
//
//        return new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                pDialog.hide();
//                if (error instanceof NetworkError){
//                    Toast.makeText(getActivity(),"No internet connection.\nPlease 'Turn ON' the internet", Toast.LENGTH_LONG).show();
//                }
//                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
//            }
//        };
//    }

    public void saveDataOnServer(String user,String status)
    {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                /*  //  Connection con = ConnectionProvider.getCon();
                    String sqlUpdate = "UPDATE login SET latitude = ? , longitude=? , active =? WHERE username = ?";
                    PreparedStatement statement1 = con.prepareStatement(sqlUpdate);
                    statement1.setString(1, latitude);
                    statement1.setString(2, longitude);
                    statement1.setString(3, status);
                    statement1.setString(4, user);
                    loginuser=user;
                    int i = statement1.executeUpdate();
                    if (i != 0) {

                        Toast.makeText(getContext(),"Record Updated",Toast.LENGTH_LONG).show();
                        System.out.println("record updated");
                                               *//* json.put("code", "200");
                                                json.put("message", "Latitude " + latitude + " longitude " + longitude + " is updated for user "
                                                        + username);
                                                PrintWriter pw = response.getWriter();
                                                pw.print(json.toString());
                                                pw.close();

                    }*/
                    Map<String, String> params = new Hashtable<String, String>();
                    params.put("username",user);
                    params.put("longitude",longitude);
                    params.put("latitude",latitude);
                    params.put("active","yes");

                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    CustomRequest1 customRequest1 = new CustomRequest1(Request.Method.POST, UtilsApp.SAVE_USER,
                            params, this.createImageSuccessListner1(), this.createImageErrorListner1());
                    customRequest1.setRetryPolicy(new DefaultRetryPolicy(MAX_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    customRequest1.setShouldCache(false);
                    requestQueue.add(customRequest1);
                } catch (Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            d.hide();
                           // Toast.makeText(getActivity().getApplicationContext(), "Unable to connect to the server !", Toast.LENGTH_LONG).show();
                            signin.setClickable(true);
                        }
                    });
                }
            }

            private Response.ErrorListener createImageErrorListner1() {
                return new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        mDialog.dismiss();
                        Log.d("SEARCH GARAGE", "Error " + volleyError.getMessage());
                        Toast.makeText(getContext(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                };
            }

            private Response.Listener<JSONObject> createImageSuccessListner1() {
                return new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int statuscode = response.getJSONObject("status").getInt("statusCode");
                            if (statuscode == 202) {
                                Toast.makeText(getContext(),"Record Updated",Toast.LENGTH_LONG).show();
                                System.out.println("record updated");
                            }
                        }
                            catch(JSONException ex)
                            {
                            }

                    }
                };
            }


        });



        t.start();
    }



    private int check() {

        if (username.getText().toString().equals("") && password.getText().toString().equals("")) {
            Toast.makeText(getContext(), "All Feild Mandatory", Toast.LENGTH_LONG).show();
        } else {

            if (username.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Please Enter Email", Toast.LENGTH_LONG).show();
            } else if (password.getText().toString().equals("")) {
                Toast.makeText(getContext(), "Please Enter Password", Toast.LENGTH_LONG).show();
            } else {
                return 1;
            }
        }
        return -1;
    }



    public void LoginUser() {
        HashMap<String, String> maps = new HashMap<>();
        user=username.getText().toString().trim();
        maps.put("username", username.getText().toString().trim());
        maps.put("password", password.getText().toString().trim());

        /*LoginModel maps=new LoginModel();
        maps.setUsername(username.getText().toString().trim());
        maps.setPass(username.getText().toString().trim());*/


       RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, UtilsApp.LOGIN_USER,
                maps, this.createImageSuccessListner(), this.createImageErrorListner());
        customRequest.setRetryPolicy(new DefaultRetryPolicy(MAX_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        customRequest.setShouldCache(false);
        requestQueue.add(customRequest);

       /* Map<String, String> postParam= new HashMap<String, String>();
        postParam.put("un", "xyz@gmail.com");
        postParam.put("p", "somepasswordhere");


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                UtilsApp.LOGIN_USER, new JSONObject(postParam),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                      *//*  msgResponse.setText(response.toString());
                        hideProgressDialog();*//*
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
               *//* hideProgressDialog();*//*
            }
        }) {

            *//**
         * Passing some request headers
         * *//*
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }



        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq,tag_json_obj);
*/

       /* JSONObject js = new JSONObject();


        try {

            js.put("username",username.getText().toString().trim());
            js.put("pass",password.getText().toString().trim());
        }catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjReq= new JsonObjectRequest(Request.Method.POST, UtilsApp.LOGIN_USER, js,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                Toast.makeText(getContext(), "test", Toast.LENGTH_LONG).show();
                try {
                    int statuscode=response.getJSONObject("status").getInt("statusCode");
                    if (statuscode==200)
                    {


                    }
                } catch (JSONException e) {
                    mDialog.dismiss();
                    e.printStackTrace();
                }
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<String, String>();
                data.put("username", username.getText().toString());
                data.put("passward", password.getText().toString());

                return data;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Map<String, String> params = new HashMap<>();
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;

            }

        };
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjReq);*/



    }

    private Response.ErrorListener createImageErrorListner() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                mDialog.dismiss();
                Log.d("SEARCH GARAGE", "Error " + volleyError.getMessage());
                Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Response.Listener<JSONObject> createImageSuccessListner() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(),"hello",Toast.LENGTH_LONG).show();
                Log.d("SEARCH GARAGE", "Response :" + response.toString());
                try {
                    int statuscode=response.getJSONObject("status").getInt("statusCode");
                    if (statuscode==200)
                    {
                        try {


                        if(response.getJSONObject("data").getString("auth").equalsIgnoreCase("y"))
                        {
                        saveDataOnServer(user,"yes");
                            if(response.getJSONObject("data").getString("type").equalsIgnoreCase("client")) {
                                if (user.equalsIgnoreCase("oppo")) {
                                    Intent in = new Intent(getActivity().getApplicationContext(), OppoHome.class);
                                    ClientHome.Client = user;
                                    // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(in);
                                    //getActivity().finish();

                                } else {
                                    Intent in = new Intent(getActivity().getApplicationContext(), ClientHome.class);
                                    ClientHome.Client = user;
                                    // in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(in);
                                    // getActivity().finish();



                                }
                            }else
                            {
                                Intent in = new Intent(getActivity().getApplicationContext(), Clients.class);
                                loggedin = user;
                                //  in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(in);
                                //  getActivity().finish();

                            }
                        }
                            else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    d.hide();
                                    Toast.makeText(getActivity().getApplicationContext(), "Your account is currently under authentication process. Please try after some time !", Toast.LENGTH_LONG).show();
                                    signin.setClickable(true);
                                }
                            });


                        }



                        }catch (JSONException e)
                        {

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    d.hide();
                                    Toast.makeText(getActivity().getApplicationContext(), "Unable to connect to the server !", Toast.LENGTH_LONG).show();
                                    signin.setClickable(true);
                                }
                            });


                        }



                   Toast.makeText(getContext(),"hello"+statuscode,Toast.LENGTH_LONG).show();
                    }
                    else
                    {

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                d.hide();
                                Toast.makeText(getActivity().getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                                signin.setClickable(true);
                            }
                        });


                }
                   /* boolean Status = response.getBoolean("status");
                    if (Status == true) {
                        SharedPrefManager.getInstance(getApplicationContext()).UserData(response.toString());
                        userlogin(response.getString("message"));
                        SharedPrefManager.getInstance(getApplicationContext()).UserLogin(email.getText().toString().trim(), password.getText().toString().trim(), "" + userId);
                        email.setText("");
                        password.setText("");
                        if (userId == 1) {
                            mDialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                            startActivity(i);
                            finish();
                        } else if (userId == 2) {
                            mDialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), GarageAdminPanel.class);
                            startActivity(i);
                            finish();
                        } else {
                            mDialog.dismiss();
                            Intent i = new Intent(getApplicationContext(), GarageUserHome.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                        mDialog.dismiss();

                    }*/
                } catch (JSONException e) {
                    mDialog.dismiss();
                    e.printStackTrace();
                }
            }
        };
    }




   /* public void userlogin(String data) {

        try {

            JSONObject obj = new JSONObject(data);
            userId = obj.getInt("group_id");
            if (userId == 1) {
                ulogin.setEmail(obj.getString("email").toString().trim());
                ulogin.setAddress(obj.getString("address").toString().trim());
                ulogin.setMobile(obj.getString("phone_number"));
                ulogin.setUserId(obj.getInt("id"));
                ulogin.setUserUniqName(obj.getString("user_id"));

                ulogin.setUserName(obj.getString("full_name").toString());

            } else if (userId == 2) {
                ulogin.setGarageName(obj.getString("garage_name"));
                ulogin.setEmail(obj.getString("email").toString().trim());
                ulogin.setGarageAddress(obj.getString("garage_address").toString().trim());
                ulogin.setMobile(obj.getString("phone_number"));
                ulogin.setUserId(obj.getInt("user_id"));
                ulogin.setUserName(obj.getString("full_name").toString());
                ulogin.setGarageId(obj.getInt("id"));
                ulogin.setStartTime1(obj.getString("m_start_time"));
                ulogin.setEndTime1(obj.getString("m_end_time"));
                ulogin.setStartTime2(obj.getString("e_start_time"));
                ulogin.setEndTime2(obj.getString("e_end_time"));
                ulogin.setPrice(obj.getInt("price"));
                ulogin.setTown(obj.getString("town"));
                ulogin.setPlace(obj.getString("place"));
                ulogin.setPincode(obj.getInt("pincode"));
                ulogin.setLandMark(obj.getString("landmark"));
                ulogin.setGender(obj.getString("gender"));
                ulogin.setState(obj.getString("garage_state"));
                ulogin.setDistrict(obj.getString("garage_district"));
                ulogin.setImage(obj.getString("profile_pic"));
                ulogin.setGarageImage(obj.getString("garage_image"));

            } else {
                ulogin.setEmail(obj.getString("email"));
                ulogin.setUnderGarageId(obj.getString("under_garage_id"));
                ulogin.setUserId(obj.getInt("id"));
                ulogin.setGarageId(obj.getInt("under_garage_id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
*/

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = createLocationRequest();
        builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates mState = result.getLocationSettingsStates();
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        } else {

                            if (mLastLocation != null) {
                             latitude= Double.toString(mLastLocation.getLatitude());
                             longitude= Double.toString(mLastLocation.getLongitude());
                               /* ulogin.setLatitude("" + mLastLocation.getLatitude());
                                ulogin.setLongitude("" + mLastLocation.getLongitude());*/
                                // Toast.makeText(LoginScreen.this, "lat" + mLastLocation.getLatitude() + "lng" + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



}
