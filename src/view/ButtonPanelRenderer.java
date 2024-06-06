package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
    private final JButton acceptButton = new JButton();
    private final JButton refuseButton = new JButton();

    public ButtonPanelRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        setBackground(Color.WHITE);

        ImageIcon originalIcon_edit = new ImageIcon(ButtonEditor.class.getResource("/icon/accept.png"));
        Image scaledImage_edit = originalIcon_edit.getImage().getScaledInstance(20, 18, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_edit = new ImageIcon(scaledImage_edit);
        acceptButton.setIcon(scaledIcon_edit);
        acceptButton.setBackground(Color.CYAN);
        acceptButton.setPreferredSize(new Dimension(24, 24));
        
        ImageIcon originalIcon_bin = new ImageIcon(ButtonEditor.class.getResource("/icon/bin.png"));
        Image scaledImage_bin = originalIcon_bin.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_bin = new ImageIcon(scaledImage_bin);
        refuseButton.setIcon(scaledIcon_bin);     
        refuseButton.setBackground(Color.RED);
        refuseButton.setPreferredSize(new Dimension(24, 24));
        add(acceptButton);
        add(refuseButton);
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
