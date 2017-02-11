/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.event.KeyEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * This class is used to prevent the Special symbol and Extra space in Text-Area in java dialog..
 */
public class TxtDocument extends PlainDocument {
    int space_count=0;
    TxtDocument() {
    super();
  }
      @Override
  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
    if (str == null) {
          return;
      }
char c=str.charAt(str.length()-1);
      str.replaceAll("\\s+", " ");
    if(c==KeyEvent.VK_SPACE && space_count==0){
        super.insertString(offset, str, attr);
        space_count+=1;
    }else if(c == KeyEvent.VK_BACK_SPACE){
       space_count=0;
    }
    if (((c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z') || (c >= '0') && (c <= '9') ||
         (c == KeyEvent.VK_BACK_SPACE) ||(c =='.')||(c ==',')|| (c ==')')|| (c =='(')||
         (c == KeyEvent.VK_DELETE))) {
             super.insertString(offset, str, attr);
             space_count=0;
    }
    else{
      
    }
  }
}
