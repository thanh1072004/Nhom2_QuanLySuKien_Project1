public class Event {
    private String name;
    private String location;
    private String start_date;
    private String end_date;
    private String type;

    public Event(String name, String location, String start_date, String end_date, String type) {
        this.name = name;
        this.location = location;
        this.start_date = start_date;
        this.end_date = end_date;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
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
}
