package src.mainMenuPanel;

import src.base.Config;
import src.base.ScrollBarCustom;
import src.model.Notification;
import src.model.User;
import src.service.ServiceNotification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class NotificationPanel extends JPanel {
    private User user;
    private JPanel notiListPanel;
    private ServiceNotification serviceNotification;

    public NotificationPanel(User user) {
        this.user = user;
        serviceNotification = new ServiceNotification();

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        setLocation(400, 50);
        setVisible(false);

        JLabel header = new JLabel("Notifications", JLabel.LEADING);
        header.setPreferredSize(new Dimension(360, 40));
        header.setBackground(Color.WHITE);
        header.setOpaque(true);
        header.setForeground(Color.GRAY);
        header.setBorder(BorderFactory.createEmptyBorder(20, 8, 4, 8));

        notiListPanel = new JPanel();
        notiListPanel.setLayout(new BoxLayout(notiListPanel, BoxLayout.Y_AXIS));
        
        JScrollPane scroll = new JScrollPane(notiListPanel);
        scroll.setBorder(null);
        scroll.setBackground(Color.WHITE);
        ScrollBarCustom sb = new ScrollBarCustom();
        sb.setUnitIncrement(28);
        sb.setForeground(new Color(180, 180, 180));
        scroll.setVerticalScrollBar(sb);

        add(header, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        loadNotifications();
    }

    private JMenuItem createNotificationItem(String text, boolean read, int id) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(new Font("Segoe UI Historic", Font.PLAIN, 15));
        menuItem.setBackground(Config.WHITE);
        if(read){
            menuItem.setForeground(new Color(101, 103, 107));
        }else{
            menuItem.setForeground(new Color(28,30,33));
        }
        Dimension fixedSize = new Dimension(360, 60);
        menuItem.setPreferredSize(fixedSize);
        menuItem.setMaximumSize(fixedSize);
        menuItem.setMinimumSize(fixedSize);
        menuItem.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                menuItem.setBackground(Color.GRAY);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
                menuItem.setBackground(Config.WHITE);
            }
        });
        menuItem.addActionListener(e -> {
            try{
                menuItem.setForeground(new Color(101, 103, 107));
                serviceNotification.updateIsRead(id);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
        return menuItem;
    }

    private void loadNotifications() {
        try{
            List<Notification> notifications = serviceNotification.getNotifications(user);
            for(Notification notification : notifications) {
                notiListPanel.add(createNotificationItem(notification.getMessage(), notification.isRead(), notification.getId()));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
