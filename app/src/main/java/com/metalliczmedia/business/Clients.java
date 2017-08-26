package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import connection.ConnectionProvider;

public class Clients extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ClientsDetails> clients=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new StaggeredGridLayoutManager(2,1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // specify an adapter (see also next example)

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    clients.add(new ClientsDetails("Amplifon", getResources().getDrawable(R.drawable.amplifon)));
                    clients.add(new ClientsDetails("Daikin", getResources().getDrawable(R.drawable.daikin)));
                    clients.add(new ClientsDetails("Falken", getResources().getDrawable(R.drawable.falken)));
                    clients.add(new ClientsDetails("Gionee", getResources().getDrawable(R.drawable.gionee)));
                    clients.add(new ClientsDetails("Indo Asian", getResources().getDrawable(R.drawable.indoasian)));
                    clients.add(new ClientsDetails("Mitsubishi", getResources().getDrawable(R.drawable.mitsubishi)));
                    clients.add(new ClientsDetails("Oppo", getResources().getDrawable(R.drawable.oppo)));
                    clients.add(new ClientsDetails("MaharajaWhiteline", getResources().getDrawable(R.drawable.maharajawhiteline)));
                    clients.add(new ClientsDetails("huawei", getResources().getDrawable(R.drawable.huawei)));
                    clients.add(new ClientsDetails("vivo", getResources().getDrawable(R.drawable.vivo)));

//                    Thread.sleep(10000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new MyAdapterClients(Clients.this,clients);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    });

                }catch (Exception e)
                {
                    Log.e("Thread",e.toString());
                }

            }
        });

        t.start();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;    }
    CharSequence items[]={"Hangouts","Whatsapp","Gmail"};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          /*  case R.id.aboutthedev:
                Intent in=new Intent(getApplicationContext(),YourActivity.class);
                startActivity(in);
                return true;
*/
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

            case R.id.menu_item_logout:
              //  Toast.makeText(this,"Hello",Toast.LENGTH_LONG).show();
                setLogout(Logintab.loginuser,"no");

                Intent intent = new Intent(Clients.this, BeforeLogin.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


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


    public void setLogout(String user, String status)
    {

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection con = ConnectionProvider.getCon();
                    String sqlUpdate = "UPDATE login SET active =? WHERE username = ?";
                    PreparedStatement statement1 = con.prepareStatement(sqlUpdate);
                    statement1.setString(1, status);
                    statement1.setString(2, user);

                    int i = statement1.executeUpdate();
                    if (i != 0) {

                      //  Toast.makeText(getBaseContext(),"Record Updated",Toast.LENGTH_LONG).show();
                        //System.out.println("record updated");
                        finish();
                                               /* json.put("code", "200");
                                                json.put("message", "Latitude " + latitude + " longitude " + longitude + " is updated for user "
                                                        + username);
                                                PrintWriter pw = response.getWriter();
                                                pw.print(json.toString());
                                                pw.close();
                                                */
                    }
                } catch (Exception e) {
                   /* this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            d.hide();
                            // Toast.makeText(getActivity().getApplicationContext(), "Unable to connect to the server !", Toast.LENGTH_LONG).show();
                            signin.setClickable(true);
                        }
                    });*/
                }
            }

        });



        t.start();
    }


}
