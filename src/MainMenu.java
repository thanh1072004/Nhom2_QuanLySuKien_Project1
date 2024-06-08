package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import src.mainMenuPanel.*;
import src.database.DatabaseConnection;
import src.model.User;
import src.service.ServiceEvent;
import src.model.Event;
import src.mainMenuPanel.TablePanel;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private EventCreatePanel eventCreatePanel;
    private TablePanel tablePanel;
    private InviteViewPanel inviteViewPanel;
    private InviteSendPanel inviteSendPanel;
    private RequestViewPanel requestViewPanel;
    private RequestSendPanel requestSendPanel;
    private Event event;
    private ServiceEvent service;
    private User user;
    private int id = 1;

    public MainMenu(User user) {
        this.user = user;
        service = new ServiceEvent();
        ActionListener eventCreate = e -> createEvent();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        CardLayout cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        tablePanel = new TablePanel(service, user, mainPanel, cardLayout);
        inviteViewPanel = new InviteViewPanel();
        inviteSendPanel = new InviteSendPanel();
        requestViewPanel = new RequestViewPanel();
        requestSendPanel = new RequestSendPanel(service, user, mainPanel, cardLayout);
        eventCreatePanel = new EventCreatePanel(user, eventCreate);
        eventCreatePanel.setFormListener(new FormListener() {
            @Override
            public void formSubmitted(String name, String date, String location, String type) {
                tablePanel.addRow(name, location, date, type, user);
                cardLayout.show(mainPanel, "tablePanel");
            }
            public void formUpdated(String name, String date, String location, String type) {}
        });


        mainPanel.add(tablePanel, "tablePanel");
        mainPanel.add(inviteViewPanel, "inviteViewPanel");
        mainPanel.add(inviteSendPanel, "inviteSendPanel");
        mainPanel.add(eventCreatePanel, "eventCreatePanel");
        mainPanel.add(requestViewPanel, "requestViewPanel");
        mainPanel.add(requestSendPanel, "requestSendPanel");

        SideBar sideBar = new SideBar(mainPanel, cardLayout, user);
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

    }

    public Event getEvent(){
        return event;
    }

    private void createEvent(){
        Event event = eventCreatePanel.getEvent();
        String eventDate = event.getDate();
        try{
            LocalDate day = getDate(eventDate);
            if(event.getName().isEmpty()  || event.getLocation().isEmpty()){
                System.out.println("Please fill all the required fields");
            }else if (day.isBefore(LocalDate.now())){
                System.out.println("Invalid date");
            }else{
                service.addEvent(event, user);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public LocalDate getDate(String date){
        return LocalDate.parse(date);
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
