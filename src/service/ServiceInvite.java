package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.Invite;
import src.model.Request;
import src.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceInvite {
    private final Connection connection;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;

    public ServiceInvite() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addInvite(User sender, User receiver, Event event) throws SQLException {
        if(connection == null){
            throw new SQLException("Failed to connect to database");
        }

        PreparedStatement ps = connection.prepareStatement("INSERT INTO invitation (sender_id, receiver_id, event_id) VALUES (? ,?, ?)");
        ps.setInt(1, sender.getUserId());
        ps.setInt(2, receiver.getUserId());
        ps.setInt(3, event.getId());
        ps.executeUpdate();

        ps.close();
    }

    public void removeInvite(User receiver, Event event) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("delete from invitation where receiver_id=? and event_id=?");
        ps.setInt(1, receiver.getUserId());
        ps.setInt(2, event.getId());
        ps.executeUpdate();
    }
    public List<Invite> getInvites(User receiver) throws SQLException {
        List<Invite> invites = new ArrayList<>();
        serviceEvent = new ServiceEvent();
        serviceUser = new ServiceUser();
        List<Request> requests = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select e.name, e.location, e.date, e.type, i.sender_id " +
                                                            "from event e " +
                                                            "join invitation i on e.event_id = i.event_id " +
                                                            "where i.receiver_id = ?");
        ps.setInt(1, receiver.getUserId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String event_name = rs.getString("name");
            int sender_id = rs.getInt("sender_id");

            Event event = serviceEvent.getSelectedEvent(event_name);
            User sender = serviceUser.getUser(sender_id);

            invites.add(new Invite(sender, event));
        }
        rs.close();
        ps.close();

        return invites;
    }
}
