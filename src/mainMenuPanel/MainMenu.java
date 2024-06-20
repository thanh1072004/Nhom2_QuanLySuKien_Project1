package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private JLayeredPane layeredPane;
    private NotificationPanel notificationPanel;
    private User user;
    private Event event;

    public MainMenu(User user) {
        this.user = user;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        notificationPanel = new NotificationPanel(user);
        TopBar topBar = new TopBar(this, user);
        SideBar sideBar = new SideBar(new Main(), this, user);

        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.add(topBar, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(mainPanel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(notificationPanel, JLayeredPane.POPUP_LAYER);

        add(sideBar, BorderLayout.WEST);
        add(layeredPane, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                int width = getWidth() - 200;
                int height = getHeight();

                layeredPane.setBounds(0, 0, width, height);
                mainPanel.setBounds(0, 68, width, height - 68);
                topBar.setBounds(0, 0, width - 12, 68);
                notificationPanel.setBounds(width - 364, 68, 360, height - 68);
                layeredPane.revalidate();
                layeredPane.repaint();
            }
        });
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

    public InviteSendPanel getInviteSendPanel() {
        return inviteSendPanel;
    }

    public NotificationPanel getNotificationPanel(){
        return notificationPanel;
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
