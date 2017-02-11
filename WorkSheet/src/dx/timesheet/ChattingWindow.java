/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.DefaultCaret;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 *
 * @author naman
 */
public class ChattingWindow extends javax.swing.JFrame implements MessageListener, WindowListener {

    /**
     * Creates new form ChattingWindow
     */
    int count=0;
    int idleSec=0;
    int receiveIdleSec=0;
    int sendIdelSec=0;
    String cUserName;
    static XMPPConnection connection;
    Chat chat;
    static AccountManager accountManager;
    boolean stop=false;
    
    public void trackTime(){
       final Timer time = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                    
                receiveIdleSec=receiveIdleSec+1;
                sendIdelSec=sendIdelSec+1;
                 if(receiveIdleSec>3){
                    chatTyping.setText("");
                 }
                 if(sendIdelSec>3){
                    userChatTyping.setText("");
                 }
                 System.out.println(stop);
                 if(stop==true){
                     time.cancel();
                     stop=false;
                 }
            }
        };
        time.schedule(timerTask, 1000, 1000);
    }
    
   
    
    public static  void createXMPPConnection(){
        connection = Connect();
    }
    
    public  static XMPPConnection Connect() {
    
     System.out.println(" connecting to server...");
    ConnectionConfiguration config = new ConnectionConfiguration("112.196.23.228", 5222);

    XMPPConnection connection = new XMPPConnection(config);
    try {
     connection.connect();
     System.out.println(" connected successfull");
    } catch (XMPPException e) {
    
     e.printStackTrace();
    }
    return connection;
   }
    
    public static void addXMPPAccount(String userName, String password){
        try {
            // Now we create the account:
            System.out.println("Creating Account");
            accountManager = new AccountManager(connection);
            accountManager.createAccount(userName, password);
        } catch (XMPPException ex) {
            System.out.println("Already Registered");
        }
       
        login(userName,password);
//        try {
//            accountManager.changePassword(password);
//            System.out.println("changePasswordSuccessfully");
//        } catch (XMPPException ex) {
//            System.out.println("error"+ex);
//        }
    }
    
    
    public static void login(String userName, String password){
         System.out.println("Started to Login..");
        try{
            connection.login(userName,password);
            System.out.println("Inside Login");
        }
        catch(XMPPException e){
            System.out.println("Outside Login");
            System.out.println("error "+e);
        }
        
    }
    
    @Override
    public void processMessage(Chat chat, Message msg) {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date dateobj = new Date();
        System.out.println(df.format(dateobj));
        String time = df.format(dateobj);

         System.out.println("Received message: " + msg.getBody());
         receiveIdleSec=0;
          if(msg.getBody()!=null){
              
                chatEditText.append("("+time+")"+cUserName+": "+msg.getBody()+"\n");
                chatTyping.setText("");
                
            }else{
                  chatTyping.setText(cUserName+" is typing");
              
        }
    }
    
    public ChattingWindow() {
        initComponents();
        
        sendEditText.addKeyListener(kd);
        sendChat.addMouseListener(sendButtonChat);
        addWindowListener(this);
        sendEditText.setLineWrap(true);
        sendEditText.setWrapStyleWord(true);
        sendEditText.requestFocusInWindow();
        DefaultCaret caret = (DefaultCaret)chatEditText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
    }
    
    public void  chatConnection(String name){
        trackTime();
        chatUserName.setText(name);
        setLocationRelativeTo(null);
        
        System.out.println("Chat User..."+name);
        cUserName=name;

        name = name.toLowerCase();
        chat = connection.getChatManager().createChat(name+"@112.196.23.228", this);
    }
    
     public void chat(String message){
        try {
             
            DateFormat df = new SimpleDateFormat("HH:mm");
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));
            String time = df.format(dateobj);
            
            chat.sendMessage(message);
            userChatTyping.setText(PopUpLogin.chatUserName+" is typing");
            sendIdelSec=0;
            if(message!=null ){
                chatEditText.append("("+time+")"+PopUpLogin.chatUserName+": "+message+"\n");
            }
            System.out.println(" Send Message succesfully");
            
        }  catch (XMPPException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     public void send(String condition){
         String message = sendEditText.getText();
        System.out.println(message);
        if(condition.equalsIgnoreCase("false")){
            chat(null);
        }
        else if(condition.equalsIgnoreCase("true")){
            if(!(message.equals(""))){
                sendEditText.setText("");
                chat(message);
            }
        }
    }
    
    
     
     /**
    * KeyListener Implementation
    */
    KeyListener kd=new KeyListener() {

        @Override
        public void keyTyped(KeyEvent e) {
           
        }
        @Override
        public void keyPressed(KeyEvent e) {
             if (e.getKeyCode()==KeyEvent.VK_ENTER){
                 System.out.println("hello");
                 e.consume();
                 send("true");
            }
             else{
                  send("false");
             }
             
        }
        @Override
        public void keyReleased(KeyEvent e) {
        }
    };
    
    MouseListener sendButtonChat = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("Clicked");
            send("true");
        }

        @Override
        public void mousePressed(MouseEvent e) {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatEditText = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        sendEditText = new javax.swing.JTextArea();
        sendChat = new javax.swing.JLabel();
        chatTyping = new javax.swing.JLabel();
        chatUserName = new javax.swing.JLabel();
        userChatTyping = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        chatEditText.setEditable(false);
        chatEditText.setColumns(20);
        chatEditText.setFont(new java.awt.Font("Monospaced", 0, 18)); // NOI18N
        chatEditText.setLineWrap(true);
        chatEditText.setRows(5);
        chatEditText.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jScrollPane1.setViewportView(chatEditText);

        sendEditText.setColumns(20);
        sendEditText.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        sendEditText.setRows(5);
        sendEditText.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jScrollPane2.setViewportView(sendEditText);

        sendChat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/send.png"))); // NOI18N
        sendChat.setToolTipText("Send");

        chatTyping.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N

        chatUserName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chatUserName.setText("UserName");

        userChatTyping.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(userChatTyping, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendChat)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chatUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(chatTyping, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chatUserName)
                    .addComponent(chatTyping, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(sendChat))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(userChatTyping, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(ChattingWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChattingWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChattingWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChattingWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
                ChattingWindow user=new ChattingWindow();
                user.createXMPPConnection(); 
                user.addXMPPAccount("Naman","welcome");
                user.chatConnection("Richa");
                    user.setVisible(true);
                   // user.trackTime();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatEditText;
    private javax.swing.JLabel chatTyping;
    private javax.swing.JLabel chatUserName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel sendChat;
    private javax.swing.JTextArea sendEditText;
    private javax.swing.JLabel userChatTyping;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("open");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        dispose();
        
            //connection.disconnect();
            stop=true;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
