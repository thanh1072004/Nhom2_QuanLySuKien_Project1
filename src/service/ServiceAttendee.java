package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;

import java.sql.*;

public class ServiceAttendee {
    private Connection connection;

    public ServiceAttendee() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addAttendee(User user, Event event) throws SQLException {
        if(connection == null) {
            throw new SQLException("Failed to connect to database");
        }
        PreparedStatement ps = connection.prepareStatement("insert into attendee(event_id, user_id) values(?, ?)");
        ps.setInt(1, event.getId());
        ps.setInt(2, user.getUserId());
        ps.executeUpdate();
    }

    public void removeAttendee(User user, Event event) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from attendee where event_id = ? and user_id = ?");
        ps.setInt(1, event.getId());
        ps.setInt(2, user.getUserId());
        ps.executeUpdate();
    }
    public boolean checkAttendee(User user, Event event) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select count(*) from attendee where event_id = ? and user_id = ?");
        ps.setInt(1, event.getId());
        ps.setInt(2, user.getUserId());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
}
