package connection;

/**
 * Created by Hp R-Series on 10-09-2015.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class Global {
    public static Connection conn;

    public static Connection getInstance()
    {
        if(conn==null)
        {
            while(conn==null) {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://mysql16872-env-2776603.ind-cloud.everdata.com:3306/mm?autoReconnect=true", "mm", "mm");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Log.e("Connection NULL", e.toString());
                }
            };
            return conn;

        }
        else
        {
            return conn;
        }
    }

    public static String read(Context context){

        SharedPreferences prefs=context.getSharedPreferences("VStockPref", Context.MODE_PRIVATE);
    String a=prefs.getString("cid","-1");
    return a;
}
    public static void write(Context context,String cid){
        SharedPreferences prefs=context.getSharedPreferences("VStockPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putString("cid",cid);
        editor.apply();
    }
}
