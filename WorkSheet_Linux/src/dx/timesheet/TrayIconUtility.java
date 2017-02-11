/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * This class is used for set Tray Icon in PMS software
 */
public class TrayIconUtility {

    TrayIcon trayIcon;
    SystemTray tray;
    //   PopUpLogin controller=new PopUpLogin();
    final PopupMenu popup = new PopupMenu();
    public static MenuItem refresh = new MenuItem("Refresh List");
    public static MenuItem show = new MenuItem("Show");
    public static MenuItem signout = new MenuItem("Sign out");
    public static MenuItem exit = new MenuItem("Exit");
    public static MenuItem aboutItem = new MenuItem("About...");
    

    protected static Image createImage(String path, String description) {
        URL imageURL = PopUpLogin.class.getResource(path);

        if (imageURL == null) {
            System.err.println("Resource not found: " + path);
            return null;
        } else {
            return (new ImageIcon(imageURL, description)).getImage();
        }
    }
    /**
     * This function is used for set Tray Icon in PMS software
     */
    public void setTrayIcon(String imgPath) {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        } else {
            System.out.println("SystemTray  supported");
        }
       
        trayIcon = new TrayIcon(createImage(imgPath, "tray icon"), "", popup);
        trayIcon.setToolTip("Timesheet");
        tray = SystemTray.getSystemTray();
        try {
            tray.add(trayIcon);

            refresh.setEnabled(false);
            signout.setEnabled(false);
            
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(refresh);
            popup.add(show);
            popup.add(signout);
            popup.addSeparator();
            
            popup.add(exit);
            
            trayIcon.setPopupMenu(popup);
            System.out.println("<<<<<<<<<<<<<<-----------end of setTrayIcon block---------->>>>>>>>> ");
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

    }
     /**
     * This function is enabled the refresh in trayIcon
     */
    public void enableRefresh(boolean status) {
        refresh.setEnabled(status);
    }
     /**
     * This function is enabled the Sign Out in trayIcon
     */
    public void enableSignOut(boolean status) {
        signout.setEnabled(status);
    }
    /**
     * This function is remove the trayIcon
     */
    public void remove() {
        tray.remove(trayIcon);
    }
    /**
     * This function is used for update the trayIcon
     */
    public void updateTrayIcon(String imgPath) {
        trayIcon.setImage(createImage(imgPath, "tray icon"));
    }
}
