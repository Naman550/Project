/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author Me
 */



public class Activity {
    
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
    
    public void openWebpage(URL url) {             //Opening a url in default browser of the system
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
        //    showInfoDialog("Url error");
            e.printStackTrace();
        }
    }
    
    
}