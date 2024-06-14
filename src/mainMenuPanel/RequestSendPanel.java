package src.mainMenuPanel;

import src.base.MyColor;
import src.base.MyTextField;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.service.ServiceRequest;
import src.service.ServiceUser;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RequestSendPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ServiceEvent serviceEvent;
    private ServiceRequest serviceRequest;
	private JLabel messageLabel;


    public RequestSendPanel(User user) {
        try {
            serviceEvent = new ServiceEvent();
            serviceRequest = new ServiceRequest();
            List<Event> events = serviceEvent.getPublicEvents(user);

            setLayout(new BorderLayout());
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

            JLabel tableNameLabel = new JLabel("Public Event", JLabel.CENTER);
            tableNameLabel.setFont(new Font("Serif", Font.BOLD, 38));
            tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            startBlinking(tableNameLabel);

            eventListPanel1.add(tableNameLabel, BorderLayout.CENTER);
            eventListPanel1.add(topPanel, BorderLayout.NORTH);

            String[] columnNames = {"Event ID", "Name", "Date", "Location", "Organizer", "Actions"};
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

            ImageIcon originalSendIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/join.png"));
            Image scaledImage_send = originalSendIcon.getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH);
            ImageIcon sendIcon = new ImageIcon(scaledImage_send);

            ImageIcon originalDeleteIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/delete.png"));
            Image scaledImage_bin = originalDeleteIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            ImageIcon deleteIcon = new ImageIcon(scaledImage_bin);;

            List<Color> backgroundColor = new ArrayList<>();
            backgroundColor.add(MyColor.CYAN);
            backgroundColor.add(MyColor.RED);
            List<ImageIcon> icons = new ArrayList<>();
            icons.add(sendIcon);
            icons.add(deleteIcon);

            ButtonRenderer buttonRender = new ButtonRenderer(icons, backgroundColor);
            table.getColumnModel().getColumn(5).setCellRenderer(buttonRender);

            List<ActionListener> actionListeners = new ArrayList<>();
            actionListeners.add(e -> {
                int row = table.getSelectedRow();
                String name = (String) table.getValueAt(row, 1);
                try{
                    Event event = serviceEvent.getSelectedEvent(name);
                    serviceRequest.addRequest(user, event);
                    showMessage("Request successfully.", Color.GREEN);
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                removeRow(row);
            });
            actionListeners.add(e -> {
                int row = table.getSelectedRow();
                String name = (String) table.getValueAt(row, 1);

                try {
                    Event event = serviceEvent.getSelectedEvent(name);
                    showMessage("Delete successfully.", Color.GREEN);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                removeRow(row);
            });

            ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
            table.getColumnModel().getColumn(5).setCellEditor(buttonEdit);

            table.setRowHeight(48);

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
            
            messageLabel = new JLabel("", JLabel.CENTER);
            messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
            messageLabel.setForeground(Color.GREEN); 

            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.add(messageLabel, BorderLayout.CENTER);
            add(messagePanel, BorderLayout.SOUTH);

            eventListPanel1.add(tableScrollPane, BorderLayout.SOUTH);
            add(eventListPanel1, BorderLayout.CENTER);

            loadPublicEvents(events);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addRow(int id, String name,  String date, String location, User organizer) {
        tableModel.addRow(new Object[]{id, name, date, location, organizer.getUsername()});
    }

    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void loadPublicEvents(List<Event> events) {
        int id = 1;
        tableModel.setRowCount(0);
        for (Event event : events) {
            addRow(id++, event.getName(), event.getDate(), event.getLocation(), event.getOrganizer());
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
    
    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setForeground(color);

        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}

