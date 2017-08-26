package com.metalliczmedia.business;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InstallationAdapter extends RecyclerView.Adapter<InstallationAdapter.ViewHolder> {
    Context context;
    ArrayList<InstallProducts> products;
    final static int cameraData=0;
    Uri cardpic;
    Uri fileUri;
    ViewHolder vh=null;
    static int selectedproduct;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView receedetails;
        public ImageView receeimg,installimg;
        ProgressBar pb;
        public ViewHolder(View v) {
            super(v);
            receedetails = (TextView) v.findViewById(R.id.receedetail);
            receeimg = (ImageView) v.findViewById(R.id.receepic);
            installimg= (ImageView) v.findViewById(R.id.installpic);
            pb=(ProgressBar) v.findViewById(R.id.installprogressbar);
        }
    }

    public  InstallationAdapter(Context context,ArrayList<InstallProducts> products)
    {
    this.context=context;
        this.products=products;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_installation_adapter, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);

        this.vh=vh;
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.receedetails.setText(products.get(position).ptype + " - " + products.get(position).pname + " - " + products.get(position).size);
        int productid=products.get(position).productid;
Log.e("PIC","Getting");
        final ProgressBar p=holder.pb;
        Picasso.with(context)
                .load("http://env-2776603.ind-cloud.everdata.com/productpic.jsp?productid=" + productid)
                .resize(250, 200)                        // optional
                .rotate(90)                             // optional
                .into(holder.receeimg, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("PIC", "Success");
//                        holder.pb.setVisibility(View.GONE);
                        p.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });
        Log.e("PIC", "Done");


        if(products.get(position).installpic==null)
        {
            Picasso.with(context)
                    .load("http://env-2776603.ind-cloud.everdata.com/productpic2.jsp?productid=" + productid)
//                    .resize(250, 200)                        // optional
//                    .rotate(90)// optional
                    .placeholder(R.drawable.upload)
                    .into(holder.installimg);
        }
        else
        {
            holder.installimg.setImageURI(products.get(position).installpic);
        }
        holder.installimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(context,Camera1.class);
                in.putExtra("position",position);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                selectedproduct=position;
                context.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
