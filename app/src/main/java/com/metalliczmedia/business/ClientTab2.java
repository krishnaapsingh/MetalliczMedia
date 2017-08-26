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
public class ClientTab2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_client_tab2, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.clientviewtab2);
        ProgressBar pb= (ProgressBar) rootView.findViewById(R.id.clientviewprogress2);
        CardView cv= (CardView) rootView.findViewById(R.id.cardviewerror2);

        pb.setVisibility(View.VISIBLE);
    new Thread(new Runnable() {
        int i=0;
        @Override
        public void run() {

            ArrayList<String> al= new ArrayList<String>();
            ArrayList<Integer> al2= new ArrayList<Integer>();
            try {
                Connection con= ConnectionProvider.getCon();
                Statement st=con.createStatement();
                ResultSet rs;
                if (ClientHome.Client.equalsIgnoreCase("oppo"))
                {
                    rs=st.executeQuery("select distinct storename,storeno from store join recee where store.storeno=recee.receeid and store.clientname='"+ClientHome.Client.toLowerCase()+"' and store.storestate='"+ClientHome.selectstate+"' or store.storecity='"+ClientHome.selectstate+"' and recee.pic2 is null");
                }
                else
                {
                    rs=st.executeQuery("select distinct storename,storeno from store join recee where store.storeno=recee.receeid and store.storecity='"+ClientHome.selectcity+"' and store.clientname='"+ClientHome.Client.toLowerCase()+"' and store.storestate='"+ClientHome.selectstate+"' and recee.pic2 is null");
                }
                while(rs.next())
                {i++;
                    al.add(rs.getString(1));
                    al2.add(rs.getInt(2));
                }
                RecyclerView_Adapter_ClientView2.al=al;
                RecyclerView_Adapter_ClientView2.al2=al2;
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
                    recyclerView.setAdapter(new RecyclerView_Adapter_ClientView2(getActivity()));

                }
            });


        }
    }).start();
        return rootView;
    }




}




/*
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity().getApplicationContext(),"Downloading",Toast.LENGTH_LONG).show();
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("QWE", "1");

*/
/*

                        final HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://env-2776603.ind-cloud.everdata.com/Upload");

                        Log.e("QWE", "2");
                        MultipartEntity reqEntity = new MultipartEntity();
                        httppost.setEntity(reqEntity);
                        Log.e("QWE", "3");
                        HttpResponse response = null;
*//*

                        try {
*/
/*
                            response = httpclient.execute(httppost);
                            Log.e("QWE", "4");
                            HttpEntity entity = response.getEntity();

                            InputStream is = entity.getContent();
                            Log.e("QWE", "5");
*//*

                            URL url = new URL("http://env-2776603.ind-cloud.everdata.com/downloadppt.jsp");
                            HttpURLConnection c = (HttpURLConnection)
                                    url.openConnection();
                            c.setRequestMethod("GET");
                            c.setDoOutput(true);
                            c.connect();
                            String filename="hello.pptx";
InputStream is=c.getInputStream();
Log.e("TTTT",Environment.getRootDirectory().getPath());


                   //         File mediaStorageDir = Environment.getRootDirectory();
  */
/*                          File file = new File(getContext().getFilesDir(), filename);
                            file.mkdirs();
                            Log.e("QWE", "2");
*//*


//                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);

//                            File temp = new File(Environment.getRootDirectory().getPath()+"/MetalliczMedia");
  //                          temp.mkdirs();
                            File file= new File(Environment.getRootDirectory().getPath()+"/hello.pptx");

                            if (!file.mkdirs()) {
                                Log.e("QWE", "Directory not created");
                            }

                            //       File file = new File(mediaStorageDir.getPath() + File.separator +
                       //             "abc.pptx");

                            FileOutputStream fos=new FileOutputStream(file);
                            Log.e("QWEe", getContext().getFilesDir().getPath());

//                            FileOutputStream fos=  getActivity().openFileOutput(filename, Context.MODE_PRIVATE);

                            Log.e("QWEe", fos.toString());

                            byte[] buffer = new byte[1000];
                            int len1 = 0;
                            int i=0;
                            while ((len1 = is.read(buffer)) != -1) {
                                fos.write(buffer, 0, len1);
                                Log.e("QWEe", "in while"+ ++i);
                            }
                            Log.e("QWEe", "Donef");

                            fos.close();
                        } catch (IOException e) {
//                            e.printStackTrace();
                            Log.e("QWE", e.getMessage());
                        }

                    }
                });
                t.start();

            }
        });
*/
