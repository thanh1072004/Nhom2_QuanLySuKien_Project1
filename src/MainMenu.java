import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import database.DatabaseConnection;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import service.ServiceEvent;
import swing.MyTextField;
import model.Event;
import components.Message;
import components.SideBar;
import view.ButtonEditor;
import view.ButtonRenderer;

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

public class MainMenu extends JFrame {

    private Event event;
    private ServiceEvent service;

    public MainMenu() {
        setTitle("Event Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

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
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

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
        createEventButton.setFont(labelFont);
        createEventButton.setFocusPainted(false);
        buttonPanel.add(createEventButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        createEventForm.add(buttonPanel, gbc);
        createEventPanel.add(createEventForm, BorderLayout.CENTER);

        // Thêm các panel vào mainPanel
        mainPanel.add(eventListPanel1, "EventList");
        mainPanel.add(createEventPanel, "CreateEvent");

        SideBar sideBar = new SideBar(mainPanel, cardLayout);
        add(sideBar, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

    }

    public Event getEvent(){
        return event;
    }
    private void createEvent(){
        Event event = this.getEvent();
        try{
            if(event.getName() == null || event.getEvent_date() == null || event.getLocation() == null || event.getType() == null){
                showMessage(Message.MessageType.ERROR, "Please fill all the required fields");
            }else{
                service.authorizeEvent(event);
                showMessage(Message.MessageType.SUCCESS, "Successfully create event");
            }
        }catch(Exception e){
            showMessage(Message.MessageType.ERROR, "Create event failed ");
        }

    }
    private void showMessage(Message.MessageType messageType, String message){
        Message msg = new Message();
        msg.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if(!msg.isShow()){
                    bg.add(msg, "pos 0.5al -30", 0);//insert to bg first index 0
                    msg.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction){
                float f;
                if(msg.isShow()){
                    f = 40 * (1f - fraction);
                }else{
                    f = 40 * fraction;
                }
                //layout.setComponentConstraints(msg, "pos 0.5al " + (int) (f-30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end(){
                if(msg.isShow()){
                    bg.remove(msg);
                    bg.repaint();
                    bg.revalidate();
                }else{
                    msg.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    animator.start();
                }catch(InterruptedException e){
                    System.err.println(e);
                }
            }
        });
    }
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(Color.WHITE);
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 588, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 496, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg)
        );

        pack();
    }

    private JLayeredPane bg;
    public static void main(String[] args) {
        /*try{
            DatabaseConnection.getInstance().connectToDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }*/
      SwingUtilities.invokeLater(() -> {
          MainMenu frame = new MainMenu();
          frame.setVisible(true);
      });
  }
}
