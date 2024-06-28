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
import src.model.Invite;
import src.model.User;
import src.service.*;
import src.base.ButtonEditor;
import src.base.ButtonRenderer;

public class InviteViewPanel extends JPanel{
    private MainMenu mainMenu;
    private TablePanel tablePanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private transient User user;
    private transient ServiceEvent serviceEvent;
    private transient ServiceInvite serviceInvite;
    private transient ServiceAttendee serviceAttendee;

    public InviteViewPanel(User user, MainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.user = user;
        tablePanel = mainMenu.getTablePanel();
        serviceEvent = new ServiceEvent();
        serviceInvite = new ServiceInvite();
        serviceAttendee = new ServiceAttendee();

    	setLayout(new BorderLayout(0,20));
        setBackground(Color.WHITE);

        JLabel tableNameLabel = new JLabel("INVITATION", JLabel.CENTER);
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 36));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        startBlinking(tableNameLabel);

        String[] columnNames = {"#", "Event ID", "Name", "Date", "Location", "Type", "Organizer", "Actions"};
        
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

        ImageIcon originalEditIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/accept.png"));
        Image scaledImage_edit = originalEditIcon.getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH);
        ImageIcon editIcon = new ImageIcon(scaledImage_edit);

        ImageIcon originalDeleteIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/delete.png"));
        Image scaledImage_bin = originalDeleteIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(scaledImage_bin);;

        List<Color> backgroundColor = new ArrayList<>();
        backgroundColor.add(Config.CYAN);
        backgroundColor.add(Config.RED);
        List<ImageIcon> icons = new ArrayList<>();
        icons.add(editIcon);
        icons.add(deleteIcon);

        ButtonRenderer buttonRender = new ButtonRenderer(icons, backgroundColor);
        table.getColumnModel().getColumn(7).setCellRenderer(buttonRender);

        List<ActionListener> actionListeners = new ArrayList<>();
        actionListeners.add(e -> {
            try{
                int row = table.getSelectedRow();
                int event_id = (int) tableModel.getValueAt(row, 1);
                Event event = serviceEvent.getSelectedEvent(event_id);
                if(!serviceAttendee.checkAttendee(user, event)){
                    serviceAttendee.addAttendee(user, event);

                    tablePanel.addRow(event.getId(), event.getName(), event.getDate(), event.getLocation(), event.getType(), event.getOrganizer());
                    mainMenu.showMessage(Notifications.Type.SUCCESS, "Invitation accepted");

                }else{
                    mainMenu.showMessage(Notifications.Type.SUCCESS, "You have already been in the event");
                }
                serviceInvite.removeInvite(user, event);
                removeRow(row);
            }catch(Exception ex){
                mainMenu.showMessage(Notifications.Type.ERROR, "Failed to accept invitation! Please try again later");
            }
        });
        actionListeners.add(e -> {
            try{
                int row = table.getSelectedRow();
                int event_id = (int) tableModel.getValueAt(row, 1);
                Event event = serviceEvent.getSelectedEvent(event_id);

                serviceInvite.removeInvite(user, event);
                mainMenu.showMessage(Notifications.Type.SUCCESS, "Invitation deleted");
                removeRow(row);
            }catch(Exception ex){
                mainMenu.showMessage(Notifications.Type.ERROR, "Failed to reject invitation! Please try again later");
            }
        });

        ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
        table.getColumnModel().getColumn(7).setCellEditor(buttonEdit);

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

    public void getInvite(User user) {
        int id = 1;
        tableModel.setRowCount(0);
        try {
            List<Invite> invites = serviceInvite.getInvites(user);
            for (Invite invite : invites) {
                Event event = invite.getEvent();
                User sender = invite.getOrganizer();
                addRowToTable(new Object[]{id++, event.getId(), event.getName(), event.getDate(), event.getLocation(), event.getType(), sender.getUsername(), "Action"});
            }
        } catch (SQLException e) {
            mainMenu.showMessage(Notifications.Type.ERROR, "Failed to get invitation! Please try again later");
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