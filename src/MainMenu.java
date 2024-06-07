package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import src.components.*;
import src.database.DatabaseConnection;
import src.model.User;
import src.service.ServiceEvent;
import src.model.Event;
import src.components.TablePanel;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private CreatePanel createPanel;
    private TablePanel tablePanel;
    private JoinPanel joinPanel;
    private InvitePanel invitePanel;
    private Event event;
    private ServiceEvent service;
    private User user;
    private int id = 1;


    public MainMenu(User user) {
        this.user = user;
        service = new ServiceEvent();
        ActionListener eventCreate = e -> createEvent();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        CardLayout cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        tablePanel = new TablePanel(service, user, mainPanel, cardLayout);
        joinPanel = new JoinPanel();
        invitePanel = new InvitePanel();
        createPanel = new CreatePanel(user, eventCreate);
        createPanel.setFormListener(new FormListener() {
            @Override
            public void formSubmitted(String name, String date, String location, String type) {
                tablePanel.addRow(id++, name, date, location, type, user);
                cardLayout.show(mainPanel, "tablePanel");
            }
            public void formUpdated(String name, String date, String location, String type) {}
        });


        mainPanel.add(tablePanel, "tablePanel");
        mainPanel.add(joinPanel, "joinPanel");
        mainPanel.add(invitePanel, "invitePanel");
        mainPanel.add(createPanel, "eventCreate");

        SideBar sideBar = new SideBar(mainPanel, cardLayout, user);
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    public Event getEvent(){
        return event;
    }

    private void createEvent(){
        Event event = createPanel.getEvent();
        String eventDate = event.getDate();
        try{
            LocalDate day = getDate(eventDate);
            if(event.getName().isEmpty()  || event.getLocation().isEmpty()){
                System.out.println("Please fill all the required fields");
            }else if (day.isBefore(LocalDate.now())){
                System.out.println("Invalid date");
            }else{
                service.authorizeEvent(event, user);
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
            User user = new User(15, "tung", "97c873b3ef5ed9c7bcb8bd2b77e79b0d");

            MainMenu frame = new MainMenu(user);
            frame.setVisible(true);
        });
    }
}
