package src.mainMenuPanel;

import src.model.User;
import src.service.ServiceNotification;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class TopBar extends JPanel {
    private MainMenu mainMenu;
    private NotificationPanel notificationPanel;
    private boolean notiVisible = false;
    private ServiceNotification serviceNotification;

    public TopBar(MainMenu mainMenu, User user) {
        this.mainMenu = mainMenu;
        notificationPanel = mainMenu.getNotificationPanel();
        serviceNotification = new ServiceNotification();

        setBounds(0, 0, mainMenu.getWidth(), 68);
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBackground(new Color(0,123,255));

        JButton home = new JButton("Home");
        home.setBackground(new Color(0,123,255));
        home.setPreferredSize(new Dimension(100, getHeight()));
        home.setMinimumSize(new Dimension(100, getHeight()));
        home.setMaximumSize(new Dimension(100, getHeight()));
        home.setSize(new Dimension(100, getHeight()));
        home.setFocusPainted(false);
        home.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        home.addActionListener(e -> {
            mainMenu.repaint();
            mainMenu.revalidate();
        });

        JButton notiLabel = new JButton();
        notiLabel.setPreferredSize(new Dimension(100, getHeight()));
        notiLabel.setMinimumSize(new Dimension(100, getHeight()));
        notiLabel.setMaximumSize(new Dimension(100, getHeight()));
        notiLabel.setSize(new Dimension(100, getHeight()));
        notiLabel.setBackground(new Color(0,123,255));
        notiLabel.setForeground(Color.WHITE);
        notiLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        notiLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        ImageIcon originalIcon_user = new ImageIcon(Toolkit.getDefaultToolkit().createImage(
                SideBar.class.getResource("/src/icon/notification.png")));
        Image scaledImage_noti = originalIcon_user.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon_noti = new ImageIcon(scaledImage_noti);
        notiLabel.setIcon(scaledIcon_noti);
        notiLabel.setFocusPainted(false);
        notiLabel.setPreferredSize(new Dimension(100, getHeight()));
        notiLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    notiVisible = !notiVisible;
                    notificationPanel.setVisible(notiVisible);
                    serviceNotification.updateIsSeen(user);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        add(home);
        add(Box.createHorizontalGlue());
        add(notiLabel);
    }


}
