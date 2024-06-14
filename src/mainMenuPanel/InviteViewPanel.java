package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.*;

import src.MainMenu;
import src.base.MyColor;
import src.base.MyTextField;
import src.model.Event;
import src.model.Invite;
import src.model.User;
import src.service.*;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

public class InviteViewPanel extends JPanel{
    private TablePanel tablePanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private User user;
    private ServiceEvent serviceEvent;
    private ServiceInvite serviceInvite;
    private ServiceAttendee serviceAttendee;
	private JLabel messageLabel;


    public InviteViewPanel(User user, MainMenu mainMenu) {
        this.user = user;
        tablePanel = mainMenu.getTablePanel();
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
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 38));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        startBlinking(tableNameLabel);

        invitationListPanel1.add(tableNameLabel, BorderLayout.CENTER);
        invitationListPanel1.add(topPanel, BorderLayout.NORTH);
        
        String[] columnNames = {"#", "Name", "Date", "Location", "Type", "Organizer", "Actions"};
        
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
        Image scaledImage_edit = originalEditIcon.getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH);
        ImageIcon editIcon = new ImageIcon(scaledImage_edit);

        ImageIcon originalDeleteIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/delete.png"));
        Image scaledImage_bin = originalDeleteIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon deleteIcon = new ImageIcon(scaledImage_bin);;

        List<Color> backgroundColor = new ArrayList<>();
        backgroundColor.add(MyColor.CYAN);
        backgroundColor.add(MyColor.RED);
        List<ImageIcon> icons = new ArrayList<>();
        icons.add(editIcon);
        icons.add(deleteIcon);

        ButtonRenderer buttonRender = new ButtonRenderer(icons, backgroundColor);
        table.getColumnModel().getColumn(6).setCellRenderer(buttonRender);

        List<ActionListener> actionListeners = new ArrayList<>();
        actionListeners.add(e -> {
            acceptInvite();
        });

        actionListeners.add(e -> {
            int row = table.getSelectedRow();
            removeRow(row);
            showMessage("Invite deleted successfully.", Color.GREEN);
        });

        ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
        table.getColumnModel().getColumn(6).setCellEditor(buttonEdit);

        table.setRowHeight(48);

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
        
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(Color.GREEN); // Màu của thông báo thành công

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        add(messagePanel, BorderLayout.SOUTH);
        
        invitationListPanel1.add(tableScrollPane, BorderLayout.SOUTH);
        add(invitationListPanel1, BorderLayout.CENTER);

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
                addRowToTable(new Object[]{id++, event.getName(), event.getDate(), event.getLocation(), event.getType(), sender.getUsername(), "Action"});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void acceptInvite(){
        SwingWorker<Event, Void> worker = new SwingWorker<>() {

            @Override
            protected Event doInBackground() throws Exception {
                int row = table.getSelectedRow();
                String event_name = tableModel.getValueAt(row, 1).toString();
                Event event = serviceEvent.getSelectedEvent(event_name);
                serviceAttendee.addAttendee(user, event);
                removeRow(row);
                return event;
            }

            @Override
            protected void done() {
                try{
                    Event event = get();
                    tablePanel.addRow(event.getName(), event.getDate(), event.getLocation(), event.getType(), event.getOrganizer());
                    showMessage("Invite accepted successfully.", Color.GREEN);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
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