package src.components;

public interface FormListener {
    void formSubmitted(String name, String date, String location, String type);
    void formUpdated(String name, String date, String location, String type);
}
