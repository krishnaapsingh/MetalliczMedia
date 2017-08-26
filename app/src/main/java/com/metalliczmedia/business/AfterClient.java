package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AfterClient extends AppCompatActivity {
    static  String selectstate;
    static  String selectcity;

    ArrayAdapter<String> cityadap;
    ArrayAdapter<String> stateadap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_client);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionSelect as=new ActionSelect();
      //  as.show(getSupportFragmentManager(), "Select Action");
        as.setCancelable(false);
        Spinner storestate = (Spinner) findViewById(R.id.state);
        Spinner storecity = (Spinner) findViewById(R.id.city);

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

        String maharashtra[]={"Select City","Mumbai","Pune","Nagpur","Thane","Pimpri-Chinchwad",
                "Nashik","Kalyan-Dombivali","Vasai-Virar","Aurangabad",
                "Navi Mumbai","Solapur","Mira-Bhayandar","Bhiwandi","Amravati","Nanded",
                "Kolhapur","Ulhasnagar","Sangli-Miraj & Kupwad","Malegaon","Jaigaon","Akola",
                "Latur","Dhule","Ahmednagar","Satara","Chandrapur","Parbhani","Ichalkaranji",
                "Jalna","Ambernath",
                "Jalgaon","Yavatmal","Raigad","Buldhana","Beed","Osmanabad","Nandurbar",
                "Ratnagiri","Gondia","Wardha","Bhandara","Washim","Hingoli","Gadchiroli","Sindhudurg","Palghar","Other"};

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

        String goa[]={"Select City","Mapusa","Panaji","Margao","Ponda","Vasco","Other"};

        String uttarpradesh[]={"Select City","Lucknow","Kanpur","Ghaziabad","Agra","Meerut",
                "Varanasi","Allahabad","Bareilly","Moradabad","Aligarh","Saharanpur",
                "Gorakhpur","Noida","Firozabad","Loni","Jhansi","Muzaffarnagar","Mathura",
                "Shahjahanpur","Rampur","Mau","Farrukhabad","Hapur","Bally","Barasat",
                "Etawah","Miezapur","Haridwar","North Dumdum","Baranagr","Other"};
        String uttrakhand[]={"Select City","Dehradun","Other"};

        String westbengal[]={"Select City","Kolkata","Howrah","Durgapur","Asansol","Siliguri",
                "Maheshtala","Rajpur Sonarpur","South Dumdum","Gopalpur","Bhatpara",
                "Panihati","Kamarhati","Bardhaman","Kulti","Other"};

        String states[]={"Select State","Andhra Pradesh","Assam","Bihar","Chandigarh","Chhattisgarh","Delhi","Gujarat","Haryana","Himachal Pradesh",
                "Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur","Mizoram","Orissa","Punjab",
                "Puducherry","Rajasthan","Tamil Nadu","Telanagan","Tripura","Uttar Pradesh","Uttrakhand","Goa","West Bengal","Other"};

        String empty[]={"Select City"};
        stateadap=new ArrayAdapter<String>(this, R.layout.spinnerlayout,states);
        cityadap=new ArrayAdapter<String>(this, R.layout.spinnerlayout,empty);
        storestate.setAdapter(stateadap);
        storecity.setAdapter(cityadap);
        storestate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectstate = states[position];
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
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, uttarpradesh);
                        break;
                    case "Uttrakhand":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, uttrakhand);
                        break;
                    case "Goa":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, goa);
                        break;
                    case "West Bengal":
                        cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, westbengal);
                        break;

                    default:  cityadap = new ArrayAdapter<String>(getApplication(), R.layout.spinnerlayout, empty);

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.submitcity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectstate.equalsIgnoreCase("Select State"))
                {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Please select a State",Toast.LENGTH_LONG).show();
                    }
                });
                }
                else if(selectcity.equalsIgnoreCase("Select City"))
                {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Please select a City",Toast.LENGTH_LONG).show();
                    }
                });
                }
                else
                {
                    if(ActionSelect.action.equalsIgnoreCase("recee"))
                    {
                        Intent in = new Intent(getApplicationContext(), DealerDetails.class);
                        startActivity(in);

                    }
                    else if(ActionSelect.action.equalsIgnoreCase("installation"))
                    {
                        Intent in = new Intent(getApplicationContext(), Installation.class);
                        startActivity(in);

                    }
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
            /*case R.id.aboutthedev:
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
                android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(this);
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
