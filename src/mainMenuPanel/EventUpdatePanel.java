package src.mainMenuPanel;

import src.MainMenu;
import src.base.CustomMessage;
import src.base.DateSelector;
import src.base.MyColor;
import src.model.User;
import src.model.Event;
import src.service.ServiceEvent;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class EventUpdatePanel extends JPanel {
    private Event event;
    private JTextField name, location;
    private DateSelector dateSelector;
    private JComboBox<String> typeList;
    private JTextArea description;
    private ServiceEvent serviceEvent;
    private TableListener tableListener;
    private JPanel messagePanel;

    public EventUpdatePanel(User user, Event event, MainMenu mainMenu) {
        this.event = event;

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

        name = new JTextField(20);
        name.setFont(font);
        gbc.gridx = 1;
        add(name, gbc);

        JLabel dateLabel = new JLabel("Date:");
        dateLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(dateLabel, gbc);

        dateSelector = new DateSelector();
        gbc.gridx = 1;
        add(dateSelector, gbc);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(locationLabel, gbc);

        location = new JTextField(20);
        location.setFont(font);
        gbc.gridx = 1;
        add(location, gbc);

        String[] type = { "Public", "Private" };
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(typeLabel, gbc);

        typeList = new JComboBox<>(type);
        typeList.setBackground(Color.WHITE);
        typeList.setFont(font);
        gbc.gridx = 1;
        add(typeList, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(descriptionLabel, gbc);

        description = new JTextArea(5, 20);
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
        JButton updateEventButton = new JButton("Update Event");
        updateEventButton.setFont(font);
        updateEventButton.setBackground(MyColor.CYAN);
        updateEventButton.setForeground(Color.WHITE);
        updateEventButton.setFocusPainted(false);
        updateEventButton.addActionListener(e -> {
            try {
                String eventName = name.getText().trim();
                String eventDate = dateSelector.getSelectedDate().trim();
                String eventLocation = location.getText().trim();
                String eventType = typeList.getSelectedItem().toString().trim();
                String eventDescription = description.getText().trim();

                if (eventName.isEmpty() || eventDate.isEmpty() || eventLocation.isEmpty() || eventType.isEmpty()) {
                    showMessage(CustomMessage.MessageType.WARNING, "Event details cannot be empty.");
                } else if (getDate(eventDate).isBefore(LocalDate.now())) {
                    showMessage(CustomMessage.MessageType.ERROR, "Event date not valid.");
                } else {
                    int row = tableListener.getRow();
                    tableListener.updateRow(row, eventName, eventDate, eventLocation, eventType);
                    Event updatedEvent = new Event(event.getId(), eventName, eventDate, eventLocation, eventType, eventDescription, user);
                    serviceEvent.updateEvent(updatedEvent, event.getId());
                    showMessage(CustomMessage.MessageType.INFO, "Event updated successfully.");
                    new Thread(() -> {
                        try {
                            Thread.sleep(1000); 
                            SwingUtilities.invokeLater(() -> mainMenu.showPanel("tablePanel"));
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }).start();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        buttonPanel.add(updateEventButton);

        add(buttonPanel, gbc);

        // Add a panel for messages
        messagePanel = new JPanel();
        messagePanel.setLayout(new BorderLayout());
        gbc.gridy = 6;
        add(messagePanel, gbc);

        try {
            setEventDetails(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessage(CustomMessage.MessageType messageType, String message) {
        CustomMessage msg = new CustomMessage();
        msg.showMessage(messageType, message);

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!msg.isShow()) {
                    messagePanel.add(msg, BorderLayout.CENTER);
                    msg.setVisible(true);
                    messagePanel.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (msg.isShow()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = 40 * fraction;
                }
                msg.setLocation(msg.getX(), (int) (f - 30));
                messagePanel.repaint();
                messagePanel.revalidate();
            }

            @Override
            public void end() {
                if (msg.isShow()) {
                    messagePanel.remove(msg);
                    messagePanel.repaint();
                    messagePanel.revalidate();
                } else {
                    msg.setShow(true);
                }
            }
        };

        Animator animator = new Animator(300, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
        animator.start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                animator.start();
            } catch (InterruptedException e) {
                System.err.println(e);
            }
        }).start();
    }

    public LocalDate getDate(String date) {
        return LocalDate.parse(date);
    }

    public void setEventDetails(Event event) throws SQLException {
        this.event = serviceEvent.getSelectedEvent(event.getName());
        name.setText(event.getName());
        dateSelector.setDate(event.getDate());
        location.setText(event.getLocation());
        typeList.setSelectedItem(event.getType());
        description.setText(event.getDescription());
    }
}



