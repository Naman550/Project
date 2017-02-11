/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Color;
import javax.swing.SwingUtilities;

/**
 *
 * This class is used for to Start the TimeSheet and call the setDomain method of Config Class
 */
public class Timesheet { 
    
    
    
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                
                if ("GTK+".equals(info.getName())) {
                   javax.swing.UIManager.setLookAndFeel(info.getClassName());        //GTK+ look and feel
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                Config.setDomain();
                new PopUpLogin().launchTimesheet();

            }
        });
    }
}
