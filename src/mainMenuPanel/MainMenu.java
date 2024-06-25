package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;

import src.Main;
import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;

import raven.toast.Notifications;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private TablePanel tablePanel;
    private EventCreatePanel eventCreatePanel;
    private EventUpdatePanel eventUpdatePanel;
    private InviteViewPanel inviteViewPanel;
    private InviteSendPanel inviteSendPanel;
    private RequestViewPanel requestViewPanel;
    private RequestSendPanel requestSendPanel;

    private transient User user;
    private transient Event event;

    public MainMenu(User user) {
        this.user = user;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 800);
        setTitle("Event Management System");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Notifications.getInstance().setJFrame(this);
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 10));

        tablePanel = new TablePanel(user, this);
        inviteViewPanel = new InviteViewPanel(user, this);
        inviteSendPanel = new InviteSendPanel(user,this);
        requestViewPanel = new RequestViewPanel(user, this);
        requestSendPanel = new RequestSendPanel(user, this);
        eventCreatePanel = new EventCreatePanel(user, this);

        mainPanel.add(tablePanel, "tablePanel");
        mainPanel.add(inviteViewPanel, "inviteViewPanel");
        mainPanel.add(inviteSendPanel, "inviteSendPanel");
        mainPanel.add(eventCreatePanel, "eventCreatePanel");
        mainPanel.add(requestViewPanel, "requestViewPanel");
        mainPanel.add(requestSendPanel, "requestSendPanel");

        SideBar sideBar = new SideBar(new Main(), this, user);
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    public void showPanel(String constraints){
        if(constraints.equals("eventUpdatePanel")){
            eventUpdatePanel = new EventUpdatePanel(user, event,this);
            mainPanel.add(eventUpdatePanel, "eventUpdatePanel");
        }
        cardLayout.show(mainPanel, constraints);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public TablePanel getTablePanel() {
        return tablePanel;
    }

    public EventUpdatePanel getEventUpdatePanel() {
        return eventUpdatePanel;
    }

    public InviteSendPanel getInviteSendPanel() {
        return inviteSendPanel;
    }

    public InviteViewPanel getInviteViewPanel() {
        return inviteViewPanel;
    }

    public RequestSendPanel getRequestSendPanel() {
        return requestSendPanel;
    }
    public RequestViewPanel getRequestViewPanel() {
        return requestViewPanel;
    }


    public void showMessage(Notifications.Type messageType, String message) {
        Notifications.getInstance().show(messageType, message);
    }

    public static void main(String[] args) {
        try{
            DatabaseConnection.getInstance().connectToDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
        	//User user = new User(1032, "hoang", "f82e62d7c3ea69cc12b5cdb8d621dab6");
            User user = new User(1, "tung", "bb7d4b236b564cf1ec27aa891331e0af");
            MainMenu frame = new MainMenu(user);
            frame.setVisible(true);
        });
    }
}
