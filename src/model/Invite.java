package src.model;

public class Invite {
    private User sender;
    private User receiver;
    private Event event;

    public Invite(User sender, Event event, User receiver) {
        this.sender = sender;
        this.event = event;
        this.receiver = receiver;
    }

    public Invite(User sender, Event event) {
        this.event = event;
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
