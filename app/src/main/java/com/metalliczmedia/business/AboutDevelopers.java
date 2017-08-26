package com.metalliczmedia.business;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class AboutDevelopers extends AppCompatActivity {
AppCompatButton tejcall,tejmessage,tejemail,tejlinkedin,lakcall,lakmessage,lakemail,laklinkedin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developers);
        tejcall= (AppCompatButton) findViewById(R.id.tejenderbutton1);
        tejmessage= (AppCompatButton) findViewById(R.id.tejenderbutton2);
        tejemail= (AppCompatButton) findViewById(R.id.tejenderbutton3);
        tejlinkedin= (AppCompatButton) findViewById(R.id.tejenderbutton4);
        lakcall= (AppCompatButton) findViewById(R.id.lakshaybutton1);
        lakmessage= (AppCompatButton) findViewById(R.id.lakshaybutton2);
        lakemail= (AppCompatButton) findViewById(R.id.lakshaybutton3);
        laklinkedin= (AppCompatButton) findViewById(R.id.lakshaybutton4);
        tejcall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+918860614996"));
                startActivity(callIntent);
            }
        });

        tejmessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:+918860614996"));
                startActivity(sendIntent);
            }
        });
        tejemail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setPackage("com.google.android.gm");
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"tejender@outlook.com"});
                startActivity(emailIntent);
            }
        });
        tejlinkedin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://in.linkedin.com/in/TejenderS"));
               startActivity(myIntent);            }
        });

        lakcall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+918800474111"));
                startActivity(callIntent);

            }
        });

        lakmessage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:+918800474111"));
                startActivity(sendIntent);

            }
        });

        lakemail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setPackage("com.google.android.gm");
                emailIntent.setType("plain/text");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"lakshayswani@outlook.com"});
                startActivity(emailIntent);

            }
        });

        laklinkedin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://in.linkedin.com/in/lakshayswani"));
                startActivity(myIntent);

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
