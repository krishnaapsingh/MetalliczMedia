package com.metalliczmedia.business;

import android.graphics.drawable.Drawable;

/**
 * Created by lenovo on 13-03-2016.
 */
public class ClientsDetails {

    String name;
    Drawable clientpic;
    ClientsDetails(String name, Drawable img)
    {
        this.name=name;
        this.clientpic=img;
    }

}
