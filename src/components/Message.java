package src.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Message extends JPanel {
    private JLabel lbMessage;
    private MessageType messageType = MessageType.SUCCESS;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    private boolean show;

    public Message() {
        initComponents();
        setOpaque(false);
    }

    public void showMessage(MessageType messageType, String message) {
        this.messageType = messageType;
        lbMessage.setText(message);
        if(messageType == MessageType.SUCCESS) {
            lbMessage.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/success.png"))));
        }else{
            lbMessage.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/error.png"))));
        }
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(messageType == MessageType.SUCCESS) {
            g2d.setColor(new Color(15, 174, 37));
        }else{
            g2d.setColor(new Color(240, 52, 53));
        }
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        g2d.setColor(new Color(245, 245, 245));
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        super.paintComponent(g);
    }
    public static enum MessageType {
        SUCCESS, ERROR
    }

    private void initComponents() {

        lbMessage = new JLabel();

        setBackground(new Color(214, 217, 223));
        setPreferredSize(new Dimension(300, 30));

        lbMessage.setFont(new Font("SansSerif", 1, 14)); // NOI18N
        lbMessage.setForeground(new Color(248, 248, 248));
        lbMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lbMessage.setText("Message");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lbMessage, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(lbMessage, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
