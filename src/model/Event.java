package src.model;

public class Event {
    private int id;
    private String name;
    private String location;
    private String date;
    private String type;
    private String description;
    private User user;

    public Event(String name, String date, String location, String type, User user) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.type = type;
        this.user = user;
    }

    public Event(int id, String name, String date, String location, String type, String description, User user) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.type = type;
        this.date = date;
        this.location = location;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
