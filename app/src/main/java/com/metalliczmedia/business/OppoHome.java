package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import connection.ConnectionProvider;

public class OppoHome extends AppCompatActivity {
Dialog d;
    static  String selectstate;
    static  String selectcity;
    static String Client="oppo";

    ArrayAdapter<String> cityadap;
    ArrayAdapter<String> stateadap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oppo_home);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.app_obar);
//        appbar.setBackground(getResources().getDrawable(R.drawable.oppo));
        switch (Client)
        {
            case "oppo":{
                appbar.setBackground(getResources().getDrawable(R.drawable.oppo));

                break;
            }
            case "falken":{
                appbar.setBackground(getResources().getDrawable(R.drawable.falken));

                break;
            }
            case "amplifon":{
                appbar.setBackground(getResources().getDrawable(R.drawable.amplifon));

                break;
            }
            case "daikin":{
                appbar.setBackground(getResources().getDrawable(R.drawable.daikin));

                break;
            }
            case "gionee":{
                appbar.setBackground(getResources().getDrawable(R.drawable.gionee));

                break;
            }
            case "indoasian":{
                appbar.setBackground(getResources().getDrawable(R.drawable.indoasian));

                break;
            }
            case "mitsubishi":{
                appbar.setBackground(getResources().getDrawable(R.drawable.mitsubishi));

                break;
            }
        }


 //        toolbar.setBackground(getResources().getDrawable(R.drawable.gionee));
  //      toolbar.setTitle(null);
