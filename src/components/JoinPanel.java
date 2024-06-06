package components;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import swing.MyTextField;
import view.ButtonEditor;
import view.ButtonPanelEditor;
import view.ButtonPanelRenderer;
import view.ButtonRenderer;

public class JoinPanel extends JPanel {
	private JTable table;
    private DefaultTableModel tableModel;
    private CardLayout cardLayout;
    private JPanel cardPanel;
	
    public JoinPanel() {
	setLayout(new BorderLayout());
	cardLayout = new CardLayout();
	cardPanel = new JPanel(cardLayout);
    
	// Create invitation list panel
    JPanel invitationListPanel1 = createInvitationListPanel();

    // Create join public event panel
    JPanel joinPublicEvent = createJoinPublicEventPanel();

    // Add both panels to the card panel
    cardPanel.add(invitationListPanel1, "InvitationList");
    cardPanel.add(joinPublicEvent, "JoinPublicEvent");
    
    // Add the card panel to the main panel
    add(cardPanel, BorderLayout.CENTER);
    
    // Add some sample data
    addSampleData();
}

private JPanel createInvitationListPanel() {
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

    JLabel tableNameLabel = new JLabel("YOUR INVITATION TABLE", JLabel.CENTER);
    tableNameLabel.setFont(new Font("Serif", Font.BOLD, 20));
    tableNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
    topPanel.add(tableNameLabel, BorderLayout.SOUTH);

    String[] columnNames = {"Event ID", "Name", "Event Date", "Location", "Type", "Organizer", "Sender", "Actions"};
    
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
    table.getColumn("Actions").setCellEditor(new ButtonPanelEditor(new JCheckBox(), this));

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    for (int i = 0; i < table.getColumnCount() - 1; i++) {
        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
    table.getTableHeader().setReorderingAllowed(false);
    table.getTableHeader().setResizingAllowed(false);

    table.setRowHeight(48);

    JScrollPane tableScrollPane = new JScrollPane(table);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
    buttonPanel.setBackground(Color.WHITE);

    JButton joinPublicEventButton = new JButton("Join Public Event");
    joinPublicEventButton.setPreferredSize(new Dimension(150, 30));
    buttonPanel.add(joinPublicEventButton);

    joinPublicEventButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "JoinPublicEvent");
        }
    });
    
    invitationListPanel1.add(topPanel, BorderLayout.NORTH);
    invitationListPanel1.add(tableScrollPane, BorderLayout.CENTER);
    invitationListPanel1.add(buttonPanel, BorderLayout.SOUTH);

    return invitationListPanel1;
}

private JPanel createJoinPublicEventPanel() {
    JPanel joinPublicEvent = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(20, 20, 20, 20);

    Font font = new Font("Arial", Font.PLAIN, 16);

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setFont(font);
    gbc.gridx = 0;
    gbc.gridy = 0;
    joinPublicEvent.add(nameLabel, gbc);

    JTextField name = new JTextField(20);
    name.setFont(font);
    gbc.gridx = 1;
    joinPublicEvent.add(name, gbc);

    JLabel eventidLabel = new JLabel("Event ID:");
    eventidLabel.setFont(font);
    gbc.gridx = 0;
    gbc.gridy = 1;
    joinPublicEvent.add(eventidLabel, gbc);

    JTextField eventid = new JTextField(20);
    eventid.setFont(font);
    gbc.gridx = 1;
    joinPublicEvent.add(eventid, gbc);

    JLabel organizerLabel = new JLabel("Organizer:");
    organizerLabel.setFont(font);
    gbc.gridx = 0;
    gbc.gridy = 2;
    joinPublicEvent.add(organizerLabel, gbc);

    JTextField organizer = new JTextField(20);
    organizer.setFont(font);
    gbc.gridx = 1;
    joinPublicEvent.add(organizer, gbc);

    JPanel joinButtonPanel = new JPanel();
    joinButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));
    JButton joinEventButton = new JButton("Join Event");
    joinEventButton.setFont(font);
    joinEventButton.setFocusPainted(false);
    joinButtonPanel.add(joinEventButton);
    
    JButton backButton = new JButton("Back");
    backButton.setFont(font);
    backButton.setFocusPainted(false);
    joinButtonPanel.add(backButton);

    backButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "InvitationList");
        }
    });

    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    joinPublicEvent.add(joinButtonPanel, gbc);

    return joinPublicEvent;
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
        addRowToTable(new Object[]{"1", "Event A", "2024-06-10", "New York", "Conference", "Org A", "User A", "Action"});
        addRowToTable(new Object[]{"2", "Event B", "2024-07-15", "Los Angeles", "Meetup", "Org B", "User B", "Action"});
        addRowToTable(new Object[]{"3", "Event C", "2024-08-20", "Chicago", "Workshop", "Org C", "User C", "Action"});
    }
}
