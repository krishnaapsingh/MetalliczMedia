package com.metalliczmedia.business;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReceeOrder extends Fragment {


    public ReceeOrder() {
        // Required empty public constructor
    }

    TextView tv;
    CardView cv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_recee_order, container, false);
        tv= (TextView) v.findViewById(R.id.receeordertextview);
        tv.setText("No of products added = "+ MyAdapterClients.productdetails.size());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(getActivity().getApplicationContext(),Orders.class);
                startActivity(in);
            }
        });

        return v;
    }


}
