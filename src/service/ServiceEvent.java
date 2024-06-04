package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;

import java.sql.*;

public class ServiceEvent {
    private final Connection connection;
    private User user;

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
}
