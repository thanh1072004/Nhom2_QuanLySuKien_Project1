package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SideBar extends JPanel {

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public SideBar(JPanel mainPanel, CardLayout cardLayout){
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setPreferredSize(new Dimension(200, getHeight()));
        setLayout(new GridBagLayout());
        setBackground(new Color(52, 58, 64));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        ImageIcon originalIcon_user = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SideBar.class.getResource("/icon/user.png")));
        Image scaledImage_user = originalIcon_user.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_user = new ImageIcon(scaledImage_user);
        usernameLabel.setIcon(scaledIcon_user);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        usernameLabel.setPreferredSize(new Dimension(200, 50));

        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(160, 10));

        JMenu eventsMenu = createMenu("Events", Color.WHITE);
        eventsMenu.setPreferredSize(new Dimension(200, 50));
        eventsMenu.setFont(new Font("sanserif", Font.PLAIN, 16));
        ImageIcon originalIcon_event = new ImageIcon(Toolkit.getDefaultToolkit().createImage(SideBar.class.getResource("/icon/event.png")));
        Image scaledImage_event = originalIcon_event.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_event = new ImageIcon(scaledImage_event);

        JPanel eventListPanel = new JPanel();
        eventListPanel.setLayout(new BoxLayout(eventListPanel, BoxLayout.Y_AXIS));

        JMenuItem view = createMenuItem("View events");
        JMenuItem create = createMenuItem("Create event");
        eventsMenu.setIcon(scaledIcon_event);
        eventsMenu.addMouseListener(new MouseAdapter() {
            private boolean eventsVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!eventsVisible) {
                    // Add event items to the event list panel
                    eventListPanel.add(view);
                    eventListPanel.add(create);
                    eventsMenu.setBackground(new Color(108, 117, 125));
                } else {
                    // Remove all event items
                    eventListPanel.removeAll();
                    eventsMenu.setBackground(new Color(52, 58, 64));
                }
                eventsVisible = !eventsVisible;

                revalidate();
                repaint();
            }
        });
        create.addActionListener(e -> cardLayout.show(mainPanel, "CreateEvent"));

        JMenu invitationsMenu = createMenu("Invitations", Color.WHITE);
        invitationsMenu.setPreferredSize(new Dimension(200, 50));
        invitationsMenu.setFont(new Font("sanserif", Font.PLAIN, 16));
        ImageIcon originalIcon_invite = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SideBar.class.getResource("/icon/list.png")));
        Image scaledImage_invite = originalIcon_invite.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_invite = new ImageIcon(scaledImage_invite);
        invitationsMenu.setIcon(scaledIcon_invite);

        JPanel invitationListPanel = new JPanel();
        invitationListPanel.setLayout(new BoxLayout(invitationListPanel, BoxLayout.Y_AXIS));

        JMenuItem request = createMenuItem("Join events");
        JMenuItem invite = createMenuItem("Invite User");
        invitationsMenu.addMouseListener(new MouseAdapter() {
            private boolean eventsVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!eventsVisible) {
                    // Add event items to the event list panel
                    invitationListPanel.add(request);
                    invitationListPanel.add(invite);
                    invitationsMenu.setBackground(new Color(108, 117, 125));
                } else {
                    // Remove all event items
                    invitationListPanel.removeAll();
                    invitationsMenu.setBackground(new Color(52, 58, 64));

                }
                // Toggle visibility
                eventsVisible = !eventsVisible;
                // Revalidate and repaint to update the layout
                revalidate();
                repaint();
            }
        });
        add(usernameLabel, gbc);

        gbc.gridy++;
        add(separator, gbc);

        gbc.gridy++;
        add(eventsMenu,gbc);

        gbc.gridy++;
        add(eventListPanel,gbc);

        gbc.gridy++;
        add(invitationsMenu, gbc);

        gbc.gridy++;
        add(invitationListPanel,gbc);

        gbc.gridy++;
        gbc.weighty = 1.0; // Give the last row a positive weight
        add(Box.createVerticalGlue(), gbc);

    }
    private JMenu createMenu(String text, Color foreground) {
        JMenu menu = new JMenu(text);
        menu.setForeground(foreground);
        menu.setBackground(new Color(52, 58, 64));
        menu.setOpaque(true);
        menu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return menu;
    }

    private JMenuItem createMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(new Font("sansserif", Font.PLAIN, 16));
        menuItem.setForeground(Color.WHITE);
        menuItem.setBackground(new Color(52, 58, 64));
        menuItem.setPreferredSize(new Dimension(200, 50));
        menuItem.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(new Color(108, 117, 125));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(new Color(52, 58, 64));
            }
        });
        return menuItem;
    }
}
