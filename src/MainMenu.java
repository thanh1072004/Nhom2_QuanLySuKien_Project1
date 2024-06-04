package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import src.components.*;
import src.database.DatabaseConnection;
import src.model.User;
import src.service.ServiceEvent;
import src.model.Event;
import src.components.TablePanel;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private Event event;
    private ServiceEvent service;
    private User user;
    private CreatePanel createPanel;
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

        TablePanel tablePanel = new TablePanel();
        createPanel = new CreatePanel(user, eventCreate);
        createPanel.setFormListener(new FormListener() {
            @Override
            public void formSubmitted(String name, String date, String location, String type) {
                tablePanel.addRow(id++, name, date, location, type, user.getUsername());
            }
        });
        mainPanel.add(tablePanel, "tablePanel");
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
        try{
            if(event.getName().isEmpty()  || event.getLocation().isEmpty()){
               System.out.println("Please fill all the required fields");
            }else if (event.getDate().compareTo(String.valueOf(Calendar.getInstance())) < 0){
                System.out.println("Invalid date");
            }else{
                service.authorizeEvent(event, user);
                System.out.println("Successfully create event");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try{
            DatabaseConnection.getInstance().connectToDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }
      SwingUtilities.invokeLater(() -> {
          User user = new User(1, "tung", "tung");
          MainMenu frame = new MainMenu(user);
          frame.setVisible(true);
      });
  }
}
