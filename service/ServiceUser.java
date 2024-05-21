package service;

import database.DatabaseConnection;
import model.User;
import java.sql.*;

public class ServiceUser {
    private final Connection con;

    public ServiceUser() {
        con = DatabaseConnection.getInstance().getConnection();
    }
    /*public String authorizeLogin(User user) {
        String username = user.getUsername();
        String password = user.getPassword();

        String dbusername = "";
        String dbpassword = "";

        try{
            Connection con = ConnectionUtil.getConnection("DESKTOP-APP81J1\\SQLEXPRESS", "sa", "tyl1312", "Event Management", "1433");

            PreparedStatement p = null; //create statement

            p = con.prepareStatement("select * from Users where username = ? and password = ?");
            p.setString(1, username);
            p.setString(2, password);
            ResultSet rs = p.executeQuery();

            while (rs.next()) {
                dbusername = rs.getString("username");
                dbpassword = rs.getString("password");

                if(username.equals(dbusername) && password.equals(dbpassword)){
                    return "success";
                }
            }
            p.close(); //close statement
            con.close(); //close connection
        }catch(Exception e){
            e.printStackTrace();
        }
        return "Wrong username or password";
    }*/

    public void authorizeRegister(User user) throws SQLException {
        if (con == null) {
            throw new SQLException("Failed to establish a database connection.");
        }
        PreparedStatement p = con.prepareStatement("insert into Users(first_name, last_name, username, password) values(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
        p.setString(1, user.getFirstName());
        p.setString(2, user.getLastName());
        p.setString(3, user.getUsername());
        p.setString(4, user.getPassword());
        p.execute();
        ResultSet rs = p.getGeneratedKeys();
        if (rs.next()) {
            int userId = rs.getInt(1);
            user.setUserId(userId);
        }
        rs.close();
        p.close();

    }

    public boolean checkDuplicateUser(String username) throws SQLException {
        boolean duplicate = false;
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        PreparedStatement p = con.prepareStatement(sql);
        p.setString(1, username);
        ResultSet rs = p.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            duplicate = true;
        }
        rs.close();
        p.close();
        return duplicate;
    }
}
