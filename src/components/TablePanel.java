package src.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.sql.SQLException;
import java.util.List;

import src.model.Event;
import src.model.User;
import src.service.ServiceEvent;
import src.swing.MyTextField;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

public class TablePanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    private ServiceEvent serviceEvent;
    private User user;

    public TablePanel(ServiceEvent serviceEvent, User user) {
        try {
            this.user = user;
            this.serviceEvent = serviceEvent;
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

            // Tạo tên bảng
            JLabel tableNameLabel = new JLabel("YOUR EVENT TABLE", JLabel.CENTER);
            tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
            tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

            eventListPanel1.add(tableNameLabel, BorderLayout.CENTER);
            eventListPanel1.add(topPanel, BorderLayout.NORTH);

            String[] columnNames = {"Event ID", "Name", "Event Date", "Location", "Type", "Organizer", "Actions"};
            tableModel = new DefaultTableModel(columnNames, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    // Make all cells not editable
                    return column == getColumnCount() - 1;
                }
            };
            table = new JTable(tableModel);

            // Set table not editable
            table.setDefaultEditor(Object.class, null);
            table.setRowSelectionAllowed(false);
            table.setFocusable(false);

            // Bold header
            JTableHeader header = table.getTableHeader();
            header.setDefaultRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                    label.setHorizontalAlignment(SwingConstants.CENTER);
                    label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return label;
                }
            });

            // Add ButtonRenderer and ButtonEditor for the "Actions" column
            table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
            table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), this, serviceEvent));

            // Center align all data
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int i = 0; i < table.getColumnCount() - 1; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
            table.getTableHeader().setReorderingAllowed(false);
            table.getTableHeader().setResizingAllowed(false);

            table.setRowHeight(48);

            JScrollPane tableScrollPane = new JScrollPane(table);

            eventListPanel1.add(tableScrollPane, BorderLayout.SOUTH);
            add(eventListPanel1, BorderLayout.CENTER);

            loadUserEvents(events);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void addRow(int id, String name, String date, String location, String type, User organizer) {
        tableModel.addRow(new Object[]{id, name, date, location, type, organizer.getUsername()});
    }

    public void removeRow(int row){
        tableModel.removeRow(row);
    }

    public void loadUserEvents(List<Event> events) {
        int id = 1;
        tableModel.setRowCount(0);
        for (Event event : events) {
            addRow(id++, event.getName(), event.getDate(), event.getLocation(), event.getType(), user);
        }
    }

}