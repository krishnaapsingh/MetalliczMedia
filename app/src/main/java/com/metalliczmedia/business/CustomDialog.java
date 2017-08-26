package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

/**
 * Created by Sarjit on 3/29/2016.
 */
public class CustomDialog extends Dialog{
    public CustomDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
        WebView wv = (WebView) findViewById(R.id.dialogwebview);
        TextView tv = (TextView) findViewById(R.id.dialogtextview);
        wv.loadUrl("file:///android_asset/demo.html");
        tv.setText("Uploading");


    }

}
