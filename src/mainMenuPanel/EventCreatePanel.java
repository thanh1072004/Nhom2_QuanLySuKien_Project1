package src.mainMenuPanel;

import raven.toast.Notifications;
import src.MainMenu;
import src.base.ComboBox;
import src.base.DateSelector;
import src.base.MyColor;
import src.base.TextField;
import src.model.User;
import src.model.Event;
import src.service.ServiceAttendee;
import src.service.ServiceEvent;

import javax.swing.*;

import java.awt.*;
import java.time.LocalDate;

public class EventCreatePanel extends JPanel{
    private MainMenu mainMenu;
    private ServiceEvent serviceEvent;
    private ServiceAttendee serviceAttendee;
    private TableListener tableListener;
    private InviteSendPanel inviteSendPanel;
    private JPanel messagePanel;

    public EventCreatePanel(User user, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        inviteSendPanel = mainMenu.getInviteSendPanel();
        tableListener = mainMenu.getTablePanel();
        serviceEvent = new ServiceEvent();
        serviceAttendee = new ServiceAttendee();

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(16, 10, 16, 10);

        Font font = new Font("sansserif", Font.PLAIN, 16);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nameLabel, gbc);

        TextField name = new TextField("");
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

        TextField location = new TextField("");
        gbc.gridx = 1;
        add(location, gbc);

        String[] type = { "Public", "Private" };
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(typeLabel, gbc);

        ComboBox<String> typeList = new ComboBox<>();
        typeList.setModel(new DefaultComboBoxModel(type));
        typeList.setSelectedIndex(0);
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
        createEventButton.setBackground(MyColor.CYAN);
        createEventButton.setForeground(Color.WHITE);
        createEventButton.setFocusPainted(false);
        createEventButton.addActionListener(e -> {
            try{
                String eventName = name.getText().trim();
                String eventDate = dateSelector.getSelectedDate().trim();
                String eventLocation = location.getText().trim();
                String eventType = typeList.getSelectedItem().toString().trim();
                String eventDescription = description.getText().trim();
                if(eventName.isEmpty() || eventType.isEmpty() || eventLocation.isEmpty()){
                	mainMenu.showMessage(Notifications.Type.WARNING, "Event details cannot be empty");
                }else if(getDate(eventDate).isBefore(LocalDate.now())){
                    mainMenu.showMessage(Notifications.Type.ERROR, "Event date not valid");
                }else{
                    //createEvent(eventName, eventDate, eventLocation, eventType, eventDescription, user);
                    mainMenu.showMessage(Notifications.Type.SUCCESS, "Event created successfully.");
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

        messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        gbc.gridy = 6;
        add(messagePanel, gbc);
    }

    public void createEvent(String name, String date, String location, String type, String description, User organizer){
        SwingWorker<Event, Void> worker = new SwingWorker<Event, Void>() {
            @Override
            protected Event doInBackground() throws Exception {
                Event event = new Event(0 ,name, date, location, type, description, organizer);
                serviceEvent.addEvent(organizer, event);
                serviceAttendee.addAttendee(organizer, event);
                mainMenu.showPanel("tablePanel");
                return event;
            }

            @Override
            protected void done() {
                try{
                    Event event = get();
                    tableListener.addRow(event.getId(), name, date, location, type, organizer);
                    inviteSendPanel.addEvent(event);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    public LocalDate getDate(String date){
        return LocalDate.parse(date);
    }

}

