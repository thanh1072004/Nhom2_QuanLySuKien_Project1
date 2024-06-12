package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;

import src.base.MyColor;
import src.base.MyTextField;
import src.model.Event;
import src.model.Invite;
import src.model.User;
import src.service.*;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

public class InviteViewPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    private User user;
    private ServiceUser serviceUser;
    private ServiceEvent serviceEvent;
    private ServiceInvite serviceInvite;
    private ServiceAttendee serviceAttendee;


    public InviteViewPanel(User user) {
        this.user = user;
        serviceUser = new ServiceUser();
        serviceEvent = new ServiceEvent();
        serviceInvite = new ServiceInvite();
        serviceAttendee = new ServiceAttendee();

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
        JLabel tableNameLabel = new JLabel("INVITATION", JLabel.CENTER);
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        invitationListPanel1.add(tableNameLabel, BorderLayout.CENTER);
        invitationListPanel1.add(topPanel, BorderLayout.NORTH);
        
        String[] columnNames = {"#", "Name", "Date", "Location",  "Organizer", "Actions"};
        
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

        List<Color> backgroundColor = new ArrayList<>();
        backgroundColor.add(Color.CYAN);
        backgroundColor.add(MyColor.RED);
        List<ImageIcon> icons = new ArrayList<>();
        icons.add(editIcon);
        icons.add(deleteIcon);

        ButtonRenderer buttonRender = new ButtonRenderer(icons, backgroundColor);
        table.getColumnModel().getColumn(5).setCellRenderer(buttonRender);

        List<ActionListener> actionListeners = new ArrayList<>();
        actionListeners.add(e -> {
            try {
                int row = table.getSelectedRow();
                String event_name = tableModel.getValueAt(row, 1).toString();
                Event event = serviceEvent.getSelectedEvent(event_name);
                serviceAttendee.addAttendee(user, event);
                serviceInvite.removeInvite(user, event);

                removeRow(row);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });

        actionListeners.add(e -> {
            int row = table.getSelectedRow();
            removeRow(row);
        });

        ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
        table.getColumnModel().getColumn(5).setCellEditor(buttonEdit);

        table.setRowHeight(48);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount() - 1; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);

        JScrollPane tableScrollPane = new JScrollPane(table);
        
        invitationListPanel1.add(tableScrollPane, BorderLayout.SOUTH);
        add(invitationListPanel1, BorderLayout.CENTER);
        
        addSampleData();
        getInvite();
    }
    
    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void addRowToTable(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    private void getInvite() {
        int id = 1;
        tableModel.setRowCount(0);

        try {
            List<Invite> invites = serviceInvite.getInvites(user);

            for (Invite invite : invites) {
                Event event = invite.getEvent();
                User sender = invite.getSender();
                addRowToTable(new Object[]{id++, event.getName(), event.getDate(), event.getLocation(), sender.getUsername(), "Action"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to add some sample data
    private void addSampleData() {
        addRowToTable(new Object[]{"1", "Event A", "2024-06-10", "New York", "Org A", "Action"});
        addRowToTable(new Object[]{"2", "Event B", "2024-07-15", "Los Angeles", "Org B", "Action"});
        addRowToTable(new Object[]{"3", "Event C", "2024-08-20", "Chicago", "Org C", "Action"});
    }
}