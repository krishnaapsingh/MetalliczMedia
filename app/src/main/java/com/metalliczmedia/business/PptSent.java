package com.metalliczmedia.business;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class PptSent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ppt_sent);

        Thread t=new Thread(new Runnable() {
            String user=Logintab.loggedin;
            String client=MyAdapterClients.clientselected;
            @Override
            public void run() {
                final HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://env-2776603.ind-cloud.everdata.com/sendTodaysEmail.jsp?receeby="+user+"&clientname="+client);
                try {
                    Log.e("TEJ", "6i");
                    HttpResponse response = httpclient.execute(httppost);
                    Thread.sleep(2000);
                    Intent in=new Intent(getApplicationContext(),Clients.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(in);
                    Log.e("TEJ", "6j");

                }catch (Exception e)
                {
                    Log.e("Error in dealer", e.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Oops, Some error has occured. We regret the inconvenience ! Please try again ", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"http://env-2776603.ind-cloud.everdata.com/sendTodaysEmail.jsp?receeby="+Logintab.loggedin+"&clientname="+MyAdapterClients.clientselected,Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });
        t.start();

    }
}
