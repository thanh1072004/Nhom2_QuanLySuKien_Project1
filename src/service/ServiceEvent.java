package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvent {
    private final Connection connection;
    private User user;

    public ServiceEvent() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addEvent(Event event, User user) throws SQLException {
        this.user = user;
        if (connection == null) {
            throw new SQLException("Failed to establish a connection");
        }
        PreparedStatement ps = connection.prepareStatement("insert into event(name, location, date, description, type, organizer_id) values(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, event.getName());
        ps.setString(2, event.getLocation());
        ps.setString(3, event.getDate());
        ps.setString(4, event.getDescription());
        ps.setString(5, event.getType());
        ps.setInt(6, user.getUserId());
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int event_id = rs.getInt(1);
            event.setId(event_id);
        }
        rs.close();
        ps.close();
    }


    public void updateEvent(Event event, int event_id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("Update event SET name = ?, location = ?, date = ?, description = ?, type = ? WHERE event_id = " + event_id);

        ps.setString(1, event.getName());
        ps.setString(2, event.getLocation());
        ps.setString(3, event.getDate());
        ps.setString(4, event.getDescription());
        ps.setString(5, event.getType());

        ps.executeUpdate();
    }

    public void deleteEvent(String event_name) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from event where name = ?");
        ps.setString(1, event_name);
        ps.execute();
        ps.close();
    }

    public List<Event> getUserEvent(User user) throws SQLException {
        this.user = user;
        List<Event> events = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT e.name, e.location, e.date, e.type, u.user_id AS organizer_id " +
                "from event e " +
                "join Users u on e.organizer_id = u.user_id " +
                "where u.user_id = ?");

        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            String location = rs.getString("location");
            String date = rs.getString("date");
            String type = rs.getString("type");
            int organizer_id = rs.getInt("organizer_id");
            User organizer = new User(organizer_id);

            events.add(new Event(name, date, location, type, organizer));
        }
        return events;
    }

    public Event getSelectedEvent(String event_name) throws SQLException {
        Event event = null;
        PreparedStatement ps = connection.prepareStatement("select * from event where name = ?");
        ps.setString(1, event_name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int event_id = rs.getInt("event_id");
            String name = rs.getString("name");
            String location = rs.getString("location");
            String date = rs.getString("date");
            String type = rs.getString("type");
            String description = rs.getString("description");
            int organizer_id = rs.getInt("organizer_id");
            User organizer = new User(organizer_id);
            event = new Event(event_id, name, date, location, type, description, organizer);
        }
        rs.close();
        ps.close();
        return event;
    }

    public List<Event> getPublicEvents(User user) throws SQLException {
        this.user = user;
        List<Event> events = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement("SELECT e.name, e.location, e.date, e.type, u.user_id AS organizer_id " +
                                                            "from event e " +
                                                            "join Users u on e.organizer_id = u.user_id " +
                                                            "where e.type = 'Public' and e.organizer_id != ?");

        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            String location = rs.getString("location");
            String date = rs.getString("date");
            String type = rs.getString("type");
            int organizer_id = rs.getInt("organizer_id");
            User organizer = new User(organizer_id);

            events.add(new Event(name, date, location, type, organizer));
        }
        return events;
    }
}
