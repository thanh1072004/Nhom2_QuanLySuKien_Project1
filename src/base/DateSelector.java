package src.base;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;


public class DateSelector extends JPanel {
    private JComboBox<Integer> dayComboBox;
    private JComboBox<String> monthComboBox;
    private JComboBox<Integer> yearComboBox;

    public DateSelector() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 10);

        Integer[] days = new Integer[31];
        for (int i = 0; i < 31; i++) {
            days[i] = i + 1;
        }
        dayComboBox = new JComboBox<>(days);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(dayComboBox, gbc);

        String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(months);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(monthComboBox, gbc);

        Integer[] years = new Integer[100];
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 0; i < 100; i++) {
            years[i] = currentYear + 50 - i;
        }
        yearComboBox = new JComboBox<>(years);
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(yearComboBox, gbc);

        resetToCurrentDate();
    }

    public String getSelectedDate(){
        int day = (int) dayComboBox.getSelectedItem();
        int month = monthComboBox.getSelectedIndex() + 1;
        int year = (int) yearComboBox.getSelectedItem();
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public void setDate(String date) {
        try {
            String[] parts = date.split("-");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid date format. Expected format: yyyy-MM-dd");
            }

            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]) - 1;
            int day = Integer.parseInt(parts[2]);

            dayComboBox.setSelectedItem(day);
            monthComboBox.setSelectedIndex(month);
            yearComboBox.setSelectedItem(year);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing date: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error setting date: " + e.getMessage());
        }
    }

    public void resetToCurrentDate() {
        Calendar currentDate = Calendar.getInstance();
        dayComboBox.setSelectedItem(currentDate.get(Calendar.DAY_OF_MONTH));
        monthComboBox.setSelectedIndex(currentDate.get(Calendar.MONTH));
        yearComboBox.setSelectedItem(currentDate.get(Calendar.YEAR));
    }
}
