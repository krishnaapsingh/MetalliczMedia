package com.metalliczmedia.business;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by lenovo on 18-03-2016.
 */
public class ActionSelect extends DialogFragment {
    static String action;
    static String all[]={"Recee","Installation","Send Today's Report"};
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        action="";
        builder.setTitle("Select Action")
                .setItems(all, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    action=all[which];

                        if(action.equalsIgnoreCase(all[0]))
                        {
                            Intent intent = new Intent(getActivity().getApplicationContext(),DealerDetails.class);
                            startActivity(intent);
                        }
                        else if(action.equalsIgnoreCase(all[1]))
                        {
                            Intent intent = new Intent(getActivity().getApplicationContext(),Installation.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getActivity().getApplicationContext(),PptSent.class);
                            startActivity(intent);
                        }

                    }
                });
        return builder.create();
    }
}