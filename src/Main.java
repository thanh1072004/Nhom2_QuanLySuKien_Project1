package src;

import src.base.MyColor;
import src.loginPanel.Message;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import src.database.DatabaseConnection;
import src.loginPanel.CoverPanel;
import src.loginPanel.PanelLoginAndRegister;
import src.model.User;
import src.model.Login;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import src.service.ServiceUser;


public class Main extends javax.swing.JFrame {

    private MigLayout layout;
    private CoverPanel cover;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin = true;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat("##0.###");
    private ServiceUser service;
    private MainMenu mainMenu;

    public Main() {
        initComponents();
        init();
    }

    private void init(){
        layout = new MigLayout("fill, insets 0");
        cover = new CoverPanel();
        service = new ServiceUser();
        ActionListener eventRegister = e -> register();
        ActionListener eventLogin = e -> login();
        loginAndRegister = new PanelLoginAndRegister(eventRegister, eventLogin);
        TimingTarget target = new TimingTargetAdapter(){
            @Override
            public void timingEvent(float fraction){
                double fractionCover;
                double fractionLogin;

                if(isLogin){
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                }else{
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                }
                if(fraction >= 0.5f){
                    loginAndRegister.showRegister(isLogin);
                }
                fractionCover = Double.parseDouble(df.format(fractionCover));
                fractionLogin = Double.parseDouble(df.format(fractionLogin));
                layout.setComponentConstraints(cover, "width " + coverSize + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(loginAndRegister, "width " + loginSize + "%, pos " + fractionLogin + "al 0 n 100%");

                bg.revalidate();
            }

            @Override
            public void end(){
                isLogin = !isLogin;
            }
        };

        Animator animator = new Animator(800, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
        bg.setLayout(layout);
        bg.add(cover, "width " + coverSize + "%, pos" + (isLogin ? "1al" : "0al") + " 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos" + (isLogin ? "0al" : "1al") + " 0 n 100%");
        loginAndRegister.showRegister(!isLogin);
        cover.login(isLogin);
        cover.addEvent(_ -> {
            if(!animator.isRunning()){
                animator.start();
            }
        });
    }

    private void register(){
        User user = loginAndRegister.getUser();
        try{
            if (service.checkDuplicateUser(user.getUsername())) {
                showMessage(Message.MessageType.ERROR,"Username already exists");
            }else if (user.getUsername().isEmpty() || user.getPassword().isEmpty() || user.getFirstName().isEmpty() || user.getLastName().isEmpty()){
                showMessage(Message.MessageType.ERROR,"Please fill all the fields");
            }else {
                service.authorizeRegister(user);
                mainMenu = new MainMenu(user);
                this.dispose();
                mainMenu.setVisible(true);

            }
        }catch(Exception e){
            showMessage(Message.MessageType.ERROR, "Register failed");
            e.printStackTrace();
        }
    }

    private void login(){
        Login dataLogin = loginAndRegister.getDataLogin();

        try{
            User user = service.authorizeLogin(dataLogin);
            if(user != null){
                mainMenu = new MainMenu(user);
                this.dispose();
                mainMenu.setVisible(true);
            }else{
                showMessage(Message.MessageType.ERROR,"Username or password is incorrect");
            }
        }catch(Exception e){
            showMessage(Message.MessageType.ERROR,"Login failed");
        }
    }

    private void showMessage(Message.MessageType messageType, String message){
        Message msg = new Message();
        msg.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if(!msg.isShow()){
                    bg.add(msg, "pos 0.5al -30", 0);//insert to bg first index 0
                    msg.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction){
                float f;
                if(msg.isShow()){
                    f = 40 * (1f - fraction);
                }else{
                    f = 40 * fraction;
                }
                layout.setComponentConstraints(msg, "pos 0.5al " + (int) (f-30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end(){
                if(msg.isShow()){
                    bg.remove(msg);
                    bg.repaint();
                    bg.revalidate();
                }else{
                    msg.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    animator.start();
                }catch(InterruptedException e){
                    System.err.println(e);
                }
            }
        });
    }
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(MyColor.WHITE);
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 588, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
                bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 496, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(bg)
        );

        pack();
    }

    public static void main(String[] args) {
        try {
            DatabaseConnection.getInstance().connectToDatabase();
        }catch(Exception e){
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    private javax.swing.JLayeredPane bg;

}
