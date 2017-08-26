package com.metalliczmedia.business;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Sarjit on 3/30/2016.
 */
public class Dialogpassword extends Dialog {
    public Dialogpassword(Context context) {
        super(context);
    }
    EditText user, phone;
    Button b;
    Dialog d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogpassword);

        user= (EditText) findViewById(R.id.forgotuser);
        phone= (EditText) findViewById(R.id.forgotphone);
        d = new Dialog(getOwnerActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.custom_dialog3);
        d.setCancelable(false);

        b= (Button) findViewById(R.id.forgot);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
