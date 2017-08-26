package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import connection.ConnectionProvider;

public class Installation extends AppCompatActivity {

    private FABToolbarLayout layout;
    private View one, two;
    String recee;
    EditText search;
    ImageButton searchbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation);

        FloatingActionButton fab= (FloatingActionButton) findViewById(R.id.fabtoolbar_fab1);
        fab.setVisibility(View.INVISIBLE);
        search= (EditText) findViewById(R.id.search);
        searchbutton= (ImageButton) findViewById(R.id.searchbutton);
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchbutton.setClickable(false);
                fab.setVisibility(View.VISIBLE);
                recee=search.getText().toString();
                Install install = (Install) getSupportFragmentManager().findFragmentById(R.id.searchfragment);
                install.installshow(search.getText().toString());
                Toast.makeText(getApplicationContext(),"Searching",Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchbutton.setClickable(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

/*
        layout = (FABToolbarLayout) findViewById(R.id.fabtoolbar1);
        one = findViewById(R.id.one1);
        two = findViewById(R.id.two1);
        fab = findViewById(R.id.fabtoolbar_fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.show();
            }
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(getApplicationContext(), Clients.class);
                startActivity(in);
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Iterator<InstallProducts> i = Install.products.iterator();
                        int i1 = 0;
                        while (i.hasNext()) {
                            Log.d("in while", "lalalala");

                            try {
                                i1++;
                                InstallProducts product = i.next();
                                Log.d("in try", "lalllaa");

                                Uri editedImageUri = product.installpic;
                                Log.e("TEJ", "1");

                                java.sql.Connection con = ConnectionProvider.getCon();
                                PreparedStatement ps = con.prepareStatement("insert into recee values");

                                final HttpClient httpclient1 = new DefaultHttpClient();
                                HttpPost httppost1 = new HttpPost("http://env-2776603.ind-cloud.everdata.com/Upload");
                                FileBody nam1 = new FileBody(new File(editedImageUri.getPath()));
                                StringBody content = new StringBody("" + product.productid);
                                StringBody content4 = new StringBody("install");


                                MultipartEntity reqEntity1 = new MultipartEntity();
                                reqEntity1.addPart("pic1", nam1);
                                reqEntity1.addPart("type", content4);
                                reqEntity1.addPart("productid", content);

                                httppost1.setEntity(reqEntity1);
                                Log.e("TEJ", "5");

                                try {
                                    Log.e("TEJ", "6i");
                                    HttpResponse response = httpclient1.execute(httppost1);
                                    Log.e("TEJ", "6j");


                                } catch (IOException e) {
                                    Log.e("TEJJJ", e.toString());
                                }

                            } catch (Exception e) {
                                Log.e("ERROR in product", e.toString());
                            }

                        }

                        Intent in = new Intent(getApplicationContext(), Clients.class);
                        startActivity(in);

                    }
                });
                t.start();
                Toast.makeText(getApplicationContext(), "Uploading Content...You will be redirected automatically once uploaded", Toast.LENGTH_LONG).show();
                layout.hide();
            }
        });
*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(Installation.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.setContentView(R.layout.custom_dialog3);
                d.setCancelable(false);
                WebView wv = (WebView) d.findViewById(R.id.dialogwebview1);
                wv.setBackgroundColor(Color.TRANSPARENT);
                wv.loadUrl("file:///android_asset/demo.html");
                d.show();


                Thread t = new Thread(new Runnable() {
                    String receeid=recee;
                    @Override
                    public void run() {
                        Iterator<InstallProducts> i = Install.products.iterator();
                        int i1 = 0;
                        while (i.hasNext()) {
                            Log.d("in while", "lalalala");

                            try {
                                i1++;
                                InstallProducts product = i.next();
                                Log.d("in try", "lalllaa");

                                Uri editedImageUri = product.installpic;
                                Log.e("TEJ", "1");

                                //java.sql.Connection con = ConnectionProvider.getCon();
                               // PreparedStatement ps = con.prepareStatement("insert into recee values");

/*
                                final HttpClient httpclient1 = new DefaultHttpClient();
                                HttpPost httppost1 = new HttpPost("http://env-2776603.ind-cloud.everdata.com/Upload");
                                FileBody nam1 = new FileBody(new File(editedImageUri.getPath()));
                                StringBody content = new StringBody("" + product.productid);
                                StringBody content4 = new StringBody("install");


                                MultipartEntity reqEntity1 = new MultipartEntity();
                                reqEntity1.addPart("pic1", nam1);
                                reqEntity1.addPart("type", content4);
                                reqEntity1.addPart("productid", content);

                                httppost1.setEntity(reqEntity1);
                                Log.e("TEJ", "5");

                                try {
                                    Log.e("TEJ", "6i");
                                    HttpResponse response = httpclient1.execute(httppost1);
                                    Log.e("TEJ", "6j");


                                } catch (IOException e) {
                                    Log.e("TEJJJ", e.toString());
                                }
*/



                                try {
                                   Connection conn=ConnectionProvider.getCon();
                                    // constructs SQL statement


                                    String sql="update recee set pic2=? where  productid=?";

                                    PreparedStatement statement = conn.prepareStatement(sql);

                                    if (editedImageUri != null) {
                                        // fetches input stream of the upload file for the blob column
                                        statement.setBlob(1, new FileInputStream(new File(editedImageUri.getPath())));
                                    }
                                    statement.setInt(2, product.productid);


                                    // sends the statement to the database server
                                    Log.e("TEJ2","SEDNING INSTALL");
                                    int row = statement.executeUpdate();
                                    Log.e("TEJ2","INSTALL SENT");

                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                }



                            } catch (Exception e) {
                                Log.e("ERROR in product", e.toString());
                            }

                        }

                        final HttpClient httpClientnew = new DefaultHttpClient();
                        HttpPost post = new HttpPost("http://env-2776603.ind-cloud.everdata.com/sendCurrentppt.jsp?receeid="+receeid);
                        try {
                            Log.e("TEJ2", "Sending Email");
                            HttpResponse response=httpClientnew.execute(post);
                            Log.e("TEJ2","Email Sent");

                        } catch (Exception e) {
                            Log.e("TEJ2", e.getMessage());
                        }

                        Intent in = new Intent(getApplicationContext(), Clients.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);

                    }
                });
                t.start();
                Toast.makeText(getApplicationContext(), "Uploading Content...You will be redirected automatically once uploaded", Toast.LENGTH_LONG).show();
                //layout.hide();
            }


        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;    }
    CharSequence items[]={"Hangouts","Whatsapp","Gmail"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           /* case R.id.aboutthedev:
                Intent in=new Intent(getApplicationContext(),YourActivity.class);
                startActivity(in);
                return true;*/

            case R.id.likeapp:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                final String appPackageName = "com.metalliczmedia.business";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
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
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! You should try it too. http://play.google.com/store/apps/details?id=com.metalliczmedia.business";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            sharingIntent.setPackage("com.google.android.talk");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else if (items[which] == "Whatsapp") {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! You should try it too. http://play.google.com/store/apps/details?id=com.metalliczmedia.business";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                            sharingIntent.setPackage("com.whatsapp");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else if (items[which] == "Gmail") {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Hey There! Check out this amazing application \"Metallicz Media\" ! \nYou should try it too. \n <a href=\"http://play.google.com/store/apps/details?id=com.metalliczmedia.business\">Metallicz Media</a>";
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Metallicz Media");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(shareBody));
                            sharingIntent.setPackage("com.google.android.gm");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        } else
                            Toast.makeText(getApplicationContext(), "Wrong Choice", Toast.LENGTH_LONG).show();
                    }
                });
                return builder1.create();

            default: break;
        }
        return super.onCreateDialog(id);
    }

}
