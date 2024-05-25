package view;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import swing.MyTextField;

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
        leftPanel.setBackground(new Color(255, 223, 128)); 
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));

        JLabel usernameLabel = new JLabel("Username");
        ImageIcon originalIcon_user = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/user1.png")));
        Image scaledImage_user = originalIcon_user.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_user = new ImageIcon(scaledImage_user);
        usernameLabel.setIcon(scaledIcon_user);
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        leftPanel.add(usernameLabel);

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(160, 10));
        leftPanel.add(separator);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.Y_AXIS));
        menuBar.setBackground(new Color(255, 223, 128)); 
        menuBar.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JMenu eventsMenu = createMenu("Events", Color.BLACK);
        ImageIcon originalIcon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/event.png")));
        Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        eventsMenu.setIcon(scaledIcon);
        eventsMenu.add(createMenuItem("View Event", Color.BLACK));
        eventsMenu.add(createMenuItem("Create Event", Color.BLACK));
        menuBar.add(eventsMenu);

        JMenu invitationsMenu = createMenu("Invitations", Color.BLACK);
        ImageIcon originalIcon_invite = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/list.png")));
        Image scaledImage_invite = originalIcon_invite.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_invite = new ImageIcon(scaledImage_invite);
        invitationsMenu.setIcon(scaledIcon_invite);
        invitationsMenu.add(createMenuItem("Requests", Color.BLACK));
        invitationsMenu.add(createMenuItem("Invite User", Color.BLACK));
        menuBar.add(invitationsMenu);

        leftPanel.add(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        MyTextField searchField = new MyTextField();
        searchField.setHint("Search");
        searchField.setColumns(20);
        searchField.setPreferredSize(new Dimension(2000, 30));
        searchPanel.add(searchField);
        topPanel.add(searchPanel, BorderLayout.NORTH);

        try {
            URL imageURL = getClass().getResource("/icon/welcome.jpg");
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(imageURL));
                Image image = icon.getImage().getScaledInstance(800, 300, Image.SCALE_SMOOTH); 
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                topPanel.add(imageLabel, BorderLayout.CENTER);
            } 
            
        } catch (IOException e) {
            e.printStackTrace();
        }


        mainPanel.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Event ID", "Name", "Event Date", "Location", "Type", "Organizer ID", "Description", "Actions"};
        Object[][] data = {
            {1, "Class meeting", "01/01/2024", "Ha Noi", "Private", "123", "Description", "Actions1" },
            {2, "Event 2", "02/01/2024", "Ha Noi", "Public", "456", "Description", "Actions2"}
        }; 
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };
        JTable eventTable = new JTable(model);
        JTableHeader header = eventTable.getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            	JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(label.getFont().deriveFont(Font.BOLD));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(UIManager.getBorder("TableHeader.cellBorder")); 
                return label;
            }
        });
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < eventTable.getColumnCount(); i++) {
            eventTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        TableColumn actionsColumn = eventTable.getColumnModel().getColumn(7);
        actionsColumn.setCellRenderer(new ButtonRenderer());
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox()));
        
        eventTable.getTableHeader().setReorderingAllowed(false);
        eventTable.getTableHeader().setResizingAllowed(false);

        
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() / 3));
        mainPanel.add(scrollPane, BorderLayout.SOUTH);
        
        add(leftPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
    }

    private JMenu createMenu(String text, Color foreground) {
        JMenu menu = new JMenu(text);
        menu.setForeground(foreground);
        menu.setBackground(new Color(255, 223, 128)); 
        menu.setOpaque(true);
        menu.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return menu;
    }

    private JMenuItem createMenuItem(String text, Color foreground) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setForeground(foreground);
        menuItem.setBackground(new Color(255, 223, 128)); 
        return menuItem;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu frame = new MainMenu();
            frame.setVisible(true);
        });
    }
}
