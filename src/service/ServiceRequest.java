package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.Request;
import src.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceRequest {
    private final Connection connection;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;

    public ServiceRequest() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addRequest(User user, Event event) throws SQLException {
        if(connection == null) {
            throw new SQLException("Failed to connect to database");
        }
        PreparedStatement ps = connection.prepareStatement("insert into request(sender_id, event_id, organizer_id) values(?,?,?)");
        ps.setInt(1, user.getUserId());
        ps.setInt(2, event.getId());
        ps.setInt(3, event.getOrganizer().getUserId());
        ps.executeUpdate();
        ps.close();
    }

    public void removeRequest(User user, Event event) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from request where sender_id=? and event_id=?");
        ps.setInt(1, user.getUserId());
        ps.setInt(2, event.getId());
        ps.executeUpdate();
    }
    public List<Request> getRequests(User user) throws SQLException {
        serviceEvent = new ServiceEvent();
        serviceUser = new ServiceUser();
        List<Request> requests = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select e.event_id, e.name, e.location, e.date, r.sender_id " +
                                                            "from event e " +
                                                            "join request r on e.event_id = r.event_id " +
                                                            "where r.organizer_id = ?");
        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int event_id = rs.getInt("event_id");
            int sender_id = rs.getInt("sender_id");

            Event event = serviceEvent.getSelectedEvent(event_id);
            User sender = serviceUser.getUser(sender_id);

            requests.add(new Request(sender, event));
        }
        rs.close();
        ps.close();

        return requests;
    }
}
