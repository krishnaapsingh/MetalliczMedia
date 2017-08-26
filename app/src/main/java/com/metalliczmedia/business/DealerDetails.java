package com.metalliczmedia.business;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/*
import com.adobe.creativesdk.aviary.AdobeImageIntent;*/


public class DealerDetails extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    EditText name, address, state, city, pincode, salesperson, card, sign;
    //String latitude = Logintab.latitude;
    // String logi = Logintab.longitude;
    TextView tv;
    Uri cardpic;
    Button submit;

    //   String userChoosenTask;
//    final static int cameraData = 0;
//    final static int photoData1 = 2;
//    final static int galleryData = 3;
    //Uri fileUri;
    String refrenceCity = "REFRESH_CITY";
    //String  refreshState="REFRESH_STATE";
    final private int STORAGE_PERMISSION_CODE = 23;
    // private static final int REQUEST_CODE = 99;
    // private int preference;
    //  LocationManager locationManager;
    // String mprovider;
    public static Uri imgUri;
    // public Uri imgUri = CropResultActivity.imgUri;
    public static Uri signUri;


    private static final String TAG = "MainActivity";
    private GoogleApiClient mGoogleApiClient;
    public Location mLastLocation = Logintab.mLastLocation;
    //    private TextView latitudePosition;
//    private TextView longitudePosition;
//    private TextView currentCity;
    private final int REQUEST_LOCATION = 200;
    private final int REQUEST_CHECK_SETTINGS = 300;
    private LocationRequest mLocationRequest;
    private PendingResult<LocationSettingsResult> result;
    private LocationSettingsRequest.Builder builder;
    public static final String MyPREFERENCES = "MyPrefs";

    ImageView imgstoreState, imgstoreCity;

    SharedPreferences sharedpreferences;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_details);
        // appid = (EditText) findViewById(R.id.appid);
        //  rtcode = (EditText) findViewById(R.id.rtcode);
//        if (!MyAdapterClients.clientselected.equalsIgnoreCase("gionee")) {
//            appid.setVisibility(View.GONE);
//            rtcode.setVisibility(View.GONE);
//        }

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();


        if (imgUri != null) {
            // Toast.makeText(DealerDetails.this, "Uri1" + String.valueOf(imgUri), Toast.LENGTH_SHORT).show();
            //card.setText(String.valueOf(imgUrl));
        }
        // Toast.makeText(DealerDetails.this, "Uri3" + String.valueOf(imgUri), Toast.LENGTH_SHORT).show();
        //  mGoogleApiClient = new GoogleApiClient.Builder(this)
        // .addApi(LocationServices.API)
        //.addConnectionCallbacks(this)
        //.addOnConnectionFailedListener(this).build();
        //checkForLocation();

        imgstoreCity = (ImageView) findViewById(R.id.imgstorecity);
        imgstoreState = (ImageView) findViewById(R.id.imgstorestate);

        imgstoreCity.setOnClickListener(this);
        imgstoreState.setOnClickListener(this);


        name = (EditText) findViewById(R.id.storename);
        address = (EditText) findViewById(R.id.storeaddress);
        state = (EditText) findViewById(R.id.storestate);
        city = (EditText) findViewById(R.id.storecity);
        pincode = (EditText) findViewById(R.id.storepincode);
        salesperson = (EditText) findViewById(R.id.storesalesperson);
        card = (EditText) findViewById(R.id.storecardpic);

        sign = (EditText) findViewById(R.id.sign);
        city.setText(AfterClient.selectcity);
        state.setText(AfterClient.selectstate);
        tv = (TextView) findViewById(R.id.tnc);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(DealerDetails.this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.custom_dialog2);
                d.show();

            }
        });

        submit = (Button) findViewById(R.id.storesubmit);
        //  card.setOnClickListener(new ScanButtonClickListener());

        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DealerDetails.this, ImageCaptureActivity.class);
                startActivity(intent);
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*
                Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                        "://" + getResources().getResourcePackageName(R.drawable.white)
                        + '/' + getResources().getResourceTypeName(R.drawable.white) + '/' + getResources().getResourceEntryName(R.drawable.white) );
