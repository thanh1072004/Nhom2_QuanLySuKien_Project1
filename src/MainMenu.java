import javax.swing.*;
import java.awt.*;

import components.*;

import database.DatabaseConnection;
import service.ServiceEvent;

import model.Event;
import components.TablePanel;

public class MainMenu extends JFrame {
    private JPanel mainPanel;
    private Event event;
    private ServiceEvent service;
    private String username = "Hoang Viet Tung";
    private int id = 1;

    public MainMenu() {
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
        CreatePanel createPanel = new CreatePanel(username);
        createPanel.setFormListener(new FormListener() {
            @Override
            public void formSubmitted(String name, String date, String location, String type) {
                tablePanel.addRow(id++, name, date, location, type, username);
            }
        });
        mainPanel.add(tablePanel, "tablePanel");
        mainPanel.add(createPanel, "eventCreate");

        SideBar sideBar = new SideBar(mainPanel, cardLayout);
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }


    public Event getEvent(){
        return event;
    }
    private void createEvent(){
        Event event = this.getEvent();
        try{
            if(event.getName() == null || event.getEvent_date() == null || event.getLocation() == null || event.getType() == null){
               System.out.println("Please fill all the required fields");
            }else{
                service.authorizeEvent(event);
                System.out.println("Successfully create event");
            }
        }catch(Exception e){
            System.out.println("Create event failed ");
        }

    }


    public static void main(String[] args) {
        try{
            DatabaseConnection.getInstance().connectToDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }
      SwingUtilities.invokeLater(() -> {
          MainMenu frame = new MainMenu();
          frame.setVisible(true);
      });
  }
}
