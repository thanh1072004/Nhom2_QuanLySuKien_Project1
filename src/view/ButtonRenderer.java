package view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    private final JButton watchButton = new JButton();
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();

    public ButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        
        ImageIcon originalIcon_watch = new ImageIcon(ButtonEditor.class.getResource("/icon/eye.png"));
        Image scaledImage_watch = originalIcon_watch.getImage().getScaledInstance(20, 26, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_watch = new ImageIcon(scaledImage_watch);
        watchButton.setIcon(scaledIcon_watch);
        watchButton.setBackground(Color.CYAN);

        watchButton.setPreferredSize(new Dimension(24, 24));

        ImageIcon originalIcon_edit = new ImageIcon(ButtonEditor.class.getResource("/icon/edit.png"));
        Image scaledImage_edit = originalIcon_edit.getImage().getScaledInstance(20, 18, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_edit = new ImageIcon(scaledImage_edit);
        editButton.setIcon(scaledIcon_edit);
        editButton.setBackground(Color.CYAN);
        editButton.setPreferredSize(new Dimension(24, 24));
        
        ImageIcon originalIcon_bin = new ImageIcon(ButtonEditor.class.getResource("/icon/bin.png"));
        Image scaledImage_bin = originalIcon_bin.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_bin = new ImageIcon(scaledImage_bin);
        deleteButton.setIcon(scaledIcon_bin);     
        deleteButton.setBackground(Color.RED);
        deleteButton.setPreferredSize(new Dimension(24, 24));
        add(watchButton);
        add(editButton);
        add(deleteButton);
    }

    private void setButtonIcon(JButton button, String imagePath, int width, int height) {
    	ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
