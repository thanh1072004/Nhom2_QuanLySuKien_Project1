package main;

import components.CoverPanel;
import components.PanelLoginAndRegister;
import java.text.DecimalFormat;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Main extends javax.swing.JFrame {
    
    private MigLayout layout;
    private CoverPanel cover;
    private PanelLoginAndRegister loginAndRegister;
    private boolean isLogin = true;
    private final double coverSize = 40;
    private final double loginSize = 60;
    private final DecimalFormat df = new DecimalFormat("##0.###");
    
    public Main() {
        initComponents();
        init();
    }
    
    private void init(){
        layout = new MigLayout("fill, insets 0");
        cover = new CoverPanel();
        loginAndRegister = new PanelLoginAndRegister();
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
    

    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
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

        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }
    private javax.swing.JLayeredPane bg;

}