*/
//                Uri imageUri = Uri.parse("android.resource://com.metalliczmedia.business/drawable/white");
//                Uri imageUri = Uri.parse("http://env-2776603.ind-cloud.everdata.com/whiteimg.png");
//
//                Intent imageEditorIntent = new AdobeImageIntent.Builder(getApplicationContext())
//                        .setData(imageUri).withToolList(new ToolLoaderFactory.Tools[]{ToolLoaderFactory.Tools.DRAW})
//                        .withOutputSize(MegaPixels.Mp0)
//                        .build();
//                startActivityForResult(imageEditorIntent, 1);

                Intent intent = new Intent(DealerDetails.this, SignatureActivity.class);

                startActivityForResult(intent, 2);
                //startActivity(intent);
            }
        });
        Dealer.newdealer();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dealer.storename = name.getText().toString();
                Dealer.storeaddress = address.getText().toString();
                Dealer.storecity = city.getText().toString();
                Dealer.storestate = state.getText().toString();
                Dealer.storepincode = pincode.getText().toString();
                Dealer.storesalesperson = salesperson.getText().toString();
                Dealer.storecard = imgUri;

                if(message!=null){
                    Uri myUri = Uri.parse(message);
                    Dealer.sign = myUri;
                }

//              String storename = name.getText().toString();
//                String storeaddress = address.getText().toString();
//                String storecity = city.getText().toString();
//                String storestate = state.getText().toString();
//                String storepincode = pincode.getText().toString();
//                String storesalesperson = salesperson.getText().toString();
//
//                String signg = sign.getText().toString();
//                String hhd = card.getText().toString();

                if (name.getText().toString().equalsIgnoreCase("") || address.getText().toString().equalsIgnoreCase("") || city.getText().toString().equalsIgnoreCase("") || state.getText().toString().equalsIgnoreCase("") || pincode.getText().toString().equalsIgnoreCase("") || salesperson.getText().toString().equalsIgnoreCase("") || sign.getText().toString().equalsIgnoreCase("") || card.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getApplicationContext(), "Please fill all details !", Toast.LENGTH_LONG).show();
                } else {
                    // if (MyAdapterClients.clientselected.equalsIgnoreCase("gionee")) {
//                        if (appid.getText().toString().equalsIgnoreCase("") || rtcode.getText().toString().equalsIgnoreCase("")) {
                    // Toast.makeText(getApplicationContext(), "Please fill all details !", Toast.LENGTH_LONG).show();
//                        } else {
//                            appid = appid.getText().toString();
//                            rtcode = rtcode.getText().toString();
//                            Intent in = new Intent(getApplicationContext(), Recee.class);
//                            startActivity(in);
//                        }
                    // } else {
                    Intent in = new Intent(getApplicationContext(), Recee.class);
                    startActivity(in);
                    finish();
                }
                // }

            }
        });
//        String lat = (sharedpreferences.getString("LAT", ""));
//        String lng = (sharedpreferences.getString("LNG", ""));

        double latitude = Double.longBitsToDouble(sharedpreferences.getLong("Latitude", 0));
        double longitude = Double.longBitsToDouble(sharedpreferences.getLong("Longitude", 0));

        // Toast.makeText(DealerDetails.this, "Lat DDD"+latitude, Toast.LENGTH_SHORT).show();
        getAddressFromLocation(latitude, longitude, getApplicationContext(), new GeoCoderHandler());


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

                /* 4) Make a case for the request code we passed to startActivityForResult() */


                    /* 5) Show the image! */
//                Uri editedImageUri = data.getParcelableExtra(AdobeImageIntent.EXTRA_OUTPUT_URI);
//                sign.setText(String.valueOf(editedImageUri));

            case 2:

                message = data.getStringExtra("SIGN_URI");
                //  signUri=    (Uri) data,getOutputMediaFileUri();
                sign.setText(message);


                break;
        }
    }


//    private void onSelectFromGalleryResult(Intent data) {
//        if (data != null) {
//            try {
//                Intent imageEditorIntent = new AdobeImageIntent.Builder(this)
//                        .setData(data.getData()).withToolList(new ToolLoaderFactory.Tools[]{})
//                        .withOutputSize(MegaPixels.Mp0)
//                        .withOutputQuality(30)
//                        .build();
//
//                startActivityForResult(imageEditorIntent, photoData1);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

 //   private static Uri getOutputMediaFileUri() {
 //       return Uri.fromFile(getOutputMediaFile());
  //  }


