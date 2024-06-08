package src.mainMenuPanel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;

import src.base.MyTextField;
import src.view.ButtonPanelRenderer;
import src.view.RequestPanelEditor;

public class RequestViewPanel extends JPanel{
    private JTable table;
    private DefaultTableModel tableModel;
    public RequestViewPanel() {
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
        JLabel tableNameLabel = new JLabel("YOUR REQUEST TABLE", JLabel.CENTER);
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        invitationListPanel1.add(tableNameLabel, BorderLayout.CENTER);
        invitationListPanel1.add(topPanel, BorderLayout.NORTH);
        
        String[] columnNames = {"#", "Name", "Event Date", "Location",  "Sender", "Actions"};
        
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

        table.getColumn("Actions").setCellRenderer(new ButtonPanelRenderer());
        table.getColumn("Actions").setCellEditor(new RequestPanelEditor(new JCheckBox(), this));

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
        
        addSampleData();

    }
    
    public void removeRow(int row){
        tableModel.removeRow(row);
    }
    
 // Method to add a row to the table
    public void addRowToTable(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    // Method to add some sample data
    private void addSampleData() {
        addRowToTable(new Object[]{"1", "Event A", "2024-06-10", "New York", "Sender A", "Action"});
        addRowToTable(new Object[]{"2", "Event B", "2024-07-15", "Los Angeles", "Sender B", "Action"});
        addRowToTable(new Object[]{"3", "Event C", "2024-08-20", "Chicago", "Sender C", "Action"});
    }
}

