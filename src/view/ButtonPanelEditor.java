package src.view;

import java.awt.*;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import src.mainMenuPanel.InviteViewPanel;

public class ButtonPanelEditor extends DefaultCellEditor {

	private final JPanel panel = new JPanel();
    private final JButton acceptButton = new JButton();
    private final JButton refuseButton = new JButton();
    private JTable table;
    private InviteViewPanel joinPanel;
    
    public ButtonPanelEditor(JCheckBox checkBox, InviteViewPanel joinPanel) {
        super(checkBox);
        this.joinPanel = joinPanel;
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));


        ImageIcon originalIcon_edit = new ImageIcon(ButtonEditor.class.getResource("/src/icon/accept.png"));
        Image scaledImage_edit = originalIcon_edit.getImage().getScaledInstance(20, 18, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_edit = new ImageIcon(scaledImage_edit);
        acceptButton.setIcon(scaledIcon_edit);
        acceptButton.setBackground(Color.CYAN);
        acceptButton.setPreferredSize(new Dimension(24, 24));
        
        ImageIcon originalIcon_bin = new ImageIcon(ButtonEditor.class.getResource("/src/icon/bin.png"));
        Image scaledImage_bin = originalIcon_bin.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_bin = new ImageIcon(scaledImage_bin);
        refuseButton.setIcon(scaledIcon_bin);        
        refuseButton.setBackground(Color.RED);
        refuseButton.setPreferredSize(new Dimension(24, 24));
        panel.add(acceptButton);
        panel.add(refuseButton);


        acceptButton.addActionListener(e -> fireEditingStopped());
        refuseButton.addActionListener(e -> {
            fireEditingStopped();
            int row = table.getSelectedRow();
            joinPanel.removeRow(row);
        });
    }
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        return panel;
    }

    private void setButtonIcon(JButton button, String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);
    }

}
