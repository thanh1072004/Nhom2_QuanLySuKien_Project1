package src.view;

import src.components.TablePanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonEditor extends DefaultCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();
    private JTable table;
    private TablePanel tablePanel;

    public ButtonEditor(JCheckBox checkBox, TablePanel tablePanel) {
        super(checkBox);
        this.tablePanel = tablePanel;
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));


        ImageIcon originalIcon_edit = new ImageIcon(ButtonEditor.class.getResource("/src/icon/edit.png"));
        Image scaledImage_edit = originalIcon_edit.getImage().getScaledInstance(20, 18, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_edit = new ImageIcon(scaledImage_edit);
        editButton.setIcon(scaledIcon_edit);
        editButton.setBackground(Color.CYAN);
        editButton.setPreferredSize(new Dimension(24, 24));
        
        ImageIcon originalIcon_bin = new ImageIcon(ButtonEditor.class.getResource("/src/icon/bin.png"));
        Image scaledImage_bin = originalIcon_bin.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_bin = new ImageIcon(scaledImage_bin);
        deleteButton.setIcon(scaledIcon_bin);        
        deleteButton.setBackground(Color.RED);
        deleteButton.setPreferredSize(new Dimension(24, 24));
        panel.add(editButton);
        panel.add(deleteButton);


        editButton.addActionListener(e -> fireEditingStopped());
        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            tablePanel.removeRow(row);
        });
    }

    private void setButtonIcon(JButton button, String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}


