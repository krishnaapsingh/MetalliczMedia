package com.metalliczmedia.business;

import android.net.Uri;

/**
 * Created by lenovo on 14-03-2016.
 */
public class Products {
     Uri picuri;
     String picsize,producttype,productname;

     Products(Uri picuri,String picsize,String producttype,String productname )
    {
        this.picuri=picuri;
        this.picsize=picsize;
        this.productname=productname;
        this.producttype=producttype;
    }
}
