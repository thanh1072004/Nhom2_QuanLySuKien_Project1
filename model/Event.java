public class Event {
	private User organizer_id;
	private int event_id;
    private String name;
    private String location;
    private String start_date;
    private String end_date;
    private String type;
	public Event(User organizer_id, int event_id, String name, String location, String start_date, String end_date, String type) {
		this.organizer_id = organizer_id;
		this.event_id = event_id;
		this.name = name;
		this.location = location;
		this.start_date = start_date;
		this.end_date = end_date;
		this.type = type;
	}
	public User getOrganizer_id() {
		return organizer_id;
	}
	public void setOrganizer_id(User organizer_id) {
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
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
