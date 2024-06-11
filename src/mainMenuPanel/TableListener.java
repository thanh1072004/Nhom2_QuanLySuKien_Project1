package src.mainMenuPanel;

import src.model.User;

public interface TableListener {
    int getRow();
    void addRow(String name, String date, String location, String type, User organizer);
    void updateRow(int row, String name, String location, String date, String type);
    void removeRow(int row);
}
