package model;

public class Event {
	private int organizer_id;
	private int event_id;
    private String name;
    private String location;
    private String event_date;
	private String description;
    private String type;
	public Event(int event_id, String name, String location, String event_date, String type, String description, int organizer_id) {
		this.organizer_id = organizer_id;
		this.event_id = event_id;
		this.name = name;
		this.location = location;
		this.event_date = event_date;
		this.type = type;
		this.description = description;
	}

	public int getOrganizer_id() {
		return organizer_id;
	}

	public void setOrganizer_id(int organizer_id) {
		this.organizer_id = organizer_id;
	}

	public int getEvent_id() {
		return event_id;
	}

	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEvent_date() {
		return event_date;
	}

	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
