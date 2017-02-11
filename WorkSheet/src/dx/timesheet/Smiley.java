/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

/**
 *
 * @author naman
 */
import com.vdurmont.emoji.EmojiParser;
import javax.swing.*;
import java.awt.*;
class Testing
{
  public void buildGUI()
  {
    JFrame f = new JFrame();
    f.getContentPane().add(new JScrollPane(new JTextArea("\u263a",5,10)));
    f.pack();
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setVisible(true);
  }
  public static void main(String[] args)
  {
    SwingUtilities.invokeLater(new Runnable(){
      public void run(){
        new Testing().buildGUI();
        
        String str = "An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!";
        String result = EmojiParser.parseToUnicode(str);
        System.out.println(result);
      }
    });
  }
}