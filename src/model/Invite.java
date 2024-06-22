package src.model;

public class Invite {
    private User organizer;
    private User receiver;
    private Event event;
    private int invite_id;

    public Invite(User organizer, Event event, User receiver) {
        this.organizer = organizer;
        this.event = event;
        this.receiver = receiver;
    }

    public Invite(User organizer, Event event) {
        this.event = event;
        this.organizer = organizer;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getInvite_id() {
        return invite_id;
    }

    public void setInvite_id(int invite_id) {
        this.invite_id = invite_id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
