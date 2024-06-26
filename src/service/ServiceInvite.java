package src.service;

import src.database.DatabaseConnection;
import src.model.Event;
import src.model.Invite;
import src.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceInvite {
    private final Connection connection;

    public ServiceInvite() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public void addInvite(User organizer, User receiver, Event event) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement("insert into invite (organizer_id, receiver_id, event_id) VALUES (? ,?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setInt(1, organizer.getUserId());
            ps.setInt(2, receiver.getUserId());
            ps.setInt(3, event.getId());
            ps.execute();
            try(ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    Invite invite = new Invite(organizer, event, receiver);
                    int invite_id = rs.getInt(1);
                    invite.setInvite_id(invite_id);
                }
            }
        }
    }

    public void removeInvite(User user, Event event) throws SQLException {
        try{
            connection.setAutoCommit(false);

            try(PreparedStatement psSelect = connection.prepareStatement("select invite_id from invite where organizer_id = ? and event_id = ? and receiver_id = ?")){
                psSelect.setInt(1, event.getOrganizer().getUserId());
                psSelect.setInt(2, event.getId());
                psSelect.setInt(3, user.getUserId());
                ResultSet rs = psSelect.executeQuery();
                int invite_id = -1;
                if(rs.next()) {
                    invite_id = rs.getInt("invite_id");
                }
                try(PreparedStatement psInsert = connection.prepareStatement("insert into finished_invite (invite_id) values (?)")){
                    psInsert.setInt(1, invite_id);
                    psInsert.executeUpdate();
                }
            }
            connection.commit();
        }catch(Exception e){
            connection.rollback();
        }
    }

    public boolean checkInvite(User organizer, User receiver, Event event) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement("select count(*) from invite where organizer_id = ? and event_id = ? and receiver_id = ?")){
            ps.setInt(1, organizer.getUserId());
            ps.setInt(2, event.getId());
            ps.setInt(3, receiver.getUserId());
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public List<Invite> getInvites(User receiver) throws SQLException {
        List<Invite> invites = new ArrayList<>();
        ServiceEvent serviceEvent = new ServiceEvent();
        ServiceUser serviceUser = new ServiceUser();

        try(PreparedStatement ps = connection.prepareStatement("select e.event_id, e.name, e.location, e.date, e.type, i.organizer_id " +
                                                            "from event e " +
                                                            "join invite i on e.event_id = i.event_id " +
                                                            "left join finished_invite f on f.invite_id = i.invite_id " +
                                                            "where i.receiver_id = ? and f.invite_id is null")){
            ps.setInt(1, receiver.getUserId());
            try(ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int event_id = rs.getInt("event_id");
                    int sender_id = rs.getInt("organizer_id");

                    Event event = serviceEvent.getSelectedEvent(event_id);
                    User sender = serviceUser.getUser(sender_id);

                    invites.add(new Invite(sender, event));
                }
            }
        }
        return invites;
    }
}
