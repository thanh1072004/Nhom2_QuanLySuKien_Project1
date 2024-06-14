package src.mainMenuPanel;

import src.base.ComboBox;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.service.ServiceInvite;
import src.service.ServiceUser;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;

public class InviteSendPanel extends JPanel {
    private User receiver;
    private Event event;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;
    private ServiceInvite serviceInvite;
    private DefaultComboBoxModel<Event> eventModel;
    private ComboBox<Event> event_user;
    private JTextField receiver_name;

    public InviteSendPanel(User sender) {
        try {
            serviceUser = new ServiceUser();
            serviceEvent = new ServiceEvent();
            serviceInvite = new ServiceInvite();
            List<Event> events = serviceEvent.getOrganizerEvent(sender);

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(20, 20, 20, 20);

            Font font = new Font("Arial", Font.PLAIN, 16);

            JLabel nameLabel = new JLabel("Name Event:");
            nameLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(nameLabel, gbc);
            Event[] event0 = new Event[events.size()];
            for (int i = 0; i < events.size(); i++) {
                event0[i] = events.get(i);
            }
            event_user = new ComboBox<>();
            eventModel = new DefaultComboBoxModel<>(event0);
            event_user.setModel(eventModel);
            gbc.gridx = 1;
            add(event_user, gbc);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(usernameLabel, gbc);

            receiver_name = new JTextField(20);
            receiver_name.setFont(font);
            gbc.gridx = 1;
            add(receiver_name, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.CENTER;

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton inviteUserButton = new JButton("Invite User");
            inviteUserButton.setFont(font);
            inviteUserButton.setFocusPainted(false);

            inviteUserButton.addActionListener(e -> {
                try {
                    Event selectedEvent = (Event) event_user.getSelectedItem();
                    int event_id = selectedEvent.getId();
                    String receiverName = receiver_name.getText();
                    if (receiverName.isEmpty()) {
                        System.out.println("Please fill out all the fields");
                    } else {
                        Event event = serviceEvent.getSelectedEvent(event_id);
                        receiver = serviceUser.getUser(receiverName);

                        if (event.getOrganizer().getUserId() == sender.getUserId()) {
                            serviceInvite.addInvite(sender, receiver, this.event);
                        } else {
                            throw new SQLException("Event not found");
                        }
                    }
                    event_user.setSelectedItem(null);
                    receiver_name.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            buttonPanel.add(inviteUserButton);
            add(buttonPanel, gbc);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void addEvent(Event event) {
        eventModel.addElement(event);
    }
}
