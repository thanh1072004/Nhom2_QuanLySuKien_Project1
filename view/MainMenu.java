package view;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(0, 102, 0)); // Màu xanh đen
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel usernameLabel = new JLabel("Username");
        ImageIcon originalIcon_user = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/user1.png")));
        Image scaledImage_user = originalIcon_user.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_user = new ImageIcon(scaledImage_user);
        usernameLabel.setIcon(scaledIcon_user);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        leftPanel.add(usernameLabel);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(160, 10));
        leftPanel.add(separator);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        menuBar.setBackground(new Color(0, 102, 0)); // Màu xanh đen
        menuBar.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JMenu eventsMenu = createMenu("Events", Color.WHITE);
        ImageIcon originalIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/event.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        eventsMenu.setIcon(scaledIcon);
        eventsMenu.add(createMenuItem("Event List", Color.WHITE));
        eventsMenu.add(createMenuItem("Create Event", Color.WHITE));
        menuBar.add(eventsMenu);

        JMenu invitationsMenu = createMenu("Invitations", Color.WHITE);
        ImageIcon originalIcon_invite = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/list.png")));
        Image scaledImage_invite = originalIcon_invite.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_invite = new ImageIcon(scaledImage_invite);
        invitationsMenu.setIcon(scaledIcon_invite);
        invitationsMenu.add(createMenuItem("Requests", Color.WHITE));
        invitationsMenu.add(createMenuItem("Invite User", Color.WHITE));
        menuBar.add(invitationsMenu);

        leftPanel.add(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        JTextField searchField = new JTextField("Search", 20);
        searchPanel.add(searchField);
        topPanel.add(searchPanel, BorderLayout.NORTH);

        try {
            URL imageURL = getClass().getResource("/icon/welcome.jpg");
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(imageURL));
                Image image = icon.getImage().getScaledInstance(800, 300, Image.SCALE_SMOOTH); // Điều chỉnh kích thước ảnh
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                topPanel.add(imageLabel, BorderLayout.CENTER);
            } 
            
        } catch (IOException e) {
            e.printStackTrace();
        }


        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"#", "Name", "Category", "Start Date", "End Date", "Location", "Type"};
        Object[][] data = {
            {1, "Event 1", "Category 1", "01/01/2024", "01/02/2024", "Location 1", "Type 1"},
            {2, "Event 2", "Category 2", "02/01/2024", "02/02/2024", "Location 2", "Type 2"}
        }; 
        JTable eventTable = new JTable(data, columnNames);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < eventTable.getColumnCount(); i++) {
            eventTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() / 3)); // Chỉnh kích thước 1/3 của cửa sổ
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        add(leftPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JMenu createMenu(String text, Color foreground) {
        JMenu menu = new JMenu(text);
        menu.setForeground(foreground);
        menu.setBackground(new Color(0, 102, 0)); 
        menu.setOpaque(true);
        menu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return menu;
    }

    private JMenuItem createMenuItem(String text, Color foreground) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setForeground(foreground);
        menuItem.setBackground(new Color(0, 102, 0)); 
        return menuItem;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu frame = new MainMenu();
            frame.setVisible(true);
        });
    }
}
