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
    private User user;
    private Event event;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;

    public ServiceRequest() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addRequest(Event event, User user) throws SQLException {
        this.user = user;
        this.event = event;
        if(connection == null) {
            throw new SQLException("Failed to connect to database");
        }
        PreparedStatement ps = connection.prepareStatement("insert into request(sender_id, event_id, organizer_id) values(?,?,?)");
        ps.setInt(1, user.getUserId());
        ps.setInt(2, event.getId());
        ps.setInt(3, event.getOrganizer().getUserId());
        System.out.println(user.getUserId());
        System.out.println(event.getOrganizer().getUserId());
        System.out.println(event.getId());
        ps.executeUpdate();
        ps.close();
    }

    public List<Request> getRequests(User user) throws SQLException {
        serviceEvent = new ServiceEvent();
        serviceUser = new ServiceUser();
        this.user = user;
        List<Request> requests = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select e.name, e.location, e.date, e.type, r.sender_id " +
                                                            "from event e " +
                                                            "join request r on e.event_id = r.event_id " +
                                                            "where r.organizer_id = ?");
        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String event_name = rs.getString("name");
            int sender_id = rs.getInt("sender_id");

            Event event = serviceEvent.getSelectedEvent(event_name);
            User sender = serviceUser.getUser(sender_id);

            requests.add(new Request(event, sender));
        }
        rs.close();
        ps.close();

        return requests;
    }
}
