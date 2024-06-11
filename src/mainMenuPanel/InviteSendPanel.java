package src.mainMenuPanel;

import src.model.Event;
import src.model.Invite;
import src.model.User;
import src.service.ServiceEvent;
import src.service.ServiceInvite;
import src.service.ServiceUser;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InviteSendPanel extends JPanel {
    private Invite invite;
    private User sender;
    private User receiver;
    private Event event;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;
    private ServiceInvite serviceInvite;

    public InviteSendPanel(User sender) {
        this.sender = sender;
        serviceUser = new ServiceUser();
        serviceEvent = new ServiceEvent();
        serviceInvite = new ServiceInvite();

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

        JTextField event_name = new JTextField(20);
        event_name.setFont(font);
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

        inviteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String eventName = event_name.getText();
                    String receiverName = receiver_name.getText();
                    if(eventName.isEmpty() || receiverName.isEmpty()){
                        System.out.println("Please fill out all the fields");
                    }else{
                        event = serviceEvent.getSelectedEvent(eventName);
                        receiver = serviceUser.getUser(receiverName);
                        System.out.println(receiver.getUsername());
                        System.out.println(sender.getUsername());
                        System.out.println(event.getName());
                    }
                    event_name.setText("");
                    receiver_name.setText("");
                    serviceInvite.addInvite(sender, receiver, event);
                }catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        buttonPanel.add(inviteUserButton);
        add(buttonPanel, gbc);
    }
}
