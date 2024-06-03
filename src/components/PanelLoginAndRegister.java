package src.components;

import base.Color1;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

import src.model.Login;
import src.model.User;
import net.miginfocom.swing.MigLayout;
import src.swing.MyPasswordField;
import src.swing.MyTextField;
import src.swing.Button;

public class PanelLoginAndRegister extends JLayeredPane {
    private User user;
    private Login dataLogin;

    public User getUser() {
        return user;
    }

    public Login getDataLogin() {
        return dataLogin;
    }

    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin) {
        initComponents();
        initLogin(eventLogin);
        initRegister(eventRegister);
        login.setVisible(false);
        register.setVisible(true);
    }
   
    private void initLogin(ActionListener eventLogin){
        login.setLayout(new MigLayout("", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Sign In");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(new Color(7, 164, 121));
        login.add(label, "wrap");
        
        MyTextField username = new MyTextField();
        username.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/mail.png"))));
        username.setHint("Username");
        login.add(username, "w 60%, wrap");
        
        MyPasswordField password = new MyPasswordField();
        password.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/pass.png"))));
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
        cmd.setBackground(Color1.BACKGROUND_LABEL);
        cmd.setForeground(new Color(250, 250, 250));
        cmd.setText("SIGN IN");
        cmd.addActionListener(eventLogin);
        login.add(cmd, "w 40%, h 40, wrap");
        cmd.addActionListener(e -> {
            String userName = username.getText().trim();
            String passWord = String.valueOf(password.getPassword());
            dataLogin = new Login(userName, passWord);
        });
    }
    
    private void initRegister(ActionListener eventRegister){
        register.setLayout(new MigLayout("", "push[center]push", "push[]10[]10[]10[]push"));
        JLabel label = new JLabel("Create Account");
        label.setFont(new Font("sanserif", Font.BOLD, 30));
        label.setForeground(Color1.BACKGROUND_LABEL);
        register.add(label, "wrap");
        
        JPanel fullName = new JPanel(new MigLayout("fill", "push[right]push"));
        fullName.setBackground(new Color(255, 255, 255));
        MyTextField firstName = new MyTextField();
        firstName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/user.png"))));
        firstName.setHint("First name");
        fullName.add(firstName, "w 45%, left");
        MyTextField lastName = new MyTextField();
        lastName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/user.png"))));
        lastName.setHint("Surname");
        fullName.add(lastName, "w 45%, right");
        register.add(fullName, "w 60%, wrap");
       
        MyTextField username = new MyTextField();
        username.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/mail.png"))));
        username.setHint("Username");
        register.add(username, "w 60%, wrap");
        
        MyPasswordField password = new MyPasswordField();
        password.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/pass.png"))));
        password.setHint("Password");
        register.add(password, "w 60%, wrap");
        
        Button cmd = new Button();  
        cmd.setBackground(Color1.BACKGROUND_LABEL);
        cmd.setForeground(Color1.COLOR_TEXT);
        cmd.setText("SIGN UP");
        cmd.addActionListener(eventRegister);
        register.add(cmd, "w 40%, h 40, wrap");
        cmd.addActionListener(e -> {
            String userName = username.getText().trim();
            String firstname = firstName.getText().trim();
            String lastname = lastName.getText().trim();
            String passWord = String.valueOf(password.getPassword());
            user = new User(0, firstname, lastname, userName, passWord);
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


    private void initComponents() {

        login = new JPanel();
        register = new JPanel();

        setLayout(new CardLayout());

        login.setBackground(Color1.WHITE);

        GroupLayout loginLayout = new GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card3");

        register.setBackground(Color1.WHITE);

        GroupLayout registerLayout = new GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card2");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel login;
    private JPanel register;
    // End of variables declaration//GEN-END:variables
}
