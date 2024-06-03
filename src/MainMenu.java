package src;

import javax.swing.*;
import java.awt.*;

import src.components.*;
import src.model.User;
import src.service.ServiceEvent;
import src.model.Event;
import src.components.TablePanel;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private Event event;
    private ServiceEvent service;
    private User user;
    private int id = 1;

    public MainMenu(User user) {
        this.user = user;
        setTitle("Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        CardLayout cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        TablePanel tablePanel = new TablePanel();
        CreatePanel createPanel = new CreatePanel(user.getUsername());
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
        Event event = this.getEvent();
        try{
            if(event.getName() == null || event.getDate() == null || event.getLocation() == null || event.getType() == null){
               System.out.println("Please fill all the required fields");
            }else{
                service.authorizeEvent(event);
                System.out.println("Successfully create event");
            }
        }catch(Exception e){
            System.out.println("Create event failed ");
        }

    }


    /*public static void main(String[] args) {
        try{
            DatabaseConnection.getInstance().connectToDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }
      SwingUtilities.invokeLater(() -> {
          MainMenu frame = new MainMenu(user);
          frame.setVisible(true);
      });
  }*/
}
