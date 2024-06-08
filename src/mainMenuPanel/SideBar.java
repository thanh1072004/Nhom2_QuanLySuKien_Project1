package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import src.base.MyColor;
import src.model.User;

public class SideBar extends JPanel {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private User user;

    public SideBar(JPanel mainPanel, CardLayout cardLayout, User user){
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.user = user;

        setPreferredSize(new Dimension(200, getHeight()));
        setLayout(new GridBagLayout());
        setBackground(MyColor.BACKGROUND_MENU_ITEM);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        JLabel usernameLabel = new JLabel(user.getUsername());
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        ImageIcon originalIcon_user = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SideBar.class.getResource("/src/icon/user1.png")));
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
        ImageIcon originalIcon_event = new ImageIcon(Toolkit.getDefaultToolkit().createImage(SideBar.class.getResource("/src/icon/event.png")));
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
                    eventsMenu.setBackground(MyColor.BACKGROUND_MENU_ITEM2);
                } else {
                    // Remove all event items
                    eventListPanel.removeAll();
                    eventsMenu.setBackground(MyColor.BACKGROUND_MENU_ITEM);
                }
                eventsVisible = !eventsVisible;

                revalidate();
                repaint();
            }
        });
        create.addActionListener(e -> cardLayout.show(mainPanel, "eventCreatePanel"));
        view.addActionListener(e -> cardLayout.show(mainPanel, "tablePanel"));

        JMenu invitationsMenu = createMenu("Invitations", Color.WHITE);
        invitationsMenu.setPreferredSize(new Dimension(200, 50));
        invitationsMenu.setFont(new Font("sanserif", Font.PLAIN, 16));
        ImageIcon originalIcon_invite = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SideBar.class.getResource("/src/icon/list.png")));
        Image scaledImage_invite = originalIcon_invite.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_invite = new ImageIcon(scaledImage_invite);
        invitationsMenu.setIcon(scaledIcon_invite);

        JPanel invitationListPanel = new JPanel();
        invitationListPanel.setLayout(new BoxLayout(invitationListPanel, BoxLayout.Y_AXIS));

        JMenuItem viewInvitations = createMenuItem("View invitations");
        JMenuItem sendInvitations = createMenuItem("Send invitations");
        invitationsMenu.addMouseListener(new MouseAdapter() {
            private boolean eventsVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!eventsVisible) {
                    // Add event items to the event list panel
                    invitationListPanel.add(viewInvitations);
                    invitationListPanel.add(sendInvitations);
                    invitationsMenu.setBackground(MyColor.BACKGROUND_MENU_ITEM2);
                } else {
                    // Remove all event items
                    invitationListPanel.removeAll();
                    invitationsMenu.setBackground(MyColor.BACKGROUND_MENU_ITEM);

                }
                // Toggle visibility
                eventsVisible = !eventsVisible;
                // Revalidate and repaint to update the layout
                revalidate();
                repaint();
            }
        });
        viewInvitations.addActionListener(e -> cardLayout.show(mainPanel, "inviteViewPanel"));
        sendInvitations.addActionListener(e -> cardLayout.show(mainPanel, "inviteSendPanel"));

        JMenu requestMenu = createMenu("Request", Color.WHITE);
        requestMenu.setPreferredSize(new Dimension(200, 50));
        requestMenu.setFont(new Font("sanserif", Font.PLAIN, 16));
        ImageIcon originalIcon_request = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SideBar.class.getResource("/src/icon/list.png")));
        Image scaledImage_request = originalIcon_request.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_request= new ImageIcon(scaledImage_request);
        requestMenu.setIcon(scaledIcon_request);

        JPanel requestPanel = new JPanel();
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        JMenuItem viewRequest = createMenuItem("View requests");
        JMenuItem sendRequest = createMenuItem("Send requests");
        requestMenu.addMouseListener(new MouseAdapter() {
            private boolean eventsVisible = false;

            @Override
            public void mouseClicked(MouseEvent e) {
                if (!eventsVisible) {
                    requestPanel.add(viewRequest);
                    requestPanel.add(sendRequest);
                    requestMenu.setBackground(MyColor.BACKGROUND_MENU_ITEM2);
                } else {
                    requestPanel.removeAll();
                    requestMenu.setBackground(MyColor.BACKGROUND_MENU_ITEM);

                }
                eventsVisible = !eventsVisible;

                revalidate();
                repaint();
            }
        });
        viewRequest.addActionListener(e -> cardLayout.show(mainPanel, "requestViewPanel"));
        sendRequest.addActionListener(e -> cardLayout.show(mainPanel, "requestSendPanel"));
        add(usernameLabel, gbc);

        gbc.gridy++;
        add(separator, gbc);

        gbc.gridy++;
        add(eventsMenu,gbc);

        gbc.gridy++;
        add(eventListPanel,gbc);

        gbc.gridy++;
        add(invitationsMenu,gbc);

        gbc.gridy++;
        add(invitationListPanel,gbc);

        gbc.gridy++;
        add(requestPanel,gbc);

        gbc.gridy++;
        add(requestMenu,gbc);

        gbc.gridy++;
        add(requestPanel,gbc);

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
                menuItem.setBackground(MyColor.BACKGROUND_MENU_ITEM2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(MyColor.BACKGROUND_MENU_ITEM);
            }
        });
        return menuItem;
    }
}