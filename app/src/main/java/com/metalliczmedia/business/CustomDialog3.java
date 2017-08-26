package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;

/**
 * Created by Sarjit on 3/30/2016.
 */
public class CustomDialog3 extends Dialog {
    public CustomDialog3(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog3);
        WebView wv = (WebView) findViewById(R.id.dialogwebview1);
        wv.loadUrl("file:///android_asset/demo.html");



    }

}
