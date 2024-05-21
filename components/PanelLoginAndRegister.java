package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;
import model.User;
import net.miginfocom.swing.MigLayout;
import swing.MyPasswordField;
import swing.MyTextField;
import swing.Button;

public class PanelLoginAndRegister extends javax.swing.JLayeredPane {
    private User user;

    public User getUser() {
        return user;
    }

    public PanelLoginAndRegister(ActionListener eventRegister) {
        initComponents();
        initLogin();
        initRegister(eventRegister);
        login.setVisible(false);
        register.setVisible(true);
    }
   
    private void initLogin(){
        login.setLayout(new MigLayout("", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Sign In");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        login.add(label, "wrap");
        
        MyTextField username = new MyTextField();
        username.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/mail.png"))));
        username.setHint("Username");
        login.add(username, "w 60%, wrap");
        
        MyPasswordField password = new MyPasswordField();
        password.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/pass.png"))));
        password.setHint("Password");
        login.add(password, " w 60%, wrap");
        
        JButton cmdForget = new JButton("Forget your password ?");
        cmdForget.setForeground(new Color(100, 100, 100));
        cmdForget.setFont(new Font("sanserif", Font.BOLD, 12));
        cmdForget.setContentAreaFilled(false);
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cmdForget.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        login.add(cmdForget, "wrap");
        
        Button cmd = new Button();  
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("SIGN IN");
        login.add(cmd, "w 40%, h 40, wrap");
    }
    
    private void initRegister(ActionListener eventRegister){
        register.setLayout(new MigLayout("", "push[center]push", "push[]10[]10[]10[]push"));
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        register.add(label, "wrap");
        
        JPanel fullName = new JPanel(new MigLayout("fill", "push[right]push"));
        fullName.setBackground(new Color(255, 255, 255));
        MyTextField firstName = new MyTextField();
        firstName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/user.png"))));
        firstName.setHint("First name");
        fullName.add(firstName, "w 45%, left");
        MyTextField lastName = new MyTextField();
        lastName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/user.png"))));
        lastName.setHint("Surname");
        fullName.add(lastName, "w 45%, right");
        register.add(fullName, "w 60%, wrap");
       
        MyTextField username = new MyTextField();
        username.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/mail.png"))));
        username.setHint("Username");
        register.add(username, "w 60%, wrap");
        
        MyPasswordField password = new MyPasswordField();
        password.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon/pass.png"))));
        password.setHint("Password");
        register.add(password, "w 60%, wrap");
        
        Button cmd = new Button();  
        cmd.setBackground(new Color(7, 164, 121));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("SIGN UP");
        cmd.addActionListener(eventRegister);
        register.add(cmd, "w 40%, h 40, wrap");
        cmd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = username.getText().trim();
                String firstname = firstName.getText().trim();
                String lastname = lastName.getText().trim();
                String passWord = password.getText().trim();
                user = new User(0, firstname, lastname, userName, passWord);
            }
        });
    }

    public void showRegister(boolean show){
        if(show){
            register.setVisible(true);
            login.setVisible(false);
        }else{
            register.setVisible(false);
            login.setVisible(true);
        }
    }


    // <editor-fold default state="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
