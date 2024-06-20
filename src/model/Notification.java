package src.model;

public class Notification {
    private User user;
    private String message;
    private boolean read;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notification(User user, String message, boolean read, int id) {
        this.user = user;
        this.message = message;
        this.read = read;
        this.id = id;
    }

    public Notification(User user, String message, boolean read) {
        this.user = user;
        this.message = message;
        this.read = read;
    }
}
