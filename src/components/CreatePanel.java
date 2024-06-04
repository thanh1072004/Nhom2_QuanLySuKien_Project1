package src.components;

import src.model.User;
import src.model.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;



public class CreatePanel extends JPanel{
    private FormListener formListener;
    private User user;
    private Event event;

    public Event getEvent(){
        return event;
    }

    public CreatePanel(User user, ActionListener eventCreate) {
        this.user = user;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allow components to fill horizontally
        gbc.insets = new Insets(10, 10, 10, 10);


        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        JTextField name = new JTextField(20);
        name.setFont(fieldFont);
        gbc.gridx = 1;
        add(name, gbc);


        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(dateLabel, gbc);

        DateSelectorPanel dateSelectorPanel = new DateSelectorPanel();
        gbc.gridx = 1;
        // Adjust as necessary
        add(dateSelectorPanel, gbc);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(locationLabel, gbc);

        JTextField location = new JTextField(20);
        location.setFont(fieldFont);
        gbc.gridx = 1;
        add(location, gbc);

        String[] type = { "Public", "Private" };
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(typeLabel, gbc);

        JComboBox<String> typeList = new JComboBox<>(type);
        typeList.setBackground(Color.WHITE);
        typeList.setFont(fieldFont);
        gbc.gridx = 1;
        add(typeList, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(descriptionLabel, gbc);

        JTextArea description = new JTextArea(5, 20);
        description.setFont(fieldFont);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(description);
        gbc.gridx = 1;
        add(descriptionScrollPane, gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // Center the button panel
        gbc.fill = GridBagConstraints.NONE;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton createEventButton = new JButton("Create Event");
        createEventButton.addActionListener(eventCreate);
        createEventButton.setFont(labelFont);
        createEventButton.setFocusPainted(false);
        createEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String eventName = name.getText().trim();
                    String eventDate = dateSelectorPanel.getSelectedDate().trim();
                    String eventLocation = location.getText().trim();
                    String eventType = typeList.getSelectedItem().toString().trim();
                    String eventDescription = description.getText().trim();
                    if(eventName.isEmpty() && eventType.isEmpty() && eventDescription.isEmpty()){
                        System.out.println("Event name or event date or event location or event type or event description is empty");
                    }else if(formListener == null){
                        System.out.println("FormListener is null");
                    }else if(getDate(eventDate).isBefore(LocalDate.now())){
                        System.out.println("Event date not valid");
                    }else{
                        formListener.formSubmitted(eventName, eventDate, eventLocation, eventType);
                    }
                    name.setText("");
                    location.setText("");
                    typeList.setSelectedIndex(0);
                    description.setText("");
                    event = new Event(0 ,eventName, eventDate, eventLocation, eventType, eventDescription, user);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(createEventButton);

        add(buttonPanel, gbc);
    }
    public void setFormListener(FormListener formListener){
        this.formListener = formListener;
    }

    public LocalDate getDate(String date) throws ParseException {
        return LocalDate.parse(date);
    }
}

