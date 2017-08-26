package connection;

/**
 * Created by Sarjit on 3/15/2016.
 */
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionProvider {


    static private String user = "root";
    static private String pass = "root";
    static private Connection  conn = null;

    public static Connection getCon()
    {conn=null;

        while(conn==null)
        {
            try {
                Log.e("TEJ", "1");

                Class.forName("com.mysql.jdbc.Driver");
//                System.out.println("connecting!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Log.e("TEJ","2");

//			conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/bose",user,pass);
//			conn= DriverManager.getConnection("jdbc:mysql://mysql42994-metallicz.whelastic.net:3306/bose?autoReconnect=true","bose","bose");
                conn= DriverManager.getConnection("jdbc:mysql://mysql16872-env-2776603.ind-cloud.everdata.com:3306/mm?autoReconnect=true","root","ZNquLyjjTy");
                Log.e("TEJ","3");

//                System.out.println("connected"); // THIS IS NOT BEING RETURNED
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e("TEJ",e.getMessage());
  //              e.printStackTrace();
            }
        }
        return conn;
    }


}
