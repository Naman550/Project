

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Me
 */
public class InputDialog extends javax.swing.JDialog {

    String comments;
    private JFrame dialo;
    
    /**
     * Creates new form InputDialog
     */
    public InputDialog(java.awt.Frame parent,boolean Model) {
        super(parent,Model);
        
        initComponents();
        jScrollPane2.getVerticalScrollBar().setPreferredSize(new Dimension(7, 0));
        txtComments.setDocument(new TxtDocument());
        txtComments.getDocument().addDocumentListener(myListener);
  //      txtComments.setForeground(new Color(211, 211, 211));
    //    btnOk.requestFocus();
        txtComments.addMouseListener(mouselistener);
        txtComments.addKeyListener(kd);
  //      txtComments.setTransferHandler(null);                                     enable/disable copy paste
     //   DefaultCaret caret = (DefaultCaret)txtComments.getCaret();
     //   caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
   //     txtComments.g
    }
    
    public void setHint(String hint){
        txtComments.setText(hint);
    }
    public void setInfo(String info){
        lblInput.setText(info);
    }
    
    public void setTextColor(Color clr){
        txtComments.setForeground(clr);
    }
    public String getInput(){
        return comments;
    }
    
    MouseListener mouselistener=new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            if(txtComments.getText().toString().equals("Minimum 30 characters required")){
            txtComments.setText("");
            txtComments.setForeground(Color.black);
        }
        }

        @Override
        public void mousePressed(MouseEvent e) {
              if(txtComments.getText().toString().equals("Minimum 30 characters required")){
            txtComments.setText("");
            txtComments.setForeground(Color.black);
        }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
    };
    public KeyAdapter kd=new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        
        if(txtComments.getText().toString().equals("Minimum 30 characters required")){
            txtComments.setText("");
            txtComments.setForeground(Color.black);
        }
        if((c==KeyEvent.VK_BACK_SPACE) && txtComments.getText().toString().equals("Minimum 30 characters require") ){
            txtComments.setText("");
            txtComments.setForeground(Color.black);
        }
        
  //      if(c==KeyEvent.VK_PASTE){
  //          System.out.println("Copied>>"+txtComments.getText());
  //      }
   //     if("minimum 30 characters required!".equals(txtComments.getText().toString())){
   //         txtComments.setText("minimum 30 characters required!");
  //      }
 // if (//Write your condition here) {
   //   e.consume();  // ignore event
}
    };
    
    
    private DocumentListener myListener = new DocumentListener() {

        @Override
        public void insertUpdate(DocumentEvent e) {
           
            Document document = e.getDocument();
         //   if(txtComments.getText().contains("minimum 30 characters required!")){
          //           txtComments.setText("");
          //           txtComments.setForeground(Color.black);
        //      }
            try {
                 String s = document.getText(0, document.getLength());
        //       System.out.println("Copied>>>>>>"+s);
             
                 
                 
                 
                 
            } catch (BadLocationException ex) {
                Logger.getLogger(InputDialog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            
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

        jPanel1 = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        lblInput = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComments = new javax.swing.JTextArea();
        btnClear = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        btnOk.setText("Ok");
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOkActionPerformed(evt);
            }
        });

        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblInput.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblInput.setText("Enter what you have done!");

        jPanel2.setBackground(new java.awt.Color(15, 161, 208));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        txtComments.setColumns(20);
        txtComments.setLineWrap(true);
        txtComments.setRows(5);
        txtComments.setSelectionColor(new java.awt.Color(51, 153, 253));
        jScrollPane2.setViewportView(txtComments);

        btnClear.setText("Clear");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(btnOk, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnClear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInput)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblInput)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOk)
                    .addComponent(btnExit)
                    .addComponent(btnClear))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
           comments="";
           btnOk.requestFocus();
        dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOkActionPerformed
        if(txtComments.getText().length()>29 && !txtComments.getText().toString().equals("Minimum 30 characters required")){
           comments=txtComments.getText().toString();
            setVisible(false);
            txtComments.setText("");
            btnOk.requestFocus();
        }
        else
        {
           
            Alertx alert = new Alertx(dialo,true);
            alert.hh();
            
//            alert.setLocation(150, 150);
            //JOptionPane.showMessageDialog(null,"Minimum 30 characters required");
            
        }
    }//GEN-LAST:event_btnOkActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        setTextColor(new Color(189, 189, 189));
        txtComments.setText("Minimum 30 characters required");
        
    }//GEN-LAST:event_btnClearActionPerformed
    
   
    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JButton btnClear;
    public static javax.swing.JButton btnExit;
    public static javax.swing.JButton btnOk;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lblInput;
    public static javax.swing.JTextArea txtComments;
    // End of variables declaration//GEN-END:variables
}
