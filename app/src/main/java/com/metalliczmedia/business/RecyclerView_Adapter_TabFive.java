package com.metalliczmedia.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Mohammad Omair on 3/13/2016.
 */
public class RecyclerView_Adapter_TabFive extends RecyclerView.Adapter<RecyclerView_Adapter_TabFive.MyViewHolder> {
    String internalb[]={"Sunboard on Vinyl","One Way Vision","Acrylic Sandwich","Clipon","Standee"};
    private LayoutInflater inflater;
    private Context context;

    public RecyclerView_Adapter_TabFive(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(inflater.inflate(R.layout.simple_list_item1, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(internalb[i]);
        myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(context,Camera.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Recee.producttype="internal";
                Recee.productname=internalb[i];
                context.startActivity(in);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cv;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.brandingnames);
            cv= (CardView) itemView.findViewById(R.id.productscard);
        }
    }
}