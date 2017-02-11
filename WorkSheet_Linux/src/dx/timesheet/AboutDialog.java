/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to open the About Dialog Message
 */
public class AboutDialog extends javax.swing.JDialog {

    /**
     * Creates new form AboutDialog
     */
    public AboutDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        iconInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource(Config.LOGOPATH))); 
        lblLink.setText("<HTML><center><U>"+Config.COMPANYURL+"<U></center><HTML>");
        lblCopyright.setText("<HTML><center>Copyright &copy "+Config.COPYRIGHT+" "+Config.COMPANYNAME+"</center><HTML>");
        jLabel1.setText("<HTML>"+Config.VERSION+"<HTML>");
        lblClose.addMouseListener(ml);
        lblLink.addMouseListener(ml);
        panelAbout.addMouseListener(ml);
    }

    public void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            
            e.printStackTrace();
        }
    }
    
    /**
    * This Listener is used to click on the About Dialog frame
    */
    
    MouseListener ml=new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            PopUpLogin.height3=0;
            if (e.getSource() == lblClose) {
                dispose();
            }
            if (e.getSource() == lblLink) {
                try {
                    openWebpage(new URL("http://"+Config.COMPANYURL));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(AboutDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (e.getSource() == panelAbout) {
                dispose();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == lblClose) {
                lblClose.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
            }
             if (e.getSource() == lblLink) {
                lblLink.setCursor(new Cursor(Cursor.HAND_CURSOR));
                lblLink.setForeground(new Color(102, 0, 102));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == lblClose) {
                lblClose.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            if (e.getSource() == lblLink) {
                lblLink.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                lblLink.setForeground(new Color(0, 0, 255));
            }
        }
    };
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelAbout = new javax.swing.JPanel();
        lblInfo = new javax.swing.JLabel();
        iconInfo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblCopyright = new javax.swing.JLabel();
        lblLink = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        panelAbout.setBackground(new java.awt.Color(255, 255, 255));
        panelAbout.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 102, 255), 1, true));
        panelAbout.setPreferredSize(new java.awt.Dimension(230, 135));

        lblInfo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblInfo.setText("Timesheet");

        jLabel1.setText("1.1.0");

        lblCopyright.setText("Copyright \\u00A9 2016 DesignersX");

        lblLink.setForeground(new java.awt.Color(0, 0, 102));
        lblLink.setText("www.designersx.com");

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/cross_about.png"))); // NOI18N

        javax.swing.GroupLayout panelAboutLayout = new javax.swing.GroupLayout(panelAbout);
        panelAbout.setLayout(panelAboutLayout);
        panelAboutLayout.setHorizontalGroup(
            panelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAboutLayout.createSequentialGroup()
                .addGroup(panelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAboutLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblClose))
                    .addGroup(panelAboutLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(iconInfo)
                        .addGroup(panelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAboutLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblInfo))
                            .addGroup(panelAboutLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel1)))
                        .addGap(0, 183, Short.MAX_VALUE)))
                .addGap(7, 7, 7))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAboutLayout.createSequentialGroup()
                .addGap(10, 44, Short.MAX_VALUE)
                .addGroup(panelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAboutLayout.createSequentialGroup()
                        .addComponent(lblLink, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAboutLayout.createSequentialGroup()
                        .addComponent(lblCopyright)
                        .addContainerGap())))
        );
        panelAboutLayout.setVerticalGroup(
            panelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAboutLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(panelAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelAboutLayout.createSequentialGroup()
                        .addComponent(lblClose)
                        .addGap(5, 5, 5)
                        .addComponent(lblInfo)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel1))
                    .addGroup(panelAboutLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(iconInfo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCopyright)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLink)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAbout, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelAbout, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AboutDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AboutDialog dialog = new AboutDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel iconInfo;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel lblClose;
    public javax.swing.JLabel lblCopyright;
    public static javax.swing.JLabel lblInfo;
    public javax.swing.JLabel lblLink;
    public static javax.swing.JPanel panelAbout;
    // End of variables declaration//GEN-END:variables
}
