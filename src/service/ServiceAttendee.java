package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServiceAttendee {
    private User user;
    private Event event;
    private Connection connection;

    public ServiceAttendee() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addAttendee(User user, Event event) throws SQLException {
        this.user = user;
        this.event = event;
        if(connection == null) {
            throw new SQLException("Failed to connect to database");
        }
        PreparedStatement ps = connection.prepareStatement("insert into attendee(event_id, user_id) values(?, ?)");
        ps.setInt(1, event.getId());
        ps.setInt(2, user.getUserId());
        ps.executeUpdate();
    }

    public void removeAttendee(User user, Event event) throws SQLException {
        this.user = user;
        this.event = event;
        PreparedStatement ps = connection.prepareStatement("delete from attendee where event_id = ? and user_id = ?");
        ps.setInt(1, event.getId());
        ps.setInt(2, user.getUserId());
        ps.executeUpdate();
    }
}
