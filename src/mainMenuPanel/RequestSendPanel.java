package src.mainMenuPanel;

import src.base.MyColor;
import src.base.MyTextField;
import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RequestSendPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ServiceEvent serviceEvent;
    private User user;

    public RequestSendPanel(ServiceEvent serviceEvent, User user) {

        try {
            this.user = user;
            this.serviceEvent = serviceEvent;
            java.util.List<src.model.Event> events = serviceEvent.getPublicEvents(user);

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

            JLabel tableNameLabel = new JLabel("Available Event", JLabel.CENTER);
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

            ImageIcon originalEditIcon = new ImageIcon("src\\icon\\edit.png");
            Image scaledImage_edit = originalEditIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
            ImageIcon editIcon = new ImageIcon(scaledImage_edit);

            ImageIcon originalDeleteIcon = new ImageIcon("src\\icon\\delete.png");
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
                System.out.println(row);
            });
            actionListeners.add(e -> {
                int row = table.getSelectedRow();
                removeRow(row);
            });

            ButtonEditor buttonEdit = new ButtonEditor(icons, actionListeners, backgroundColor);
            table.getColumnModel().getColumn(6).setCellEditor(buttonEdit);

            table.setRowHeight(48);

            // Center align all data
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount() - 1; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);

            JScrollPane tableScrollPane = new JScrollPane(table);

            eventListPanel1.add(tableScrollPane, BorderLayout.SOUTH);
            add(eventListPanel1, BorderLayout.CENTER);

            loadPublicEvents(events);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addRow(int id, String name, String location, String date, String type, User organizer) {
        tableModel.addRow(new Object[]{id, name, location, date, type, organizer.getUsername()});
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

    public void loadPublicEvents(List<src.model.Event> events) {
        int id = 1;
        tableModel.setRowCount(0);
        for (Event event : events) {
            addRow(id++, event.getName(), event.getLocation(), event.getDate(), event.getType(), event.getOrganizer());
        }
    }
}

