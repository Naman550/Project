/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 *
 * @author Me
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

            //      CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
            //       CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");


            refresh.setEnabled(false);
            signout.setEnabled(false);
            
            popup.add(aboutItem);
            popup.addSeparator();
            //     popup.add(cb1);
            //     popup.add(cb2);
            popup.add(refresh);
            //     popup.addSeparator();
            popup.add(show);
            //      popup.addSeparator();
            popup.add(signout);
            popup.addSeparator();
            popup.add(exit);
            //  popup.addSeparator();

            //   popup.add(displayMenu);
            //    displayMenu.add(errorItem);
            //    displayMenu.add(warningItem);
            //    displayMenu.add(infoItem);
            //    displayMenu.add(noneItem);
           

            trayIcon.setPopupMenu(popup);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

    }

    public void enableRefresh(boolean status) {
        refresh.setEnabled(status);
    }

    public void enableSignOut(boolean status) {
        signout.setEnabled(status);
    }

    public void remove() {
        tray.remove(trayIcon);
    }

    public void updateTrayIcon(String imgPath) {
        trayIcon.setImage(createImage(imgPath, "tray icon"));
    }
}
