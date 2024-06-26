package src.loginPanel;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Objects;
import javax.swing.*;

import src.base.Config;
import src.model.Login;
import src.model.User;
import net.miginfocom.swing.MigLayout;
import src.base.PasswordField;
import src.base.LoginTextField;
import src.base.Button;

public class PanelLoginAndRegister extends JLayeredPane {
    private transient User user;
    private transient Login dataLogin;

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
        login.setLayout(new MigLayout("", "push[center]push", "push[]25[]15[]15[]25[]push"));
        JLabel label = new JLabel("Sign In");
        label.setFont(Config.FONT_LOGIN);
        label.setForeground(Config.GREEN);
        login.add(label, "wrap");

        LoginTextField username = new LoginTextField("Username");
        username.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/mail.png"))));
        login.add(username, "w 60%, wrap");

        PasswordField password = new PasswordField();
        password.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/pass.png"))));
        password.setHint("Password");
        login.add(password, " w 60%, wrap");

        Button cmd = new Button();
        cmd.setBackground(Config.GREEN);
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
        label.setFont(Config.FONT_LOGIN);
        label.setForeground(Config.GREEN);
        register.add(label, "wrap");

        JPanel fullName = new JPanel(new MigLayout("fill", "push[right]push"));
        fullName.setBackground(Config.WHITE);
        LoginTextField firstName = new LoginTextField("First Name");
        firstName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/user.png"))));

        fullName.add(firstName, "w 45%, left");
        LoginTextField lastName = new LoginTextField("Surname");
        lastName.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/user.png"))));

        fullName.add(lastName, "w 45%, right");
        register.add(fullName, "w 60%, wrap");

        LoginTextField username = new LoginTextField("Username");
        username.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/mail.png"))));

        register.add(username, "w 60%, wrap");

        PasswordField password = new PasswordField();
        password.setPrefixIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/src/icon/pass.png"))));
        password.setHint("Password");
        register.add(password, "w 60%, wrap");

        Button cmd = new Button();
        cmd.setBackground(Config.GREEN);
        cmd.setForeground(Config.COLOR_TEXT);
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

        login.setBackground(Config.WHITE);

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

        register.setBackground(Config.WHITE);

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
