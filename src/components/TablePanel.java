package components;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

import swing.MyTextField;

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
        JLabel tableNameLabel = new JLabel("YOUR CREATED EVENT TABLE", JLabel.CENTER);
        tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
        tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));

        try {
            URL imageURL = getClass().getResource("/icon/welcome.jpg");
            if (imageURL != null) {
                ImageIcon icon = new ImageIcon(ImageIO.read(imageURL));
                Image image = icon.getImage().getScaledInstance(800, 300, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(image));
                topPanel.add(imageLabel, BorderLayout.CENTER);
                topPanel.add(tableNameLabel, BorderLayout.SOUTH);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        eventListPanel1.add(topPanel, BorderLayout.NORTH);

        String[] columnNames = {"Event ID", "Name", "Event Date", "Location", "Type", "Organizer"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        add(tableScrollPane, BorderLayout.CENTER);

        /*header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setFont(label.getFont().deriveFont(Font.BOLD));
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                return label;
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        centerRenderer.setVerticalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < eventTable.getColumnCount(); i++) {
            eventTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumn actionsColumn = eventTable.getColumnModel().getColumn(6);
        actionsColumn.setCellRenderer(new ButtonRenderer());
        actionsColumn.setCellEditor(new ButtonEditor(new JCheckBox()));

        eventTable.getTableHeader().setReorderingAllowed(false);
        eventTable.getTableHeader().setResizingAllowed(false);

        // Đặt chiều cao của mỗi dòng
        eventTable.setRowHeight(48);

        JScrollPane scrollPane = new JScrollPane(eventTable);
        scrollPane.setPreferredSize(new Dimension(getWidth(), getHeight() / 4));
        eventListPanel1.add(scrollPane, BorderLayout.SOUTH);

        add(eventListPanel1, BorderLayout.NORTH);
        add(eventTable, BorderLayout.SOUTH);*/
    }
    public void addRow(int id, String name, String date, String location, String type, String organizer) {
        tableModel.addRow(new Object[]{id, name, date, location, type, organizer});
    }


}
