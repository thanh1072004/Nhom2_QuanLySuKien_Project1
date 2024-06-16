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
    private Request request;

    public ServiceRequest() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addRequest(User user, Event event) throws SQLException {
        if(connection == null) {
            throw new SQLException("Failed to connect to database");
        }
        PreparedStatement ps = connection.prepareStatement("insert into request(sender_id, event_id, organizer_id) values(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, user.getUserId());
        ps.setInt(2, event.getId());
        ps.setInt(3, event.getOrganizer().getUserId());
        ps.execute();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            Request request = new Request(user, event);
            int request_id = rs.getInt(1);
            request.setRequest_id(request_id);
        }
        rs.close();
        ps.close();
    }

    public void removeRequest(User user, Event event) throws SQLException {
        try{
            connection.setAutoCommit(false);

            PreparedStatement psSelect = connection.prepareStatement("select request_id from request where sender_id = ? and event_id = ? and organizer_id = ?");
            psSelect.setInt(1, user.getUserId());
            psSelect.setInt(2, event.getId());
            psSelect.setInt(3, event.getOrganizer().getUserId());
            ResultSet rs = psSelect.executeQuery();
            int request_id = -1;
            if(rs.next()) {
                request_id = rs.getInt("request_id");
            }

            PreparedStatement psInsert = connection.prepareStatement("insert into finished_request (request_id) values (?)");
            psInsert.setInt(1, request_id);
            psInsert.executeUpdate();

            connection.commit();
        }catch(Exception e){
            connection.rollback();
        }
    }

    public List<Request> getRequests(User user) throws SQLException {
        serviceEvent = new ServiceEvent();
        serviceUser = new ServiceUser();
        List<Request> requests = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("select e.event_id, e.name, e.location, e.date, r.sender_id " +
                                                            "from event e " +
                                                            "join request r on e.event_id = r.event_id " +
                                                            "left join finished_request f on r.request_id = f.request_id " +
                                                            "left join attendee a on e.event_id = a.event_id and a.user_id = ? " +
                                                            "where r.organizer_id = ? and f.request_id is null");
        ps.setInt(1, user.getUserId());
        ps.setInt(2, user.getUserId());
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
