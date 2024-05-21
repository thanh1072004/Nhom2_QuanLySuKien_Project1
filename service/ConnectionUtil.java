package service;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtil {
    public static Connection getConnection(String servername, String user, String pass, String database, String port) {
        Connection con = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url ="jdbc:sqlserver://"+servername+":"+port+"/"+database;
        }catch(Exception e){
            e.printStackTrace();
        }
        return con;
    }
}
