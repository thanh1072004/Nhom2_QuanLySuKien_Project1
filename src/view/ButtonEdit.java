package src.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonEdit extends DefaultCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton button = new JButton();
    private ActionListener actionListener;

    public ButtonEdit(JCheckBox checkBox, ActionListener actionListener, Icon icon, Color backgroundColor) {
        super(checkBox);
        this.actionListener = actionListener;

        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        button.setIcon(icon);
        button.addActionListener(actionListener);
        button.setBackground(backgroundColor);
        button.setPreferredSize(new Dimension(24, 24));
        panel.add(button);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}
