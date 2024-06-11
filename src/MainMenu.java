package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Objects;

import src.mainMenuPanel.*;
import src.database.DatabaseConnection;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.mainMenuPanel.TablePanel;
import src.service.ServiceUser;

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
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;
    private User user;
    private Event event;
    private int row;

    public MainMenu(User user) {
        this.user = user;
        serviceEvent = new ServiceEvent();
        serviceUser = new ServiceUser();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        tablePanel = new TablePanel(user, this);
        inviteViewPanel = new InviteViewPanel(user);
        inviteSendPanel = new InviteSendPanel(user);
        requestViewPanel = new RequestViewPanel(user);
        requestSendPanel = new RequestSendPanel(user);
        eventCreatePanel = new EventCreatePanel(user, this);

        mainPanel.add(tablePanel, "tablePanel");
        mainPanel.add(inviteViewPanel, "inviteViewPanel");
        mainPanel.add(inviteSendPanel, "inviteSendPanel");
        mainPanel.add(eventCreatePanel, "eventCreatePanel");
        mainPanel.add(requestViewPanel, "requestViewPanel");
        mainPanel.add(requestSendPanel, "requestSendPanel");


        SideBar sideBar = new SideBar(this, user);
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

    public static void main(String[] args) {
        try{
            DatabaseConnection.getInstance().connectToDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
        	User user = new User(1015, "tunghv", "bb7d4b236b564cf1ec27aa891331e0af");

            MainMenu frame = new MainMenu(user);
            frame.setVisible(true);
        });
    }

}
