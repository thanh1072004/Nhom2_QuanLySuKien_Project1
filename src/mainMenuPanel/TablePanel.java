package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.base.MyColor;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.base.MyTextField;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

public class TablePanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    private User user;
    private int id = 1;
    private EventUpdatePanel eventUpdatePanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ServiceEvent service;

    public TablePanel(ServiceEvent service, User user, JPanel mainPanel, CardLayout cardLayout) {
        try {
            this.user = user;
            this.service = service;
            this.mainPanel = mainPanel;
            this.cardLayout = cardLayout;
            ActionListener eventUpdate = e -> updateEvent();
            List<Event> events = service.getUserEvent(user);

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

            String[] columnNames = {"Event ID", "Name", "Location", "Date", "Type", "Organizer", "Actions"};
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
                int row = table.getSelectedRow();
                String event_name = table.getValueAt(row, 1).toString();

                try{
                    Event event = service.getSelectedEvent(event_name);
                    eventUpdatePanel = new EventUpdatePanel(user, eventUpdate);
                    eventUpdatePanel.setEventDetails(event);
                    mainPanel.add(eventUpdatePanel, "eventUpdate");
                    cardLayout.show(mainPanel, "eventUpdate");
                    eventUpdatePanel.setFormListener(new FormListener() {
                        @Override
                        public void formSubmitted(String name, String date, String location, String type) {

                        }

                        @Override
                        public void formUpdated(String name, String date, String location, String type) {
                            updateRow(row, name, date, location, type);
                            cardLayout.show(mainPanel, "tablePanel");
                        }
                    });
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            });
            actionListeners.add(e -> {
                int row = table.getSelectedRow();
                String event_name = table.getModel().getValueAt(row, 1).toString();
                try {
                    service.deleteEvent(event_name);
                } catch (SQLException ex) {
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
    public void addRow(String name, String location, String date, String type, User organizer) {
        tableModel.addRow(new Object[]{id++, name, location, date, type, organizer.getUsername()});
    }
    public void updateRow(int row, String name, String location, String date, String type) {
        tableModel.setValueAt(name, row, 1);
        tableModel.setValueAt(location, row, 2);
        tableModel.setValueAt(date, row, 3);
        tableModel.setValueAt(type, row, 4);
    }
    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void loadUserEvents(List<Event> events) {
        tableModel.setRowCount(0);
        for (Event event : events) {
            addRow(event.getName(), event.getLocation(), event.getDate(), event.getType(), user);
        }
    }

    public void updateEvent(){
        Event event = eventUpdatePanel.getEvent();
        int event_id = event.getId();
        String eventDate = event.getDate();
        try{
            LocalDate day = getDate(eventDate);
            if(event.getName().isEmpty()  || event.getLocation().isEmpty()){
                System.out.println("Please fill all the required fields");
            }else if (day.isBefore(LocalDate.now())){
                System.out.println("Invalid date");
            }else{
                service.updateEvent(event, event_id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public LocalDate getDate(String date){
        return LocalDate.parse(date);
    }
}