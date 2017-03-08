/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vjavaapplication;

import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.imageio.ImageIO;

/**
 *
 * @author naman
 */
public class ScreenShot {
    SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmma");
    public void getScreenShot() throws AWTException, IOException{
        Calendar now = Calendar.getInstance();
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        image = resize(image, 1280, 960);
        String str = formatter.format(now.getTime());
        createScreenshotDirectory();
        ImageIO.write(image, "jpg", new File("C://Services/" + str + ".jpg"));
        ImageUpload upload = new ImageUpload();
        upload.main(str,""+Session.getId()); 
    }
    
     /**
    * this function is used to  resize the Image 
    */
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }
    
    /**
    * this function is used to create Screenshot Directory 
    */
    public void createScreenshotDirectory() {
        
        File theDir = new File("C:\\Services");
        if (!theDir.exists()) {
            boolean result = theDir.mkdir();
              if(result) {    
                 System.out.println("DIR created");  
              }else{
                   System.out.println("DIR not created"); 
              }
        }
        deleteAllScreenshots();
    }
    
     /**
    * this function is used to  delete all ScreenShot from temp folder
    */
    public void deleteAllScreenshots() {
        File file = new File("C:\\Services");
        String[] myFiles;
        if (file.isDirectory()) {
            myFiles = file.list();
            for (int i = 0; i < myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]);
                myFile.delete();
            }
        }
    }
}
