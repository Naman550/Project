/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.Timer;

/**
 *
 * This class is used for Detail of task like Task description, task DeadLine, Task status
 */
public class TaskDetailDialog extends javax.swing.JDialog {

    
    int height1=0;
    int width1=0;
    private Rectangle screenRect = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getMaximumWindowBounds();
    /**
     * Creates new form TaskDetailDialog
     */
    public TaskDetailDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addMouseListener(ml);
        lblCross.addMouseListener(ml);
        scrolller.setBorder(BorderFactory.createEmptyBorder());
        scrolller.getViewport().setBackground(Color.white);
        scrolller.getVerticalScrollBar().setPreferredSize(new Dimension(9, 0));
        scrolller.addMouseListener(ml);
    }
    /**
     *This function is used to set the tasks Detail in TimeSHeet like Task description, task DeadLine, Task status
     */
    public void setDetailedTask(String text, String date, String status, String currentDate, String lTime){
         String[] s1=text.split(":");
         
         for(String s: s1) {
         System.out.println(s);
            }
        if(status.equals("Working") || status.equals("Progress") || status.equals("Testing-Working")){
         lblDetailedTask.setText("<html><p width=\"168px\"><b>Description - </b>" + text + ""
                 + "</p></p></br><b>DeadLine</b> - "+date+"<p></br><b> Status </b>- <font color=green>"+status+"</font>"
                 + "</p>"
                 + "</html>");
        }
    
        else
        if(status.equals("Pause") || status.equals("Testing-Pause")){
         lblDetailedTask.setText("<html><p width=\"168px\"><b>Description - </b>" + text + "</p></br><b>DeadLine </b>- "+date+"</br><p><b> Status </b>- <font color=orange>"+status+"</font></p></html>");
        }
        else{
         lblDetailedTask.setText("<html><p width=\"168px\"><b>Description - </b>" + text + "</p></br><b>DeadLine</b> - "+date+"</br><p> <b>Status</b> -<font color=red> "+status+"</font></p></html>");
        }
    }
    public void clearDetailedTask(){
        lblDetailedTask.setText("");
    }
    
    /**
     *This function is used for animation of Task Detailed Dialog box
     */
     public Timer timer = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            width1 += 4;
            if (width1 >= getWidth()+30) {
                timer.stop();
                //             new Thread(getIdleTimeThread).start();
            }
            setLocation(screenRect.width - width1,
                    screenRect.height - getWidth()-60);
            repaint();
        }
    });
     /**
     *This function is used for animation of Task Detailed Dialog box
     */
     public Timer timer2 = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            width1 -= 4;
            if (width1 >= getWidth()+30) {
                timer.stop();
            }
            setLocation(screenRect.width - width1,
                    screenRect.height - getWidth()-60);
            repaint();
        }
    });
     
     /**
     *This function is used for start the animation of Task Detailed Dialog box
     */
     public void startTimer(){
                
                timer.setInitialDelay(0);
                timer.setDelay(2);
                timer.start();
                setVisible(true);
                
     }
     
     /**
     *This mouse listener is used for dispose the Task Detailed Dialog box
     */
    MouseListener ml=new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
             if (e.getSource() == lblCross) {
               setVisible(false);
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
            if (e.getSource() == lblCross) {
               lblCross.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == lblCross) {
               lblCross.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

        jComboBox1 = new javax.swing.JComboBox();
        jPanel9 = new javax.swing.JPanel();
        scrolller = new javax.swing.JScrollPane();
        lblDetailedTask = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        lblCross = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel9.setPreferredSize(new java.awt.Dimension(230, 135));

        scrolller.setBackground(new java.awt.Color(255, 255, 255));
        scrolller.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        lblDetailedTask.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDetailedTask.setText("jLabel2");
        lblDetailedTask.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblDetailedTask.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrolller.setViewportView(lblDetailedTask);

        jPanel10.setBackground(new java.awt.Color(15, 161, 208));

        lblCross.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/cross_about.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel1.setText("Task Description");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                .addComponent(lblCross)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCross)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scrolller, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(scrolller, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(TaskDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TaskDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TaskDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TaskDetailDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TaskDetailDialog dialog = new TaskDetailDialog(new javax.swing.JFrame(), true);
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
    public javax.swing.JComboBox jComboBox1;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPanel jPanel10;
    public javax.swing.JPanel jPanel9;
    public static javax.swing.JLabel lblCross;
    public static javax.swing.JLabel lblDetailedTask;
    public javax.swing.JScrollPane scrolller;
    // End of variables declaration//GEN-END:variables

}
