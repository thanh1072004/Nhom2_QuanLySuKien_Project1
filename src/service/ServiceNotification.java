package src.service;

import src.database.DatabaseConnection;

import src.model.Notification;
import src.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceNotification {

    private final Connection connection;
    private ServiceUser serviceUser;


    public ServiceNotification() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addNotification(User user, String message) throws SQLException {
        if(connection == null) {
            throw new SQLException("Failed to connect to database");
        }
        PreparedStatement ps = connection.prepareStatement("insert into notification(user_id, message, isRead, isSeen) values (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, user.getUserId());
        ps.setString(2, message);
        ps.setInt(3, 0);
        ps.setInt(4, 0);
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        Notification notification = new Notification(user, message, false);
        if (rs.next()) {
            int id = rs.getInt(1);
            notification.setId(id);
        }
        rs.close();
        ps.close();
    }

    public List<Notification> getNotifications(User user) throws SQLException {
        serviceUser = new ServiceUser();
        List<Notification> notifications = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("select * from notification where user_id = ? ORDER BY id DESC");
        ps.setInt(1, user.getUserId());

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int user_id = rs.getInt("user_id");
            user = serviceUser.getUser(user_id);
            String message = rs.getString("message");
            int id = rs.getInt("id");
            int read = rs.getInt("isRead");
            boolean isRead = read == 1;
            notifications.add(new Notification(user, message, isRead, id));
        }
        rs.close();
        ps.close();

        return notifications;
    }

    public void updateIsRead(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update notification set isRead = ? where id = " + id);
        ps.setInt(1, 1);
        ps.executeUpdate();
    }

    public void updateIsSeen(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("update notification set isSeen = ? where user_id = ?");
        ps.setInt(1, 1);
        ps.setInt(2, user.getUserId());
        ps.executeUpdate();
    }

    public boolean checkAllSeen(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select count(*) from notification where user_id = ? and isSeen = 0");
        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int count = rs.getInt(1);
            if (count > 0) {
                return false;
            }
        }
        return true;
    }
}
