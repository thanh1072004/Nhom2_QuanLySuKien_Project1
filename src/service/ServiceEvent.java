package src.service;

import src.components.TablePanel;
import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvent {
    private final Connection connection;
    private User user;
    //private TablePanel tablePanel;

    public ServiceEvent(){
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void authorizeEvent(Event event, User user) throws SQLException{
        this.user = user;
        if(connection == null){
            throw new SQLException("Failed to establish a connection");
        }
        PreparedStatement ps = connection.prepareStatement("insert into event(name, location, event_date, description, type, organizer_id) values(?,?,?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, event.getName());
        ps.setString(2, event.getLocation());
        ps.setString(3, event.getDate());
        ps.setString(4, event.getDescription());
        ps.setString(5, event.getType());
        ps.setInt(6, user.getUserId());
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        if(rs.next()){
            int event_id = rs.getInt(1);
            event.setId(event_id);
        }
        rs.close();
        ps.close();
    }

    /*public void onUserLogin(User user) throws SQLException{
        List<Event> events = getUserEvent(user);
        tablePanel.loadUserEvents(events);
    }*/
    public List<Event> getUserEvent(User user) throws SQLException{
        this.user = user;
        List<Event> events = new ArrayList<>();
        PreparedStatement ps =  connection.prepareStatement("SELECT e.name, e.event_date, e.location, e.type, u.user_id AS organizer_id " +
                                                            "from event e " +
                                                            "join Users u on e.organizer_id = u.user_id " +
                                                            "where u.user_id = ?");

        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String name = rs.getString("name");
            String location = rs.getString("location");
            String date = rs.getString("event_date");
            String type = rs.getString("type");
            int organizer_id = rs.getInt("organizer_id");
            User organizer = new User(organizer_id);

            events.add(new Event(name, location, date, type, organizer));
        }
        return events;
    }
}
