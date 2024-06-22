package src.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class ButtonRenderer extends JPanel implements TableCellRenderer {

    public ButtonRenderer(List<ImageIcon> icons, List<Color> backgroundColors) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        for (int i = 0; i < icons.size(); i++) {
            JLabel label = new JLabel(icons.get(i));
            label.setBorder(new EmptyBorder(8, 8, 8, 8));
            label.setBackground(backgroundColors.get(i));
            label.setOpaque(true);
            add(label, gbc);
            gbc.gridx++;
        }
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        return this;
    }
}


