package com.metalliczmedia.business;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapterClients extends RecyclerView.Adapter<MyAdapterClients.ViewHolder> {
    private ArrayList<ClientsDetails> mDataset;
    public static ArrayList<Products> productdetails;
    public static String clientselected;
    Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ImageView mImageView;


        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.clientname);
            mImageView = (ImageView) v.findViewById(R.id.clientpic);
        }
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapterClients(Context context,ArrayList<ClientsDetails> myDataset) {
        mDataset = myDataset;
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapterClients.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clients, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).name);
        holder.mImageView.setImageDrawable(mDataset.get(position).clientpic);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productdetails=new ArrayList<Products>();
                clientselected=mDataset.get(position).name;

//                Intent in=new Intent(context,AfterClient.class);
//                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(in);

                ActionSelect as=new ActionSelect();
                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                as.show(fragmentManager, "Select Action");
                as.setCancelable(false);

            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}