package src.mainMenuPanel;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InviteSendPanel extends JPanel {
	
	public InviteSendPanel() {
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
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(usernameLabel, gbc);

        JTextField username = new JTextField(20);
        username.setFont(font);
        gbc.gridx = 1;
        add(username, gbc);

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(font);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(noteLabel, gbc);

        JTextField note = new JTextField(20);
        note.setFont(font);
        gbc.gridx = 1;
        add(note, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
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
