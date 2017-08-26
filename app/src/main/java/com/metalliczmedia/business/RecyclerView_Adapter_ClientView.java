package com.metalliczmedia.business;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Sarjit on 3/16/2016.
 */
public class RecyclerView_Adapter_ClientView extends RecyclerView.Adapter<RecyclerView_Adapter_ClientView.MyViewHolder>{
    public static ArrayList<String> al=new ArrayList<>();
    public static ArrayList<Integer> al2=new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;
    public RecyclerView_Adapter_ClientView(Context context)  {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {



        return new MyViewHolder(inflater.inflate(R.layout.clientviewrecyclerlayout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {

        Log.e("TEH"," "+i+" "+al.size());
//        myViewHolder.textView.setText("hi");
        myViewHolder.textView.setText(al.get(i));
        myViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked at " + i, Toast.LENGTH_LONG).show();
                DownloadManager downloadManager= (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                Uri uri=Uri.parse("http://env-2776603.ind-cloud.everdata.com/downloadppt.jsp?receeid="+al2.get(i));
                DownloadManager.Request request= new DownloadManager.Request(uri);
                request.setDescription("Recee PPT ").setTitle("ReceePPT"+al2.get(i)).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                downloadManager.enqueue(request);
                Toast.makeText(context, "Clicked at " + i, Toast.LENGTH_LONG).show();


            }
        });
        }

    @Override
    public int getItemCount() {
        return al.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CardView cv;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.recyclerViewClientView1);
            cv=(CardView)itemView.findViewById(R.id.clientviewcardview);
        }
    }


}
