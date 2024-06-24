package src.service;

import src.database.DatabaseConnection;
import src.model.Login;
import src.model.User;

import java.security.MessageDigest;
import java.sql.*;

public class ServiceUser {
    private final Connection connection;

    public ServiceUser() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public User authorizeLogin(Login dataLogin) throws SQLException {
        User data = null;
        try(PreparedStatement ps = connection.prepareStatement("select user_id, username, password from Users where username = ? and password = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ps.setString(1, dataLogin.getUsername());
            ps.setString(2, md5(dataLogin.getPassword()));
            try(ResultSet rs = ps.executeQuery()){
                if(rs.first()){
                    int userId = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    data = new User(userId, username, password);
                }
            }
        }
        return data;
    }


    public void authorizeRegister(User user) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement("insert into Users(first_name, last_name, username, password) values(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, md5(user.getPassword()));
            ps.execute();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                int userId = rs.getInt(1);
                user.setUserId(userId);
            }}
        }
    }

    public boolean checkDuplicateUser(String username) throws SQLException {
        boolean duplicate = false;
        try(PreparedStatement p = connection.prepareStatement("select count(*) from Users where username = ?")){
            p.setString(1, username);
            try(ResultSet rs = p.executeQuery()){
                if (rs.next() && rs.getInt(1) > 0) {
                    duplicate = true;
                }
            }
        }
        return duplicate;
    }



    public static String md5(String msg) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(msg.getBytes());
            byte byteData[] = md.digest();
            //convert the byte to hex format method 1
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return  sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public User getUser(int user_id) throws SQLException {
        User user = null;
        try(PreparedStatement ps = connection.prepareStatement("select * from Users where user_id = ?")){
            ps.setInt(1, user_id);
            try(ResultSet rs = ps.executeQuery()){if(rs.next()){
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(user_id, username, password);
            }

            }
        }
        return user;
    }

    public User getUser(String username) throws SQLException {
        User user = null;
        try(PreparedStatement ps = connection.prepareStatement("select * from Users where username = ?")){
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int user_id = rs.getInt("user_id");
                    String password = rs.getString("password");
                    user = new User(user_id, username, password);
                }
            }
        }
        return user;
    }

    public String getUsernameFromUserID(int userId) throws SQLException {
        String username = null;
        try(PreparedStatement ps = connection.prepareStatement("SELECT username FROM Users WHERE user_id = ?")){
            ps.setInt(1, userId);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }
        }
        return username;
    }
}
