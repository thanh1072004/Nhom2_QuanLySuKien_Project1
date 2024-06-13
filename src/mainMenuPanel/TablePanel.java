package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import src.MainMenu;
import src.base.MyColor;
import src.model.Event;
import src.model.User;
import src.service.ServiceAttendee;
import src.service.ServiceEvent;
import src.base.MyTextField;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

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

            JLabel tableNameLabel = new JLabel("YOUR EVENT TABLE", JLabel.CENTER);
            tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
            tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

            eventListPanel1.add(tableNameLabel, BorderLayout.CENTER);
            eventListPanel1.add(topPanel, BorderLayout.NORTH);

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

            ImageIcon originalEditIcon = new ImageIcon(ButtonEditor.class.getResource("/src/icon/edit.png"));
            Image scaledImage_edit = originalEditIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
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
                try{
                    row = table.getSelectedRow();
                    String event_name = table.getValueAt(row, 1).toString();
                    Event event = serviceEvent.getSelectedEvent(event_name);
                    mainMenu.setEvent(event);
                    mainMenu.showPanel("eventUpdatePanel");
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            });
            actionListeners.add(e -> {
                int row = table.getSelectedRow();
                String event_name = table.getModel().getValueAt(row, 1).toString();
                try {
                    serviceAttendee.removeAttendee(user, serviceEvent.getSelectedEvent(event_name));
                    serviceEvent.deleteEvent(event_name);
                    System.out.println("delete successfully");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                removeRow(row);
            });

            ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
            table.getColumnModel().getColumn(6).setCellEditor(buttonEdit);

            table.setRowHeight(48);
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount() - 1; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            JScrollPane tableScrollPane = new JScrollPane(table);

            eventListPanel1.add(tableScrollPane, BorderLayout.SOUTH);
            add(eventListPanel1, BorderLayout.CENTER);

            loadUserEvents(events);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public void addRow(String name, String date, String location, String type, User organizer) {
        tableModel.addRow(new Object[]{id++, name, date, location, type, organizer.getUsername()});
    }

    @Override
    public void updateRow(int row, String name, String date, String location,  String type) {
        tableModel.setValueAt(name, row, 1);
        tableModel.setValueAt(date, row, 2);
        tableModel.setValueAt(location, row, 3);
        tableModel.setValueAt(type, row, 4);
    }
    @Override
    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void loadUserEvents(List<Event> events) {
        tableModel.setRowCount(0);
        for (Event event : events) {
            addRow(event.getName(), event.getDate(), event.getLocation(), event.getType(), event.getOrganizer());
        }
    }
}