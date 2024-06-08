package src.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import src.mainMenuPanel.RequestViewPanel;


public class RequestPanelEditor extends DefaultCellEditor {

	private final JPanel panel = new JPanel();
    private final JButton acceptButton = new JButton();
    private final JButton refuseButton = new JButton();
    private JTable table;
    private RequestViewPanel requestViewPanel;
    
    public RequestPanelEditor(JCheckBox checkBox, RequestViewPanel requestViewPanel) {
        super(checkBox);
        this.requestViewPanel = requestViewPanel;
        panel.setBackground(Color.WHITE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,10));


        ImageIcon originalIcon_edit = new ImageIcon(ButtonEditor.class.getResource("/src/icon/accept.png"));
        Image scaledImage_edit = originalIcon_edit.getImage().getScaledInstance(20, 18, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_edit = new ImageIcon(scaledImage_edit);
        acceptButton.setIcon(scaledIcon_edit);
        acceptButton.setBackground(Color.CYAN);
        acceptButton.setPreferredSize(new Dimension(24, 24));
        
        ImageIcon originalIcon_bin = new ImageIcon(ButtonEditor.class.getResource("/src/icon/reject.png"));
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
            requestViewPanel.removeRow(row);
        });
    }

    private void setButtonIcon(JButton button, String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        button.setIcon(scaledIcon);
    }

}


