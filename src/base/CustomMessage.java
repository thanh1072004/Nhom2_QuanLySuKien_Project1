package src.base;

import javax.swing.*;
import java.awt.*;

public class CustomMessage extends JPanel {
    public enum MessageType {
        INFO, WARNING, ERROR
    }

    private JLabel messageLabel;
    private boolean show = false;

    public CustomMessage() {
        setLayout(new BorderLayout());
        messageLabel = new JLabel("", JLabel.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(messageLabel, BorderLayout.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        setBackground(Color.LIGHT_GRAY);
        setPreferredSize(new Dimension(300, 50));
    }

    public void showMessage(MessageType messageType, String message) {
        switch (messageType) {
            case INFO:
                setBackground(Color.CYAN);
                break;
            case WARNING:
                setBackground(Color.YELLOW);
                break;
            case ERROR:
                setBackground(Color.RED);
                break;
        }
        messageLabel.setText(message);
        setVisible(true);
        revalidate();
        repaint();
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
