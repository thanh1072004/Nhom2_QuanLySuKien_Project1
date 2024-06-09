package src.mainMenuPanel;

public interface FormListener {
    void formSubmitted(String name, String date, String location, String type);
    void formUpdated(String name, String location, String date, String type);
}
