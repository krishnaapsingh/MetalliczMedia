package com.metalliczmedia.business;

import android.net.Uri;

/**
 * Created by lenovo on 16-03-2016.
 */
public class  Dealer {
    static String storename,storeaddress,storecity,storestate,storepincode,storesalesperson;
    static Uri storecard,sign;

    static void newdealer()
    {
        storename="";
        storeaddress="";
        storecity="";
        storestate="";
        storepincode="";
        storesalesperson="";
        storecard=null;
        sign=null;
    }

}
