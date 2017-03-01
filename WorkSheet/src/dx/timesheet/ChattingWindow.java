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
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultCaret;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
    static int number=0;
    String cUserName;
    int offset=10;
    Random random = new Random();
    static int counting=0;
    static XMPPConnection connection;
    StringBuilder responseString;
    Chat chat;
    static ArrayList<String> openUser = new ArrayList<String>();
    static AccountManager accountManager;
    boolean stop=false;
    boolean chatDelete=false;
    SimpleDateFormat formatter = new SimpleDateFormat("yyMMddhhmma");
    private String chatReceiverId;
    DateFormat df = new SimpleDateFormat("HH:mm");
    SimpleDateFormat currentDate = new SimpleDateFormat("ddMMMyyyy");
    DefaultCaret caret;
    
    /**
     *This function is used for track the time 
    */
    
    public void trackTime(){
       final Timer time = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                
                getAllChatLength(PopUpLogin.chatUserId,chatReceiverId);
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
        time.schedule(timerTask, 1000, 2000);
    }
    
   
    /**
     *This function is used for create the connection 
    */
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
    /**
     *This function is used for create the addXMPPAccount
    */
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
    
     /**
     *This function is used for login in XMPPAccount
    */
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
    
    
    /**
     *This function is used for received the message from another end
    */
    @Override
    public void processMessage(Chat chat, Message msg) {
        Date dateobj = new Date();
        System.out.println(df.format(dateobj));
        
        String time = df.format(dateobj);
        String date = currentDate.format(dateobj);
        
        System.out.println("Received message: " + msg.getBody());
        receiveIdleSec=0;
        if(msg.getBody()!=null){
            setChat(chatReceiverId,PopUpLogin.userId ,msg.getBody());
            chatEditText.append("("+date+"-"+time+")"+cUserName+": "+msg.getBody()+"\n");
            chatTyping.setText("");
        }else{
            chatTyping.setText(cUserName+" is typing");
        }
    }
    
    public ChattingWindow() {
        
        initComponents();
        sendEditText.addKeyListener(kd);
        sendChat.addMouseListener(sendButtonChat);
        //clearChat.addMouseListener(clearButton);
        loadChat.addMouseListener(loadButton);
        addWindowListener(this);
        sendEditText.setLineWrap(true);
        sendEditText.setWrapStyleWord(true);
        sendEditText.requestFocusInWindow();
        caret = (DefaultCaret)chatEditText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE); 
        
    }
    
    /**
     *This function is used for chatConnection with other user
    */
    public void  chatConnection(String name, String chatUserId){
        
        chatUserName.setText(name);
        setLocationRelativeTo(null);
        
        System.out.println("Chat User..."+name);
        cUserName=name;
        chatReceiverId=chatUserId;
        trackTime();
        name = name.toLowerCase();
        chat = connection.getChatManager().createChat(name+"@112.196.23.228", this);
        openUser.add(chatUserId);
        counting=counting+1;
        System.out.println("chat is added.."+counting);
    }
    
    /**
     *This function is used to send chat message to other user
    */
     public void chat(String message){
        try {
            Date dateobj = new Date();
            System.out.println(df.format(dateobj));
            String time = df.format(dateobj);
            String date = currentDate.format(dateobj);
            
            chat.sendMessage(message);
            userChatTyping.setText(PopUpLogin.chatUserName+" is typing");
            sendIdelSec=0;
            if(message!=null ){
                chatEditText.append("("+date+"-"+time+")"+PopUpLogin.chatUserName+": "+message+"\n");
                //code here...
                setChat(PopUpLogin.userId, chatReceiverId,message);
                userChatTyping.setText("");
            }
            System.out.println(" Send Message succesfully");
            
        }  catch (XMPPException ex) {
            Logger.getLogger(ChatWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
     /**
     *This function is used to check the chat message whether its is null or have message
    */
     public void send(String condition){
         String message = sendEditText.getText();
        System.out.println(message);
        if(condition.equalsIgnoreCase("false")){
            chat(null);
        }
        else if(condition.equalsIgnoreCase("true")){
            message = message.trim();
            if(!(message.equals(""))){
                sendEditText.setText("");
                
//                message = message.replaceAll("^\\s+", "");
//                message = message.replaceAll("\\s+$", "");
                chat(message);
            }
        }
    }
    /**
     *This Listener is used to load the chat from server.
    */ 
     
     public MouseListener loadButton = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            offset+=10;
            getChat(PopUpLogin.userId, chatReceiverId, cUserName);
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
     *This Listener is used to clear the chat on server.
    */ 
    public MouseListener clearButton = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            ChatConfirmDialog ChatConfirmDialo=new ChatConfirmDialog(new javax.swing.JFrame(), true);
            
            String option=ChatConfirmDialo.getInput();
            
            if(option.equals("yes")){
                clearChat(PopUpLogin.userId,chatReceiverId);
                chatEditText.setText("");
            }
           
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
    
     /**
    * MouseListener Implementation
    */
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
        loadChat = new javax.swing.JLabel();
        staticLab = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        chatEditText.setEditable(false);
        chatEditText.setColumns(20);
        chatEditText.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
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
        sendChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        chatTyping.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N

        chatUserName.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        chatUserName.setText("UserName");

        userChatTyping.setFont(new java.awt.Font("Tahoma", 2, 10)); // NOI18N

        loadChat.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        loadChat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/dx/timesheet/extend.png"))); // NOI18N
        loadChat.setToolTipText("Load Chat");
        loadChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(122, 122, 122)
                                .addComponent(staticLab, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(loadChat)
                                .addGap(30, 30, 30)))
                        .addComponent(sendChat)
                        .addGap(30, 30, 30))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chatUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(chatTyping, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(chatUserName)
                    .addComponent(chatTyping, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(staticLab)
                            .addComponent(userChatTyping, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loadChat)
                            .addComponent(sendChat))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                user.chatConnection("Richa","115");
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
    private javax.swing.JLabel loadChat;
    private javax.swing.JLabel sendChat;
    private javax.swing.JTextArea sendEditText;
    public static javax.swing.JLabel staticLab;
    private javax.swing.JLabel userChatTyping;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        System.out.println("open");
    }

    @Override
    public void windowClosing(WindowEvent e) {
        counting=counting-1;
        openUser.remove(chatReceiverId);
        System.out.println("chat is Removed.."+counting);
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
    
    
    /**
     *this function is used for getChat from server
     */
    public void getChat(String userid, String receiverId, String senderName)  {
        
        try {
            double i=random.nextDouble()+random.nextDouble();
            number+=1;
            
            URL url = new URL(Config.HTTP+Config.DOMAIN + "employee/conversations/getChat/" + userid + "/" + receiverId + "/" +offset+"?"+number+"naman"+i);
            System.out.println("NamanSetChat : "+url);
            responseString = getStreamResponse(url);
            System.out.println("NamanSetChatResponseString"+responseString);
            
            System.out.println("-->>"+responseString.toString().equals("empty"));
            if(responseString.toString().equals("empty")){
                
            }
            else{
                URLConnection con = url.openConnection();
                InputStream in = con.getInputStream();

                Document doc = parse(in);

                NodeList listOfCodes = doc.getElementsByTagName("chat");
                int totalTasks = listOfCodes.getLength();
                chatEditText.setText("");
                System.out.println("Total NO. of Chat.."+totalTasks);
                for (int temp = 0; temp < totalTasks; temp++) {
                   Node nNode = listOfCodes.item(temp); 
                   
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String userId = getElements(eElement, "user_id");
                        
                        if(userId.equals(PopUpLogin.chatUserId)){
                            userId=PopUpLogin.chatUserName;
                        }else{
                            userId=senderName;
                        }
                        System.out.println("ss"+userId);
                        String msg = getElements(eElement, "text");
                        String time = getElements(eElement, "time");
                        long tm=Long.parseLong(time);
                        String currentDate = new SimpleDateFormat("ddMMMyyyy").format(new Date(tm * 1000L));
                        System.out.println("date--"+currentDate);
                        String msgTime = new SimpleDateFormat("HH:mm").format(new Date(tm * 1000L));
                        System.out.println("msgTime--"+msgTime);
                        
                        chatEditText.append("("+currentDate+"-"+msgTime+")"+userId+": "+msg+"\n");
                    }
                }
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    /**
     * This function is used for xml parsing.
    */
    public String getElements(Element element, String tag) {
        NodeList nodelist = element.getElementsByTagName(tag);
        System.out.println(nodelist.getLength());
        String value = "";
        if (nodelist.getLength() > 0) {
            Element elm = (Element) nodelist.item(0);
            value = elm.getChildNodes().item(0).getNodeValue();
                System.out.println(value);
        }
        return value;
    }
    
    /**
     *this function is used for setChat to server
     */
    public void setChat(String userid, String receiverId, String message)  {
        try {
            Calendar now = Calendar.getInstance();
            String time = formatter.format(now.getTime());
            double i=random.nextDouble()+random.nextDouble();
            number+=1;
            char str=message.charAt(0);
            if(str=='?'){
                message="qwert@"+message;
            }
            if(message.contains("/")){
                message=message.replaceAll("/", "[--");
                System.out.println("ppppppp------>>"+message);
            }
            if(message.contains(":")){
                message=message.replaceAll(":", "]--");
                System.out.println("ppppppp------>>"+message);
            }
            
            message = message.replaceAll(" ", "%20");
            URL url = new URL(Config.HTTP+Config.DOMAIN + "employee/conversations/setChat/" + userid + "/" + receiverId + "/" + message+ "/" +time+"?"+number+"naman"+i);
            System.out.println("NamanSetChat : "+url);
            StringBuilder responseString = getStreamResponse(url);
            System.out.println("NamanSetChatResponseString"+responseString);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
      /**
    * This function is used for getting the response from server
    */
        public StringBuilder getStreamResponse(URL url) throws IOException {
        InputStream in;
        URLConnection con = url.openConnection();
        in = con.getInputStream();
        System.out.println("Naman:inputStream- "+in);
        
        DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
        System.out.println("Naman:DataInputStream- "+dis);
        String s;
        StringBuilder responseString = new StringBuilder("");
        while ((s = dis.readLine()) != null) {
            responseString.append(s);
        }
        System.out.println("responseString - "+responseString);
       
        return responseString;
    }
        
    /**
     * This function is used for DOMXML parsing  and return Document 
    */
    public Document parse(InputStream is) {
        Document ret = null;
        DocumentBuilderFactory domFactory;
        DocumentBuilder builder;

        try {
            domFactory = DocumentBuilderFactory.newInstance();
            domFactory.setValidating(false);
            domFactory.setNamespaceAware(false);
            builder = domFactory.newDocumentBuilder();
            ret = builder.parse(is);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("unable to load XML: " + ex);
        }
        return ret;
    }
    
    /**
     *this function is used for Clear-Chat to server
     */
    public void clearChat(String userid, String receiverId)  {
        try {
            double i=random.nextDouble()+random.nextDouble();
            number+=1;
            URL url = new URL(Config.HTTP+Config.DOMAIN + "employee/conversations/clearChat/" + userid + "/" + receiverId +"?"+number+"naman"+i);
            System.out.println("NamanSetChat : "+url);
            StringBuilder responseString = getStreamResponse(url);
            System.out.println("NamanSetChatResponseString"+responseString);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public void getAllChatLength(String userid, String receiverId)  {
        
        try {
            double i=random.nextDouble()+random.nextDouble();
            number+=1;
            URL url = new URL(Config.HTTP+Config.DOMAIN + "employee/conversations/getChat/" + userid + "/" + receiverId +"?"+number+"naman"+i);
            StringBuilder responseString = getStreamResponse(url);
            if(responseString.toString().equals("empty")){
              //code here pending    
            }
           

        } catch (MalformedURLException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
