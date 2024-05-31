package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import model.Login;
import model.User;
import service.ServiceUser;
import swing.MyTextField;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

     
        // Hiển thị tên người dùng trong leftPanel
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        ImageIcon originalIcon_user = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/user.png")));
        Image scaledImage_user = originalIcon_user.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_user = new ImageIcon(scaledImage_user);
        usernameLabel.setIcon(scaledIcon_user);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        usernameLabel.setPreferredSize(new Dimension(200, 50));

        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(160, 10));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        leftPanel.setBackground(new Color(52, 58, 64));
        leftPanel.setPreferredSize(new Dimension(200, getHeight()));

        JMenu eventsMenu = createMenu("Events", Color.WHITE);
        eventsMenu.setPreferredSize(new Dimension(200, 50));
        eventsMenu.setFont(new Font("sanserif", Font.PLAIN, 16));
        ImageIcon originalIcon_event = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/event1.png")));
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

                leftPanel.revalidate();
                leftPanel.repaint();
            }
        });

        JMenu invitationsMenu = createMenu("Invitations", Color.WHITE);
        invitationsMenu.setPreferredSize(new Dimension(200, 50));
        invitationsMenu.setFont(new Font("sanserif", Font.PLAIN, 16));
        ImageIcon originalIcon_invite = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                MainMenu.class.getResource("/icon/list1.png")));
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
                leftPanel.revalidate();
                leftPanel.repaint();
            }
        });


        leftPanel.add(usernameLabel, gbc);

        gbc.gridy++;
        leftPanel.add(separator, gbc);

        gbc.gridy++;
        leftPanel.add(eventsMenu,gbc);

        gbc.gridy++;
        leftPanel.add(eventListPanel,gbc);

        gbc.gridy++;
        leftPanel.add(invitationsMenu, gbc);

        gbc.gridy++;
        leftPanel.add(invitationListPanel,gbc);

        gbc.gridy++;
        gbc.weighty = 1.0; // Give the last row a positive weight
        leftPanel.add(Box.createVerticalGlue(), gbc);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JPanel eventListPanel1 = new JPanel(new BorderLayout());
        eventListPanel1.setBackground(Color.WHITE);

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
        
        // Tạo tên bảng
        JLabel tableNameLabel = new JLabel("YOUR CREATED EVENT TABLE", JLabel.CENTER);
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        try {
            URL imageURL = getClass().getResource("/icon/welcome.jpg");
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(imageURL));
                Image image = icon.getImage().getScaledInstance(800, 300, Image.SCALE_SMOOTH); 
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                topPanel.add(imageLabel, BorderLayout.CENTER);
                topPanel.add(tableNameLabel, BorderLayout.SOUTH);
            } 
            
        } catch (IOException e) {
            e.printStackTrace();
        }


        eventListPanel1.add(topPanel, BorderLayout.NORTH);

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
        
        // Đặt chiều cao của mỗi dòng
        eventTable.setRowHeight(25);

        
        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() / 4));
        eventListPanel1.add(scrollPane, BorderLayout.SOUTH);
        
        // Tạo panel cho Create Event
        JPanel createEventPanel = new JPanel(new BorderLayout());
        createEventPanel.setBackground(Color.WHITE);

        // Thêm các Label và TextField
        JPanel createEventForm = new JPanel(new GridBagLayout());
        createEventForm.setBackground(Color.WHITE);
        createEventForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.insets = new Insets(10, 10, 10, 10);
        gbc1.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.PLAIN, 16);
        Font fieldFont = new Font("Arial", Font.PLAIN, 16);

        JLabel nameLabel = new JLabel("Event Name:");
        nameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        createEventForm.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setFont(fieldFont);
        gbc.gridx = 1;
        createEventForm.add(nameField, gbc);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        createEventForm.add(descriptionLabel, gbc);

        JTextArea descriptionField = new JTextArea(5, 20);
        descriptionField.setFont(fieldFont);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
        gbc.gridx = 1;
        createEventForm.add(descriptionScrollPane, gbc);


        JLabel dateLabel = new JLabel("Event Date:");
        dateLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        createEventForm.add(dateLabel, gbc);

        JTextField dateField = new JTextField(20);
        dateField.setFont(fieldFont);
        gbc.gridx = 1;
        createEventForm.add(dateField, gbc);

        JLabel locationLabel = new JLabel("Location:");
        locationLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        createEventForm.add(locationLabel, gbc);

        JTextField locationField = new JTextField(20);
        locationField.setFont(fieldFont);
        gbc.gridx = 1;
        createEventForm.add(locationField, gbc);

        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        createEventForm.add(typeLabel, gbc);

        JTextField typeField = new JTextField(20);
        typeField.setFont(fieldFont);
        gbc.gridx = 1;
        createEventForm.add(typeField, gbc);

        JLabel idLabel = new JLabel("Your ID:");
        idLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        createEventForm.add(idLabel, gbc);

        JTextField idField = new JTextField(20);
        idField.setFont(fieldFont);
        gbc.gridx = 1;
        createEventForm.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton createEventButton = new JButton("Create Event");
        JButton homeButton = new JButton("Home");
        createEventButton.setFont(labelFont);
        homeButton.setFont(labelFont);
        buttonPanel.add(createEventButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0))); 
        buttonPanel.add(homeButton);
        createEventForm.add(buttonPanel, gbc);

        createEventPanel.add(createEventForm, BorderLayout.CENTER);

        // Thêm các panel vào mainPanel
        mainPanel.add(eventListPanel1, "EventList");
        mainPanel.add(createEventPanel, "CreateEvent");
        
        add(leftPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);
        
        // Hành động của các nút bấm
        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "EventList"));
        create.addActionListener(e -> cardLayout.show(mainPanel, "CreateEvent"));
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
        menuItem.setForeground(Color.WHITE);
        menuItem.setBackground(new Color(52, 58, 64));
        menuItem.setPreferredSize(new Dimension(200, 50));
        menuItem.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        return menuItem;
    }

    public static void main(String[] args) {
      SwingUtilities.invokeLater(() -> {
          MainMenu frame = new MainMenu();
          frame.setVisible(true);
      });
  }
}
