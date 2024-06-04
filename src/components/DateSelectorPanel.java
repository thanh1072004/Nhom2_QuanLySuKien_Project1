package src.components;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;


public class DateSelectorPanel extends JPanel {
    private JComboBox<Integer> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;
    private JLabel dateLabel;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public DateSelectorPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Initialize and set the label
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 10);

        // Create day ComboBox
        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        dayComboBox = new JComboBox<>(days);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(dayComboBox, gbc);

        // Create month ComboBox
        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(monthComboBox, gbc);

        // Create year ComboBox
        Integer[] years = new Integer[100];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            years[i] = currentYear - i;
        }
        yearComboBox = new JComboBox<>(years);
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(yearComboBox, gbc);
    }

    public String getSelectedDate(){
        int day = (int) dayComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex() + 1;
        int year = (int) yearComboBox.getSelectedItem();
        return String.format("%02d/%02d/%04d", day, month, year);
    }
}
