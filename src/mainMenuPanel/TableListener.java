package src.mainMenuPanel;

import src.model.User;

public interface TableListener {
    int getRow();
    void addRow(int event_id, String name, String date, String location, String type, User organizer);
    void updateRow(int row, String name, String date, String location, String type);
    void removeRow(int row);
}

