package src.base;

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

    protected JPanel panel;
    private List<JButton> buttons;
    private List<ActionListener> actionListeners;
    private List<Color> backgroundColors;
    private boolean isOrganizer;

    public ButtonEditor(List<ImageIcon> icons, List<ActionListener> actionListeners, List<Color> backgroundColors) {
        this.backgroundColors = backgroundColors;
        panel = new JPanel(new GridBagLayout());
        this.actionListeners = actionListeners;
        buttons = new ArrayList<>();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < icons.size(); i++) {
            JButton button = new JButton(icons.get(i));
            button.setBorder(new EmptyBorder(8, 8, 8, 8));
            button.setBackground(backgroundColors.get(i));
            button.setFocusPainted(false);
            button.addActionListener(this);
            buttons.add(button);
            panel.add(button, gbc);
            gbc.gridx++;
        }

    }


    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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
    public void setIsOrganizer(boolean isOrganizer) {
        this.isOrganizer = isOrganizer;
        // Disable the edit button if not an organizer
        buttons.get(0).setEnabled(isOrganizer);
    }
}

