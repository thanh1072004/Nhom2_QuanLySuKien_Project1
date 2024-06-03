package src.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreatePanel extends JPanel{
    private FormListener formListener;
    private String username;

    public CreatePanel(String username) {
        this.username = username;
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

        JTextField date = new JTextField(20);
        date.setFont(fieldFont);
        gbc.gridx = 1;
        add(date, gbc);

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
        createEventButton.setFont(labelFont);
        createEventButton.setFocusPainted(false);
        createEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(formListener != null){
                    formListener.formSubmitted(name.getText(), date.getText(), location.getText(), (String) typeList.getSelectedItem());
                }
                name.setText("");
                date.setText("");
                location.setText("");
                typeList.setSelectedIndex(0);
                description.setText("");
            }
        });
        buttonPanel.add(createEventButton);

        add(buttonPanel, gbc);
    }
    public void setFormListener(FormListener formListener){
        this.formListener = formListener;
    }
}

