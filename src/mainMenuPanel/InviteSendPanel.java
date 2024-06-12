package src.mainMenuPanel;

import src.base.ComboBox;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.service.ServiceInvite;
import src.service.ServiceUser;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;

public class InviteSendPanel extends JPanel {
    private User receiver;
    private Event event;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;
    private ServiceInvite serviceInvite;

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
            String[] eventNames = new String[events.size()];
            for (int i = 0; i < events.size(); i++) {
                eventNames[i] = events.get(i).getName();
            }
            ComboBox<String> event_name = new ComboBox<>();
            event_name.setModel(new DefaultComboBoxModel<>(eventNames));
            gbc.gridx = 1;
            add(event_name, gbc);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(usernameLabel, gbc);

            JTextField receiver_name = new JTextField(20);
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
                    String eventName = event_name.getSelectedItem().toString();

                    String receiverName = receiver_name.getText();
                    if (receiverName.isEmpty()) {
                        System.out.println("Please fill out all the fields");
                    } else {
                        event = serviceEvent.getSelectedEvent(eventName);
                        receiver = serviceUser.getUser(receiverName);

                        if (event.getOrganizer().getUserId() == sender.getUserId()) {
                            serviceInvite.addInvite(sender, receiver, event);
                        } else {
                            throw new SQLException("Event not found");
                        }
                    }
                    event_name.setSelectedItem(null);
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
}
