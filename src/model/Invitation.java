package model;

public class Invitation {
	private User sender;
	private Event event;
	public Invitation(User sender, Event event) {
		this.sender = sender;
		this.event = event;
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
	
}