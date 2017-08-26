package com.metalliczmedia.business;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connection.ConnectionProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientTab1 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_client_tab1, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.clientviewtab1);
        ProgressBar pb= (ProgressBar) rootView.findViewById(R.id.clientviewprogress1);
        CardView cv= (CardView) rootView.findViewById(R.id.cardviewerror1);
        pb.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            int i=0;
           @Override
           public void run() {
               try {
                   Connection con= ConnectionProvider.getCon();
                   Statement st=con.createStatement();
                   ResultSet rs;
                   if(ClientHome.Client.equalsIgnoreCase("oppo"))
                   {
                       rs=st.executeQuery("select distinct storename,storeno from store join recee where store.storeno=recee.receeid and store.clientname='"+ClientHome.Client.toLowerCase()+"' and store.storestate='"+ClientHome.selectstate+"' or store.storecity='"+ClientHome.selectstate+"' and recee.pic2 is not null");
                   }
                   else
                   {
                       rs=st.executeQuery("select distinct storename,storeno from store join recee where store.storeno=recee.receeid and store.storecity='"+ClientHome.selectcity+"' and store.clientname='"+ClientHome.Client.toLowerCase()+"' and store.storestate='"+ClientHome.selectstate+"' and recee.pic2 is not null");
                   }
                   ArrayList<String> al= new ArrayList<String>();
                   ArrayList<Integer> al2= new ArrayList<Integer>();
                   while(rs.next())
                   {i++;
                       al.add(rs.getString(1));
                       al2.add(rs.getInt(2));
                   }
                   RecyclerView_Adapter_ClientView.al=al;
                   RecyclerView_Adapter_ClientView.al2=al2;
//                    o.notifyAll();
               } catch (SQLException e) {
                   e.printStackTrace();
               }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb.setVisibility(View.GONE);
                        if(i==0)
                        {
                            cv.setVisibility(View.VISIBLE);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(new RecyclerView_Adapter_ClientView(getActivity()));

                        }
                    });
           }
       }).start();

        return rootView;
    }


}
