package com.metalliczmedia.business;

import android.net.Uri;

/**
 * Created by lenovo on 22-03-2016.
 */
public class InstallProducts {
    int productid;
    String size;
    String ptype;
    String pname;
    Uri installpic;

    InstallProducts(int productid, String size, String ptype, String pname, Uri installpic)
    {
        this.productid=productid;
        this.size=size;
        this.ptype=ptype;
        this.pname=pname;
        this.installpic=installpic;
    }
}
