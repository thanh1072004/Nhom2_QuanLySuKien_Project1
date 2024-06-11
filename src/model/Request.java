package src.model;

public class Request {
    private Event event;
    private User sender;

    public Request(User sender, Event event) {
        this.sender = sender;
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }
}
