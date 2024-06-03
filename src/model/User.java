package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private static List<User> users = new ArrayList<>();
	private List<Event> events;
	private List<Invitation> invitations;
	private int user_id;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	
	public User(int user_id, String userName, String password, String firstName, String lastName, String phoneNumber) {
		this.events = new ArrayList<>();
		this.invitations = new ArrayList<>();
		this.user_id = user_id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
	
	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Invitation> getInvitations() {
		return invitations;
	}

	public void setInvitations(List<Invitation> invitations) {
		this.invitations = invitations;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/*public void createEvent(String name, int event_id, String location, String start_date, String end_date, String type) {
		Event newEvent = new Event(this, event_id, name, location, start_date, end_date, type);
        this.events.add(newEvent);
    }*/

    public static void signUp(int user_id, String userName, String password, String firstName, String lastName, String phoneNumber) {
    	 User newUser = new User(user_id, userName, password, firstName, lastName, phoneNumber);
         users.add(newUser);
    }

    public boolean login(String userName, String password) {
    	for (User user : users) {
			if(user.userName.equals(userName) && user.password.equals(password)) {
				return true;
			} 
		}
		return false;
    }

    public void acceptInvitation(Invitation invitation) {
    	this.events.add(invitation.getEvent());
        this.invitations.remove(invitation);
    }

    /*public void registerForPublicEvent(Event event) {
    	if (event.getType().equals("Public")) {
            Invitation invitation = new Invitation(this, event);
            event.getOrganizer_id().invitations.add(invitation);
        } else {
            System.out.println("Cannot register for a private event");
        }
    }*/

    public void sendInvitation(User recipient, Event event) {
    	if (this.events.contains(event)) {
            Invitation invitation = new Invitation(this, event);
            recipient.invitations.add(invitation);
        } else {
            System.out.println("Cannot send an invitation for an event you didn't create");
        }
    }
}
