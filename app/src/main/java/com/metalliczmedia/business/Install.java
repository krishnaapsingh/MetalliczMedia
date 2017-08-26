package com.metalliczmedia.business;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectionProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class Install extends Fragment {
    static ArrayList<InstallProducts> products=new ArrayList<>();
    InstallationAdapter adap;

    public Install() {
        // Required empty public constructor
    }
        Dialog d;
    void installshow(String receeid)
    {
        d = new Dialog(getActivity());
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.custom_dialog3);
        d.setCancelable(false);
        WebView wv = (WebView) d.findViewById(R.id.dialogwebview1);
        wv.setBackgroundColor(Color.TRANSPARENT);
        wv.loadUrl("file:///android_asset/demo.html");
        d.show();

        String recee=receeid;
        Thread t=new Thread(new Runnable() {
            String receeid=recee;
            @Override
            public void run() {
                try
                {
                    products=new ArrayList<>();
                    Connection con= ConnectionProvider.getCon();
                    Statement st=con.createStatement();
                    ResultSet rs=st.executeQuery("select productid, pname, ptype, size from recee where receeid="+receeid);
                    while(rs.next())
                    {
                        products.add(new InstallProducts(rs.getInt(1),rs.getString(4),rs.getString(3),rs.getString(2),null));
                    }
                }catch (Exception e)
                {
                    Log.e("Error Install",e.toString());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adap=new InstallationAdapter(getActivity().getApplicationContext(), products);
                        recyclerView.setAdapter(adap);
                        d.hide();
                    }
                });

            }
        });
        t.start();
    }


    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_install, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listinstall);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        try
        {
            if(adap!=null)
            {
                adap.notifyItemChanged(InstallationAdapter.selectedproduct);
            }
        }catch (Exception e)
        {

        }
    }
}
