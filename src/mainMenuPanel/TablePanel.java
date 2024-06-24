package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import raven.toast.Notifications;
import src.base.Config;
import src.base.ScrollBarCustom;
import src.model.Event;
import src.model.User;
import src.service.ServiceAttendee;
import src.service.ServiceEvent;
import src.base.ButtonEditor;
import src.base.ButtonRenderer;

public class TablePanel extends JPanel implements TableListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private ServiceEvent serviceEvent;
    private ServiceAttendee serviceAttendee;
    private int id = 1;
    private int row;

    public TablePanel(User user, MainMenu mainMenu) {
        try {
            serviceEvent = new ServiceEvent();
            serviceAttendee = new ServiceAttendee();
            List<Event> events = serviceEvent.getUserEvent(user);

            setLayout(new BorderLayout(0,20));
            setBackground(Color.WHITE);
            JLabel tableNameLabel = new JLabel("YOUR EVENT TABLE", JLabel.CENTER);
            tableNameLabel.setFont(new Font("Serif", Font.BOLD, 36));
            tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
            startBlinking(tableNameLabel);

            String[] columnNames = {"#", "Event ID",  "Name", "Date", "Location", "Type", "Organizer", "Actions"};
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

            ImageIcon originalEditIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/edit.png"));
            Image scaledImage_edit = originalEditIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
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
                row = table.getSelectedRow();
                int event_id = (int) table.getValueAt(row, 1);
                try{
                    Event event = serviceEvent.getSelectedEvent(event_id);
                    mainMenu.setEvent(event);
                    mainMenu.showPanel("eventUpdatePanel");
                }catch(Exception ex){
                    mainMenu.showMessage(Notifications.Type.ERROR, "Failed to update event! Please try again later");
                }
            });
            actionListeners.add(e -> {
                int row = table.getSelectedRow();
                int event_id = (int) table.getValueAt(row, 1);
                try {
                    if(table.getValueAt(row, 6).toString().equals(user.getUsername())){
                        serviceAttendee.removeAttendee(user, serviceEvent.getSelectedEvent(event_id));
                        serviceEvent.deleteEvent(event_id);
                    }else{
                        serviceAttendee.removeAttendee(user, serviceEvent.getSelectedEvent(event_id));
                    }
                    System.out.println("delete successfully");
                } catch (Exception ex) {
                    mainMenu.showMessage(Notifications.Type.ERROR, "Failed to delete event! Please try again later");
                }
                removeRow(row);
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

            TableColumn eventIDColumn = table.getColumnModel().getColumn(1);
            eventIDColumn.setMinWidth(0);
            eventIDColumn.setMaxWidth(0);
            eventIDColumn.setWidth(0);
            eventIDColumn.setPreferredWidth(0);

            JScrollPane tableScrollPane = new JScrollPane(table);
            tableScrollPane.setPreferredSize(new Dimension(800, 100));
            tableScrollPane.setBackground(Color.WHITE);
            ScrollBarCustom sb = new ScrollBarCustom();
            sb.setUnitIncrement(28);
            sb.setForeground(new Color(180, 180, 180));
            tableScrollPane.setVerticalScrollBar(sb);

            add(tableNameLabel, BorderLayout.NORTH);
            add(tableScrollPane, BorderLayout.CENTER);

            loadUserEvents(events);
        }catch(Exception e){
            mainMenu.showMessage(Notifications.Type.ERROR, "Failed to get user's events! Please try again later");
        }
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public void addRow(int event_id, String name, String date, String location, String type, User organizer) {
        tableModel.addRow(new Object[]{id++, event_id, name, date, location, type, organizer.getUsername()});
        table.repaint();
        table.revalidate();
    }

    @Override
    public void updateRow(int row, String name, String date, String location,  String type) {
        tableModel.setValueAt(name, row, 2);
        tableModel.setValueAt(date, row, 3);
        tableModel.setValueAt(location, row, 4);
        tableModel.setValueAt(type, row, 5);
        table.repaint();
        table.revalidate();
    }
    @Override
    public void removeRow(int row){
        tableModel.removeRow(row);
        table.repaint();
        table.revalidate();
    }

    public void loadUserEvents(List<Event> events) {
        tableModel.setRowCount(0);
        for (Event event : events) {
            addRow(event.getId(), event.getName(), event.getDate(), event.getLocation(), event.getType(), event.getOrganizer());
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
            if (c instanceof JLabel label) {
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