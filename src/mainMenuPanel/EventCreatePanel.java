package src.mainMenuPanel;

import src.MainMenu;
import src.base.DateSelector;
import src.model.User;
import src.model.Event;
import src.service.ServiceEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;


public class EventCreatePanel extends JPanel{
    private User user;
    private Event event;
    private ServiceEvent serviceEvent;
    private MainMenu mainMenu;
    private TableListener tableListener;


    public EventCreatePanel(User user, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.user = user;
        tableListener = mainMenu.getTablePanel();
        serviceEvent = new ServiceEvent();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        JTextField name = new JTextField(20);
        name.setFont(font);
        gbc.gridx = 1;
        add(name, gbc);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(dateLabel, gbc);

        DateSelector dateSelector = new DateSelector();
        gbc.gridx = 1;
        add(dateSelector, gbc);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(locationLabel, gbc);

        JTextField location = new JTextField(20);
        location.setFont(font);
        gbc.gridx = 1;
        add(location, gbc);

        String[] type = { "Public", "Private" };
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(typeLabel, gbc);

        JComboBox<String> typeList = new JComboBox<>(type);
        typeList.setBackground(Color.WHITE);
        typeList.setFont(font);
        gbc.gridx = 1;
        add(typeList, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(descriptionLabel, gbc);

        JTextArea description = new JTextArea(5, 20);
        description.setFont(font);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(description);
        gbc.gridx = 1;
        add(descriptionScrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton createEventButton = new JButton("Create Event");
        createEventButton.setFont(font);
        createEventButton.setFocusPainted(false);
        createEventButton.addActionListener(e -> {
            try{
                String eventName = name.getText().trim();
                String eventDate = dateSelector.getSelectedDate().trim();
                String eventLocation = location.getText().trim();
                String eventType = typeList.getSelectedItem().toString().trim();
                String eventDescription = description.getText().trim();
                if(eventName.isEmpty() || eventType.isEmpty() || eventLocation.isEmpty()){
                    System.out.println("Event name or event date or event location or event type is empty");
                }else if(getDate(eventDate).isBefore(LocalDate.now())){
                    System.out.println("Event date not valid");
                }else{
                    tableListener.addRow(eventName, eventDate, eventLocation, eventType, user);
                    event = new Event(0 ,eventName, eventDate, eventLocation, eventType, eventDescription, user);
                    serviceEvent.addEvent(event, user);
                    mainMenu.showPanel("tablePanel");
                }
                name.setText("");
                location.setText("");
                dateSelector.resetToCurrentDate();
                typeList.setSelectedIndex(0);
                description.setText("");

            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        buttonPanel.add(createEventButton);

        add(buttonPanel, gbc);
    }

    public LocalDate getDate(String date){
        return LocalDate.parse(date);
    }

}

