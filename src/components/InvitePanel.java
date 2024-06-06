package src.components;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InvitePanel extends JPanel {
	
	public InvitePanel() {
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

        JTextField name = new JTextField(20);
        name.setFont(font);
        gbc.gridx = 1;
        add(name, gbc);
        
        JLabel eventIDLabel = new JLabel("Event ID:");
        eventIDLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(eventIDLabel, gbc);

        JTextField eventID = new JTextField(20);
        eventID.setFont(font);
        gbc.gridx = 1;
        add(eventID, gbc);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(usernameLabel, gbc);

        JTextField username = new JTextField(20);
        username.setFont(font);
        gbc.gridx = 1;
        add(username, gbc);
        
        JLabel userIDLabel = new JLabel("User ID:");
        userIDLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(userIDLabel, gbc);

        JTextField userID = new JTextField(20);
        userID.setFont(font);
        gbc.gridx = 1;
        add(userID, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton inviteUserButton = new JButton("Invite User");
        inviteUserButton.setFont(font);
        inviteUserButton.setFocusPainted(false);
        
        buttonPanel.add(inviteUserButton);
        add(buttonPanel, gbc);
	}

}
