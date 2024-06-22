package src.loginPanel;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.*;

import src.base.Config;
import net.miginfocom.swing.MigLayout;
import src.base.ButtonOutline;

public class CoverPanel extends javax.swing.JPanel {

    private final DecimalFormat df= new DecimalFormat("##0.###");
    private ActionListener event;
    private MigLayout layout;
    private JLabel title;
    private JLabel description;
    private JLabel description1;
    private ButtonOutline button;
    private boolean isLogin;


    public CoverPanel() {
        initComponents();
        setOpaque(false);
        layout = new MigLayout("wrap, fill", "[center]", "push[]25[]10[]25[]push");
        setLayout(layout);
        init();
    }

    private void init(){
        title = new JLabel("Event planner");
        title.setFont(Config.FONT_LOGIN);
        title.setForeground(Config.TEXT_LOGIN);
        add(title);

        description = new JLabel("Life is an event");
        description.setForeground(Config.TEXT_LOGIN);
        add(description);

        description1 = new JLabel("Make it memorable");
        description1.setForeground(Config.TEXT_LOGIN);
        add(description1);

        button = new ButtonOutline();
        button.setBackground(Config.WHITE);
        button.setForeground(Config.WHITE);
        button.setText("SIGN UP");
        button.addActionListener(evt -> {
            event.actionPerformed(evt);
            if(button.getText().equals("SIGN IN")){
                button.setText("SIGN UP");
                description.setText("Life is an event");
                description1.setText("Make it memorable");
            }else{
                button.setText("SIGN IN");
                description.setText("Sign Up");
                description1.setText("It's quick and easy");
            }
        });
        add(button, "w 60%, h 40");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        GradientPaint gp = new GradientPaint(0, 0, Config.DARKER_GREEN, 0, getHeight(), Config.DARK_GREEN);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    public void addEvent(ActionListener event) {
        this.event= event;
    }

    public void login(boolean login){
        if(this.isLogin != login){
            if(login){
                button.setText("SIGN UP");
            }else{
                button.setText("SIGN IN");
            }
            this.isLogin = login;
        }
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 300, Short.MAX_VALUE)
        );
    }

}
