package src.model;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User(int userId, String firstName, String lastName, String username, String password) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.password = password;
        this.username = username;
    }

    public User(int userId){
        this.userId = userId;

    }
}
