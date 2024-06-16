package src.mainMenuPanel;

import raven.toast.Notifications;
import src.MainMenu;
import src.base.ComboBox;
import src.base.TextField;
import src.base.MyColor;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.service.ServiceInvite;
import src.service.ServiceUser;

import java.awt.*;
import java.util.List;
import javax.swing.*;

public class InviteSendPanel extends JPanel {
    private User receiver;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;
    private ServiceInvite serviceInvite;
    private DefaultComboBoxModel<Event> eventModel;
    private ComboBox<Event> event_name;
    private TextField receiver_name;
	private MainMenu mainMenu;

    public InviteSendPanel(User organizer, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        try {
            serviceUser = new ServiceUser();
            serviceEvent = new ServiceEvent();
            serviceInvite = new ServiceInvite();
            List<Event> events = serviceEvent.getOrganizerEvent(organizer);

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
            Event[] event_names = new Event[events.size()];
            for (int i = 0; i < events.size(); i++) {
                event_names[i] = events.get(i);
            }
            event_name = new ComboBox<>();
            eventModel = new DefaultComboBoxModel<>(event_names);
            event_name.setModel(eventModel);
            gbc.gridx = 1;
            add(event_name, gbc);

            JLabel usernameLabel = new JLabel("Username:");
            usernameLabel.setFont(font);
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(usernameLabel, gbc);

            receiver_name = new TextField("");
            receiver_name.setFont(font);
            receiver_name.setPreferredSize(new Dimension(200, 28));
            gbc.gridx = 1;
            add(receiver_name, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 6;
            gbc.fill = GridBagConstraints.CENTER;

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            JButton inviteUserButton = new JButton("Invite User");
            inviteUserButton.setFont(font);
            inviteUserButton.setBackground(MyColor.CYAN);
            inviteUserButton.setForeground(Color.WHITE);
            inviteUserButton.setFocusPainted(false);

            inviteUserButton.addActionListener(e -> {
                try {
                    Event event = (Event) event_name.getSelectedItem();

                    String receiverName = receiver_name.getText();
                    if (receiverName.isEmpty()) {
                        mainMenu.showMessage(Notifications.Type.WARNING, "Please fill out all the fields");
                    } else {
                        receiver = serviceUser.getUser(receiverName);
                        if(receiver == null) {
                            mainMenu.showMessage(Notifications.Type.ERROR, "User not found");
                        }
                        else if (serviceInvite.checkInvite(organizer, receiver, event)    ) {
                            mainMenu.showMessage(Notifications.Type.ERROR, "Invitation already sent to this user for the selected event");
                        } else {
                            mainMenu.showMessage(Notifications.Type.INFO, "Event invited successfully");
                            serviceInvite.addInvite(organizer, receiver, event);
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

    public void addEvent(Event event) {
        eventModel.addElement(event);
    }
}
