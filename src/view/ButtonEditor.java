package src.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    private JPanel panel;
    private List<JButton> buttons;
    private List<ActionListener> actionListeners;
    private List<Color> backgroundColors;

    public ButtonEditor(List<ImageIcon> icons, List<ActionListener> actionListeners, List<Color> backgroundColors) {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        this.actionListeners = actionListeners;
        this.backgroundColors = backgroundColors;
        buttons = new ArrayList<>();

        for (int i = 0; i < icons.size(); i++) {
            JButton button = new JButton(icons.get(i));
            button.setBorder(new EmptyBorder(8,8,8,8));
            button.setBackground(backgroundColors.get(i));

            button.setFocusPainted(false);
            button.addActionListener(this);
            buttons.add(button);
            panel.add(button);
        }
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped();
        for (int i = 0; i < buttons.size(); i++) {
            if (e.getSource() == buttons.get(i)) {
                actionListeners.get(i).actionPerformed(e);
                break;
            }
        }
    }


}

