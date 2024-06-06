package src.view;

import src.components.FormListener;
import src.components.TablePanel;
import src.components.UpdatePanel;
import src.model.User;
import src.service.ServiceEvent;
import src.model.Event;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;


public class ButtonEditor extends DefaultCellEditor {
    private final JPanel panel = new JPanel();
    private final JButton editButton = new JButton();
    private final JButton deleteButton = new JButton();
    private JTable table;
    private TablePanel tablePanel;
    private UpdatePanel updatePanel;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private User user;
    private ServiceEvent service;
    private int row;
    public ButtonEditor(JCheckBox checkBox, TablePanel tablePanel, ServiceEvent service, JPanel mainPanel, CardLayout cardLayout) {
        super(checkBox);
        this.service = service;
        this.tablePanel = tablePanel;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        ActionListener eventUpdate = e -> updateEvent();

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

        editButton.addActionListener(e -> {
            fireEditingStopped();
            row = table.getSelectedRow();
            String event_name = table.getValueAt(row, 1).toString();

            try{
                Event event = service.getSelectedEvent(event_name);
                updatePanel = new UpdatePanel(user, eventUpdate);
                updatePanel.setEventDetails(event);
                mainPanel.add(updatePanel, "eventUpdate");
                cardLayout.show(mainPanel, "eventUpdate");
                updatePanel.setFormListener(new FormListener() {
                    @Override
                    public void formSubmitted(String name, String date, String location, String type) {

                    }

                    @Override
                    public void formUpdated(String name, String date, String location, String type) {
                        tablePanel.updateRow(row, name, date, location, type);
                    }
                });
            }catch(SQLException ex){
                ex.printStackTrace();
            }
        });

        deleteButton.addActionListener(e -> {
            fireEditingStopped();
            row = table.getSelectedRow();
            String event_name = table.getModel().getValueAt(row, 1).toString();
            try {
                service.deleteEvent(event_name);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            tablePanel.removeRow(row);
        });
    }

    public void updateEvent(){
        Event event = updatePanel.getEvent();
        int event_id = event.getId();
        String eventDate = event.getDate();
        try{
            LocalDate day = getDate(eventDate);
            if(event.getName().isEmpty()  || event.getLocation().isEmpty()){
                System.out.println("Please fill all the required fields");
            }else if (day.isBefore(LocalDate.now())){
                System.out.println("Invalid date");
            }else{
                service.updateEvent(event, event_id);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public LocalDate getDate(String date){
        return LocalDate.parse(date);
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