//        setSupportActionBar(toolbar);

        Spinner storestate = (Spinner) findViewById(R.id.oppostate2);
        Spinner storecity = (Spinner) findViewById(R.id.oppocity2);
        EditText password= (EditText) findViewById(R.id.passwordoppo);
        String andhrapradesh[]={"Select City","Visakhapatnam","Vijayawada","Guntur","Nellore",
                "Kurnool","Rajahmundri","Tirupati","Kadapa","Kakinada","Anantpur",
                "Vijayanagaram","Other"};

        String assam[]={"Select City","Guwahati","Other"};

        String bihar[]={"Select City","Patna","Gaya","Bhagalpur","Muzaffarpur","Bihar Sharif",
                "Darbhanga","Purnia","Arrah","Begusarai","Katihar","Other"};

        String chandigarh[]={"Select City","Chandigarh","Bilaspur","Other"};

        String chhattisgarh[]={"Select City","Raipur","Bhilai","Korba","Durg","Other"};

        String delhi[]={"Select City","New Delhi","Old Delhi","Kirari Suleman Nagar","Karawal Nagar","Other"};

        String gujarat[]={"Select City","Ahmedabad","Surat","Vadodara","Rajkot","Bhavnagar","Jamnagar","Junahgad","Gandhidham","Gandhinagar","Other"};

        String haryana[]={"Select City","Faridabad","Gurgaon","Rohtak","Panipat","Karnal","Sonipat","Other"};

        String himachalpradesh[]={"Select City","Shimla","Other"};

        String jammuandkashmir[]={"Select City","Srinagar","Jammu","Other"};

        String jharkhand[]={"Select City","Dhanbad","Ranchi","Jamshedpur","Bokaro","Other"};

        String karnataka[]={"Select City","Bangalore","Hubballi-Dharwad","Mysore","Gulbarga",
                "Mangalore","Belgaun","Davanagere","Bellary","bijapur","Shivamogga",
                "Tumkur","Raichur","Other"};

        String kerala[]={"Select City","Thiruvananthapuram","Kochi","Kozhikode","kollam",
                "Thrissur","Other"};

        String madhyapradesh[]={"Select City","Indore","Bhopal","Jabalpur","Gwalior","Ujjain",
                "Dewas","Satna","Sagar","Ratlam","Rewa","Other"};

        String maharashtra[]={"Select Region","Mumbai","Pune","Nagpur"};

        String manipur[]={"Select City","Imphal","Other"};

        String mizoram[]={"Select City","Aizwal","Other"};

        String orissa[]={"Select City","Bhubaneswar","Cuttack","Berhampur","Rourkela","Other"};

        String punjab[]={"Select City","Ludhiana","Amritsar","Jalandhar","Patiala","Bathinda","Other"};

        String puducherry[]={"Select City","Ozhukarai","Puducherry","Other"};

        String rajashthan[]={"Select City","Jaipur","Jodhpur","Kota","Bikaner","Ajmer","Udaipur",
                "Bhilwara","Alwar","Bharatpur","Sikar","Pali","Sri Ganganagar","Other"};

        String tamilnadu[]={"Select City","Chennai","Coimbatore","Madurai","Tiruchirappalli",
                "Tiruppur","Salem","Erode","Ambattur","Tirunelveli","Avadi","Tiruvottiyur",
                "Thoothukudi","Other"};

        String telangana[]={"Select City","Hyderabad","Warangal","Nizamabad","Khammam","Karimnagar",
                "Ramagundam","Other"};

        String tripura[]={"Select City","Agartala","Other"};

        String uttarpradesh[]={"Select Region","Uttar Pradesh East","Uttar Pradesh West"};
        String uttrakhand[]={"Select City","Dehradun","Other"};

        String westbengal[]={"Select City","Kolkata","Howrah","Durgapur","Asansol","Siliguri",
                "Maheshtala","Rajpur Sonarpur","South Dumdum","Gopalpur","Bhatpara",
                "Panihati","Kamarhati","Bardhaman","Kulti","Other"};

        String states[]={"Select State","Andhra Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Gujarat","Haryana","Himachal Pradesh",
                "Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Mizoram","Orissa","Punjab",
                "Puducherry","Rajasthan","Tamil Nadu","Telanagan","Tripura","Uttar Pradesh","West Bengal","Other"};
        d = new Dialog(OppoHome.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.custom_dialog3);
        d.setCancelable(false);
        WebView wv = (WebView) d.findViewById(R.id.dialogwebview1);
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.loadUrl("file:///android_asset/demo.html");

        String empty[]={"Select City"};
        stateadap=new ArrayAdapter<String>(this, R.layout.spinnerlayout,states);
        cityadap=new ArrayAdapter<String>(this, R.layout.spinnerlayout,empty);
        storestate.setAdapter(stateadap);
        storecity.setAdapter(cityadap);
        storecity.setVisibility(View.INVISIBLE);
        storestate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectstate = states[position];
                selectcity="";
                storecity.setVisibility(View.INVISIBLE);
                switch (states[position]) {
                    case "Andhra Pradesh":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, andhrapradesh);
                        break;
                    case "Assam":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, assam);
                        break;
                    case "Bihar":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, bihar);
                        break;
                    case "Chandigarh":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, chandigarh);
                        break;
                    case "Chhattisgarh":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, chhattisgarh);
                        break;
                    case "Delhi":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, delhi);
                        break;
                    case "Gujarat":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, gujarat);
                        break;
                    case "Haryana":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, haryana);
                        break;
                    case "Himachal Pradesh":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, himachalpradesh);
                        break;
                    case "Jammu and Kashmir":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, jammuandkashmir);
                        break;
                    case "Jharkhand":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, jharkhand);
                        break;
                    case "Karnataka":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, karnataka);
                        break;
                    case "Kerala":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, kerala);
                        break;
                    case "Madhya Pradesh":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, madhyapradesh);
                        break;
                    case "Maharashtra":
                        storecity.setVisibility(View.VISIBLE);
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, maharashtra);
                        break;
                    case "Manipur":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, manipur);
                        break;
                    case "Mizoram":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, mizoram);
                        break;
                    case "Orissa":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, orissa);
                        break;
                    case "Punjab":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, punjab);
                        break;
                    case "Puducherry":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, puducherry);
                        break;
                    case "Rajasthan":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, rajashthan);
                        break;
                    case "Tamil Nadu":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, tamilnadu);
                        break;
                    case "Telangana":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, telangana);
                        break;
                    case "Tripura":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, tripura);
                        break;
                    case "Uttar Pradesh":
                        storecity.setVisibility(View.VISIBLE);
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, uttarpradesh);
                        break;
                    case "West Bengal":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, westbengal);
                        break;

                    default:
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, empty);

                }
                storecity.setAdapter(cityadap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        storecity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectcity=cityadap.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.submitoppocity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.show();
                if(selectstate.equalsIgnoreCase("uttar pradesh")||selectstate.equalsIgnoreCase("maharashtra"))
                {
                    selectstate=selectcity;
                }
                if (selectstate.equalsIgnoreCase("Select State")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please select a State", Toast.LENGTH_LONG).show();
                            d.hide();
                        }
                    });
                } else if (selectcity.equalsIgnoreCase("Select Region")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Please select a Region", Toast.LENGTH_LONG).show();
                            d.hide();
                        }
                    });
                } else {

                    String pass=password.getText().toString();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try
                            {
                                Connection con= ConnectionProvider.getCon();
                                Statement st=con.createStatement();
                                ResultSet rs=st.executeQuery("select * from oppologin where username='"+selectstate+"' and password='"+pass+"'");
                                if(rs.next())
                                {
                                    Intent in = new Intent(getApplicationContext(), ClientView.class);
                                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(in);
                                }
                                else
                                {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            d.hide();
                                            Toast.makeText(getApplicationContext(),"Please enter valid login credentials.",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }catch (Exception e)
                            {
                                Log.e("TEJJJ",e.toString());
                            }
                        }
                    }).start();
                 }
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

            default: break;
        }
        return super.onCreateDialog(id);
    }

}
