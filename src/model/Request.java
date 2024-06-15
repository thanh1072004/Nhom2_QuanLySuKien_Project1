package src.model;

public class Request {
    private Event event;
    private User sender;
    private int request_id;

    public Request(User sender, Event event) {
        this.sender = sender;
        this.event = event;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
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
