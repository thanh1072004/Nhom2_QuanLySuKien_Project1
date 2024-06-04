package src.components;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

import src.swing.MyTextField;
import src.view.ButtonEditor;
import src.view.ButtonRenderer;

public class TablePanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;

    public TablePanel() {
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
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox()));

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
        
    }
    public void addRow(int id, String name, String date, String location, String type, String organizer) {
        tableModel.addRow(new Object[]{id, name, date, location, type, organizer});
    }


}