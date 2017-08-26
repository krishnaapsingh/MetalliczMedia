package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import static connection.Global.conn;

public class Orders extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View one, two;
    private View fab;
    private static final int MAX_TIMEOUT_MS = 30000;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        mRecyclerView = (RecyclerView) findViewById(R.id.productsrecyclerview);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        fab = findViewById(R.id.ordersubmitbutton);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(Orders.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.setContentView(R.layout.custom_dialog3);
                d.setCancelable(false);
                WebView wv = (WebView) d.findViewById(R.id.dialogwebview1);
                wv.setBackgroundColor(Color.TRANSPARENT);
                wv.loadUrl("file:///android_asset/demo.html");

                d.show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadAll(MyAdapterClients.productdetails);
                        Intent in = new Intent(getApplicationContext(), Clients.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }
                });
                t.start();
                Toast.makeText(getApplicationContext(), "Uploading Content...You will be redirected automatically once uploaded", Toast.LENGTH_LONG).show();

            }
        });
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.sleep(10000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new MyAdapterProducts(getApplicationContext(), MyAdapterClients.productdetails);
                            mRecyclerView.setAdapter(mAdapter);


                        }
                    });

                } catch (Exception e) {
                    Log.e("Thread", e.getMessage());
                }

            }
        });

        t.start();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void uploadAll(ArrayList<Products> products) {
        int receeid = 0;

/*
        final HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://env-2776603.ind-cloud.everdata.com/Upload");
        FileBody nam = new FileBody(new File(Dealer.storecard.getPath()));
*/
/*
try {
    StringBody name = new StringBody(Dealer.storename);
    StringBody address = new StringBody(Dealer.storeaddress);
    StringBody city = new StringBody(Dealer.storecity);
    StringBody state = new StringBody(Dealer.storestate);
    StringBody pin = new StringBody(Dealer.storepincode);
    StringBody sales = new StringBody(Dealer.storesalesperson);
    StringBody type = new StringBody("store");
    StringBody client = new StringBody(MyAdapterClients.clientselected);
    Log.e("TEJ", "6i"+MyAdapterClients.clientselected);

    StringBody receeby = new StringBody(Logintab.loggedin);
    StringBody recee = new StringBody(""+receeid);

        MultipartEntity reqEntity = new MultipartEntity();
        reqEntity.addPart("pic1", nam);
        reqEntity.addPart("name",name );
        reqEntity.addPart("address",address );
        reqEntity.addPart("city",city );
        reqEntity.addPart("state",state );
        reqEntity.addPart("pin",pin );
        reqEntity.addPart("sales",sales );
    reqEntity.addPart("type", type);
    reqEntity.addPart("receeby", receeby);
    reqEntity.addPart("clientname",client);
    reqEntity.addPart("receeid", recee);

        httppost.setEntity(reqEntity);
    Log.e("TEJ", "6i");
    HttpResponse response = httpclient.execute(httppost);
    Log.e("TEJ", "6j");

}catch (Exception e)
{
    Log.e("Error in dealer",e.getMessage());
}
*/


//        Date d = Calendar.getInstance().getTime();
//        final String year = d.toString().substring(d.toString().length() - 4);
//        String months = d.toString().substring(4, 7);
//        final String month = toInt(months);
//        final String day = d.toString().substring(8, 10);
//        String date = year + "-" + month + "-" + day;
//        java.sql.Connection conn=ConnectionProvider.getCon();
//        try {
//            // constructs SQL statement
//            String sql = "";
//            if (Dealer.sign != null)
//                sql = "insert into store(storename,storeaddress,storecity,storestate,storepincode,storesales,storecard,storedate,clientname,receeby,sign) values(?,?,?,?,?,?,?,?,?,?,?)";
//            else
//                sql = "insert into store(storename,storeaddress,storecity,storestate,storepincode,storesales,storecard,storedate,clientname,receeby) values(?,?,?,?,?,?,?,?,?,?)";
//            System.out.println(sql);
//            PreparedStatement statement = conn.prepareStatement(sql);
//
//            statement.setString(1, Dealer.storename);
//            statement.setString(2, Dealer.storeaddress);
//            statement.setString(3, Dealer.storecity);
//            statement.setString(4, Dealer.storestate);
//            statement.setString(5, Dealer.storepincode);
//            statement.setString(6, Dealer.storesalesperson);
//
//
//            String Sstr=  getRealPathFromUri(this,Dealer.storecard);;
//            String Ssign=  getRealPathFromUri(this,Dealer.sign);;
//
//            if (Dealer.storecard != null)
//                statement.setBlob(7, new FileInputStream(new File(getRealPathFromUri(this,Dealer.storecard))));
//            statement.setString(8, date);
//            statement.setString(9, MyAdapterClients.clientselected);
//            statement.setString(10, Logintab.loggedin);
//           // String str1= Dealer.sign.getPath();
//
//            if (Dealer.sign != null) {
//                statement.setBlob(11, new FileInputStream(new File(getRealPathFromUri(this,Dealer.sign))));
//
//            }
//            Log.e("TEJ2", "Adding Client");
//
//            // sends the statement to the database server
//            int row = statement.executeUpdate();
//            Log.e("TEJ2", "Client Added");
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        } catch (FileNotFoundException e) {
//            Log.e("TEJ2", e.getMessage());
//            e.printStackTrace();
//        }
//
//
//        try {
//            Statement st = conn.createStatement();
//            ResultSet rs = st.executeQuery("select max(storeno) from store");
//            if (rs.next()) {
//                if (rs.getInt(1) == 0) {
//                    receeid = 1;
//                } else
//                    receeid = rs.getInt(1);
//
//            } else {
//                receeid = 1;
//            }
//        } catch (Exception e) {
//            Log.e("Error in recee", e.getMessage());
//        }

        setDataOnServer();

       /* final int temp = receeid;

        runOnUiThread(new Runnable() {
            int receeid1 = temp;

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Recee ID : " + receeid1, Toast.LENGTH_LONG).show();
                Snackbar.make(getWindow().getDecorView().getRootView(), "Recee ID : " + receeid1, Snackbar.LENGTH_LONG).show();
            }
        });

        try {
            if (MyAdapterClients.clientselected.equalsIgnoreCase("gionee")) {
                Statement st = conn.createStatement();
                st.executeUpdate("insert into gioneecodes values(" + receeid + ",'" + GioneeCodes.appid + "','" + GioneeCodes.rtcode + "')");
            }
        } catch (Exception e) {
            Log.e("TEJJJ", e.toString());
        }

        Log.d("After recee", "ii" + receeid);

        Iterator<Products> i = products.iterator();
        int i1 = 0;
        while (i.hasNext()) {
            Log.d("in while", "lalalala");

            try {
                i1++;
                Products product = i.next();
                Log.d("in try", "lalllaa");

                Log.d("name", "%" + product.productname);
                Log.d("size", "%" + product.picsize);
                Log.d("type", "%" + product.producttype);
                Log.d("recee", "%" + receeid);

                Uri editedImageUri = product.picuri;
                String size = product.picsize;


                //>>>>>>>>>>>>>>>>>CHeck>>>>>>>
*//*
                Log.e("TEJ", "1");


                final HttpClient httpclient1 = new DefaultHttpClient();
                Log.e("XX", "1");
                HttpPost httppost1 = new HttpPost("http://env-2776603.ind-cloud.everdata.com/Upload");
                Log.e("XX", "2");
                FileBody nam1 = new FileBody(new File(editedImageUri.getPath()));
                Log.e("XX", "3");
                StringBody content = new StringBody(size);
                Log.e("XX", "4");
                StringBody content2 = new StringBody(""+receeid);
                Log.e("XX", "5");
                StringBody content4 = new StringBody("recee");
                Log.e("XX", "6");
                StringBody content5=new StringBody(product.productname);
                Log.e("XX", "7");
                StringBody content6=new StringBody(product.producttype);
                Log.e("XX", "8");


                MultipartEntity reqEntity1 = new MultipartEntity();
                Log.e("XX", "9");
                reqEntity1.addPart("pic1", nam1);
                Log.e("XX", "10");
                reqEntity1.addPart("size", content);
                Log.e("XX", "11");
                reqEntity1.addPart("receeid", content2);
                Log.e("XX", "12");
                reqEntity1.addPart("type", content4);
                Log.e("XX", "13");
                reqEntity1.addPart("pname", content5);
                Log.e("XX", "14");
                reqEntity1.addPart("ptype", content6);
                Log.e("XX", "15");





                httppost1.setEntity(reqEntity1);
                Log.e("TEJ", "5");

                        try {
                            Log.e("TEJ", "6i");
                            HttpResponse response = httpclient1.execute(httppost1);
                            Log.e("TEJ", "6j");


                        } catch (IOException e) {
                            Log.e("TEJJJ", e.getMessage());
                        }


*//*
                // constructs SQL statement


//>>>>>>>>>>>>Check>>>>>>>>>>>>>>>>

                String sql = "insert into recee (receeid,size,pic1,pname,ptype,receedate) values(?,?,?,?,?,?)";
//storename,storeaddress,storecity,storestate,storepincode,storesales,
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setInt(1, receeid);
//			statement.setInt(2, productid);
                statement.setString(2, size);
                if (editedImageUri != null) {
                    // fetches input stream of the upload file for the blob column
                    statement.setBlob(3, new FileInputStream(new File(editedImageUri.getPath())));
                }
                statement.setString(4, product.productname);
                statement.setString(5, product.producttype);
                //statement.setString(6, date);

                Log.e("TEJ2", "SENDING RECEE");
                // sends the statement to the database server
                int row = statement.executeUpdate();
                Log.e("TEJ2", "RECEE ADDED");


            } catch (Exception e) {
                Log.e("ERROR in product", e.getMessage());
            }


        }

        final HttpClient httpClientnew = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://env-2776603.ind-cloud.everdata.com/sendCurrentppt.jsp?receeid=" + receeid);
        try {
            Log.e("TEJ2", "Sending Email");
            HttpResponse response = httpClientnew.execute(post);
            Log.e("TEJ2", "Email Sent");

        } catch (Exception e) {
            Log.e("TEJ2", e.getMessage());
        }*/
    }

    public static String toInt(String day) {
        if (day.equalsIgnoreCase("jan")) {
            return "1";
        } else if (day.equalsIgnoreCase("feb")) {
            return "2";
        } else if (day.equalsIgnoreCase("mar")) {
            return "3";
        } else if (day.equalsIgnoreCase("apr")) {
            return "4";
        } else if (day.equalsIgnoreCase("may")) {
            return "5";
        } else if (day.equalsIgnoreCase("jun")) {
            return "6";
        } else if (day.equalsIgnoreCase("jul")) {
            return "7";
        } else if (day.equalsIgnoreCase("aug")) {
            return "8";
        } else if (day.equalsIgnoreCase("sep")) {
            return "9";
        } else if (day.equalsIgnoreCase("oct")) {
            return "10";
        } else if (day.equalsIgnoreCase("nov")) {
            return "11";
        } else if (day.equalsIgnoreCase("dec")) {
            return "12";
        } else
            return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    CharSequence items[] = {"Hangouts", "Whatsapp", "Gmail"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          /*  case R.id.aboutthedev:
                Intent in=new Intent(getApplicationContext(),YourActivity.class);
                startActivity(in);
                return true;*/

            case R.id.likeapp:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                final String appPackageName = "com.metalliczmedia.business";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                return true;

            case R.id.menu_item_share:
                showDialog(0);


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setIcon(R.drawable.share);
                builder1.setTitle("Share Using...");
                int checkedItem = -1;
                builder1.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (items[which] == "Hangouts") {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! You should try it too. http://play.google.com/store/apps/details?id=com.metalliczmedia.business";
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                            sharingIntent.setPackage("com.google.android.talk");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else if (items[which] == "Whatsapp") {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! You should try it too. http://play.google.com/store/apps/details?id=com.metalliczmedia.business";
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                            sharingIntent.setPackage("com.whatsapp");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else if (items[which] == "Gmail") {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! \nYou should try it too. \n <a href=\"http://play.google.com/store/apps/details?id=com.metalliczmedia.business\">Metallicz Media</a>";
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(shareBody));
                            sharingIntent.setPackage("com.google.android.gm");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else
                            Toast.makeText(getApplicationContext(), "Wrong Choice", Toast.LENGTH_LONG).show();
                    }
                });
                return builder1.create();

            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    public String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void setDataOnServer() {

        Map<String, String> params = new Hashtable<String, String>();
        params.put("storename", Dealer.storename);
        params.put("storeaddress", Dealer.storeaddress);
        params.put("storecity", Dealer.storecity);
        params.put("storestate", Dealer.storestate);
        params.put("storepincode", Dealer.storepincode);
        params.put("storesales", Dealer.storesalesperson);
        Bitmap bm = BitmapFactory.decodeFile(getRealPathFromUri(getApplicationContext(),Dealer.storecard));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
        String storecard = Base64.encodeToString(b, Base64.DEFAULT);
        params.put("storecard", storecard);
        params.put("clientname", MyAdapterClients.clientselected);
        params.put("receeby", Logintab.loggedin);
        Bitmap bm1 = BitmapFactory.decodeFile(getRealPathFromUri(getApplicationContext(),Dealer.sign));
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        bm1.compress(Bitmap.CompressFormat.JPEG, 100, baos1); //bm is the bitmap object
        byte[] b1 = baos1.toByteArray();
        String sign = Base64.encodeToString(b1, Base64.DEFAULT);
        params.put("sign", sign);


        RequestQueue requestQueue = Volley.newRequestQueue(Orders.this);
        CustomRequest customRequest = new CustomRequest(Request.Method.POST, UtilsApp.SAVE_STORE,
                params, this.createImageSuccessListner(), this.createImageErrorListner());
        customRequest.setRetryPolicy(new DefaultRetryPolicy(MAX_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        customRequest.setShouldCache(false);
        requestQueue.add(customRequest);
    }

    private String encodeImage(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imagefile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return encImage;

    }

    private Response.ErrorListener createImageErrorListner() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // mDialog.dismiss();
                Log.d("SEARCH GARAGE", "Error " + volleyError.getMessage());
                Toast.makeText(Orders.this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private Response.Listener<JSONObject> createImageSuccessListner() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int statuscode = response.getJSONObject("status").getInt("statusCode");
                    if (statuscode == 201) {
                        Toast.makeText(Orders.this, "Record Updated", Toast.LENGTH_LONG).show();

                        
                        // System.out.println("record updated");
                    }
                } catch (JSONException ex) {
                }

            }
        };
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Orders Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
