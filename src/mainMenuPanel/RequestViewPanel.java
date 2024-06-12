package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;

import src.base.MyTextField;
import src.model.Event;
import src.model.Request;
import src.model.User;
import src.service.ServiceAttendee;
import src.service.ServiceEvent;
import src.service.ServiceRequest;
import src.service.ServiceUser;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

public class RequestViewPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    private ServiceRequest serviceRequest;
    private ServiceAttendee serviceAttendee;
    private ServiceEvent serviceEvent;
    private ServiceUser serviceUser;
    private User user;

    public RequestViewPanel(User user) {
        this.user = user;
        serviceRequest = new ServiceRequest();
        serviceAttendee = new ServiceAttendee();
        serviceEvent = new ServiceEvent();
        serviceUser = new ServiceUser();

    	setLayout(new BorderLayout());
        JPanel invitationListPanel1 = new JPanel(new BorderLayout());
        invitationListPanel1.setBackground(Color.WHITE);

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
        JLabel tableNameLabel = new JLabel("Participation Requests", JLabel.CENTER);
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        invitationListPanel1.add(tableNameLabel, BorderLayout.CENTER);
        invitationListPanel1.add(topPanel, BorderLayout.NORTH);
        
        String[] columnNames = {"#", "Name", "Date", "Location",  "Sender", "Actions"};
        
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

        ImageIcon originalDeleteIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/reject.png"));
        Image scaledImage_bin = originalDeleteIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(scaledImage_bin);;

        java.util.List<Color> backgroundColor = new ArrayList<>();
        backgroundColor.add(Color.CYAN);
        backgroundColor.add(Color.RED);
        java.util.List<ImageIcon> icons = new ArrayList<>();
        icons.add(editIcon);
        icons.add(deleteIcon);

        ButtonRenderer buttonRender = new ButtonRenderer(icons, backgroundColor);
        table.getColumnModel().getColumn(5).setCellRenderer(buttonRender);

        List<ActionListener> actionListeners = new ArrayList<>();
        actionListeners.add(e -> {
            try {
                int row = table.getSelectedRow();
                String event_name = tableModel.getValueAt(row, 1).toString();
                String username = tableModel.getValueAt(row, 4).toString();
                Event event = serviceEvent.getSelectedEvent(event_name);
                User sender = serviceUser.getUser(username);
                serviceAttendee.addAttendee(sender, event);

                removeRow(row);
            }catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        actionListeners.add(e -> {
            int row = table.getSelectedRow();
            removeRow(row);
        });

        ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
        table.getColumnModel().getColumn(5).setCellEditor(buttonEdit);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount() - 1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        table.setRowHeight(48);

        JScrollPane tableScrollPane = new JScrollPane(table);
        
        invitationListPanel1.add(tableScrollPane, BorderLayout.SOUTH);
        add(invitationListPanel1, BorderLayout.CENTER);
        
        getRequest();
    }

    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void addRowToTable(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    private void getRequest() {
        int id = 1;
        tableModel.setRowCount(0);

        try {
            List<Request> requests = serviceRequest.getRequests(user);

            for (Request request : requests) {
                Event event = request.getEvent();
                User sender = request.getSender();
                addRowToTable(new Object[]{id++, event.getName(), event.getDate(), event.getLocation(), sender.getUsername(), "Action"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