//    private static File getOutputMediaFile() {
//        // To be safe, you should check that the SDCard is mounted
//        // using Environment.getExternalStorageState() before doing this.
//
//        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(
//        ), "MyCameraApp");
//        // This location works best if you want the created images to be shared
//        // between applications and persist after your app has been uninstalled.
//
//        // Create the storage directory if it does not exist
//        if (!mediaStorageDir.exists()) {
//            if (!mediaStorageDir.mkdirs()) {
//                Log.d("MyCameraApp", "failed to create directory");
//                return null;
//            }
//        }
//
//        // Create a media file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        File mediaFile = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//
//        return mediaFile;
//    }

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
           /* case R.id.aboutthedev:
                Intent in = new Intent(getApplicationContext(), YourActivity.class);
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

//    private void selectImage() {
//        final CharSequence[] items = {"Take Photo", "Choose from Library",
//                "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(DealerDetails.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    cameraIntent();
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask = "Choose from Library";
//                    galleryIntent();
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//    }

//    private void cameraIntent() {
//        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        fileUri = getOutputMediaFileUri();
//        i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
//        startActivityForResult(i, cameraData);
//    }

//    private void galleryIntent() {
////        Intent intent = new Intent();
////        intent.setType("image/*");
////        intent.setAction(Intent.ACTION_GET_CONTENT);//
////        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////        startActivityForResult(Intent.createChooser(intent, "Select File"),galleryData);
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(galleryIntent, galleryData);
//    }

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

            default:
                break;
        }
        return super.onCreateDialog(id);
    }


//    public void loadGallery(Context context1) {
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//
//
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
//                    , Manifest.permission.CAMERA}, STORAGE_PERMISSION_CODE);
//
//            return;
//        } else {
//            //startScan(preference);
//
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //startScan(preference);
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_LOCATION)

        {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mLastLocation != null) {

                    //    Toast.makeText(DealerDetails.this, "lat" + mLastLocation.getLatitude() + "lng" + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
//                                latitudePosition.setText(String.valueOf(mLastLocation.getLatitude()));
//                                longitudePosition.setText(String.valueOf(mLastLocation.getLongitude()));
                    // getAddressFromLocation(mLastLocation, getApplicationContext(), new GeoCoderHandler());
                }
            } else {
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
            return;
        }

    }

//    public void scanData() {
//        startScan(preference);
//    }

//    protected void startScan(int preference) {
//        Intent intent = new Intent(this, ScanActivity.class);
//        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
//        startActivityForResult(intent, REQUEST_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
//            Bitmap bitmap = null;
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                getContentResolver().delete(uri, null, null);
//
//                card.setText(String.valueOf(uri));
//                //Toast.makeText(this, "Bitmap After Scan"+bitmap, Toast.LENGTH_SHORT).show();
//                // scannedImageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void checkForLocation(){
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//
//        mprovider = locationManager.getBestProvider(criteria, false);
//
//        if (mprovider != null && !mprovider.equals("")) {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            Location location = locationManager.getLastKnownLocation(mprovider);
//            locationManager.requestLocationUpdates(mprovider, 15000, 1, this);
//
//            if (location != null)
//                onLocationChanged(location);
//            else
//                Toast.makeText(getBaseContext(), "No Location Provider Found Check Your Code", Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    public void onLocationChanged(Location location) {
//        TextView longitude = (TextView) findViewById(R.id.textView);
//        TextView latitude = (TextView) findViewById(R.id.textView1);

        // Toast.makeText(getBaseContext(), "Current Longitude:" + location.getLongitude() + "Current Latitude:" + location.getLatitude(), Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

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
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        if (ActivityCompat.checkSelfPermission(DealerDetails.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(DealerDetails.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(DealerDetails.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                        } else {

                            if (mLastLocation != null) {

                                // Toast.makeText(DealerDetails.this,"lat"+mLastLocation.getLatitude() + "lng"+mLastLocation.getLongitude(),Toast.LENGTH_SHORT).show();
//                                latitudePosition.setText(String.valueOf(mLastLocation.getLatitude()));
//                                longitudePosition.setText(String.valueOf(mLastLocation.getLongitude()));
                                getAddressFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), getApplicationContext(), new GeoCoderHandler());
                            }
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(DealerDetails.this, REQUEST_CHECK_SETTINGS);
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

    public static void getAddressFromLocation(double lat, double lng, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {

//                Double doubleLat=Double.parseDouble(lat);
//                Double doubleLng=Double.parseDouble(lng);
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                Address address = null;
                try {
                    List<Address> list = geocoder.getFromLocation(lat, lng, 1);
                    if (list != null && list.size() > 0) {
                        address = list.get(0);
                        // sending back first address line and locality
                        result = address.getAddressLine(0) + ", " + address.getLocality() + ", " + address.getCountryName();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address_line", address.getAddressLine(0));
                        bundle.putString("locality", address.getLocality());

                        if (address.getCountryName() != null) {
                            bundle.putString("country_name", address.getCountryName());
                        } else {
                            bundle.putString("country_name", "");
                        }
                        if (address.getAdminArea() != null) {
                            bundle.putString("state_name", address.getAdminArea());
                        } else {
                            bundle.putString("state_name", address.getLocality());
                        }
                        if (address.getPostalCode() != null) {
                            bundle.putString("postal_code", address.getPostalCode());
                        } else {
                            bundle.putString("postal_code", "");
                        }

                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgstorecity:
                if (mLastLocation != null) {
                    refrenceCity = "REFRESH_CITY";
                    // Toast.makeText(DealerDetails.this,"lat"+mLastLocation.getLatitude() + "lng"+mLastLocation.getLongitude(),Toast.LENGTH_SHORT).show();
//                                latitudePosition.setText(String.valueOf(mLastLocation.getLatitude()));
//                                longitudePosition.setText(String.valueOf(mLastLocation.getLongitude()));

                    // Toast.makeText(this, "Button1 clicked.", Toast.LENGTH_SHORT).show();
                    getAddressFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), getApplicationContext(), new GeoCoderHandler());
                }
                break;
            case R.id.imgstorestate:
                if (mLastLocation != null) {
                    refrenceCity = "REFRESH_STATE";
                    // Toast.makeText(DealerDetails.this,"lat"+mLastLocation.getLatitude() + "lng"+mLastLocation.getLongitude(),Toast.LENGTH_SHORT).show();
//                                latitudePosition.setText(String.valueOf(mLastLocation.getLatitude()));
//                                longitudePosition.setText(String.valueOf(mLastLocation.getLongitude()));
                    //Toast.makeText(this, "Button2 clicked.", Toast.LENGTH_SHORT).show();
                    getAddressFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), getApplicationContext(), new GeoCoderHandler());
                }
                //Inform the user the button2 has been clicked
                //  Toast.makeText(this, "Button2 clicked.", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String address_line, locality = null, country_name = null, state_name = null, postal_code = null;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locality = bundle.getString("locality");
                    country_name = bundle.getString("country_name");
                    state_name = bundle.getString("state_name");
                    postal_code = bundle.getString("postal_code");
                    break;
                default:
                    result = null;
            }
            city.setText(locality);
            state.setText(state_name);


            if (refrenceCity.equalsIgnoreCase("REFRESH_CITY")) {
                city.setText(locality);
            } else if (refrenceCity.equalsIgnoreCase("REFRESH_STATE")) {
                state.setText(state_name);
            }
            // pincode.setText(postal_code);
            //Toast.makeText(DealerDetails.this,"Address"+result,Toast.LENGTH_SHORT).show();
            //currentCity.setText(result);
        }
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (imgUri != null) {
            //Toast.makeText(DealerDetails.this, "Uri2" + String.valueOf(imgUri), Toast.LENGTH_SHORT).show();
            card.setText(String.valueOf(imgUri));
        }
        // Toast.makeText(DealerDetails.this, "Uri4" + String.valueOf(imgUri), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        imgUri = null;
        finish();
    }


}
