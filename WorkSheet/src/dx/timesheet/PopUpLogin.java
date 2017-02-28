/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

/**
 *
 * @this is the Main class in TimeSheet all basic operation perform in this class
 */
import static dx.timesheet.WaitingPanel.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Random;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.json.JSONObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PopUpLogin {

    Border raisedBorder;
    boolean test=false; 
    JPanel contentPane;
    boolean onHoldFlag=false,oneTime=true;
    String firstTimeUser="false";
    static boolean reAgain=false; 
    private JFrame dialo;
    String email, password, host = "smtp.gmail.com", port = "465";
    String client_code, project_code, task, start_time, date, end_time, status;
    String mac_address;
    String forgotton_user;
    static String chatUserName,chatUserPassword,chatUserId;        
    static int k=0;
    Random random = new Random();
    String server_response;
    boolean colorFlag=false;
    String start_date;
    ArrayList<String> nDate=new ArrayList<>();
    ArrayList<String> nTime=new ArrayList<>();
     int holdTask=0;
    int totalNoOfHoldTask=0;
    int unHoldTask=0;
    int totalNoOfUnHoldTask=0;
    int todoTask=0;
    int totalNoOfTodoTask=0;
    int testingTask=0;
    int totalNoOfTestingTask=0;
    SelectChatUser selectChatUser;
    int taskLength=0;
    int totalTasks=0;
    int totalLength=0;
    boolean flagTrue=false;
    String router_ip, os_name, system_name, sys_ram, sys_hdd, cpu_model;
    String timesheet_response;
    String userName;
    static String userId, task_id, work_status, play_pause_comment, timesheet_id;
    String[] status_array;
    String[] p_code;
    String lTime="", currentDate="",currentDate1="";
    static String name, imgUrl, user_name;
    String company_alias="Dsx";
    public static String usr;
    static boolean  reviewAgain=false;
    String url = "http://www.designersx.com";
    static ArrayList<String> notification = new ArrayList<String>(); 
    private final String ROUTER_IP_DOMAIN = "http://api.externalip.net/ip/";
    private final String ROUTER_IP_DOMAIN2 = " http://checkip.amazonaws.com/";
    private int height = 0;
    int height2 = 0;
    static int height3 = 0;
    int count = 0;
    boolean flag=true;
    int allCount=0;
    int countPanels = 0;
    int countRecords = 0;
    int countInXml = 0;
    //int taskLength = 0;
    String main_name, main_username, main_imgurl, main_timeid, main_password;
    static String main_userid;
    int main_tasklength;
    int length;
    boolean interruptDialog = false;
    boolean taskUpdated;
    boolean firstLogin;
    boolean first_refresh = false;
    boolean refresh;
    static boolean userIdle = false;
    static boolean loginStatus = false;
    static boolean working_task = false;
    static boolean task_paused = false;
    boolean done = false;
    EncryptPwd encrypt = new EncryptPwd();
    private final static Sigar sigar = new Sigar();
    private Rectangle screenRect = GraphicsEnvironment
            .getLocalGraphicsEnvironment().getMaximumWindowBounds();
    public LoginPanel panel = new LoginPanel(new ImageIcon(this.getClass().getResource("/images/center_img.png")).getImage());
    public JPanel panelDetails = new WaitingPanel(new ImageIcon(this.getClass().getResource("/images/center_img.png")).getImage());
    public JPanel panelForgotPwd = new ForgotPwdPanel(new ImageIcon(this.getClass().getResource("/images/center_img.png")).getImage());
    public TaskPanel taskPanel = new TaskPanel(new ImageIcon(this.getClass().getResource("/images/center_img.png")).getImage());
    static DatabaseHandler dbHandler = new DatabaseHandler();
    TaskAddedDialog taskDialog = new TaskAddedDialog(null, true);
    
    TrackPacket trc = new TrackPacket();
    TaskDetailDialog td; 
    HardwareDetails hd = new HardwareDetails();
    InputDialog in2;
    PauseOptionDialog pauseDialog;
    AboutDialog abt = new AboutDialog(null, true);
    Win32IdleTime win = new Win32IdleTime();
    Thread tracker_thread;
    Thread track_net_thread;
    ReviewDialog reviewDlg;
    
    boolean kOnHold=false;
    ReadXml readxml = new ReadXml();
    boolean nPause=false;
    LoaderDialog infD;
    OnHoldMsg onHoldMsg;
    ConfirmDialog cd;
    ErrorDescription errorDescription;
    SignOut signOut;
    ExtendDeadlineDialog ed;
    ProgressDialog prDialog;
    TrayIcon trayIcon;
    SystemTray tray;
    Activity act = new Activity();
    TrayIconUtility trayIcons = new TrayIconUtility();
    TaskWithSubtaskDialog stask;
    List<Image> icons = new ArrayList<>();
    Image user_image = null;
    //   ImageIcon progress = new ImageIcon(PopUpLogin.this.getClass().getResource("/dx/timesheet/loader24.gif"));
    private final Dimension panelDimension = new Dimension(290, 460);
    private final Dimension panelDimension2 = new Dimension(300, 450);
    private static ScheduledExecutorService worker, refresh_worker, task_reader_thread, idle_listener, screeShotListener;
    static JLabel jLbl = new JLabel("");
    //   JXDatePicker picker = new JXDatePicker();
    //  Map<String, List<String>> subtask_map = new HashMap<String, List<String>>();
    private boolean complete=false;
    private String oid;
    private boolean todo,reopen;
    private boolean hold=true;
    private boolean trackingFirstTime = true;
    
    
    /**
     * This button is for TODO functionality; this button is only enable when
     * user send the review for tester and at tester side, the fourth button
     * will displayed.
    */
    public void fourButton(){
     
        PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
        PanelPlayPause.lblPlay.setToolTipText("Start Task");

        PanelPlayPause.lblReOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/play2.png")));
        PanelPlayPause.lblStop.setEnabled(false);

        PanelPlayPause.lblDone.setEnabled(false);
        PanelPlayPause.lblReOpen.setToolTipText("Re-Open Task");
        PanelPlayPause.lblReOpen.setEnabled(false);
    }
    
    
    JSeparator seperator = new JSeparator(JSeparator.HORIZONTAL);
    
    
    private JFrame dialog = new JFrame() {
      
        private static final long serialVersionUID = 1L;

        @Override
        public void paint(Graphics g) {
            g.setClip(0, 0, getWidth(), height);
            super.paint(g);
        }
    };
    
    /**
     * This Timer function will start the animation of Login window 
    */
    public Timer timer = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            height += 4;
            if (height >= dialog.getHeight()) {
                timer.stop();
                trayIcons.setTrayIcon("/images/tray_disabled.png");
                trayIcons.trayIcon.addMouseListener(minMaxListener);
                new Thread(getIdleTimeThread).start();
                System.out.println("ss"+System.getProperty("java.ext.dirs"));
                
            }
            dialog.setLocation(screenRect.width - (dialog.getWidth() + 1),
                    screenRect.height - height);
            dialog.repaint();
        }
    });
    
    /**
     * This timer function will start the animation of About Dialog window
    */
    public Timer timerAbout = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            height3 += 4;
            if (height3 >= abt.getHeight()) {
                timerAbout.stop();
            }
            abt.setLocation(screenRect.width - (abt.getWidth() + 1),
                    screenRect.height - height3);
            abt.repaint();
        }
    });
    
    /**
     * This Timer window will start the animation of Minimize the TimeSheet Window 
    */
    public Timer timer3 = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            height -= 4;
            if (height <= 0) {
                timer3.stop();
                dialog.setVisible(false);
            }
            dialog.setLocation(screenRect.width - (dialog.getWidth() + 1),
                    screenRect.height - height);
            dialog.repaint();
        }
    });
    
    /**
     *  This timer function  will start the animation of TaskDialog window  
    */
    public Timer timer4 = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            height2 += 4;
            if (height2 >= taskDialog.getHeight()) {
                timer4.stop();
            }
            taskDialog.setLocation(screenRect.width - (taskDialog.getWidth() + 1),
                    screenRect.height - height2);
            taskDialog.repaint();

        }
    });
    
    

    /**
     * This function will get the visibility of the frame 
    */
    public static boolean getVisibility() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(jLbl);
        return topFrame.isVisible();
    }
    /**
     * This function will set the task-bar icon 
    */
    public void setTaskbarIcon() {
        icons.add(TrayIconUtility.createImage("/images/taskbar.png", "taskbar"));
        //    icons.add(TrayIconUtility.createImage("/dx/timesheet/taskbar.png", "tray icon"));
        dialog.setIconImages(icons);
    }
    /**
     *This function will get the IP address of the system 
    */
    public String getIpAddress(URL url) throws MalformedURLException, IOException, ConnectException {
        URL myIP = url;//new URL(ROUTER_IP_DOMAIN);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(myIP.openStream()));
        return in.readLine();
    }
    /**
     * This function will get the MAC address of the system
    */
    void getMacAddress() throws UnknownHostException, SocketException {
        InetAddress address = InetAddress.getLocalHost();
        NetworkInterface nwi = NetworkInterface.getByInetAddress(address);
        byte mac[] = nwi.getHardwareAddress();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
            sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
        }
        //     System.out.println(sb.toString());
        mac_address = sb.toString();
    }
    
    /**
     *This function will empty the variable like
     * userid, imgURL.... and also change the tray icon image. 
    */
    public void onSignOut() throws SQLException {
        //    dbHandler.deleteRecord();
        main_userid = "";
        main_name = "";
        main_imgurl = "";
        main_timeid = "";
        main_username = "";
        main_password = "";
        ReadXml.isPresent = false;
        try{
            ChattingWindow.connection.disconnect();
        }catch(Exception e){
            System.out.println("connection can not be created");
        }
        
        trayIcons.updateTrayIcon("/images/tray_disabled.png");
        panel.lblStatus1.setText("Sign in");
    }
    
    /**
     * This function is used for Update the Time Sheet at server side but
     * temporary this function is closed in this project.
    */
    Runnable updateTimesheetThread = new Runnable() {
        @Override
        public void run() {
//            try {
//                //sendDataForTimesheet(userId, task_id, "Progress", "1", "Progress");
//            } catch (MalformedURLException ex) {
//                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
    };
    
    /**
     * This runnable Thread is used to complete refresh the list in given time-sheet
    */
    Runnable refreshListThread = new Runnable() {
        @Override
        public void run() {
            try {
                refresh();
            } catch (SQLException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    /**
     *This Runnable thread is used to get user idle time. 
     * we can call this function from Win32Idle class.
    */
    Runnable getIdleTimeThread = new Runnable() {
        @Override
        public void run() {
            win.getIdleTime();

        }
    };
    
    /**
     *This Runnable thread is used for Screen Shot tracking from Win32IdleTime
    */
    Runnable tracking_thread = new Runnable() {
        @Override
        public void run() {
            win.trackTime(usr);
        }
    };
    
    /**
     * This Runnable thread will call the readOnHoldTask which perform :
     * Hold, Testing and ToDo task Dialog operation. and
     * this function  count the total number of task in TimeSheet
     * and when we add task to user from server, and delete the task from server 
     * taskDialog box was open like:- New Task added or Task List Updated.
    *  This function also check Pause and auto-pause status of user. 
    */ 
    Runnable refreshToReadTaskLength = new Runnable() {
        @Override
        public void run() {
            try {
                
                if(oneTime){
                    getAllChatLength(userId);
                    oneTime=false;
                }
                
                System.out.println("poopopopop");
                System.out.println(ChattingWindow.connection==null);

                getAllChat(userId);
                readOnHoldTask(main_username, main_password);
                length = readxml.readTaskLength(main_username, main_password);
                if (firstLogin) {
                    System.out.println("Task length is:" + main_tasklength);
                }
                if (length > main_tasklength) {
                    System.out.println("Task Added");
                    taskUpdated = true;
                    taskDialog.setMsg("New Task!");
                    timer4.setInitialDelay(0);
                    timer4.setDelay(10);
                    timer4.start();
                    taskDialog.setVisible(true);
                    main_tasklength = length;
                } else if (length == main_tasklength) {
                    System.out.println("No task added");
                    taskUpdated = false;
                } else if (length < main_tasklength) {
                    taskUpdated = true;
                    main_tasklength = length;
                    System.out.println("Task Removed");
                    taskDialog.setMsg("Task list updated!");
                    timer4.setInitialDelay(0);
                    timer4.setDelay(10);
                    timer4.start();
                    taskDialog.setVisible(true);
                }

                firstLogin = false;

                System.out.println("track_net_thread alive is>>" + track_net_thread.isAlive());
                //       showInfoDialog("track_net_thread alive "+track_net_thread.isAlive());
                if (win.getPauseStatus()) {
                    if (Win32IdleTime.auto_pause) {                                       //if task is auto paused
                            colorFlag=true;
                    }        
                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<win.getpausestatus is true>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    try {
                        refresh();
                        if (!worker.isShutdown()) {
                            System.out.println("Worker thread stopped!");
                            System.out.println("\n\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Worker thread stopped!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                   
                            worker.shutdownNow();
                        }
                        if (!screeShotListener.isShutdown()) {
                            System.out.println("Screnshots disabled!");
                            screeShotListener.shutdownNow();
                        }
                        win.setPauseStatus(false);                                 //set pause status to false

                        if (Win32IdleTime.auto_pause) {                                       //if task is auto paused
                            
                            showMainDialog();
                            Win32IdleTime.auto_pause = false;
                            //   win.setWorkingStatus(false);
                            showInfoDialog("<html><p width=\"155px\">" + "One of your task has been paused. Please resume paused task!" + "</p></html>");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } catch (MalformedURLException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    /**
     *  This function is used to create ScreenShot Directory and get the basic details like mac address, ip address,
     *  connect to database, set Taskbar icon, etc
     *  Basically this function is called the Main timesheet Login window.
     */
    public void launchTimesheet() {
        try {
            hd.createScreenshotDirectory();
            System.out.println("NamanScreenShot");
            dbHandler.connect();
            
            panel.lblAccount.setVisible(false);
            
            setTaskbarIcon();
            dialog.setTitle("Timesheet   ");
            cpu_model = hd.processorModel();
            cpu_model=cpu_model.replaceAll(" ", "%20");
            sys_ram = hd.getRamSize();
            os_name = hd.getOsName();
            sys_hdd = hd.getHarddisk();
            setUnderlineParameters();
            raisedBorder = BorderFactory.createLineBorder(Color.yellow, 2);
            dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            dialog.setUndecorated(true);
            dialog.setAlwaysOnTop(true);
            dialog.setShape(new RoundRectangle2D.Double(0, 5, 290, 460, 15, 15));
            JComponent cont = (JComponent) dialog.getContentPane();
            cont.setBorder(raisedBorder);
            int width = panelDetails.getWidth();
            int height = panelDetails.getHeight();
            task_reader_thread = Executors.newSingleThreadScheduledExecutor();
            screeShotListener =  Executors.newSingleThreadScheduledExecutor();
            system_name = InetAddress.getLocalHost().getHostName();
          

            panel.setPreferredSize(panelDimension);
            dialog.setContentPane(panel);
            panel.txtUserName.setText(userName);
           
            stask = new TaskWithSubtaskDialog(dialog, true);
            reviewDlg = new ReviewDialog(dialog, true);
            reviewDlg.setDialog(dialog);
            
            selectChatUser = new SelectChatUser(dialog, true);
            //selectChatUser.setDialog(dialog);
            
            in2 = new InputDialog(dialog, true);
            pauseDialog = new PauseOptionDialog(dialog, true);
            cd = new ConfirmDialog(dialog, true);
            errorDescription = new ErrorDescription(dialog, true);
            signOut = new SignOut(dialog,true);
            onHoldMsg = new OnHoldMsg(dialog,true);
            ed = new ExtendDeadlineDialog(dialog, true);
            ed.setDialog(dialog);
            infD = new LoaderDialog(dialog, true);
            panel.btnLogin.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    loginListener();
                }
            });

            lblRefresh.addMouseListener(minMaxListener);
            ForgotPwdPanel.btnSubmit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    forgotPwdListener();
                }
            });
            TrayIconUtility.aboutItem.addActionListener(tray_icons_actionlistener);
            TrayIconUtility.refresh.addActionListener(tray_icons_actionlistener);
            TrayIconUtility.exit.addActionListener(tray_icons_actionlistener);
            TrayIconUtility.show.addActionListener(tray_icons_actionlistener);
            TrayIconUtility.signout.addActionListener(tray_icons_actionlistener);
            setMouseListeners();                            // set the mouselisteners for elements
            dialog.pack();
            dialog.setVisible(true);
            timer.setInitialDelay(0);
            timer.setDelay(10);
            timer.start();


        } catch (ClassNotFoundException | SQLException | IOException ex) {
            hideLoaderDialog();
            showInfoDialog("Connection error nam! Try again.");
            hd.errorDescription("ErrorDescription1", ex);
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SigarException ex) {
            hideLoaderDialog();
            hd.errorDescription("SigarException", ex);
            showInfoDialog("Connection error! Try again.");
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * this function is used to hide the Loader
    */
    public void hideLoaderDialog() {
     if (infD.isVisible()) {
            infD.setVisible(false);
        }
    }
    
    /**
     * this ActionListener is used to perform basic action like sign-out, 
     * about-item at TimeSheet tray icon
    */
    ActionListener tray_icons_actionlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == TrayIconUtility.signout) {
                if (taskUpdated) {
                    showMainDialog();
                    showInfoDialog("Please click dialog below!");
                } else {
                     signOut.setLocationRelativeTo(dialog);
                    signOut.setVisible(true);

                    boolean sign = signOut.flagResult();
                    System.out.println("sssssssss+++"+sign);
                    if(sign==true){
                        signOutBlock("");
    //                
                    }
                    else{}
                }
            }
            if (e.getSource() == TrayIconUtility.aboutItem) {
                if (taskUpdated) {
                    showMainDialog();
                    showInfoDialog("Please click dialog below!");
                } else {
                    timerAbout.setInitialDelay(0);
                    timerAbout.setDelay(10);
                    timerAbout.start();
                    abt.setVisible(true);
                }
            }
            if (e.getSource() == TrayIconUtility.refresh) {
                if (taskUpdated) {
                    showMainDialog();
                    showInfoDialog("Please click dialog below!");
                } else {
                    showMainDialog();
                    new BackgroundWorker().execute();
                    showLoaderDialog2();
                }
            }
            if (e.getSource() == TrayIconUtility.exit) {
                if (taskUpdated) {
                    showMainDialog();
                    showInfoDialog("Please click dialog below!");
                } else {
                    if(ChattingWindow.counting>0){
                        showMainDialog();
                        showInfoDialog("Close the chat window!");
                    }else
                    if (!working_task) {
                        signOutBlock("exit");
                        exitApp();
                    } else {
                        showMainDialog();
                        showInfoDialog("Please stop working task first!");
                    }
                } 
            }
            if (e.getSource() == TrayIconUtility.show) {
                showMainDialog();
            }
            if (e.getSource() == TrayIconUtility.signout) {
            }
            if (e.getSource() == TrayIconUtility.signout) {
            }
            if (e.getSource() == TrayIconUtility.signout) {
            }
            if (e.getSource() == TrayIconUtility.signout) {
            }

        }
    };
     /**
     * this function is used for when user try to Login in TimeSheet
     * this function detect whether the user is first time login or regular login.
     * if the user is first time then it add the hardware from server side and wait for response
     * and for regular user, login operation is done without any problem. 
    */
    public void doInLogin() throws IOException, SQLException {
        
        k+=1;
        double i=random.nextDouble()+random.nextDouble();
        if (firstLogin) {
            System.out.println(firstLogin + "block called");
            System.out.println(Config.HTTP+Config.DOMAIN + "users/url/" + user_name + "/" + password + "/" 
                    + system_name + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" 
                    + os_name + "/" + sys_hdd+"?"+k+"naman"+i);
            sendLoginDetailsInUrl(user_name, Config.HTTP+Config.DOMAIN + "users/url/" + user_name + "/" + password + "/" + system_name
                    + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" + os_name + "/" + sys_hdd+"?"+k+"naman"+i);
        }
        if (!firstLogin) {
            //System.out.println(firstLogin+"block called");

            sendLoginDetailsInUrl(user_name,Config.HTTP+Config.DOMAIN + "users/url1/" + user_name + "/" + password + "/" + system_name 
                    + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" + os_name + "/" + sys_hdd+"?"+k+"naman"+i);
            // System.out.println(DOMAIN + "/users/url1/" + user_name + "/" + password + "/" + system_name + "/" + mac_address + "/" + router_ip);

        }
    }

    /**
     * This function is used for Login in TimeSheet to get the user name and password and router IP.
    */
    public void loginListener() {
        interruptDialog = false;
        firstLogin = true;
        System.out.println(firstLogin+" ");
        try {
            try {
                getMacAddress();
            } catch (UnknownHostException | SocketException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            user_name = panel.txtUserName.getText();
            password = panel.txtPassword.getText();
                
            if (user_name.equals("") || password.equals("") ) {
                showInfoDialog("Empty Fields");
            } else {
                // password = encrypt.encrypt(password);
                main_password = password;
                System.out.println("Encrypted Password " + password);
                router_ip = "";
                try {
                    if (router_ip == null || router_ip.equals("")) {
                        router_ip = getIpAddress(new URL(ROUTER_IP_DOMAIN2));
                        if (router_ip.length() > 17) {
                            System.out.println("IP not correct");
                            router_ip = getIpAddress(new URL(ROUTER_IP_DOMAIN2));
                        }

                    }
                } catch (IOException ex) {
                    router_ip = "127.0.0.1";
                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    System.out.println(router_ip);
                    new AnswerWorker().execute();
                    showMainDialog();
                    showLoaderDialog();
                } catch (ArrayIndexOutOfBoundsException ex) {
                    hideLoaderDialog();
                    showInfoDialog("Invalid gmail id!");
                } finally {
                    System.out.println("Finally  called");
                    System.out.println("");
                }
            }
        } catch (HeadlessException ex) {
            hideLoaderDialog();
            hd.errorDescription("ErrorDescription2", ex);
            showInfoDialog("Connection error! Try again.");
        }
    }
    /**
     *this function is used for forgot the user password
     * user enter the email without enter the @gmail.com signature
    */
    public void forgotPwdListener() {
        try {
            String user = ForgotPwdPanel.txtEmail.getText();
            if (user.equals("")) {
                server_response = "";
                showInfoDialog("Empty username");
            } else if (user.contains(" ")) {
                showInfoDialog("Wrong  username");
            } else {
                if (user.contains("@")) {
                    String[] part = user.split("@");
                    String user1 = part[0];
                    String user2 = part[1];
                    //                         System.out.println(user1);
                    if (!user1.equals("") && "gmail.com".equals(user2) && !user1.contains(" ")) {
                        forgotton_user = user1;
                        new ForgotPwdWorker().execute();
                        showLoaderDialog4();
                    } else {
                        server_response = "";
                        showInfoDialog("Enter valid gmail id!");
                    }
                } else {
                    forgotton_user = user;
                    new ForgotPwdWorker().execute();
                    showLoaderDialog4();
                }
            }
            //                     System.out.println(server_response);
            if ("ok".equals(server_response)) {
                hideLoaderDialog();
                showInfoDialog("Password sent to your email!");
                dialog.setContentPane(panel);
                panelForgotPwd.setVisible(false);
                ForgotPwdPanel.txtEmail.setText("");
                panel.setVisible(true);
                dialog.repaint();
            } else if ("sorry".equals(server_response)) {
                hideLoaderDialog();
                showInfoDialog("Invalid user! Try again.");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            hideLoaderDialog();
            showInfoDialog("Enter valid id!");
        }
    }
    /**
     *this function is used for set the Image in Time-sheet
    */
    public void setUserPic(String img) throws IOException, MalformedURLException {
        TaskPanel.lblPic.setSize(34, 34);
        int width1 = TaskPanel.lblPic.getWidth();
        int height1 = TaskPanel.lblPic.getHeight();
        System.out.println(img);
        URL imgurl = new URL(img);
        System.out.println("imgurl is>> " + imgurl);
        
        user_image = ImageIO.read(imgurl);
        System.out.println(user_image);
        Image resizedImage = user_image.getScaledInstance(TaskPanel.lblPic.getWidth(), TaskPanel.lblPic.getHeight(), Image.SCALE_SMOOTH);
        TaskPanel.lblPic.setIcon(new ImageIcon(resizedImage));
    }
    /**
     *This function is used for UnderLine Parameter like in Forgot Password, Sign-Out..etc
    */
    public void setUnderlineParameters() {
        panel.lblForgotPwd.setText("<HTML><U>Forgot Password?<U><HTML>");
        panel.lblAccount.setText("<HTML><U>Don't have an account?<U><HTML>");
        TaskPanel.lblSignOut2.setText("<HTML><U>Sign Out<U><HTML>");
        TaskPanel.lblRefreshMain.setText("<HTML><U>Refresh<U><HTML>");
        panel.lblDx.setText("<HTML><U>"+Config.URL+"<U><HTML>");
        WaitingPanel.lblDx.setText("<HTML><U>"+Config.URL+"<U><HTML>");
        TaskPanel.lblDx.setText("<HTML><U>"+Config.URL+"<U><HTML>");
        ForgotPwdPanel.lblDx.setText("<HTML><U>"+Config.URL+"<U><HTML>");
        ForgotPwdPanel.lblGoLogin.setText("<HTML><U>Go to login<U><HTML>");
        TaskPanel.chat.setText("<HTML><U>Chat<U><HTML>");
    }
    //Run Background task here 
    /**
     * this function will add and set MouseListener, KeyListener 
    */
    public void setMouseListeners() {
        TaskPanel.chat.addMouseListener(showHandCursor);
        TaskPanel.lblSignOut2.addMouseListener(showHandCursor);
        TaskPanel.lblRefreshMain.addMouseListener(showHandCursor);
        panel.lblForgotPwd.addMouseListener(showHandCursor);
        panel.lblAccount.addMouseListener(showHandCursor);
        panel.lblMinimize.addMouseListener(minMaxListener);
        panel.lblClose.addMouseListener(minMaxListener);
        TaskPanel.lblMinimize3.addMouseListener(minMaxListener);
        ForgotPwdPanel.lblMinimize2.addMouseListener(minMaxListener);
        TaskPanel.lblClose3.addMouseListener(minMaxListener);
        lblMinimize2.addMouseListener(minMaxListener);
        lblClose2.addMouseListener(minMaxListener);
        ForgotPwdPanel.lblClose2.addMouseListener(minMaxListener);
        panel.lblDx.addMouseListener(showHandCursor);
        TaskPanel.lblDx.addMouseListener(showHandCursor);
        WaitingPanel.lblDx.addMouseListener(showHandCursor);
        ForgotPwdPanel.lblDx.addMouseListener(showHandCursor);
        ForgotPwdPanel.lblGoLogin.addMouseListener(showHandCursor);
        TaskAddedDialog.panelTaskAdded.addMouseListener(showHandCursor);
        TaskAddedDialog.lblInfo.addMouseListener(showHandCursor);
        panel.txtUserName.addKeyListener(kl);
        panel.txtPassword.addKeyListener(kl);
        panel.btnLogin.addKeyListener(kl);
        ForgotPwdPanel.btnSubmit.addKeyListener(kl);
        ForgotPwdPanel.txtEmail.addKeyListener(kl);
    }
    
    /**
    * This function is used for getting the response from server
    */
        public StringBuilder getStreamResponse(URL url) throws IOException {
        InputStream in;
        URLConnection con = url.openConnection();
        System.out.println("Naman:URLConnection- "+con);
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
     * This swing worker class is used for waiting the response from server
     * if the server response is Waiting then user wait for hardware conformation
     * otherwise the task panel dialog will open. 
    */
    class AnswerWorker extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {

            doInLogin();
            return 0;
        }

        @Override
        protected void done() {
            try {
                if ("sorry".equals(server_response)) {
                    hideLoaderDialog();
                    showInfoDialog("Invalid credentals! Try again.");

                    //               LoginPanel.lblProgress.setVisible(false);
                    panel.lblStatus1.setText("Sign in");
                } else if (server_response.equals("Waiting") || server_response.equals("Waiting..")) {
                    //         System.out.println("waiting");
                    panel.setVisible(false);
                    panelDetails.setVisible(true);
                    dialog.setContentPane(panelDetails);
                    dialog.repaint();
                    hideLoaderDialog();
                } else {
                    //System.out.println("in else"+firstLogin);
                    main_imgurl = imgUrl;
                    setUserPic(main_imgurl);
                    firstLogin = true;
                    readPlayPauseTask(user_name, main_password);
                    firstLogin = false;
                    worker = Executors.newSingleThreadScheduledExecutor();
                    screeShotListener = Executors.newSingleThreadScheduledExecutor();
                    refresh_worker = Executors.newSingleThreadScheduledExecutor();
                    task_reader_thread = Executors.newSingleThreadScheduledExecutor();
                    task_reader_thread.scheduleWithFixedDelay(refreshToReadTaskLength, 10, 20, TimeUnit.SECONDS);
                    main_userid = userId;
                    main_name = name;
                    main_username = user_name;
                    String[] u = main_username.split("@");
                    usr = u[0];
                    System.out.println("User is>> " + usr);


                    panel.setVisible(false);
                    panelDetails.setVisible(false);
                    taskPanel.setVisible(true);
                    dialog.setContentPane(taskPanel);
                    int width1 = TaskPanel.lblPic.getWidth();
                    int height1 = TaskPanel.lblPic.getHeight();
                    trayIcons.updateTrayIcon("/images/tray_enabled.png");
                    System.out.print(main_imgurl);

                    trayIcons.enableRefresh(true);
                    trayIcons.enableSignOut(true);
                    hideLoaderDialog();
                    dialog.repaint();
                    TrackPacket.stop_track = false;                                   //start tracknet thread here
                    track_net_thread = new Thread(trc.trackNet);
                    track_net_thread.start();
                    
                }
                    
            } catch (Exception e) {
                hideLoaderDialog();
                hd.errorDescription("ErrorDescription3", e);
                showInfoDialog("Connection error!try again!");
                e.printStackTrace();
            }
        }
    }
    /**
     * This Swing worker class is used for hide confirm Dialog(cd), Input Dialog(in2),Pause OptionDialog(pauseDialog)
    */
    class BackgroundWorker extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            if (cd.isVisible()) {
                cd.setVisible(false);
                interruptDialog = true;
            }
            if (in2.isVisible()) {
                in2.setVisible(false);
            }
            if (pauseDialog.isVisible()) {
                pauseDialog.setVisible(false);
            }

            refresh();
            return 0;
        }

        @Override
        protected void done() {
            hideLoaderDialog();
        }
    }
    /**
     * This Swing worker class is used for request forgot password.
    */
    class ForgotPwdWorker extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            requestPassword(forgotton_user);
            return 0;
        }

        @Override
        protected void done() {
            hideLoaderDialog();
        }
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
     * This function perform Hold,Testing, and ToDo taskDialog box in it.
     * This function performed, automatically hold the task in time-sheet, when admin hold the task from server,
     * when user perform Testing and TODO functionality then this function will call the taskDialog class and open 
     * task Dialog box like:- Hold Task, Testing, ToDo.
    */
    public void readOnHoldTask(final String username, final String pwd) {
        
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                
                
                try{
                    k+=1;
                    taskLength=0;
                    URL xmlUrl = null;
                    InputStream in=null;
                    String status2=""; 
                    String holdStatus="";
                    String assignedTo="";
                   
                    double i=random.nextDouble()+random.nextDouble();
                    xmlUrl = new URL(Config.HTTP+Config.DOMAIN + "users/getXml/" + username + "/" + pwd+"?"+k+"naman"+i);
                   
                    System.out.println("readOnHoldTaskURL"+xmlUrl);
                    StringBuilder responseString=null;
                    responseString = getStreamResponse(xmlUrl);
                    server_response = responseString.toString();
                    System.out.println("readOnHoldTaskURL server_response ---->>>>"+server_response);
                    
                    if ("sorry".equals(server_response)) {
                          holdTask=0;
                          totalNoOfHoldTask=0;
                          unHoldTask=0;
                          totalNoOfUnHoldTask=0;
                          todoTask=0;
                          totalNoOfTodoTask=0;
                          testingTask=0;
                          totalNoOfTestingTask=0;
                          taskLength=0;
                          totalTasks=0;
                          totalLength=0;
                          flagTrue=false;
                    } else {
                    if(!(flagTrue)){
                        try {
                            totalLength = new ReadXml().readTaskLength(username, pwd);
                        } catch (IOException ex) {
                            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                        }   
                     }
                        
                    in = xmlUrl.openStream();
                      
                    Document doc = parse(in);
                    doc.getDocumentElement().normalize();
                    NodeList listOfCodes = doc.getElementsByTagName("tasks");
                    totalTasks = listOfCodes.getLength();
                    System.out.println("NamanTotalTasks---->>>>><<<<< : "+totalTasks);
                    
                    for (int temp = 0; temp < listOfCodes.getLength(); temp++) {
                        Node nNode = listOfCodes.item(temp);
                            
                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;
                            status2 = getElements(eElement, "status");
                            holdStatus = getElements(eElement, "hold");
                            assignedTo = getElements(eElement,"assignto");
                            if(status2.equals("onHold")){
                                totalNoOfHoldTask += 1;
                            }
                            if(status2.equals("ToDo") && !(assignedTo.equals("1")) ){
                                totalNoOfTodoTask += 1;
                                System.out.println("\n\n\n---------------->>>>>>>>>>>>>>>>--------------------\n\n\n");
                            }
                            if(status2.equals("Testing") && assignedTo.equals("1") ){
                                totalNoOfTestingTask += 1;
                            }
                            if(holdStatus.equals("2")){
                                totalNoOfUnHoldTask += 1;
                            }
                        }
                    }
                    
                    /**
                    * This condition is used for checking the TestingTask
                    */
                    
                     if(totalNoOfTestingTask > testingTask && (totalLength==totalTasks)){
                        testingTask=totalNoOfTestingTask;
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<todoTask refresh condition True>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                            if(flagTrue){
                            taskDialog.setMsg("Testing!");
                            timer4.setInitialDelay(0);
                            timer4.setDelay(10);
                            timer4.start();
                            taskDialog.setVisible(true);
                            }
                        
                    }
                    else{
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<todoTask refresh condition False>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                        
                        testingTask=totalNoOfTestingTask;
                        totalLength=totalTasks;
                    }
                    totalNoOfTestingTask=0;
                    totalTasks=0;
                    System.out.println("\n\n\n>>>>>>>>status2status2status2status2-------->>>>>>>>"+status2);

                    /**
                    * This condition is used for checking the TODO Task
                    */
                    if(totalNoOfTodoTask > todoTask){

                        todoTask=totalNoOfTodoTask;
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<todoTask refresh condition True>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                        
                            if(flagTrue){
                            taskDialog.setMsg("ToDo!");
                            timer4.setInitialDelay(0);
                            timer4.setDelay(10);
                            timer4.start();
                            taskDialog.setVisible(true);
                            }
                       
                    }
                    else{
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<todoTask refresh condition False>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                        
                        todoTask=totalNoOfTodoTask;
                    }
                    totalNoOfTodoTask=0;
                    System.out.println("\n\n\n>>>>>>>>status2status2status2status2-------->>>>>>>>"+status2);

                    /**
                    * This condition is used for checking the HoldTask
                    */
                    if(totalNoOfHoldTask > holdTask){

                        holdTask=totalNoOfHoldTask;
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<HOLD refresh condition True>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                       
                            if(flagTrue){
                            taskDialog.setMsg("Hold from Admin! ");
                            timer4.setInitialDelay(0);
                            timer4.setDelay(10);
                            timer4.start();
                            taskDialog.setVisible(true);
                            }
                        
                    }
                    else{
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<HOLD refresh condition False>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                        
                        holdTask=totalNoOfHoldTask;
                    }
                    totalNoOfHoldTask=0;
                    System.out.println("\n\n\n>>>>>>>>status2status2status2status2-------->>>>>>>>"+status2);

                    /**
                    * This condition is used for checking the UnHoldTask
                    */
                    if(totalNoOfUnHoldTask > unHoldTask){

                        unHoldTask=totalNoOfUnHoldTask;
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<UNHOLD refresh condition True>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                       
                                if(flagTrue){
                                taskDialog.setMsg("UnHold from Admin!");
                                timer4.setInitialDelay(0);
                                timer4.setDelay(10);
                                timer4.start();
                                taskDialog.setVisible(true);
                            }
                      
                    }
                    else{
                        System.out.println("\n\n\n<<<<<<<<<<<<<<<HOLD refresh condition False>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
                        
                        unHoldTask=totalNoOfUnHoldTask;
                    }
                        totalNoOfUnHoldTask=0;
                        flagTrue=true;

                        System.out.println("\n\n\n>>>>>>>>status2status2status2status2-------->>>>>>>>"+status2);
                    }
                    status2=""; 
                    holdStatus="";
                    assignedTo="";
            }catch(IOException ex){
              Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            }
        });
        thread.start();
       
    }
             
    /**
    * This function is used for read the XML of the user and maintain the Task panel List.   
    */              

    public void readPlayPauseTask(final String username, final String pwd)  {
        readxml.getUserList();
        //read users from xml for review
        if(hold){
            
              Thread chattingWindow=new Thread(new Runnable() {

                @Override
                public void run() {
                    ChattingWindow.createXMPPConnection();
                    
                    System.out.println(username+"<<<<<<<<>>>>>>>>>"+pwd+""+userId);
                    chatUserId=userId;
                    chatUserPassword=pwd; 
                    String chatName[] = chatUserName.split(" ");
                    chatUserName= chatName[0];
                    System.out.println(chatUserName+"<<<<<<<<>>>>>>>>>"+chatUserPassword);
                    readOnHoldTask(username,pwd);
                    ChattingWindow.addXMPPAccount(chatUserName, "welcome");
                }
            });
            chattingWindow.start();
            
            
            hold=false;
        }else{
            System.out.println("\n\n\n<<<<<<<<<<<<<<<HOLD FALSE>>>>>>>>>>>>>>>>>>>>>\n\n\n\n");
        }
        
       
        
        System.out.println("Naman is here");
        TaskPanel.panelforScrollPane.setLayout(new BorderLayout());
        TaskSubPanel.panelSubTaskContainer = new JPanel(new GridBagLayout());
        TaskSubPanel.panelSubTaskContainer.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        JPanel scrollpanel = new JPanel();
        scrollpanel.setBackground(Color.white);
        TaskSubPanel.panelSubTaskContainer.add(scrollpanel, gbc);
        JScrollPane sPane = new JScrollPane(TaskSubPanel.panelSubTaskContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sPane.getVerticalScrollBar().setUnitIncrement(6);
        sPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        sPane.setBorder(BorderFactory.createEmptyBorder());
        working_task = false;
        task_paused = false;
        TaskPanel.panelforScrollPane.add(sPane);
        Long currentTimeStamp = hd.getCurrentTimestamp();
        InputStream in = null;
        try {
            k+=1;
            double i=random.nextDouble()+random.nextDouble();
            
            URL xmlUrl = new URL(Config.HTTP+Config.DOMAIN + "users/getXml/" + username + "/" + main_password+"?"+k+"naman"+i);
            System.out.println("NamanxmlURL"+xmlUrl);
           
            StringBuilder responseString = getStreamResponse(xmlUrl);
            server_response = responseString.toString();
            if ("sorry".equals(server_response)) {
                dialog.repaint();
                JPanel panelNoTask = new JPanel();
                panel.setPreferredSize(new Dimension(100, 60));
                JLabel lbl = new JLabel("                 No Tasks Found");
                
                lbl.setVisible(true);
                panel.add(lbl);
              
                TaskPanel.panelforScrollPane.add(lbl);
                TaskPanel.panelforScrollPane.validate();
                TaskPanel.panelforScrollPane.repaint();
                main_tasklength = 0;
                showInfoDialog("No task found!");
            } else {
                in = xmlUrl.openStream();
                Document doc = parse(in);
                doc.getDocumentElement().normalize();
                String uri = doc.getDocumentURI();

                NodeList listOfCodes = doc.getElementsByTagName("tasks");
                int totalTasks = listOfCodes.getLength();
                System.out.println("NamanTotalTasks : "+totalTasks);
                main_tasklength = totalTasks;
                status_array = new String[totalTasks];
                int tLength = 1;
                for (int temp = 0; temp < listOfCodes.getLength(); temp++) {
                    final JPanel panel1;
                    panel1 = new PanelPlayPause();
                    //     panel.setBorder(new MatteBorder(0, 0, 1, 0, Color.GRAY));
                    GridBagConstraints gbc1 = new GridBagConstraints();

                    gbc1.gridwidth = GridBagConstraints.REMAINDER;
                    gbc1.weightx = 1;
                    gbc1.fill = GridBagConstraints.HORIZONTAL;
                    TaskSubPanel.panelSubTaskContainer.add(panel1, gbc1, countPanels);
                    countPanels += 1;
                    Node nNode = listOfCodes.item(temp);

                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        String panelid = getElements(eElement, "task_id");
                        panel1.setName(panelid);
                        String client_code1 = getElements(eElement, "client_code");
                        String project_code1 = getElements(eElement, "project_code");
                        String task = getElements(eElement, "task");
                        task = task.replaceAll("\\s+", " ");
                        start_date = getElements(eElement, "task_start_date");
                        System.out.println("namanStartdate - "+start_date);
                        String current_time = getElements(eElement, "time");
                        System.out.println(current_time);
                        String end_date = getElements(eElement, "task_end_date");
                        System.out.println("namanStartdate - "+end_date);
                        int end,end1;
                        if(start_date.equals("") || end_date.equals("")){
                            currentDate="N/A";
                            System.out.println("\n\ncurrentDate\n\n");
                        }
                        else{
                            end = Integer.parseInt(end_date);
                            end1 = Integer.parseInt(start_date);
                            System.out.println("namanEnddate - "+end_date);
                            currentDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(end * 1000L));
                            currentDate1 = new SimpleDateFormat("dd-MMM-yyyy").format(new Date(end1 * 1000L));
                            System.out.println("namanEnddate - "+currentDate);
                            nDate.add(currentDate +"\n\n\n");
                            lTime = new SimpleDateFormat("h:mm a").format(new Date(end * 1000L));
                            nTime.add(lTime + "\n\n\n");
                        }
                        
                        String tracking = getElements(eElement, "tracking");
                        String screenshot = getElements(eElement, "screenshot");
                        String priority = getElements(eElement, "priority");
                        final String oId = getElements(eElement, "old_task_id");
                        final String toDo_status = getElements(eElement,"toDo_status");
                        final String todo_task_id = getElements(eElement,"todo_task_id");
                        PanelPlayPause.TodoId.setText(todo_task_id);
                        final String toDo_description = getElements(eElement,"toDo_description");
                        final String assigned_by = getElements(eElement, "assigned_by");
                        String status2 = getElements(eElement, "status");
                        final String status3=status2;
                        final String assignedby = getElements(eElement, "assigned_by");
                        final String holdReason = getElements(eElement, "hold_reason");
                        final String assignedto = getElements(eElement,"assignto");
                        System.out.println("NamanAssigned_by - "+assignedby);
                        System.out.println("NamanAssigned_To - "+assignedto);
                        
                        System.out.println("Naman-Status- "+status2);
                        if(status2.equals("Progress")){
                            status2="Working";
                        }
                         /**
                        * This condition is used for checking First Time Login, when the PMS is Start.
                        * after login,when we sign-out the Time Sheet and then again login the time-sheet this condition will false.
                        * Tracking FirstTime will only true when we start the PMS software
                        * if the condition will true this mean one of your task is in working or in pause condition,
                        * then that task will be in stop. by calling StopTimesheet Function.
                        */
                        if((status2.equals("Progress") || status2.equals("Working") || status2.equals("Pause") ) && trackingFirstTime ){
                            firstTimeUser="true";
                            task_id=panelid;
                            System.out.println("task_id - "+task_id);
                            System.out.println("user_id - "+userId);
                            stopTimesheet(userId, task_id);
                        }
                        System.out.println("Length--"+tLength);
                            
                        if(tLength == listOfCodes.getLength()){
                            
                            trackingFirstTime=false;
                            firstTimeUser="false";
                            System.out.println("trackingFirstTime--"+trackingFirstTime);
                            System.out.println("firstTimeUser--"+firstTimeUser);
                        }
                        tLength+=1;
                        
                        String subtasks = getElements(eElement, "subtasks");
                        List list = new ArrayList();
                        if (!"".equals(subtasks)) {
                            NodeList itemlist = eElement.getElementsByTagName("items");
                            int subtasklength = itemlist.getLength();
                            System.out.println("subtasklength is" + subtasklength);
                            for (int temp1 = 0; temp1 < subtasklength; temp1++) {
                                Node nNode1 = itemlist.item(temp1);
                                if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eElement1 = (Element) nNode1;
                                    String itemid = getElements(eElement1, "itemid");
                                    System.out.println("itemid is" + itemid);

                                    String item = getElements(eElement1, "item");
                                    System.out.println("item is" + item);
                                    list.add(itemid + "^" + item);
                                    String item_status = getElements(eElement1, "itemstatus");
                                    System.out.println("itemstatus is" + item_status);

                                }
                                stask.subtask_map.put(panelid, list);
                            }

                        }

                        if(end_date.equals("") || start_date.equals("")){
                            PanelPlayPause.taskProgressbar.setVisible(false);
                            PanelPlayPause.jLabel1.setVisible(false);
                            PanelPlayPause.lblDeadlineDate.setText("N/A");
                            PanelPlayPause.lblDeadlineDate.setVisible(false);
                            PanelPlayPause.lblExtentDeadline.setVisible(false);
                        }
                        
                        else{
                        Double a = hd.getTaskProgress(start_date, end_date, current_time);
                        PanelPlayPause.lblDeadlineDate.setText(hd.printDate(end_date) +"  "+lTime);
                        
                        /**
                        * This condition is used for checking the Progress percentage
                        */
                        if (a.intValue() <= 90) {
                            hd.setProgressBackground(new Color(1, 153, 1));      //green progressbar
                        }
                        if (a.intValue() > 90) {
                            hd.setProgressBackground(new Color(216, 0, 0));      //red progressbar
                        }
                        System.out.println("*****************************Percentage is:" + a + "***********************************");
                        PanelPlayPause.lblExtentDeadline.setVisible(false);
                        
                        /**
                        * This condition is used for checking the visibility of extend Deadline
                        */
                        if (a > 100.00) {
                            System.out.println("Greater than 100");
                            PanelPlayPause.lblExtentDeadline.setVisible(true);
                        } else {
                            System.out.println("Less than 100");
                            
                        }
                        /**
                        * set the Progress status and Percentage
                        */
                        PanelPlayPause.taskProgressbar.setValue(a.intValue());
                        }
                        
                        PanelPlayPause.lblStatus.setText(status2);
                        PanelPlayPause.lblStatus.setVisible(false);
                        PanelPlayPause.lblTimesheetId.setText(tracking);
                        PanelPlayPause.lblTimesheetId.setVisible(false);

                        status_array[temp] = status2;
                        
                        /**
                        * set the task list and handle according to their status. 
                        */
                        switch (status2) {
                            
                            
                            case "onHold":
                                panel1.setBackground(new Color(231, 231, 231));
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                PanelPlayPause.lblStop.setEnabled(false);
                                PanelPlayPause.lblDone.setEnabled(false);
                                System.out.println("sdfgghhhhhhhhhhh------>>>>>>>>"+holdReason);
                                PanelPlayPause.lblPlay.setToolTipText(""+holdReason);
                                
                                /**
                                * this condition will open for tester side
                                */
                                 if(assignedto.equals("1")){
                                   
                                    PanelPlayPause.lblReOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/play2.png")));
                                    PanelPlayPause.lblReOpen.setEnabled(false);
                                    PanelPlayPause.lblReOpen.setToolTipText("Re-Open Task");
                                    
                                }else{
                                    PanelPlayPause.lblReOpen.setVisible(false);
                                }
                                 break;
                            
                            case "New":
                                panel1.setBackground(new Color(234, 252, 255));
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                PanelPlayPause.lblStop.setEnabled(false);
                                PanelPlayPause.lblDone.setEnabled(false);
                                PanelPlayPause.lblPlay.setToolTipText("Start Task");
                                PanelPlayPause.lblReOpen.setVisible(false);
                            break;
                            case "ToDo":
                                 panel1.setBackground(new Color(255, 247, 246  ));
                                if(assignedto.equals("1")){
                                    PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                    PanelPlayPause.lblStop.setEnabled(false);
                                    PanelPlayPause.lblPlay.setEnabled(false);
                                    PanelPlayPause.lblPlay.setToolTipText("Start Task");
                                    PanelPlayPause.lblReOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/play2.png")));
                                    PanelPlayPause.lblReOpen.setEnabled(false);
                                    PanelPlayPause.lblDone.setEnabled(false);
                                    PanelPlayPause.lblReOpen.setToolTipText("Re-Open Task");
                                     
                                    
                                    
                                }else{
                                    todo=true;
                                    PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                    PanelPlayPause.lblStop.setEnabled(false);
                                    PanelPlayPause.lblPlay.setToolTipText("Start Task");
                                    PanelPlayPause.lblDone.setEnabled(false);
                                    flag=false;
                                    PanelPlayPause.lblReOpen.setVisible(false);
                                    
                                 }
                                
                                
                                break;
                                
                            case "Progress":
                            case "Working":
                                
                                working_task = true;
                                colorFlag=false;
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
                                PanelPlayPause.lblStop.setEnabled(true);
                                PanelPlayPause.lblDone.setEnabled(true);
                                PanelPlayPause.lblPlay.setToolTipText("Pause Task");
                                panel1.setBackground(new Color(236, 252, 244));
                                /**
                                * this condition will open for tester side
                                */
                                if(assignedto.equals("1")){
                                   
                                    PanelPlayPause.lblReOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/play2.png")));
                                    PanelPlayPause.lblReOpen.setToolTipText("Re-Open Task");
                                }else{
                                    PanelPlayPause.lblReOpen.setVisible(false);
                                 }
                                
                                break;
                            case "Stop"://stop
                                
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                PanelPlayPause.lblStop.setEnabled(false);
                                PanelPlayPause.lblPlay.setToolTipText("Start Task");
                                panel1.setBackground(new Color(238, 240, 247));
                                /**
                                * this condition will open for tester side
                                */
                                if(assignedto.equals("1")){
                                    PanelPlayPause.lblReOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/play2.png")));
                                    PanelPlayPause.lblReOpen.setToolTipText("Re-Open Task");
                                    System.out.println("\n\n\n*********NamanArora Complete******* == "+complete);
                                    
                                }else{
                                    PanelPlayPause.lblReOpen.setVisible(false);
                                }
                                
                                flag=true;
                                break;
                              
                         
                            case "Pause":
                                working_task = true;
                                task_paused = true;
                                win.setWorkingStatus(false);
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/pauseresume.png")));
                                PanelPlayPause.lblStop.setEnabled(false);
                                PanelPlayPause.lblPlay.setToolTipText("Resume Task");
                                if(colorFlag){
                                    panel1.setBackground(new Color(255, 230, 230));   
                                }else{
                                    panel1.setBackground(new Color(236, 252, 244)); 
                                }
                                  
                                /**
                                * this condition will open for tester side
                                */
                                if(assignedto.equals("1")){
                
                                    PanelPlayPause.lblReOpen.setIcon(new ImageIcon(this.getClass().getResource("/images/play2.png")));
                                    PanelPlayPause.lblReOpen.setToolTipText("Re-Open Task");
                                    PanelPlayPause.lblDone.setEnabled(true);
                                    PanelPlayPause.lblDone.setToolTipText("Finish");

                                }else{
                                    PanelPlayPause.lblReOpen.setVisible(false);
                                   
                                 }
                                flag=true;
                                break;
                                
                            case "Testing":
                                panel1.setBackground(new Color(255, 251, 234));
                                /**
                                * this condition will open for tester side
                                */
                                if(assignedto.equals("1")){
                                        panel1.setBackground(new Color(255, 251, 234));
                                       fourButton();
                                        flag=true;
                                }else{
                                    PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                    PanelPlayPause.lblStop.setEnabled(false);
                                    PanelPlayPause.lblPlay.setEnabled(false);
                                    PanelPlayPause.lblDone.setEnabled(false);
                                    PanelPlayPause.lblReOpen.setVisible(false);
                                    PanelPlayPause.lblPlay.setToolTipText("Testing");
                                    PanelPlayPause.lblStop.setToolTipText("Testing");
                                    PanelPlayPause.lblDone.setToolTipText("Testing");
                                   flag=false;
                                    
                                }
                                
                                break;
                                
                            case "Done":
                                
                                    PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                    PanelPlayPause.lblStop.setEnabled(false);
                                    PanelPlayPause.lblPlay.setEnabled(false);
                                    PanelPlayPause.lblDone.setEnabled(false);
                                /**
                                * this condition will open for tester side
                                */
                                 if(assignedto.equals("1")){
                                   
                                    PanelPlayPause.lblReOpen.setEnabled(false);
                                   
                                }else{
                                    PanelPlayPause.lblReOpen.setVisible(false);
                                    
                                }
                                flag=false;
                                break;
                        }
                        /**
                        * set the clientCode and Project Code and Task details
                        */
                        PanelPlayPause.lblTask.setText(client_code1 + ":" + project_code1 + ":" + task);
                        
                        /**
                        * this condition will describe the ToDo Message in Time SHeet
                        */
                        if (task.length() > 30 && !(status3.equals("ToDo")) ) {
                            PanelPlayPause.lblTask.setToolTipText("<html><p width=\"250px\">" 
                                    + task + "</p></html>");
                        }
                        
                        if (task.length() < 30 && !(status3.equals("ToDo"))) {
                            PanelPlayPause.lblTask.setToolTipText(task);//toDo_description
                        }
                        
                        if (toDo_description.length() > 30 && status3.equals("ToDo") ) {
                            PanelPlayPause.lblTask.setToolTipText("<html><p width=\"250px\">" 
                                    + toDo_description + "</p></html>");
                        }
                        
                        if (toDo_description.length() < 30 && status3.equals("ToDo")) {
                            PanelPlayPause.lblTask.setToolTipText(toDo_description);//toDo_description
                        }
                        
                        /**
                        * this condition will describe the Hold Message in TimeSheet
                        */
                        if (holdReason.length() > 30 && status3.equals("onHold") ) {
                            PanelPlayPause.lblTask.setToolTipText("<html><p width=\"250px\">" 
                                    + holdReason + "</p></html>");
                        }
                        
                        if (holdReason.length() < 30 && status3.equals("onHold")) {
                            PanelPlayPause.lblTask.setToolTipText(holdReason);//toDo_description
                        }
                        
                        
                        /**
                        * this condition will describe the priority Message in TimeSheet
                        */
                        if(end_date.equals("")){
                         PanelPlayPause.lblAssigned.setText("<html><body>Priority : " +
                                priority+"</body></html>");
                        }
                        else{
                            PanelPlayPause.lblAssigned.setText("<html><body>Priority : " +
                                priority+"<br>TaskDate : "+currentDate1+"</body></html>");
                        }
                        System.out.println("priority - "+priority);
                        
                        /**
                        * add-Mouse-Listener of TaskSubPanel class 
                        */
                        panel1.addMouseListener(new MouseListener() {
                            JLabel lblStatus1 = (JLabel) panel1.getComponent(1);

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                String pName = panel1.getName().toString();
                               
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                               
                                Point p = panel1.getLocation();
                                int x = (int) p.getX();
                                int y = (int) p.getY();
                                System.out.println(x + " " + y);
                               
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
 
                            }
                        });
                        
                        /**
                        * this will describe the PanelPlayPause for Done functionality
                        */
                        try {

                            PanelPlayPause.lblDone.addMouseListener(new MouseListener() {
                                JLabel lbl_Done = (JLabel) panel1.getComponent(6);
                                JLabel lblPlayPause = (JLabel) panel1.getComponent(4);
                                JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
                                JLabel lbl_status1 = (JLabel) panel1.getComponent(1);
                                JLabel lbl_task = (JLabel) panel1.getComponent(11);

                                
                                @Override
                                public void mouseClicked(MouseEvent e) {

                                    /**
                                    * this condition is used for Tester or review user only
                                    */
                                     if(assignedto.equals("1")){
                                        flag=true;
                                    }
                                    System.out.println("ssssddddffrewqwwqwq"+lbl_status1.getText());
                                    
                                    if(lbl_status1.getText().equals("New") || lbl_status1.getText().equals("Testing") || lbl_status1.getText().equals("ToDo") || lbl_status1.getText().equals("onHold")){
                                        flag=false;
                                    }else{
                                        flag=true;
                                    }
                                    
                                    /**
                                    * for New, Testing, ToDo,Hold flag will disable and finish button will not work
                                    */
                                    
                                    if(flag){
                                    String panelid = panel1.getName().toString();
                                    task_id = panelid;
                                    System.out.println("task_id is : " + task_id);
                             //       checkAndGetSubtaskIds();

                                    cd.setLocationRelativeTo(dialog);
                                    cd.setInfo("Do you want to Finish task?");
                                    cd.setVisible(true);
                                    /**
                                    * this condition is used for Tester or review user only
                                    */
                                    if(assignedto.equals("1")){
                                        complete=true;
                                    }
                                    
                                    String c = cd.getInput();
                                    if (interruptDialog) {
                                        c = "";
                                    }
                                    if (c.equals("yes")) {
                                    /**
                                    * this condition is used for Tester or review user only, 
                                    * this Finish will done the task of the user and task is removed from the both user timeSheet(user and TimeSheet)  
                                    */
                                        if(complete){
                                            if ( (lbl_status1.getText().equals("Working") || 
                                                    lbl_status1.getText().equals("Progress") || lbl_status1.getText().equals("Pause"))){
                                                showInfoDialog("Stop working task first");
                                            }
                                            else{
                                                System.out.println("taskid-"+task_id);
                                                System.out.println("userid-"+userId);
                                                System.out.println("comment-Done");
                                                System.out.println("status-Done");
                                                System.out.println("old id -"+oId);

                                                bDoneNComplete(userId,task_id,"Done","Done",oId,assigned_by);
                                                showInfoDialog("Done Successful");
                                                try {
                                                    refresh();
                                                } catch (SQLException ex) {
                                                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                                System.out.println("\n\ndoneeeeeeeeeeeeeee\n\n");
                                                complete=false;
                                            }
                                            
                                        }
                                        else
                                /**
                                * this condition is used for every user except Review user.
                                */
                                if(!(assignedto.equals("1"))){
//                                        System.out.println("Name of c1 is " + panel1.getComponent(1).getName());
//                                        System.out.println("Name of c2 is " + panel1.getComponent(2).getName());
//                                        System.out.println("Name of c3 is " + panel1.getComponent(3).getName());
//                                        System.out.println("Name of c4 is " + panel1.getComponent(4).getName());
//                                        System.out.println("Name of c5 is " + panel1.getComponent(5).getName());
//                                        System.out.println("Name of c6 is " + panel1.getComponent(6).getName());
//                                        System.out.println("Name of c7 is " + panel1.getComponent(7).getName());
//                                        System.out.println("Name of c8 is " + panel1.getComponent(8).getName());
//                                        System.out.println("Name of c9 is " + panel1.getComponent(9).getName());
//                                        System.out.println("Name of c10 is " + panel1.getComponent(10).getName());
//                                        System.out.println("Name of c11 is " + panel1.getComponent(11).getName());
//                                        System.out.println("Name of c11 is " + panel1.getComponent(12).getName());
//                                        System.out.println("Name of c11 is " + panel1.getComponent(13).getName());
//                                        
//                                        

                                        JLabel lblStatus1 = (JLabel) panel1.getComponent(1);
                                        System.out.println("namanLblStatus "+lblStatus1);
                                        String stts = lblStatus1.getText().toString();
                                        System.out.println("namanStts - "+stts);
                                        if ( (stts.equals("Working") || stts.equals("Progress") || stts.equals("Pause")) && !stask.subtask_map.containsKey(task_id)) {
                                            System.out.println("NamanArora1");
                                            doOnStop(panel1, "Done","no_subtask");
                                        } else {
                                            boolean allSelected=false;
                                            if (stask.subtask_map.containsKey(task_id)) {                // show task list
                                        stask.clearList();
                                        List list = stask.subtask_map.get(task_id);
                                        List<String> list1 = new ArrayList<String>();
                                        List<String> subtaskIds = new ArrayList<String>();
                                        System.out.println("list is : " + list);
                                        for (int i = 0; i < list.size(); i++) {
                                            String s = list.get(i).toString();
                                            System.out.println("list" + s);
                                            String[] parts = s.split("\\^");
                                            System.out.println("parts[0]" + parts[0] + "<");
                                            System.out.println("parts[1]" + parts[1] + "<");
                                            subtaskIds.add(parts[0]);
                                            list1.add(parts[1]);
                                        }
                                        System.out.println("list1 is : " + list1);
                                        System.out.println("subtaskIds are : " + subtaskIds);
                                        stask.addItemListToList(list1);
                                        stask.setTask(lbl_task.getText().toString());
                                        stask.setToolTip(lbl_task.getText().toString());
                                        stask.setLocationRelativeTo(dialog);
                                        stask.setVisible(true);
                                        String flag = stask.getFlag();
                                            System.out.println("hello NAMAN 1 - response from getFlag -  ");
                                        if ("ok".equals(flag)) {
  
                                            System.out.println("hello NAMAN 2 - response from getFlag -  ");
                                            List<String> listToBeDone = new ArrayList<>();
                                            List<String> idListToBeDone = new ArrayList<>();
                                            listToBeDone = stask.getTaskList();
                                            idListToBeDone = stask.getTaskIds();
                                            System.out.println("new list is" + listToBeDone);
                                            System.out.println("new id list is" + idListToBeDone);
                                            
                                            String subtasks_id = "";
                                            for (int i = 0; i < idListToBeDone.size(); i++) {
                                                subtaskIds.get(Integer.parseInt(idListToBeDone.get(i)));
                                                System.out.println("Selected ids" + subtaskIds.get(Integer.parseInt(idListToBeDone.get(i))));
                                                subtasks_id = subtasks_id + "_" + subtaskIds.get(Integer.parseInt(idListToBeDone.get(i)));
                                            }
                                            subtasks_id = subtasks_id.startsWith("_") ? subtasks_id.substring(1) : subtasks_id;
                                            System.out.println("subtasks_id are : " + subtasks_id);
                                            
                                            
                                            if (idListToBeDone.size() == subtaskIds.size()) {  // if whole list is selected
                                                if (stts.equals("Working") ){
                                                    System.out.println("NamanArora2");
                                                    doOnStop(panel1, "Done","has_subtask");
                                                    if(!"task_not_stopped".equals(work_status)){
                                                           userId = main_userid;
                                                    work_status = "Done";
                                                        System.out.println("makeReviewAndFinish1");
                                                     makeReviewAndFinish(stts,subtasks_id,"has_subtasks","review_required");
                                                    }
                                                 }else{
                                                    System.out.println("Equal");
                                                allSelected=true;
                                                userId = main_userid;
                                                work_status = "Done";
                                                System.out.println("makeReviewAndFinish2");
                                                makeReviewAndFinish(stts,subtasks_id,"has_subtasks","review_required");
                                                
                                                }
                                                
                                                
                                            }else{
                                                allSelected=false;
                                      
                                                if(!"".equals(subtasks_id)){
                                                  
                                                    if (stts.equals("Working")) {   
                                                            System.out.println("NamanArora3");
                                                            doOnStop(panel1, "Done","has_subtask");
                                                            if(!"task_not_stopped".equals(work_status)){
                                                                userId = main_userid;
                                                                work_status = "Stop";
                                                                System.out.println("makeReviewAndFinish3");
                                                                makeReviewAndFinish(stts,subtasks_id,"has_subtasks","no_review_required");
                                                        }
                                                    }else {
                                                        userId = main_userid;
                                                        work_status = "Stop"; 
                                                        System.out.println("makeReviewAndFinish4");
                                                        makeReviewAndFinish(stts,subtasks_id,"has_subtasks","no_review_required"); 
                                                    }
                                                }else{
                                                    showInfoDialog("No Task Selected");
                                                }
                                            }
                                            
                                        }else if("cancel".equals(flag))
                                            {
                                            
                                            }
                                    }else{
                                        userId = main_userid;
                                        work_status = "Done";
                                        System.out.println("makeReviewAndFinish5");
                                        makeReviewAndFinish(stts,task_id,"no_subtasks","review_required");
                                    }
                                }
                                        
                                        
                            }
                                
                                else{ }
                        }
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
                                    System.out.println("Mouse Entered");
                                     
                                    //panel1.setBackground(new Color(240, 240, 240));
                                    lbl_Done.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                     
                                   lbl_Done.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }

                             
                                /**
                                * this will describe the PanelPlayPause for Review functionality
                                * if any user is in working condition or in pause condition and the user wanted to review his task then, 
                                * task first in stop and then make review dialog were open.
                                * if task is in stop condition then it is simply open the review dialog 
                                */
                                public void makeReviewAndFinish(String stts,String taskid,String mode,String review) {
                                    try {
                                        if(!(todo_task_id.equals("0"))){
                                            
                                            System.out.println("asdfggggggggggggggggggggg");
                                            new ReadXml().getUserList2(todo_task_id);
                                        }
                                        
                                        
                                        System.out.println("Review@|@");
                                        System.out.println(stts+taskid+mode+review);
                                        if("review_required".equals(review)){
                                             reviewDlg.setTaskId(task_id);
                                        reviewDlg.setLocationRelativeTo(dialog);
                                        ReviewDialog.listUserReview.setSelectedIndex(0);
                                        reviewDlg.setVisible(true);
                                        if (ReadXml.response1.equals("ok")) {
                                            
                                            showInfoDialog("Review Successful");
                                            if("has_subtasks".equals(mode)){
                                                sendDoneRequest(userId, task_id, work_status,taskid);
                                            }else{
                                             sendPlayRequest(userId, task_id, work_status);   
                                            }
                                            ReadXml.response1 = "";
                                            lblPlayPause.setEnabled(false);
                                            lbl_Stop.setEnabled(false);
                                            lbl_Done.setEnabled(false);
                                            if (stts.equals("Pause")) {
                                                task_paused = false;
                                                win.setWorkingStatus(false);
                                            }
                                        } else {
                                            showInfoDialog("Review Failed.");
                                        }
                                        }else{
                                            if("has_subtasks".equals(mode)){
                                                sendDoneRequest(userId, task_id, work_status,taskid);
                                                showInfoDialog("Success!");
                                            }else{
                                             sendPlayRequest(userId, task_id, work_status);
                                            }
                                        }
                                       
                                        System.out.println("Selected user for review>> " + reviewDlg.getSelectedUser());
                                        refresh();
                                    } catch (IOException | SQLException ex) {
                                        Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            
                            
                            /**
                            * this will describe the TaskDetailed Dialog when user click on task portion.
                            * and manage the functionality of Extend Deadline Dialog box
                            */
                            MouseListener  mListener = new MouseListener() {
                                
                                JLabel lblStatus1 = (JLabel) panel1.getComponent(1);
                                JLabel lblTasks = (JLabel) panel1.getComponent(12);
                                JLabel lblPlus = (JLabel) panel1.getComponent(10);
                                JLabel lblDate = (JLabel) panel1.getComponent(9);
                                //8 -- namlblDeadline
                                //6 -- namlblDone
                                //5 -- namlblStop
                                //4 -- namlblPlay
                                //3 -- namAssignedBy
                                JLabel lblReOpen = (JLabel) panel1.getComponent(11);
                                JLabel lblReOpen1 = (JLabel) panel1.getComponent(6);
                                JLabel lblReOpen2 = (JLabel) panel1.getComponent(3);
                                
                                
                                String task_id = panel1.getName().toString();

                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    System.out.println("namanClickedMouse");
                                    System.out.println("lblTasks  -- "+lblTasks);
                                    System.out.println("lblReOpen  -- "+lblReOpen);
                                    System.out.println("lblReOpen1  -- "+lblReOpen1);
                                    System.out.println("lblReOpen2 -- "+lblReOpen2);
                                    
                                    System.out.println("e.getSource -- "+e.getSource());
                                    if (e.getSource() == lblTasks) {
                                        String task = lblTasks.getText().toString();
                                        String status= lblStatus1.getText().toString();
                                        String date = lblDate.getText().toString();
                                        
                                    if(assignedto.equals("1")){
                                        if(status.equals("Testing")){
                                        
                                        }
                                        else
                                       status="Testing-"+status;
                                    }
                                        
                                        td = new TaskDetailDialog(dialog, true);
                                        td.setLocationRelativeTo(dialog);
                                        td.setDetailedTask(task,date,status,currentDate,lTime);
                                        td.startTimer();
                                    }
                                    if (e.getSource() == lblPlus) {
                                        userId = main_userid;
                                        String flag = showExtentionDialog();
                                        String date = ed.getDate();
                                        String hour = ed.getTimeInHour();
                                        String min  = ed.getTimeInMin();
                                        System.out.println("flag is:::" + flag);
                                        System.out.println("date is:::" + date);
                                        System.out.println("hour is:::" + hour);
                                        System.out.println("hour is:::" + min);
                                        if (!"".equals(date)) {
                                            String response = "";
                                            try {
                                                response = readxml.sendDeadlineRequest(userId, task_id, date,hour,min);
                                            } catch (IOException ex) {
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                            if ("ok".equals(response)) {
                                                showInfoDialog("<html><p width=\"155px\">" + "Your request has been submitted successfully!" + "</p></html>");
                                             
                                            } else {
                                                showInfoDialog("<html><p width=\"155px\">" + "Connection error. Please try again!" + "</p></html>");
                                            }
                                        }
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
                                     System.out.println("namanEnetrMouse");
                                    
                                    if (e.getSource() == lblTasks) {
                                        lblTasks.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    }
                                    
                                    if (e.getSource() == lblPlus) {
                                        lblPlus.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    }
                                }
                                @Override
                                public void mouseExited(MouseEvent e) {
                                     System.out.println("namanExitMouse");
                                    
                                    if (e.getSource() == lblTasks) {
                                        lblTasks.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    }
                                 
                                    if (e.getSource() == lblPlus) {
                                        lblPlus.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    }
                                }

                            /**
                            *Show the Extend DeadLine Dialog
                            */
                                   
                                public String showExtentionDialog() {
                                    ed.setLocationRelativeTo(dialog);
                                    ed.setDate(lblDate.getText().toString());
                                    ed.setCurrentDate();
                                    ed.setVisible(true);
                                    String flag ="";
                                    flag = ed.getFlag();
                                   
                                        
                                    if (flag.equals("wrong")) {
                                        showInfoDialog("Incorrect date! or time!");
                                        showExtentionDialog();
                                    }
                                    return flag;
                                }
                            };

                            /**
                            * add the panelPlayPause MouseListener in Task Detailed and extendDeadLine
                            */
                            
                            PanelPlayPause.lblTask.addMouseListener(mListener);
                            PanelPlayPause.lblExtentDeadline.addMouseListener(mListener);
                            
                            /**
                            * add the panelPlayPause MouseListener in Play functionality.
                            */
                            PanelPlayPause.lblPlay.addMouseListener(new MouseListener() {
                                JLabel lblPlayPause = (JLabel) panel1.getComponent(4);
                                JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
                                JLabel lblStatus1 = (JLabel) panel1.getComponent(1);
                                JLabel lblDone2 =(JLabel) panel1.getComponent(6);
                                JLabel lblDone =(JLabel) panel1.getComponent(11);
                                @Override
                                public void mouseClicked(MouseEvent e) {

                                    String panelid;
                                    System.out.println("lblDone2  ---->> "+lblDone2);
                                    System.out.println("lblDone2  ---->> "+lblDone);
                                    panelid = panel1.getName().toString();
                                    task_id = panelid;
                                    System.out.println("Name of this component is : " + lblStatus1.getText().toString());
                                    System.out.println("Status of this panel is : " + lblStatus1.getText().toString());


                                    JLabel lblTimesheet = (JLabel) panel1.getComponent(2);
                                    String stts = lblStatus1.getText().toString();
                                     String statusTodo = stts;
                                    System.out.println("Status st this row is>> " + stts);
                                    
                                    System.out.println("Working task is >> " + working_task);
                                    System.out.println("Paused task is>> " + task_paused);
                                    /**
                                    * for Review user only
                                    */
                                    if(assignedto.equals("1")){
                                        System.out.println("sssssssssssssssssssssssssssssssss");
                                        test=true;
                                    }
                                    else{
                                        System.out.println("svvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
                                        test=false;
                                    }
                                    
                                    if(stts.equals("New") || stts.equals("ToDo")){
                                    stts="Stop";
                                    }
                                    
                                    if(assignedto.equals("1") && statusTodo.equals("ToDo")){
                                        stts="Stop1";
                                    }
                                    
                                    if(stts.equals("onHold")){
                                        stts="Stop";
                                        kOnHold=true;
                                        
                                    }
                                   /**
                                    * when our task is in Working Condition and we wanted to pause that task
                                    */
                                    
                                    if (stts.equals("Working") || stts.equals("Progress")) {
                                        pauseDialog.setLocationRelativeTo(dialog);
                                        System.out.println("\n\nInside Working....\n\n\n");
                                        pauseDialog.setVisible(true);
                                        play_pause_comment = pauseDialog.getPauseString();
                                        System.out.println("\n\nInside Working....\n\n\n");
                                        String time = pauseDialog.getExpectedTime();
                                        System.out.println("\n\nInside Working....\n\n\n");
                                        System.out.println("Expected time is>> " + time);
                                        if (!pauseDialog.isVisible() && !play_pause_comment.equals("")) {
                                            try {
                                                System.out.println("\n\nInside WorkingWorking....\n\n\n");
                                                userId = main_userid;
                                                timesheet_id = main_timeid;
                                                work_status = "Pause";
                                                String time_id = dbHandler.getTimeId();
                                                System.out.println("\n\n\n\n*********************************************");
                                                System.out.println("\n\ntime_id -- "+time_id);
                                                String user_id = dbHandler.getUserId();
                                                play_pause_comment = play_pause_comment.replaceAll("\\s+", " ");
                                                play_pause_comment = play_pause_comment.replaceAll(" ", "^");
                                                
                                                sendDataForTimesheetToPause(userId, task_id, work_status, time_id, play_pause_comment,time);
                                                if (timesheet_response.equals("done")) {
                                                    System.out.println("timesheet_responsetimesheet_responsetimesheet_response....");
                                                    worker.shutdownNow();                          //stop timesheet uudate thread
                                                    screeShotListener.shutdownNow();                // stop mouse keyboard tracking thread
                                                    main_timeid = "";
                                                    lblStatus1.setText(work_status);
                                                    lblPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/pauseresume.png")));
                                                    lbl_Stop.setEnabled(false);
                                                    working_task = true;
                                                    task_paused = true;
                                                    win.setWorkingStatus(false);
                                                    showInfoDialog("Task paused!");
                                                    lblPlayPause.setToolTipText("Resume task");
                                                } else {
                                                    System.out.println("ELSEPARTELSEPARTELSEPARTELSEPART....");
                                                    work_status = "Working";
                                                    lblStatus1.setText("Working");
                                                    win.setWorkingStatus(true);
                                                }
                                            } catch (SQLException ex) {
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (MalformedURLException ex) {
                                                hd.errorDescription("ErrorDescription4", ex);
            
                                                showInfoDialog("Connection error! Please try again.");
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (IOException ex) {
                                                hd.errorDescription("ErrorDescription5", ex);
                                                showInfoDialog("Connection error! Please try again.");
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }

                                        interruptDialog = false;
                                    }
                                    if (stts.equals("Stop") && (working_task || task_paused)) {
                                        showInfoDialog("Please Stop another working task!");
                                        System.out.println("2059-Stop->Please Stop another working task");
                                    }
                                    
                                    if (stts.equals("Testing") && test && (working_task || task_paused)) {
                                        System.out.println("2063-testing->Please Stop another working task");
                                        showInfoDialog("Please Stop another working task!");
                                    }
                                    
                                    
                                    if ((stts.equals("Stop") && !working_task) || (stts.equals("Pause") && task_paused)) {
                                        String info = "";
                                        cd.setLocationRelativeTo(dialog);
                                        if (stts.equals("Stop")) {
                                            info = "Task started";
                                            cd.setInfo("Do you want to Start task?");
                                        }
                                        if (stts.equals("Pause")) {
                                            
                                            info = "Task resumed";
                                            cd.setInfo("Do you want to Resume task?");
                                            nPause=true;
                                        }
                                        cd.setVisible(true);
                                        String c = cd.getInput();
                                        if (interruptDialog) {
                                            c = "";
                                        }
                                        if (c.equals("yes")) {
                                                if(nPause){
                                                    if(colorFlag){
                                                        onHoldMsg.setString("Reason for Start the Task");
                                                        onHoldMsg.setLocationRelativeTo(dialog);
                                                        
                                                        onHoldMsg.setVisible(true);
                                                        onHoldMsg.textArea1.setText("");
                                                        String comments=onHoldMsg.getComments();
                                                        System.out.println("\n\n\n************************"+task_id+"************************\n\n\n");

                                                        String flg=onHoldMsg.getFlag();
                                                        if(flg.equals("false")){
                                                            showInfoDialog("Task is not Started");
                                                        }else{
                                                                try {
                                                                    String time_id=dbHandler.getTimeId();
                                                                    System.out.println("NNTimeId  --  "+time_id);
                                                                    System.out.println("NNUserId -- "+userId);
                                                                    System.out.println("NNTaskId --  "+task_id);
                                                                    comments = comments.replaceAll(" ", "^");
                                                                    sendDataForTimesheet(userId, task_id, "Progress", time_id, comments);
                                                                    showInfoDialog("Task Started Successful");
                                                                    working_task = true;
                                                                    if (working_task) {
                                                                        win.setWorkingStatus(true);
                                                                        worker = Executors.newSingleThreadScheduledExecutor();
                                                                        worker.scheduleWithFixedDelay(updateTimesheetThread, 0, 10, TimeUnit.MINUTES);
                                                                        if (lblTimesheet.getText().toString().equals("1")) {
                                                                            screeShotListener = Executors.newSingleThreadScheduledExecutor();
                                                                            screeShotListener.schedule(tracking_thread, 10, TimeUnit.SECONDS);
                                                                            //tracker_thread  new Thread(win.doEvery10).start();
                                                                        }
                                                                    }
                                                                    colorFlag=false;
                                                                    try {
                                                                        refresh();
                                                                    } catch (SQLException ex) {
                                                                        Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                                    }
                                                                    
                                                                } catch (SQLException ex) {
                                                                        Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                                    } catch (IOException ex) {
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
                                                                
                                                            }
                                                    }else{
                                                         System.out.println("\n\n\n********88Inside Pause*********** \n\n\n");
                                                           try {   
                                                            userId = main_userid;

                                                            work_status = "Progress";
                                                            String time_id=dbHandler.getTimeId();
                                                                 System.out.println("NNTimeId  --  "+time_id);
                                                                 System.out.println("NNUserId -- "+userId);
                                                                 System.out.println("NNTaskId --  "+task_id);

                                                            sendDataForTimesheet(userId, task_id, "Progress", time_id, "");
                                                            lbl_Stop.setEnabled(true);
                                                            lblDone2.setEnabled(true);
                                                            lblStatus1.setText(work_status);
                                                            lblPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
                                                            working_task = true;
                                                            panel1.setBackground(new Color(236, 252, 244));
                                                            showInfoDialog(info);
                                                            lblPlayPause.setToolTipText("Pause task");
                                                            } catch (SQLException ex) {
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
                                                            catch (IOException ex) {
                                                                hd.errorDescription("ErrorDescription6", ex);
                                                                showInfoDialog("Connection error! Please try again.");
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                           }
                                                   
                                                           if (working_task) {
                                                                win.setWorkingStatus(true);
                                                                worker = Executors.newSingleThreadScheduledExecutor();

                                                               if (lblTimesheet.getText().toString().equals("1")) {
                                                                    screeShotListener = Executors.newSingleThreadScheduledExecutor();
                                                                    screeShotListener.schedule(tracking_thread, 10, TimeUnit.SECONDS);
                                                                }

                                                            }
                                                   
                                                        }
                                                        nPause=false;
                                                }else{
                                                    /**
                                                     * when user wanted to start the task, when his task is in Hold
                                                     * user give proper reason while started his task.
                                                     */
                                                    if(kOnHold){
                                                        onHoldMsg.setString("Reason for your Idle Task");
                                                        onHoldMsg.setLocationRelativeTo(dialog);
                                                        
                                                        onHoldMsg.setVisible(true);
                                                        onHoldMsg.textArea1.setText("");
                                                        String comments=onHoldMsg.getComments();
                                                        System.out.println("\n\n\n************************"+task_id+"************************\n\n\n");

                                                        String fla=onHoldMsg.getFlag();
                                                        if(fla.equals("false")){
//                                                            showInfoDialog("Task Started Successful");
                                                        }else{
                                                                comments = comments.replaceAll(" ", "^");
                                                                bOnHold(task_id,comments,"Progress");
                                                                showInfoDialog("Task Started Successful");
                                                                working_task = true;
                                                                if (working_task) {
                                                                    win.setWorkingStatus(true);
                                                                    worker = Executors.newSingleThreadScheduledExecutor();
                                                                    worker.scheduleWithFixedDelay(updateTimesheetThread, 0, 10, TimeUnit.MINUTES);
                                                                    if (lblTimesheet.getText().toString().equals("1")) {
                                                                        screeShotListener = Executors.newSingleThreadScheduledExecutor();
                                                                        screeShotListener.schedule(tracking_thread, 10, TimeUnit.SECONDS);
                                                                        //tracker_thread  new Thread(win.doEvery10).start();
                                                                    }
                                                                }
                                                                try {
                                                                    refresh();
                                                                } catch (SQLException ex) {
                                                                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                                }
                                                            }
                                                        
                                                        kOnHold=false;
                                                    }
                                                  else  {
                                                try {
                                                    try {
                                                        userId = main_userid;
                                                        work_status = "Working";
                                                        sendPlayRequest(userId, task_id, work_status);
                                                        lbl_Stop.setEnabled(true);
                                                        lblDone2.setEnabled(true);
                                                        lblStatus1.setText(work_status);
                                                        lblPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
                                                        working_task = true;
                                                        panel1.setBackground(new Color(236, 252, 244));
                                                        showInfoDialog(info);
                                                        lblPlayPause.setToolTipText("Pause task");
                                                        String time_id = dbHandler.getTimeId();
                                                        
                                                    } catch (SQLException ex) {
                                                        Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    catch (IOException ex) {
                                                        
                                                        hd.errorDescription("ErrorDescription7", ex);
                                                        showInfoDialog("Connection error! Please try again.");
                                                        Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                    if (working_task) {
                                                        win.setWorkingStatus(true);
                                                        worker = Executors.newSingleThreadScheduledExecutor();
                                                        worker.scheduleWithFixedDelay(updateTimesheetThread, 0, 10, TimeUnit.MINUTES);
                                                        if (lblTimesheet.getText().toString().equals("1")) {
                                                            screeShotListener = Executors.newSingleThreadScheduledExecutor();
                                                            screeShotListener.schedule(tracking_thread, 10, TimeUnit.SECONDS);
                                                            //tracker_thread  new Thread(win.doEvery10).start();
                                                        }
                                                        
                                                    }
                                                    refresh();
                                                } catch (Exception ex) {
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                    }
                                    }
                                        }
                                        interruptDialog = false;
                                    }
                                    
                                     /**
                                     * this case for Testing or Review user, rest will be same
                                     */
                                    
                                   else  if ((stts.equals("Testing") && test && !working_task) || (stts.equals("Pause") && task_paused)) {
                                        String info = "";
                                        cd.setLocationRelativeTo(dialog);
                                        System.out.println("dddddddddddddddddddddddddddddddddddddddddddddddddd");
                                        if (stts.equals("Testing")) {
                                            info = "Task started";
                                            System.out.println("inside testinggggg");
                                            cd.setInfo("Do you want to Start task?");
                                        }
                                        if (stts.equals("Pause")) {
                                            info = "Task resumed";
                                            System.out.println("Hellllllllllllllllllooooooooo");
                                            nPause=true;
                                            cd.setInfo("Do you want to Resume task?");
                                            
                                        }
                                        cd.setVisible(true);
                                        String c = cd.getInput();
                                        if (interruptDialog) {
                                            c = "";
                                        }
                                        
                                        if(c.equals("no")){System.out.println("sseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");test=false;}
                                        
                                        if (c.equals("yes")) {
                                                    if(nPause){
                                                        colorFlag=false;
                                                    System.out.println("\n\n\n********88Inside Pause*********** \n\n\n");
                                                        try {   
                                                            userId = main_userid;
                                                            work_status = "Progress";
                                                            String time_id=dbHandler.getTimeId();
                                                            System.out.println("NNTimeId  --  "+time_id);
                                                            System.out.println("NNUserId -- "+userId);
                                                            System.out.println("NNTaskId --  "+task_id);
                                                            sendDataForTimesheet(userId, task_id, "Progress", time_id, "");
                                                            lbl_Stop.setEnabled(true);
                                                            lblDone2.setEnabled(true);
                                                            lblStatus1.setText(work_status);
                                                            lblPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
                                                            working_task = true;
                                                            panel1.setBackground(new Color(236, 252, 244));
                                                            showInfoDialog(info);
                                                            lblPlayPause.setToolTipText("Pause task");
                                                            } catch (SQLException ex) {
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
                                                            catch (IOException ex) {
                                                                hd.errorDescription("ErrorDescription8", ex);
                                                                showInfoDialog("Connection error! Please try again.");
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                           }

                                                   if (working_task) {
                                                        win.setWorkingStatus(true);
                                                        worker = Executors.newSingleThreadScheduledExecutor();
                                                        if (lblTimesheet.getText().toString().equals("1")) {
                                                            screeShotListener = Executors.newSingleThreadScheduledExecutor();
                                                            screeShotListener.schedule(tracking_thread, 10, TimeUnit.SECONDS);
                                                        }
                                                    }

                                                    nPause=false;
                                                    }else{
                                                    
                                                    
                                                        try {
                                                    
                                                            try {
                                                                userId = main_userid;
                                                                work_status = "Working";
                                                                sendPlayRequest(userId, task_id, work_status);
                                                                lbl_Stop.setEnabled(true);
                                                                lblDone2.setEnabled(true);
                                                                lblStatus1.setText(work_status);
                                                                lblDone.setEnabled(true);
                                                                lblPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
                                                                working_task = true;
                                                                panel1.setBackground(new Color(236, 252, 244));
                                                                showInfoDialog(info);
                                                                lblPlayPause.setToolTipText("Pause task");
                                                                String time_id = dbHandler.getTimeId();
                                                                
                                                            } catch (SQLException ex) {
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
                                                            catch (IOException ex) {
                                                                hd.errorDescription("ErrorDescription9", ex);
                                                                showInfoDialog("Connection error! Please try again.");
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
                                                            if (working_task) {
                                                                win.setWorkingStatus(true);
                                                                worker = Executors.newSingleThreadScheduledExecutor();
                                                                worker.scheduleWithFixedDelay(updateTimesheetThread, 0, 10, TimeUnit.MINUTES);
                                                                if (lblTimesheet.getText().toString().equals("1")) {
                                                                    screeShotListener = Executors.newSingleThreadScheduledExecutor();
                                                                    screeShotListener.schedule(tracking_thread, 10, TimeUnit.SECONDS);
                                                                    
                                                                }

                                                            }
                                                    
                                                            refresh();
                                                            } catch (Exception ex) {
                                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                                            }
                                                        }
                                            ////
                                        } else{test=false;}
                                        interruptDialog = false;
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
                                    
                                    lblPlayPause.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    JLabel status1 = (JLabel) panel1.getComponent(1);
                                    lblPlayPause.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            });
                            
                             /**
                             * In this we can add Mouse Listener of PanelPlayPause for Re-Open
                             */
                            PanelPlayPause.lblReOpen.addMouseListener(new MouseListener() {
                                JLabel lbl_ReOpen = (JLabel) panel1.getComponent(11);
                                JLabel status1 = (JLabel) panel1.getComponent(1);
                                
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                
                               boolean tru=false;
                                
                                System.out.println("NamanAroraMouseClicked---->>>>>"+status1.getText());
                                /**
                                * In this case, first close the running task then you will be able to ToDo the task. 
                                */
                                if(status1.getText().equals("Progress") || status1.getText().equals("Working") || status1.getText().equals("Pause") ){
                                    showInfoDialog("Stop the Working Task!");
                                    tru=false;
                                }else
                                 if((status1.getText().equals("ToDo")) || (status1.getText().equals("Testing")) ||
                                         status1.getText().equals("onHold") ){
                                     tru=false;
                                 }else{tru=true;}
                                
                                if(tru){
                                tru=false;
                                String panelid = panel1.getName().toString();
                                    task_id = panelid;
                                System.out.println("NamanAroraMouseClicked");
                                System.out.println("oid----->>>>>>"+oId);
                                errorDescription.setLocationRelativeTo(dialog);
                                
                                errorDescription.setVisible(true);
                                errorDescription.textArea1.setText("");
                                String comments=errorDescription.getComments();
                                System.out.println("\n\n\n************************"+task_id+"************************\n\n\n");
                               
                                String fla=errorDescription.getFlag();
                                if(fla.equals("false")){
                                    
                                }else{
                                    
                                    bDoneNComplete(userId,task_id,"ToDo",comments,oId,assigned_by);
                                    showInfoDialog("ToDo Successful");
                                    working_task=false;
                                    task_paused=false;
                                }
                                try {
                                        refresh();
                                    } catch (SQLException ex) {
                                        Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                                    System.out.println("NamanArora-mousePressed");
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                                 System.out.println("NamanArora-mouseReleased");
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                System.out.println("NamanArora-mouseEntered\n"+lbl_ReOpen);
                                
                                lbl_ReOpen.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {
                                System.out.println("NamanArora-mouseExited");
                                lbl_ReOpen.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                  
                            }
                        });
                            
                             /**
                             * In this we can add Mouse Listener of PanelPlayPause for Stop Functionality
                             */
                            PanelPlayPause.lblStop.addMouseListener(new MouseListener() {
                                JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
                                JLabel lblStatus1 = (JLabel) panel1.getComponent(1);

                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    System.out.println("NamanArora4");
                                    doOnStop(panel1, "Stop","no_subtask");
                                }

                                @Override
                                public void mousePressed(MouseEvent e) {
                                }

                                @Override
                                public void mouseReleased(MouseEvent e) {
                                }

                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    
                                    lbl_Stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                   
                                    
                                    lbl_Stop.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            });
                        } catch (Exception ex) {
                        }
                    }
                    dialog.repaint();
                }
                TaskPanel.panelforScrollPane.validate();
                TaskPanel.panelforScrollPane.repaint();
                System.out.println("Map is>>" + stask.subtask_map);
            }
        } catch (IOException | DOMException ex) {
            hd.errorDescription("ErrorDescription10", ex);
            showInfoDialog("Connection error! Please try again.");
            JLabel lbl = new JLabel("                        Check internet connection!");
            lbl.setVisible(true);
            TaskPanel.panelforScrollPane.add(lbl);
            TaskPanel.panelforScrollPane.validate();
            TaskPanel.panelforScrollPane.repaint();
            dialog.repaint();
        } finally {
        }
    }
    /**
    * this Function is used for stop the task in Timesheet
    */
    public void doOnStop(JPanel panel1, String tag,String subtask) {
        
        System.out.println("NamanArora - "+tag);
        JLabel lbl_PlayPause = (JLabel) panel1.getComponent(4);
        JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
        JLabel lbl_Done = (JLabel) panel1.getComponent(6);
        String panelid;
        panelid = panel1.getName().toString();
        task_id = panelid;
        JLabel lblStatus1 = (JLabel) panel1.getComponent(1);
        JLabel lblTimesheet = (JLabel) panel1.getComponent(2);
        JLabel toId = (JLabel) panel1.getComponent(13);
        String stts = lblStatus1.getText().toString();
        System.out.println("llllll ---->>>>>"+stts);
        String tId = toId.getText().toString();
        System.out.println("NamanArora - "+stts);
        if ((stts.equals("Progress") || stts.equals("Working")) && tag.equals("Stop")) {
            cd.setLocationRelativeTo(dialog);
            cd.setInfo("Do you want to stop task?");
            cd.setVisible(true);
            String c = cd.getInput();
            if (interruptDialog) {
                c = "";
            }
            if (c.equals("yes")) {
                stopOrDone(lblStatus1, lbl_PlayPause, lbl_Stop, done);
                panel1.setBackground(new Color(238, 240, 247));
            }
            interruptDialog = false;
        }  else if ((stts.equals("Progress") || stts.equals("Working") || stts.equals("Pause")) && tag.equals("Done")) {
               
            work_status="task_not_stopped";
            if (stopOrDone(lblStatus1, lbl_PlayPause, lbl_Stop, done)) {
                System.out.println("doneeeeee" + done);
                System.out.println("Task stopped!!!");
                userId = main_userid;

                
                work_status = "Done";
                System.out.println("doneeeeee" + done);
                if("no_subtask".equals(subtask)){                                  // if no subtasks 
                    try {
                        
                         if(!(tId.equals("0"))){
                                            
                            System.out.println("asdfggggggggggggggggggggg");
                            new ReadXml().getUserList2(tId);
                        }
                        
                    System.out.println("doneeeeee" + done);
                    reviewDlg.setTaskId(task_id);
                    reviewDlg.setLocationRelativeTo(dialog);
                    ReviewDialog.listUserReview.setSelectedIndex(0);
                    reviewDlg.setVisible(true);
                    if (ReadXml.response1.equals("ok")) {
                        ReadXml.response1 = "";
                        showInfoDialog("Review Successful");
                        sendPlayRequest(userId, task_id, work_status);
                    } else {
                        showInfoDialog("Review Failed");
                        showInfoDialog("<html><p width=\"155px\">" + "You have to select atleast one user for reviewing in order to finish task." + "</p></html>");

                    }
                    System.out.println("Selected user for review>> " + reviewDlg.getSelectedUser());
                    lbl_PlayPause.setEnabled(false);
                    lbl_Stop.setEnabled(false);
                    lbl_Done.setEnabled(false);
                    refresh();
                } catch (IOException | SQLException ex) {
                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                
            }

        }

    }
    /**
     * this function is used to open the Stop Dialog box.
     */
    public boolean stopOrDone(JLabel lblStatus1, JLabel lbl_PlayPause, JLabel lbl_Stop, boolean done) {
        //      InputDialog in = new InputDialog(dialog, true);
        boolean stopped = false;
        in2.setLocationRelativeTo(dialog);
        in2.setInfo("Enter what you have done");
        in2.setHint("Minimum 30 characters required");
        in2.setTextColor(new Color(189, 189, 189));
        InputDialog.btnOk.requestFocus();
        in2.setVisible(true);
        play_pause_comment = in2.getInput();
        if (!in2.isVisible() && play_pause_comment.length() > 29) {
            try {
                System.out.println(main_userid);
                userId = main_userid;
                timesheet_id = main_timeid;
                String time_id = dbHandler.getTimeId();
                String user_id = dbHandler.getUserId();
                work_status = "Stop";
                play_pause_comment = play_pause_comment.replaceAll("\\s+", " ");
                play_pause_comment = play_pause_comment.replaceAll(" ", "^");
                sendDataForTimesheet(userId, task_id, work_status, time_id, play_pause_comment);
                if (timesheet_response.equals("done")) {
                    worker.shutdownNow();
                    screeShotListener.shutdownNow();
                    main_timeid = "";
                    lblStatus1.setText(work_status);
                    lbl_PlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                    lbl_Stop.setEnabled(false);
                    working_task = false;
                    task_paused = false;
                    win.setWorkingStatus(false);
                    showInfoDialog("Task stopped successfully!");
                    refresh();
                    stopped = true;
                } else {
                    working_task = true;
                    win.setWorkingStatus(true);
                    work_status = "Working";
                    lblStatus1.setText(work_status);
                    stopped = false;
                }
            } catch (SQLException | IOException ex) {
                hd.errorDescription("ErrorDescription11", ex);
                showInfoDialog("Connection error! Please try again.");
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            done = false;
            stopped = false;
        }
        return stopped;
    }
    /**
     * this function is check whether the given string is Integer 
     */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    /**
     * This function will display the Message Dialog box
     */
    public void showInfoDialog(String Info) {
        InfoDialog inf = new InfoDialog(dialog, true);
        inf.setLocationRelativeTo(dialog);
        inf.setInfo(Info);
        inf.setVisible(true);
    }
    /**
     * This function will display the Loader relative to panel.panelLoginContainer
     */
    public void showLoaderDialog() {
        infD.setLocationRelativeTo(panel.panelLoginContainer);
        infD.setVisible(true);
    }
    /**
     * This function will display the Loader relative to TaskPanel.panelforScrollPane
     */
    public void showLoaderDialog2() {
        infD.setLocationRelativeTo(TaskPanel.panelforScrollPane);
        infD.setVisible(true);
    }
    /**
     * This function will display the Loader relative to WaitingPanel.panelWaitingContainer
     */
    public void showLoaderDialog3() {
        infD.setLocationRelativeTo(WaitingPanel.panelWaitingContainer);
        infD.setVisible(true);
    }
    /**
     * This function will display the Loader relative to ForgotPwdPanel.panelForgotPwdContainer
     */
    public void showLoaderDialog4() {
        infD.setLocationRelativeTo(ForgotPwdPanel.panelForgotPwdContainer);
        infD.setVisible(true);
    }
    
    public MouseListener minMaxListener = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() == lblRefresh) {
                firstLogin = false;
                first_refresh = true;
                //              System.out.println(firstLogin);
                new AnswerWorker().execute();
//                    lblWaitingProgress.setVisible(true);
                showLoaderDialog3();
            }

            if (e.getSource() == panel.lblMinimize || e.getSource() == TaskPanel.lblMinimize3 || e.getSource() == WaitingPanel.lblMinimize2 || e.getSource() == ForgotPwdPanel.lblMinimize2) {
                timer3.setInitialDelay(0);
                timer3.setDelay(10);
                timer3.start();
            }

            if (e.getSource() == panel.lblClose || e.getSource() == TaskPanel.lblClose3 || e.getSource() == WaitingPanel.lblClose2 || e.getSource() == ForgotPwdPanel.lblClose2) {

                dialog.setVisible(false);
               
            }
            if (e.getSource() == trayIcons.trayIcon) {
               
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showMainDialog();
                }
              
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
            
            if (e.getSource() == lblRefresh) {
                lblRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == lblRefresh) {
                lblRefresh.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }
    };

    public void showMainDialog() {
        //    timer3.stop();
        dialog.setVisible(true);
        height = 460;
        dialog.setLocation(screenRect.width - (dialog.getWidth() + 1),
                screenRect.height - height);
        dialog.repaint();
        IconifyUtilityClass.restore(dialog);
    }
    /**
     *This function Show The Hand Cursor at specific area
     */
    public MouseListener showHandCursor = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

            if (e.getSource() == TaskAddedDialog.panelTaskAdded || e.getSource() == TaskAddedDialog.lblInfo) {
                height2 = 0;
                taskDialog.dispose();
                taskUpdated = false;
                showMainDialog();
                new BackgroundWorker().execute();
                showLoaderDialog2();
            }
            
            if(e.getSource() == TaskPanel.chat){
                System.out.println("Chat COde HERE");
                //SelectChatUser selectChatUser = new SelectChatUser(dialo, true);
                selectChatUser.setLocationRelativeTo(dialog);
                selectChatUser.chatList.setSelectedIndex(0);
                selectChatUser.setVisible(true);
               
            }
            
            if (e.getSource() == TaskPanel.lblSignOut2) {
                
                signOut.setLocationRelativeTo(dialog);
                signOut.setVisible(true);
               
                boolean sign = signOut.flagResult();
                System.out.println("sssssssss+++"+sign);
                if(sign==true){
                    signOutBlock("");
//                
                }
                else{}
               // signOutBlock("");
            }
            if (e.getSource() == TaskPanel.lblRefreshMain) {

                new BackgroundWorker().execute();
                showLoaderDialog2();
            }
            if (e.getSource() == panel.lblForgotPwd) {
                panel.setVisible(false);
                panelForgotPwd.setVisible(true);
                dialog.setContentPane(panelForgotPwd);
                dialog.repaint();
            }
            if (e.getSource() == ForgotPwdPanel.lblDx || e.getSource() == panel.lblDx || e.getSource() == WaitingPanel.lblDx || e.getSource() == TaskPanel.lblDx) {
                try {
                    act.openWebpage(new URL("http://"+Config.URL));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (e.getSource() == panel.lblAccount) {
                try {
                    act.openWebpage(new URL(Config.HTTP+Config.DOMAIN + "users/register"));
                   
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (e.getSource() == ForgotPwdPanel.lblGoLogin) {
                panel.setVisible(true);
                panelForgotPwd.setVisible(false);
                dialog.setContentPane(panel);
                dialog.repaint();
                ForgotPwdPanel.txtEmail.setText("");
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

            if (e.getSource() == ForgotPwdPanel.lblGoLogin) {
                setHandCursor(ForgotPwdPanel.lblGoLogin);
            }
            if (e.getSource() == TaskAddedDialog.panelTaskAdded || e.getSource() == TaskAddedDialog.lblInfo) {
                setHandCursor(TaskAddedDialog.panelTaskAdded);
                setHandCursor(TaskAddedDialog.lblInfo);

            }
            if (e.getSource() == panel.lblForgotPwd) {
                setHandCursor(panel.lblForgotPwd);
            }
            if (e.getSource() == TaskPanel.lblSignOut2) {
                setHandCursor(TaskPanel.lblSignOut2);
            }
            if (e.getSource() == TaskPanel.lblRefreshMain) {

                setHandCursor(TaskPanel.lblRefreshMain);
            }
            if (e.getSource() == ForgotPwdPanel.lblDx) {
                setHandCursor(ForgotPwdPanel.lblDx);
            }
            if (e.getSource() == panel.lblDx) {

                setHandCursor(panel.lblDx);
            }
            if (e.getSource() == WaitingPanel.lblDx) {
                setHandCursor(WaitingPanel.lblDx);
            }
            if (e.getSource() == TaskPanel.lblDx) {

                setHandCursor(TaskPanel.lblDx);
            }
            if (e.getSource() == panel.lblAccount) {
                setHandCursor(panel.lblAccount);
            }
            if (e.getSource() == TaskDetailDialog.lblCross) {
                setHandCursor(TaskDetailDialog.lblCross);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == panel.lblForgotPwd) {

                setDefaultCursor(panel.lblForgotPwd);
            }

            if (e.getSource() == TaskAddedDialog.panelTaskAdded || e.getSource() == TaskAddedDialog.lblInfo) {
                setDefaultCursor(TaskAddedDialog.lblInfo);
                setDefaultCursor(TaskAddedDialog.panelTaskAdded);
            }
            if (e.getSource() == TaskPanel.lblSignOut2) {
                setDefaultCursor(TaskPanel.lblSignOut2);
            }
            if (e.getSource() == TaskPanel.lblRefreshMain) {
                setDefaultCursor(TaskPanel.lblRefreshMain);
            }
            if (e.getSource() == ForgotPwdPanel.lblDx) {
                setDefaultCursor(ForgotPwdPanel.lblDx);
            }
            if (e.getSource() == panel.lblDx) {
                setDefaultCursor(panel.lblDx);
            }
            if (e.getSource() == WaitingPanel.lblDx) {
                setDefaultCursor(WaitingPanel.lblDx);
            }
            if (e.getSource() == TaskPanel.lblDx) {
                setDefaultCursor(TaskPanel.lblDx);
            }
            if (e.getSource() == panel.lblAccount) {
                setDefaultCursor(panel.lblAccount);
            }
            if (e.getSource() == ForgotPwdPanel.lblGoLogin) {
                setDefaultCursor(ForgotPwdPanel.lblGoLogin);
            }

        }

        public void setDefaultCursor(Component cmp) {
            cmp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

        public void setHandCursor(Component cmp) {
            cmp.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    };
    /**
     *This function is used for sign-out
     */
    public  void signOutBlock(String exit_tag) {
        
        try {
            System.out.println("Enter111");
            if (working_task) {
                showMainDialog();
                showInfoDialog("Please stop working task first!");
            }else if(ChattingWindow.counting>0){
                showMainDialog();
                showInfoDialog("Close the chat window!");
            }else {
                
                  System.out.println("Enter222");
                  
                  
                if(true){System.out.println("yes");}
                else{System.out.println("no");}
                if (!task_reader_thread.isShutdown()) {
                    // refresh_worker.shutdownNow();
                    task_reader_thread.shutdownNow();
                      System.out.println("Enter333");
                }

                if (cd.isVisible()) {
                    System.out.println("Enter444");
                    cd.setVisible(false);
                    interruptDialog = true;
                }
                if (in2.isVisible()) {
                    in2.setVisible(false);
                }
                TrackPacket.stop_track = true;

                if (!"exit".equals(exit_tag)) {
                    track_net_thread.interrupt();
                    System.out.println("track_net_thread alive" + track_net_thread.isAlive());
                }

                onSignOut();
                working_task = false;
                firstLogin = false;

                //              System.out.println(firstLogin);
                TaskPanel.panelforScrollPane.removeAll();
                countPanels = 0;
                taskPanel.setVisible(false);
                panel.setVisible(true);
                dialog.setContentPane(panel);
                dialog.repaint();
                trayIcons.enableRefresh(false);
                trayIcons.enableSignOut(false);
            }

        } catch (SQLException ex) {
            hd.errorDescription("ErrorDescription12", ex);
            showInfoDialog("Connection error");
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *this will remove all the task in the list and call to readPlayPausetask method.
     */
    public void refresh() throws SQLException {
        TaskPanel.panelforScrollPane.removeAll();
        countPanels = 0;
        //      String uname = dbHandler.getUserName();
        String uname = main_username;
        readPlayPauseTask(uname, main_password);
   }
    /**
     *Parse the JSON data and send the string response to user
     */
    public String jsonData(String data){
    
        JSONObject obj = new JSONObject(data);
        String response = obj.getString("response");
        String id = obj.getString("id");
        DatabaseHandlerId databaseHandlerId=new DatabaseHandlerId();
        //databaseHandlerId.deleteId();
        databaseHandlerId.insertId(id);
        return response;
    }
    
    /**
     *just Parse the JSON data 
     */
     public void json_Data(final String data){
    
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                JSONObject obj = new JSONObject(data);
                String response = obj.getString("response");
                String id = obj.getString("id");
                DatabaseHandlerId databaseHandlerId=new DatabaseHandlerId();
                //databaseHandlerId.deleteId();
                databaseHandlerId.insertId(id);
            }
        });
        thread.start();
    }
    /**
     *Stop the task in TimeSheet when user task is already working or Pause at First Time
     */
     void stopTimesheet(final String userid, final String taskid) throws MalformedURLException, IOException {
        trackingFirstTime=false;
        firstTimeUser="false";
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                
                double i=random.nextDouble()+random.nextDouble();
                k+=1;
                String timesheetid = new DatabaseHandlerId().getId();
                System.out.println("\n\n\nTTT------->>>>>>\n\n\n"+timesheetid);
                URL url11=null;
                try {
                    url11 = new URL(Config.HTTP+Config.DOMAIN + "TimeSheets/getSheet/" + userid + "/" + taskid + "/Stop/" + timesheetid +
                            "/UnExpectedly%20Task%20has%20been%20Stoped,%20due%20to%20bad%20Internet%20Connection%20or%20System%20ReStart!?"+k+"naman"+i);
                    System.out.println("NamanSendDataForTimeSheet"+url11);
                    StringBuilder responseString = getStreamResponse(url11);
                    json_Data(responseString.toString());
                    taskDialog.setMsg("Task stopped!");
                            timer4.setInitialDelay(0);
                            timer4.setDelay(10);
                            timer4.start();
                            taskDialog.setVisible(true);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                } 
                 
                
            }
        });
        thread.start();
    }
    /**
     *this function is used for Stop the user Task
     */
    void sendDataForTimesheet(String userid, String taskid, String status, String timesheetid, String comment) throws MalformedURLException, IOException {
        double i=random.nextDouble()+random.nextDouble();
        k+=1;
        timesheetid = new DatabaseHandlerId().getId();
        System.out.println("\n\n\nTTT------->>>>>>\n\n\n"+timesheetid);
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "TimeSheets/getSheet/" + userid + "/" + taskid + "/" + status + "/" + timesheetid + "/" + comment+"?"+k+"naman"+i);
        System.out.println("NamanSendDataForTimeSheet"+url11);
        StringBuilder responseString = getStreamResponse(url11);
        timesheet_response = jsonData(responseString.toString());
        System.out.println("Naman_sendDataForTimesheet-timesheet_response"+timesheet_response);
    }
    /**
     *this function is used for Pause the user Task
     */
    void sendDataForTimesheetToPause(String userid, String taskid, String status, String timesheetid, String comment,String time) throws MalformedURLException, IOException {
        double i=random.nextDouble()+random.nextDouble();
        k+=1;
        timesheetid = new DatabaseHandlerId().getId();
        System.out.println("\n\n\nTTT------->>>>>>\n\n\n"+timesheetid);
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "TimeSheets/getSheet/" + userid + "/" + taskid + "/" + status + "/" + timesheetid + "/" + comment +"/" + null +"/" +time+"?"+k+"naman"+i);
        System.out.println("NamanSendDataForTimesheetToPause"+url11);
        StringBuilder responseString = getStreamResponse(url11);
        timesheet_response = jsonData(responseString.toString());
        System.out.println("Naman_sendDataForTimesheetToPause-timesheet_response"+timesheet_response);
    }
    /**
     *this function is used for Done the user Task
     */
    void bDoneNComplete(String userid, String taskid, String status, String comment,String oId, String assign )  {
        try {
            double i=random.nextDouble()+random.nextDouble();
            k+=1;
            String timesheetid = new DatabaseHandlerId().getId();
            comment=comment.replaceAll("\n","");
            System.out.println("comment  ---->>>>>"+comment);
            URL url11 = new URL(Config.HTTP+Config.DOMAIN + "TimeSheets/markDone/" + userid + "/" + taskid + "/" + status + "/" + comment + "/" + oId +"/"+assign+"/"+timesheetid+"?"+k+"naman"+i);
            System.out.println("NamanbDoneNComplete"+url11);
            StringBuilder responseString;
            try {
                responseString = getStreamResponse(url11);
                timesheet_response = jsonData(responseString.toString());
                 System.out.println("Naman_bDoneNComplete-timesheet_response"+timesheet_response);
            } catch (IOException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            } catch (MalformedURLException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    /**
     *this function is used for Hold to working the user Task
     */
    void bOnHold(String taskid, String comment, String status)  {
        try {
            double i=random.nextDouble()+random.nextDouble();
            k+=1;
            comment=comment.replaceAll("\n","");
            URL url11 = new URL(Config.HTTP+Config.DOMAIN + "Tasks/startHoldTask/" + taskid + "/" + comment + "/" + status +"?"+k+"naman"+i);
            System.out.println("NamanbDoneNComplete"+url11);
            StringBuilder responseString;
            try {
                responseString = getStreamResponse(url11);
                timesheet_response = responseString.toString();
                 System.out.println("Naman_bDoneNComplete-timesheet_response"+timesheet_response);
            } catch (IOException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void sendErrorDescription(final String a){
        System.out.println("aaaaaa--> "+a);
        Thread t1 =new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("userid-"+userId);
                System.out.println("taskid-"+task_id);
                System.out.println("userid-"+"TODO");
                System.out.println("userid-"+a);
                //System.out.println("userid-"+oId);
            }
        });
        t1.start();
    }
    /**
     *this function is used for ToDo the user Task
     */
     void bToDo(String userid, String taskid, String status, String comment,String oId, String assign )  {
        try {
            double i=random.nextDouble()+random.nextDouble();
            k+=1;
            comment=comment.replaceAll("\n","");
            URL url11 = new URL(Config.HTTP+Config.DOMAIN + "TimeSheets/markDone/" + userid + "/" + taskid + "/" + status + "/" + comment + "/" + oId +"/"+assign+"?"+k+"naman"+i);
            System.out.println("NamanbDoneNComplete"+url11);
            StringBuilder responseString;
            try {
                responseString = getStreamResponse(url11);
                timesheet_response = responseString.toString();
                 System.out.println("Naman_bDoneNComplete-timesheet_response"+timesheet_response);
            } catch (IOException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *this function is used for Working the user Task
     */
    public void sendPlayRequest(String userid, String taskid, String status) throws MalformedURLException, IOException, SQLException {
        
        double i=random.nextDouble()+random.nextDouble();
        k+=1;
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "TimeSheets/getSheet/" + userid + "/" + taskid + "/" + status+"?"+k+"naman"+i);
        System.out.println("NamanSendPlayRequest : "+url11);
        StringBuilder responseString = getStreamResponse(url11);
        System.out.println("NamanResponseString"+responseString);
        timesheet_id = responseString.toString();
        timesheet_response = responseString.toString();
        System.out.println("Naman_sendPlayRequest-timesheet_response"+timesheet_response);
        main_timeid = timesheet_id;
        if(status.equals("Done")){
            
        }else{
            new DatabaseHandlerId().insertId(timesheet_id);
        }    
        //dbHandler.insertTimesheetId(main_timeid);
    }
    
    public void sendDoneRequest(String userid, String taskid, String status,String subtasks_ids) throws MalformedURLException, IOException, SQLException {
        double i=random.nextDouble()+random.nextDouble();
        k+=1;
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "TimeSheets/getSheet/" + userid + "/" + taskid + "/" + status +"/" + null+ "/" +null+"/" + subtasks_ids+"?"+k+"naman"+i);
        System.out.println("NamanSend"+url11);
        StringBuilder responseString = getStreamResponse(url11);
        timesheet_id = responseString.toString();
        timesheet_response = responseString.toString();
        main_timeid = timesheet_id;
        
        //dbHandler.insertTimesheetId(main_timeid);
    }
    /**
     *this function is used for request a Password
     */
    void requestPassword(String username) {
        try {
            double i=random.nextDouble()+random.nextDouble();
            k+=1;
            URL url11 = new URL(Config.HTTP+Config.DOMAIN + "users/remotereset/" + username+"?"+k+"naman"+i);
            System.out.println("NaRequestPassword"+url11);
            StringBuilder responseString = getStreamResponse(url11);
            server_response = responseString.toString();
        } catch (IOException ex) {
            hideLoaderDialog();
            hd.errorDescription("ErrorDescription13", ex);
            showInfoDialog("Connection error! Please try again.");
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exitApp() {
        dialog.dispose();
        dbHandler.closeConnection();
        System.exit(0);
    }

    void sendLoginDetailsInUrl(String user, String ur) throws MalformedURLException, IOException {
        server_response = "";
        countInXml += 1;
        URL url11 = new URL(ur);
        int code=1;
        if(firstLogin){
             code= readxml.getStatusCode(url11);
        }
        System.out.println("status code is"+code);
            if(code==5){
                showInfoDialog("Unknownhost or Wrong Alias");
            }else{
            try {
                dbHandler.saveCompanyAlias(company_alias);
            } catch (SQLException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
                 StringBuilder responseString = getStreamResponse(url11);
        server_response = responseString.toString();
        System.out.println();
        System.out.println("sendLoginDetailsInUrl response is>>" + server_response);
        System.out.println();
        if (!server_response.equals("Waiting") && !server_response.equals("sorry") && !server_response.equals("Waiting..")) {
            System.out.println("Reading login xml");
            System.out.println("USER - : "+user);
            readLoginXml(user);
        }
            }       
    }
    KeyListener kl = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getSource() == panel.txtUserName || e.getSource() == panel.txtPassword || e.getSource() == panel.btnLogin) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginListener();
                }
            }
            if (e.getSource() == ForgotPwdPanel.btnSubmit || e.getSource() == ForgotPwdPanel.txtEmail) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    forgotPwdListener();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };

    public void readLoginXml(String user) throws MalformedURLException {
        URL url2 = new URL(Config.HTTP+Config.DOMAIN + "users/url1/" + user_name + "/" + password + "/" + system_name + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" + os_name + "/" + sys_hdd);
        System.out.println(url2);

        InputStream is = null;
        try {
            InputStream in = url2.openStream();
            Document doc = parse(in);
            doc.getDocumentElement().normalize();
            System.out.println("DOC - :"+doc);
            userId = getLoginElements(doc, "user_id");
            System.out.println("userId - "+userId);
            imgUrl = getLoginElements(doc, "image");
            System.out.println("imgUrl - "+imgUrl);
            name = getLoginElements(doc, "name");
            System.out.println("name - "+name);
            TaskPanel.lblUserName.setText(name);
            chatUserName=name;
        } catch (IOException | DOMException ex) {
            hd.errorDescription("ErrorDescription14", ex);
            showInfoDialog("Connection error! Please try again.");
        }
    }

    public String getLoginElements(Document doc, String tag) {
        NodeList listOfItems = doc.getElementsByTagName(tag);
        Node node_item = listOfItems.item(0);
        String item = node_item.getFirstChild().getNodeValue().toString();
        return item;
    }

    public static void main2(String[] args) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PopUpLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PopUpLogin().launchTimesheet();

            }
        });
    }
    
     public void getAllChat(final String userid)  {
         
         Thread getChat = new Thread(new Runnable() {

             @Override
             public void run() {
                 try {
                    double i=random.nextDouble()+random.nextDouble();
                    k+=1;

                    URL url = new URL(Config.HTTP+Config.DOMAIN + "employee/conversations/allChat/" + userid +"?"+k+"naman"+i);
                    System.out.println("NamanSetChat : "+url);
                    StringBuilder responseString = getStreamResponse(url);
                    System.out.println("NamanSetChatResponseString"+responseString);

                    System.out.println("-->>"+responseString.toString().equals("empty"));
                    if(responseString.toString().equals("empty")){
                        //pending code..
                    }
                    else{
                        URLConnection con = url.openConnection();
                        InputStream in = con.getInputStream();

                        Document doc = parse(in);

                        NodeList listOfCodes = doc.getElementsByTagName("chat");
                        int totalChat = listOfCodes.getLength();

                        System.out.println("Total NO. of Chat.."+totalChat);
                        
                        if(totalChat>allCount){
                            allCount=totalChat;
                        }
                        
                        for (int temp = 0; temp < totalChat; temp++) {
                           Node nNode = listOfCodes.item(temp); 

                            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element eElement = (Element) nNode;
                                String userId = getElements(eElement, "user_id");
                                String msg = getElements(eElement, "text");
                                String name = getElements(eElement, "name");
                                String time = getElements(eElement, "time");
                                
                                
                                boolean checkUser=true;
                                 
                                if(!(ChattingWindow.openUser.isEmpty())){
                                    int size=ChattingWindow.openUser.size();
                                    String nameId;
                                    for(int num=0; num<size;num++){
                                        nameId=ChattingWindow.openUser.get(num);
                                        if(userId.equals(nameId)){
                                            checkUser=false;
                                        }
                                    }
                                }
                                if(checkUser){
            
                                    ChattingWindow ch =new ChattingWindow();
                                    ch.getChat(PopUpLogin.userId, userId,name);
                                    //ch.createXMPPConnection(); 
                                    //ch.addXMPPAccount(PopUpLogin.chatUserName,"welcome");
                                    ch.chatConnection(name,userId);

                                    ch.setVisible(true);
                                }
                                
                                
                            }
                        }
                    }

                } catch (MalformedURLException ex) {
                    Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
             
             }
         });
        
        getChat.start();
    }
    
    public void getAllChatLength(final String userid)  {
        
        
                try {
                    double i=random.nextDouble()+random.nextDouble();
                    k+=1;
                    URL url = new URL(Config.HTTP+Config.DOMAIN + "employee/conversations/allChat/" + userid +"?"+k+"naman"+i);
                    StringBuilder responseString = getStreamResponse(url);
                    if(responseString.toString().equals("empty")){
                      //code here pending    
                    }
                    else{
                        URLConnection con = url.openConnection();
                        InputStream in = con.getInputStream();
                        Document doc = parse(in);
                        NodeList listOfCodes = doc.getElementsByTagName("chat");
                        allCount = listOfCodes.getLength();
                        System.out.println("ssTotal NO. of Chat.."+allCount);
                    }

                } catch (MalformedURLException ex) {
                    Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(ChattingWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
       
    }
    
}
