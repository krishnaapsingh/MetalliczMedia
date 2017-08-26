package com.metalliczmedia.business;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import connection.ConnectionProvider;

public class Welcome extends AppCompatActivity {
    Connection con;
    WebView splash, wv;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        im = (ImageView) findViewById(R.id.splashimageview);
        YoYo.with(Techniques.FadeInUp).duration(4000).playOn(im);
        splash = (WebView) findViewById(R.id.webviewsplash);
        splash.loadUrl("file:///android_asset/1.html");


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    con = ConnectionProvider.getCon();
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery("select * from version");
                    if (rs.next()) {
                        if (!rs.getString(1).equalsIgnoreCase("9")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "A new version of this application is available", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    Log.e("Error", e.toString());
                }
            }
        });
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (con == null) {
                    Log.e("COnnection", String.valueOf(con));
                }
                Intent in = new Intent(getApplicationContext(), BeforeLogin.class);
                //in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();
            }
        });
        t.start();
        t1.start();
    }
}
