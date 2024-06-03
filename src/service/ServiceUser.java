package service;

import database.DatabaseConnection;
import model.Login;
import model.User;
import java.sql.*;

public class ServiceUser {
    private final Connection con;

    public ServiceUser() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public User authorizeLogin(Login dataLogin) throws SQLException {
        User data = null;
        PreparedStatement ps = con.prepareStatement("select user_id, username, password from Users where username = ? and password = ?",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, dataLogin.getUsername());
        ps.setString(2, dataLogin.getPassword());
        ResultSet rs = ps.executeQuery();
        if(rs.first()){
            int userId = rs.getInt("user_id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            data = new User(userId, username, password);
        }
        rs.close();
        ps.close();
        return data;
    }


    public void authorizeRegister(User user) throws SQLException {
        if (con == null) {
            throw new SQLException("Failed to establish a database connection.");
        }
        PreparedStatement ps = con.prepareStatement("insert into Users(first_name, last_name, username, password) values(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getUsername());
        ps.setString(4, user.getPassword());
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int userId = rs.getInt(1);
            user.setUserId(userId);
        }
        rs.close();
        ps.close();

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
