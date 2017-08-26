package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


public class SignUpConfirm extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_confirm);
        final Intent in = getIntent();
        ProgressBar pb=new ProgressBar(getApplicationContext());
        pb.setIndeterminate(true);
        pb.setVisibility(View.VISIBLE);
        final String name = in.getStringExtra("name");
        final String user = in.getStringExtra("user");
        final String pass = in.getStringExtra("pass");
        final String phone = in.getStringExtra("phone");
        Thread thread = new Thread(new Runnable() {
            String namei=name;
            String useri=user;
            String phonei=phone;
            @Override
            public void run() {

                final HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://env-2776603.ind-cloud.everdata.com/authentication.jsp?name="+namei+"&username="+useri+"&phone="+phonei);
                try {
                    Log.e("TEJ", "6i");
                    HttpResponse response = httpclient.execute(httppost);
                    Log.e("TEJ", "6j");

                }catch (Exception e)
                {
                    Log.e("Error in dealer",e.toString());
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    pb.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),""+namei+""+phonei+""+useri,Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
        thread.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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