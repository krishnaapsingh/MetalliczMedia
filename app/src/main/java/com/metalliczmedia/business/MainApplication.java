package com.metalliczmedia.business;

import android.app.Application;

/*import com.adobe.creativesdk.aviary.IAviaryClientCredentials;
import com.adobe.creativesdk.foundation.AdobeCSDKFoundation;*/

/**
 * Created by lenovo on 14-03-2016.
 */
public class MainApplication extends Application  {

    /* Be sure to fill in the two strings below. */
    private static final String CREATIVE_SDK_CLIENT_ID = "85110425848a47bb9d71b696958079da";
    private static final String CREATIVE_SDK_CLIENT_SECRET = "43685dca-8f13-4da2-8df1-f2aa2aeee294";

    @Override
    public void onCreate() {
        super.onCreate();
       // AdobeCSDKFoundation.initializeCSDKFoundation(getApplicationContext());
    }

//    @Override
//    public String getClientID() {
//        return CREATIVE_SDK_CLIENT_ID;
//    }
//
//    @Override
//    public String getClientSecret() {
//        return CREATIVE_SDK_CLIENT_SECRET;
//    }


    public String getBillingKey() {
        return ""; // Leave this blank
    }}