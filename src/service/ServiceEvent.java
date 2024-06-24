package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvent {
    private final Connection connection;
    private ServiceUser serviceUser;

    public ServiceEvent() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addEvent(User user, Event event) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement("insert into event(name, location, date, description, type, organizer_id) values(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, event.getName());
            ps.setString(2, event.getLocation());
            ps.setString(3, event.getDate());
            ps.setString(4, event.getDescription());
            ps.setString(5, event.getType());
            ps.setInt(6, user.getUserId());
            ps.execute();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                    int event_id = rs.getInt(1);
                    event.setId(event_id);
                }
            }
        }
    }


    public void updateEvent(Event event, int event_id) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement("update event set name = ?, location = ?, date = ?, description = ?, type = ? where event_id = ?")){
            ps.setString(1, event.getName());
            ps.setString(2, event.getLocation());
            ps.setString(3, event.getDate());
            ps.setString(4, event.getDescription());
            ps.setString(5, event.getType());
            ps.setInt(6, event_id);

            ps.executeUpdate();
        }
    }

    public void deleteEvent(int event_id) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement("delete from event where event_id = ?")){
            ps.setInt(1, event_id);
            ps.execute();
        }
    }

    public List<Event> getUserEvent(User user) throws SQLException {
        serviceUser = new ServiceUser();
        List<Event> events = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("select e.event_id, e.name, e.location, e.date, e.type, e.organizer_id " +
                                                            "from event e " +
                                                            "join attendee a on e.event_id = a.event_id " +
                                                            "join Users u on u.user_id = a.user_id " +
                                                            "where u.user_id = ?");){
            ps.setInt(1, user.getUserId());
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    int event_id = rs.getInt("event_id");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    String date = rs.getString("date");
                    String type = rs.getString("type");
                    int organizer_id = rs.getInt("organizer_id");
                    User organizer = serviceUser.getUser(organizer_id);

                    events.add(new Event(event_id, name, date, location, type, organizer));
                }
            }
        }
        return events;
    }

    public List<Event> getOrganizerEvent(User user) throws SQLException {
        serviceUser = new ServiceUser();
        List<Event> events = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("select e.event_id, e.name, e.location, e.date, e.type, u.user_id AS " +
                                                            "from event e " +
                                                            "join Users u on e.organizer_id = u.user_id " +
                                                            "where u.user_id = ?")){
            ps.setInt(1, user.getUserId());
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    int event_id = rs.getInt("event_id");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    String date = rs.getString("date");
                    String type = rs.getString("type");
                    int organizer_id = rs.getInt("organizer_id");
                    User organizer = serviceUser.getUser(organizer_id);

                    events.add(new Event(event_id, name, date, location, type, organizer));
                }
            }
        }
        return events;
    }

    public Event getSelectedEvent(int event_id) throws SQLException {
        Event event = null;
        serviceUser = new ServiceUser();
        try(PreparedStatement ps = connection.prepareStatement("select * from event where event_id = ?")){
            ps.setInt(1, event_id);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    String date = rs.getString("date");
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    int organizer_id = rs.getInt("organizer_id");
                    User organizer = serviceUser.getUser(organizer_id);
                    event = new Event(event_id, name, date, location, type, description, organizer);
                }
            }
        }
        return event;
    }

    public List<Event> getPublicEvents(User user) throws SQLException {
        serviceUser = new ServiceUser();
        List<Event> events = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement("select e.event_id, e.name, e.location, e.date, e.type, e.organizer_id " +
                                                            "from event e " +
                                                            "left join request on e.event_id = r.event_id and r.sender_id = ? " +
                                                            "left join attendee a on e.event_id = a.event_id and a.user_id = ? " +
                                                            "where e.type = 'Public' and e.organizer_id != ? " +
                                                            "and r.request_id is null " +
                                                            "and a.event_id is null"
                                                            )){
            ps.setInt(1, user.getUserId());
            ps.setInt(2, user.getUserId());
            ps.setInt(3, user.getUserId());
            try(ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    int event_id = rs.getInt("event_id");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    String date = rs.getString("date");
                    String type = rs.getString("type");
                    int organizer_id = rs.getInt("organizer_id");
                    String organizerUsername = serviceUser.getUsernameFromUserID(organizer_id);

                    User organizer = new User(organizer_id);
                    organizer.setUsername(organizerUsername);

                    events.add(new Event(event_id, name, date, location, type, organizer));
                }
            }
        }
        return events;
    }
}
