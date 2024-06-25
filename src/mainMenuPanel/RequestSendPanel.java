package src.mainMenuPanel;

import raven.toast.Notifications;
import src.base.Config;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.service.ServiceRequest;
import src.base.ButtonEditor;
import src.base.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestSendPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private transient ServiceEvent serviceEvent;
    private transient ServiceRequest serviceRequest;
    private transient User user;

    public RequestSendPanel(User user, MainMenu mainMenu) {
        this.user = user;
        try {
            serviceEvent = new ServiceEvent();
            serviceRequest = new ServiceRequest();

            setLayout(new BorderLayout(0, 20));
            setBackground(Color.WHITE);

            JLabel tableNameLabel = new JLabel("Public Event", JLabel.CENTER);
            tableNameLabel.setFont(new Font("Serif", Font.BOLD, 36));
            tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            startBlinking(tableNameLabel);

            String[] columnNames = {"#", "Event ID", "Name", "Date", "Location", "Organizer", "Actions"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == getColumnCount() - 1;
                }
            };
            table = new JTable(tableModel);
            table.setRowHeight(48);
            table.setDefaultEditor(Object.class, null);
            table.setRowSelectionAllowed(false);
            table.setFocusable(false);

            ImageIcon originalSendIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/join.png"));
            Image scaledImage_send = originalSendIcon.getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH);
            ImageIcon sendIcon = new ImageIcon(scaledImage_send);

            ImageIcon originalDeleteIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/delete.png"));
            Image scaledImage_bin = originalDeleteIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            ImageIcon deleteIcon = new ImageIcon(scaledImage_bin);;

            List<Color> backgroundColor = new ArrayList<>();
            backgroundColor.add(Config.CYAN);
            backgroundColor.add(Config.RED);
            List<ImageIcon> icons = new ArrayList<>();
            icons.add(sendIcon);
            icons.add(deleteIcon);

            ButtonRenderer buttonRender = new ButtonRenderer(icons, backgroundColor);
            table.getColumnModel().getColumn(6).setCellRenderer(buttonRender);

            List<ActionListener> actionListeners = new ArrayList<>();
            actionListeners.add(e -> {
                try{
                    int row = table.getSelectedRow();
                    int event_id = (int) tableModel.getValueAt(row, 1);
                    Event event = serviceEvent.getSelectedEvent(event_id);
                    serviceRequest.addRequest(user, event);
                    mainMenu.showMessage(Notifications.Type.SUCCESS, "Request sent");
                    removeRow(row);

                }catch(Exception ex){
                    mainMenu.showMessage(Notifications.Type.ERROR, "Failed to send request! Please try again later");
                }
            });
            actionListeners.add(e -> {
                int row = table.getSelectedRow();
                int event_id = (int) tableModel.getValueAt(row, 1);

                try {
                    Event event = serviceEvent.getSelectedEvent(event_id);
                    serviceRequest.addRequest(user, event);
                    serviceRequest.removeRequest(user, event);
                } catch (SQLException ex) {
                    mainMenu.showMessage(Notifications.Type.ERROR, "Failed to delete event! Please try again later");
                }
                removeRow(row);
            });

            ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
            table.getColumnModel().getColumn(6).setCellEditor(buttonEdit);

            JTableHeader tableHeader = table.getTableHeader();
            tableHeader.setDefaultRenderer(new HeaderRenderer(tableHeader.getDefaultRenderer()));
            tableHeader.setFont(new Font("Calibri", Font.BOLD, 15));
            
            // Center align all data
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount() - 1; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);

            JScrollPane tableScrollPane = new JScrollPane(table);

            TableColumn eventIDColumn = table.getColumnModel().getColumn(1);
            eventIDColumn.setMinWidth(0);
            eventIDColumn.setMaxWidth(0);
            eventIDColumn.setWidth(0);
            eventIDColumn.setPreferredWidth(0);

            add(tableNameLabel, BorderLayout.NORTH);
            add(tableScrollPane, BorderLayout.CENTER);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addRow(int id, int event_id, String name,  String date, String location, User organizer) {
        tableModel.addRow(new Object[]{id, event_id, name, date, location, organizer.getUsername()});
    }

    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void loadPublicEvents() {
        int id = 1;
        tableModel.setRowCount(0);
        try{
            List<Event> events = serviceEvent.getPublicEvents(user);
            for (Event event : events) {
                addRow(id++, event.getId(), event.getName(), event.getDate(), event.getLocation(), event.getOrganizer());
            }
        }catch(Exception e){
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
                label.setHorizontalAlignment(JLabel.CENTER); 
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

