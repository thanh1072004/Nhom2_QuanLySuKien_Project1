package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;

import raven.toast.Notifications;
import src.base.Config;
import src.model.Event;
import src.model.Request;
import src.model.User;
import src.service.*;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

public class RequestViewPanel extends JPanel{
    private MainMenu mainMenu;
    private JTable table;
    private DefaultTableModel tableModel;
    private ServiceRequest serviceRequest;
    private ServiceAttendee serviceAttendee;
    private ServiceEvent serviceEvent;
    private ServiceUser serviceUser;
    private ServiceNotification serviceNotification;
    private User user;
    private String message;

    public RequestViewPanel(User user, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.user = user;
        serviceUser = new ServiceUser();
        serviceEvent = new ServiceEvent();
        serviceRequest = new ServiceRequest();
        serviceAttendee = new ServiceAttendee();
        serviceNotification = new ServiceNotification();

    	setLayout(new BorderLayout(0, 20));
        setBackground(Color.WHITE);

        JLabel tableNameLabel = new JLabel("Participation Requests", JLabel.CENTER);
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 36));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        startBlinking(tableNameLabel);
        
        String[] columnNames = {"#", "Event Id", "Name", "Date", "Location",  "Sender", "Actions"};
        
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == getColumnCount() - 1;
            }
        };
        
        table = new JTable(tableModel);

        table.setDefaultEditor(Object.class, null);
        table.setRowSelectionAllowed(false);
        table.setFocusable(false);

        ImageIcon originalEditIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/accept.png"));
        Image scaledImage_edit = originalEditIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon editIcon = new ImageIcon(scaledImage_edit);

        ImageIcon originalDeleteIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/delete.png"));
        Image scaledImage_bin = originalDeleteIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(scaledImage_bin);;

        java.util.List<Color> backgroundColor = new ArrayList<>();
        backgroundColor.add(Config.CYAN);
        backgroundColor.add(Config.RED);
        java.util.List<ImageIcon> icons = new ArrayList<>();
        icons.add(editIcon);
        icons.add(deleteIcon);

        ButtonRenderer buttonRender = new ButtonRenderer(icons, backgroundColor);
        table.getColumnModel().getColumn(6).setCellRenderer(buttonRender);

        List<ActionListener> actionListeners = new ArrayList<>();
        actionListeners.add(e -> {
            try {
                int row = table.getSelectedRow();
                int event_id = (int) tableModel.getValueAt(row, 1);
                String username = tableModel.getValueAt(row, 5).toString();
                Event event = serviceEvent.getSelectedEvent(event_id);
                User sender = serviceUser.getUser(username);

                if(!serviceAttendee.checkAttendee(sender, event)){
                    mainMenu.showMessage(Notifications.Type.SUCCESS,"Request accepted");
                    serviceAttendee.addAttendee(sender, event);
                    message = user.getUsername() + " has accepted your request to join event " + event.getName();
                    serviceNotification.addNotification(sender, message);
                }
                serviceRequest.removeRequest(sender, event);

                removeRow(row);

            }catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        actionListeners.add(e -> {
            try{
                int row = table.getSelectedRow();
                int event_id = (int) tableModel.getValueAt(row, 1);
                String username = tableModel.getValueAt(row, 5).toString();
                Event event = serviceEvent.getSelectedEvent(event_id);
                User sender = serviceUser.getUser(username);

                message = user.getUsername() + " has rejected your request to join event " + event.getName();
                serviceNotification.addNotification(sender, message);

                serviceRequest.removeRequest(sender, event);
                mainMenu.showMessage(Notifications.Type.SUCCESS,"Request removed");
                removeRow(row);

            }catch(Exception ex){
                ex.printStackTrace();
            }
        });

        ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
        table.getColumnModel().getColumn(6).setCellEditor(buttonEdit);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new HeaderRenderer(tableHeader.getDefaultRenderer()));
        tableHeader.setFont(new Font("Calibri", Font.BOLD, 15));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount() - 1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        table.setRowHeight(48);

        JScrollPane tableScrollPane = new JScrollPane(table);

        TableColumn eventIDColumn = table.getColumnModel().getColumn(1);
        eventIDColumn.setMinWidth(0);
        eventIDColumn.setMaxWidth(0);
        eventIDColumn.setWidth(0);
        eventIDColumn.setPreferredWidth(0);

        add(tableNameLabel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
    }

    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void addRowToTable(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void getRequest() {
        int id = 1;
        tableModel.setRowCount(0);

        try {
            List<Request> requests = serviceRequest.getRequests(user);

            for (Request request : requests) {
                Event event = request.getEvent();
                User sender = request.getSender();
                addRowToTable(new Object[]{id++, event.getId(), event.getName(), event.getDate(), event.getLocation(), sender.getUsername(), "Action"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static class HeaderRenderer implements TableCellRenderer {
        private final TableCellRenderer delegate;

        public HeaderRenderer(TableCellRenderer delegate) {
            this.delegate = delegate;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = delegate.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (c instanceof JLabel) {
                JLabel label = (JLabel) c;
                label.setFont(label.getFont().deriveFont(Font.BOLD));
                label.setHorizontalAlignment(JLabel.CENTER); // Optional: Center align the header text
            }
            return c;
        }
    }
    
    private void startBlinking(JLabel label) {
        Timer timer = new Timer(2500, new ActionListener() {
            private boolean isBlue = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isBlue) {
                    label.setForeground(Color.BLUE);
                } else {
                    label.setForeground(Color.MAGENTA);
                }
                isBlue = !isBlue;
            }
        });
        timer.start();
    }
}

