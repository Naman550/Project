/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Color;
import javax.swing.SwingUtilities;

/**
 *
 * @author Me
 */
public class Timesheet { 
    
    
    
    public static void main(String args[]) {

        try {
          //  System.out.println("naman1");
         //   javax.swing.UIManager.put("nimbusBase", new Color(34,56,17));
          //  javax.swing.UIManager.put("nimbusBlueGrey", new Color(134,56,167));
         //   javax.swing.UIManager.put("control", new Color(234,156,17));
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                
                if ("GTK+".equals(info.getName())) {
                   javax.swing.UIManager.setLookAndFeel(info.getClassName());        //Nimbus look and feel
      // javax.swing. UIManager.setLookAndFeel( javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
      // javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
      // javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //   SwingUtilities.updateTreeComponentUI(frame);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() { 
                Config.setDomain();
                new PopUpLogin().launchTimesheet();

            }
        });
    }
}
