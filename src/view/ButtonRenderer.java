package src.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.List;

public class ButtonRenderer extends JPanel implements TableCellRenderer {

    private List<ImageIcon> icons;
    private List<Color> backgroundColors;

    public ButtonRenderer(List<ImageIcon> icons, List<Color> backgroundColors) {
        this.icons = icons;
        this.backgroundColors = backgroundColors;
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));

        for (int i = 0; i < icons.size(); i++) {
            JLabel label = new JLabel(icons.get(i));
            label.setBorder(new EmptyBorder(8,8,8,8));
            label.setBackground(backgroundColors.get(i));
            label.setOpaque(true);
            add(label);
        }
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }
}


