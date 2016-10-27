/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

/**
 *
 * @author Me
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
import java.util.ArrayList;

import java.util.List;

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
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PopUpLogin {

    Border raisedBorder;
    JPanel contentPane;
    String email, password, host = "smtp.gmail.com", port = "465";
    String client_code, project_code, task, start_time, date, end_time, status;
    String mac_address;
    String forgotton_user;
    String server_response;
    String router_ip, os_name, system_name, sys_ram, sys_hdd, cpu_model;
    String timesheet_response;
    String userName;
    String userId, task_id, work_status, play_pause_comment, timesheet_id;
    String[] status_array;
    String[] p_code;
    static String name, imgUrl, user_name;
    String company_alias;
    public static String usr;
    String url = "http://www.designersx.com";
   
    private final String ROUTER_IP_DOMAIN = "http://api.externalip.net/ip/";
    private final String ROUTER_IP_DOMAIN2 = " http://checkip.amazonaws.com/";
    private int height = 0;
    int height2 = 0;
    static int height3 = 0;
    int count = 0;
    int countPanels = 0;
    int countRecords = 0;
    int countInXml = 0;
    int taskLength = 0;
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
    boolean task_paused = false;
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
    ReadXml readxml = new ReadXml();
    LoaderDialog infD;
    ConfirmDialog cd;
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
    JSeparator seperator = new JSeparator(JSeparator.HORIZONTAL);
    private JFrame dialog = new JFrame() {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        @Override
        public void paint(Graphics g) {
            g.setClip(0, 0, getWidth(), height);
            super.paint(g);
        }
    };
    public Timer timer = new Timer(1, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            height += 4;
            if (height >= dialog.getHeight()) {
                timer.stop();
                trayIcons.setTrayIcon("/images/tray_disabled.png");
                trayIcons.trayIcon.addMouseListener(minMaxListener);
                new Thread(getIdleTimeThread).start();
                //      new CheckedList();
                //    trc.setDialog(dialog);
                //    TrackPacket.stop_track=false;                                  
                //         track_net_thread=new Thread(trc.trackNet);
                //        track_net_thread.start();
                //  showInfoDialog("<html><p width=\"155px\">" +"One of your task has been paused. Please resume paused task!"+ "</p></html>");
                System.out.println(System.getProperty("java.ext.dirs"));
                //     showInfoDialog("<html><p width=\"155px\">" +System.getProperty("java.ext.dirs")+ "</p></html>");

            }
            dialog.setLocation(screenRect.width - (dialog.getWidth() + 1),
                    screenRect.height - height);
            dialog.repaint();
        }
    });
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

    public static boolean getVisibility() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(jLbl);
        return topFrame.isVisible();
    }

    public void setTaskbarIcon() {
        icons.add(TrayIconUtility.createImage("/images/taskbar.png", "taskbar"));
        //    icons.add(TrayIconUtility.createImage("/dx/timesheet/taskbar.png", "tray icon"));
        dialog.setIconImages(icons);
    }

    public String getIpAddress(URL url) throws MalformedURLException, IOException, ConnectException {
        URL myIP = url;//new URL(ROUTER_IP_DOMAIN);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(myIP.openStream()));
        return in.readLine();
    }

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

    public void onSignOut() throws SQLException {
        //    dbHandler.deleteRecord();
        main_userid = "";
        main_name = "";
        main_imgurl = "";
        main_timeid = "";
        main_username = "";
        main_password = "";
        ReadXml.isPresent = false;

        //   track_net_thread.interrupt();
        trayIcons.updateTrayIcon("/images/tray_disabled.png");
        panel.lblStatus1.setText("Sign in");
    }
    Runnable updateTimesheetThread = new Runnable() {
        @Override
        public void run() {
            try {
                sendDataForTimesheet(userId, task_id, "Progress", timesheet_id, "In^Progress");
            } catch (MalformedURLException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    Runnable refreshListThread = new Runnable() {
        @Override
        public void run() {
            try {
                refresh();
                //          System.out.println("Refreshed");
            } catch (SQLException ex) {
                //  showInfoDialog("Error! Click on refresh!");
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    Runnable getIdleTimeThread = new Runnable() {
        @Override
        public void run() {
            win.getIdleTime();

            //     if(win.getPauseStatus()){
            //        try {
            //           refresh();
            //       } catch (SQLException ex) {
            //           Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            //        }
            //      }

        }
    };
    Runnable tracking_thread = new Runnable() {
        @Override
        public void run() {
            //      try {
            win.trackTime(usr);
            //         hd.getScreenShot(usr, "60");
            //      catch(InterruptedException e){
            //       Thread.currentThread().interrupt(); 
            //       Thread.currentThread().interrupt();
            //       } catch (AWTException ex) {
            //          Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            //       }

        }
    };
    Runnable refreshToReadTaskLength = new Runnable() {
        @Override
        public void run() {
            try {
                length = readxml.readTaskLength(main_username, main_password);
                //        System.out.println("Task Read refresh");
                //        System.out.println(firstLogin);
                if (firstLogin) {
                    //  main_tasklength=length;
                    System.out.println("Task length is:" + main_tasklength);
                }
                if (length > main_tasklength) {
                    System.out.println("Task Added");
                    taskUpdated = true;
                    // main_tasklength=length;
                    //     taskDialog.setShape(new RoundRectangle2D.Double(2, 5, 290, 130, 15, 15));
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

                    System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<win.getpausestatus is true>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    try {
                        refresh();
                        if (!worker.isShutdown()) {
                            System.out.println("Worker thread stopped!");
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

                //           System.out.println(firstLogin);
            } catch (MalformedURLException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public void launchTimesheet() {
        try {
            //    UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
            hd.createScreenshotDirectory();
            dbHandler.connect();
            panel.txtCompanyAlias.setText(dbHandler.getAlias());
            
            panel.lblAccount.setVisible(false);
            //        LoginPanel.txtUserName.addAncestorListener(new RequestFocusListener(true));
            //      LoginPanel.txtPassword.addAncestorListener(new RequestFocusListener(true));

            setTaskbarIcon();
            //      dbHandler.connect();
            dialog.setTitle("Timesheet   ");
            cpu_model = hd.processorModel();
            //           System.out.println(cpu_model);
            sys_ram = hd.getRamSize();
            //         System.out.println(sys_ram);
            os_name = hd.getOsName();
            //         System.out.println(os_name);
            sys_hdd = hd.getHarddisk();
            //        System.out.println(sys_hdd);
            setUnderlineParameters();
            raisedBorder = BorderFactory.createLineBorder(Color.yellow, 2);
            //   jLbl.setBounds(0, 0, 50, 20);
            //  jLbl.setVisible(true);
            //    dialog.add(jLbl);
            dialog.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
            dialog.setUndecorated(true);
            dialog.setAlwaysOnTop(true);
            dialog.setShape(new RoundRectangle2D.Double(0, 5, 290, 460, 15, 15));
            //    Container cont= getContentPane();
            JComponent cont = (JComponent) dialog.getContentPane();
            cont.setBorder(raisedBorder);
            //    dialog.setSize(panelDimension);
            int width = panelDetails.getWidth();
            int height = panelDetails.getHeight();
            //    os_name = "os.name";
            task_reader_thread = Executors.newSingleThreadScheduledExecutor();
            screeShotListener = Executors.newSingleThreadScheduledExecutor();
            system_name = InetAddress.getLocalHost().getHostName();
            //             System.out.println(system_name);
            //       getMacAddress();
            //       dbHandler.deleteRecord();
            //       dbHandler.checkLoginSession();
            //       if (loginStatus) {
            //            taskPanel.setPreferredSize(panelDimension);
            //           readPlayPauseTask(user_name);
            //           dialog.setContentPane(taskPanel);
            //         System.out.println("Width is>>"+width1);
            //         System.out.println("Height is>>"+height1);

            //           TaskPanel.lblUserName.setText(name);
            //  dialog.repaint();
            //           trayIcons.setTrayIcon("/dx/timesheet/tray_enabled.png");

            //           setUserPic(imgUrl);
            //         } else {

            panel.setPreferredSize(panelDimension);
            //          dialog.getRootPane().setDefaultButton(LoginPanel.btnLogin);
            dialog.setContentPane(panel);
            panel.txtUserName.setText(userName);
            //       }

            //      panel.setBackground(Color.WHITE);
            //      panel.setLayout(null);

            stask = new TaskWithSubtaskDialog(dialog, true);
            reviewDlg = new ReviewDialog(dialog, true);
            reviewDlg.setDialog(dialog);
            in2 = new InputDialog(dialog, true);
            pauseDialog = new PauseOptionDialog(dialog, true);
            cd = new ConfirmDialog(dialog, true);
            ed = new ExtendDeadlineDialog(dialog, true);
            ed.setDialog(dialog);
            infD = new LoaderDialog(dialog, true);
            //  idle_listener = Executors.newSingleThreadScheduledExecutor();
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
            showInfoDialog("Connection error! Try again.");
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SigarException ex) {
            hideLoaderDialog();
            showInfoDialog("Connection error! Try again.");
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void hideLoaderDialog() {
        if (infD.isVisible()) {
            infD.setVisible(false);
        }
    }
    ActionListener tray_icons_actionlistener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == TrayIconUtility.signout) {
                if (taskUpdated) {
                    showMainDialog();
                    showInfoDialog("Please click dialog below!");
                } else {

                    signOutBlock("");
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

    public void doInLogin() throws IOException, SQLException {
        if (firstLogin) {
            System.out.println(firstLogin + "block called");
            System.out.println(Config.HTTP+Config.DOMAIN + "/users/url/" + user_name + "/" + password + "/" + system_name + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" + os_name + "/" + sys_hdd);
            sendLoginDetailsInUrl(user_name, Config.HTTP+Config.DOMAIN + "/users/url/" + user_name + "/" + password + "/" + system_name + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" + os_name + "/" + sys_hdd);
        }
        if (!firstLogin) {
            //          System.out.println(firstLogin+"block called");

            sendLoginDetailsInUrl(user_name,Config.HTTP+Config.DOMAIN + "/users/url1/" + user_name + "/" + password + "/" + system_name + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" + os_name + "/" + sys_hdd);
            // System.out.println(DOMAIN + "/users/url1/" + user_name + "/" + password + "/" + system_name + "/" + mac_address + "/" + router_ip);

        }
    }

    public void loginListener() {
        interruptDialog = false;
        firstLogin = true;
        System.out.println(firstLogin);
        try {
            try {
                getMacAddress();
            } catch (UnknownHostException | SocketException ex) {
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }
            //      LoginPanel.txtUserName.set("-fx-focus-color: transparent;");
            //    LoginPanel.txtUserName.setBorder(BorderFactory.createEmptyBorder());
            //   LoginPanel.txtPassword.setBorder(BorderFactory.createEmptyBorder());
            user_name = panel.txtUserName.getText();
            password = panel.txtPassword.getText();
            company_alias=panel.txtCompanyAlias.getText();
                
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
                    //   if (user_name.contains("@")) {
                    //    String[] part = user_name.split("@");
                    //     String user1 = part[0];
                    //    String user2 = part[1];
                    //     System.out.println(user1);
                    //     if (!user1.equals("") && "gmail.com".equals(user2)) {
                    //         user_name = user1;
                    //         new AnswerWorker().execute();
                    //         showLoaderDialog();
                    //     } else {
                    //         server_response = "";
                    //         showInfoDialog("Enter valid gmail id!");
                    //     }
                    //    } else {
                    new AnswerWorker().execute();
                    showMainDialog();
                    showLoaderDialog();
                    //    }
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
            showInfoDialog("Connection error! Try again.");
        }
    }

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

    public void setUserPic(String img) throws IOException, MalformedURLException {
        TaskPanel.lblPic.setSize(34, 34);
        int width1 = TaskPanel.lblPic.getWidth();
        int height1 = TaskPanel.lblPic.getHeight();
        System.out.println(img);
        URL imgurl = new URL(img);
        System.out.println("imgurl is>> " + imgurl);
        user_image = ImageIO.read(imgurl);
        Image resizedImage = user_image.getScaledInstance(TaskPanel.lblPic.getWidth(), TaskPanel.lblPic.getHeight(), Image.SCALE_SMOOTH);
        TaskPanel.lblPic.setIcon(new ImageIcon(resizedImage));
    }

    public void setUnderlineParameters() {
        panel.lblForgotPwd.setText("<HTML><U>Forgot Password?<U><HTML>");
        panel.lblAccount.setText("<HTML><U>Don't have an account?<U><HTML>");
        TaskPanel.lblSignOut2.setText("<HTML><U>Sign Out<U><HTML>");
        TaskPanel.lblRefreshMain.setText("<HTML><U>Refresh<U><HTML>");
        panel.lblDx.setText("<HTML><U>www.designersx.com<U><HTML>");
        WaitingPanel.lblDx.setText("<HTML><U>www.designersx.com<U><HTML>");
        TaskPanel.lblDx.setText("<HTML><U>www.designersx.com<U><HTML>");
        ForgotPwdPanel.lblDx.setText("<HTML><U>www.designersx.com<U><HTML>");
        ForgotPwdPanel.lblGoLogin.setText("<HTML><U>Go to login<U><HTML>");
    }
    //Run Background task here 

    public void setMouseListeners() {
        //    lblPlay.addMouseListener(showHandCursor);
        TaskPanel.lblSignOut2.addMouseListener(showHandCursor);
        TaskPanel.lblRefreshMain.addMouseListener(showHandCursor);
        //        lblSignOut.addMouseListener(showHandCursor);
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
        //     TaskDetailDialog.lblCross.addMouseListener(showHandCursor);
//        TaskAddedDialog.iconInfo.addMouseListener(showHandCursor);
        panel.txtUserName.addKeyListener(kl);
        panel.txtPassword.addKeyListener(kl);
        panel.btnLogin.addKeyListener(kl);
        ForgotPwdPanel.btnSubmit.addKeyListener(kl);
        ForgotPwdPanel.txtEmail.addKeyListener(kl);
    }

    public StringBuilder getStreamResponse(URL url) throws IOException {
        InputStream in;
        URLConnection con = url.openConnection();
        in = con.getInputStream();
        
        DataInputStream dis = new DataInputStream(new BufferedInputStream(in));
        String s;
        StringBuilder responseString = new StringBuilder("");
        while ((s = dis.readLine()) != null) {
            responseString.append(s);
        }
        return responseString;
    }

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
                    //         System.out.println("in else"+firstLogin);
                    main_imgurl = imgUrl;
                    setUserPic(main_imgurl);
                    firstLogin = true;
                    readPlayPauseTask(user_name, main_password);
                    firstLogin = false;
                    worker = Executors.newSingleThreadScheduledExecutor();
                    screeShotListener = Executors.newSingleThreadScheduledExecutor();
                    refresh_worker = Executors.newSingleThreadScheduledExecutor();
                    task_reader_thread = Executors.newSingleThreadScheduledExecutor();
                    //        if (firstLogin) {
                    //  refirstLoginfresh_worker.scheduleWithFixedDelay(refreshListThread, 0, 1, TimeUnit.MINUTES);
                    task_reader_thread.scheduleWithFixedDelay(refreshToReadTaskLength, 10, 20, TimeUnit.SECONDS);
                    //             System.out.println("Task Reader Started");
                    //       }


                    //       dbHandler.updateLoggedInUserTable(userId, name, user_name, imgUrl);
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
                    //      showInfoDialog("track_net_thread started");
                    //     firstLogin=false;
                }
            } catch (Exception e) {
                hideLoaderDialog();
                showInfoDialog("Connection error!try again!");
                e.printStackTrace();
            }
        }
    }

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
            //          System.out.println("unable to load XML: " + ex);
        }
        return ret;
    }

    public String getElements(Element element, String tag) {
        NodeList nodelist = element.getElementsByTagName(tag);
        System.out.println(nodelist.getLength());
        String value = "";
        if (nodelist.getLength() > 0) {
            Element elm = (Element) nodelist.item(0);
            value = elm.getChildNodes().item(0).getNodeValue();
            //    System.out.println(value);
        }
        return value;
    }

    public void readPlayPauseTask(String username, String pwd) {
        readxml.getUserList();                                       //read users from xml for review
        working_task = false;
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
        TaskPanel.panelforScrollPane.add(sPane);
        Long currentTimeStamp = hd.getCurrentTimestamp();

    //     Method[] methods = PopUpLogin.class.getMethods();                       //print methods of a class
	//    String[] names = new String[methods.length];
	//    for(int i = 0; i < methods.length; i++) {
	//	names[i] = methods[i].toString();
       //         System.out.println(methods[i].toString());
	//    }
        InputStream in = null;
        try {
            URL xmlUrl = new URL(Config.HTTP+Config.DOMAIN + "/users/xml/" + username + "/" + main_password);
            System.out.println(xmlUrl);
            
           
            StringBuilder responseString = getStreamResponse(xmlUrl);
            server_response = responseString.toString();
            if ("sorry".equals(server_response)) {
                dialog.repaint();
                JPanel panelNoTask = new JPanel();
                panel.setPreferredSize(new Dimension(100, 60));
                JLabel lbl = new JLabel("                              No Tasks Found");
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
//                System.out.println("Root element of this doc is "
//                        + doc.getDocumentElement().getNodeName());
                String uri = doc.getDocumentURI();

                NodeList listOfCodes = doc.getElementsByTagName("tasks");
                int totalTasks = listOfCodes.getLength();
                //            System.out.println(firstLogin);
                main_tasklength = totalTasks;
                status_array = new String[totalTasks];
                //           System.out.println("Total no of Tasks : " +temp++) totalTasks);
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
                        String start_date = getElements(eElement, "task_start_date");
                        String current_time = getElements(eElement, "time");
                        String end_date = getElements(eElement, "task_end_date");
                        String tracking = getElements(eElement, "tracking");
                        String screenshot = getElements(eElement, "screenshot");
                        String priority = getElements(eElement, "priority");
                        String assigned_by = getElements(eElement, "assigned_by");
                        String status2 = getElements(eElement, "status");
                        String subtasks = getElements(eElement, "subtasks");
                        List list = new ArrayList();
                        if (!"".equals(subtasks)) {
                            //     Node nNode = listOfCodes.item(temp);
                            //     NodeList itemlist = doc.getElementsByTagName("items");
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

                        Double a = hd.getTaskProgress(start_date, end_date, current_time);
                        PanelPlayPause.lblDeadlineDate.setText(hd.printDate(end_date));
                        if (a.intValue() <= 90) {
                            hd.setProgressBackground(new Color(1, 153, 1));      //green progressbar
                        }
                        if (a.intValue() > 90) {
                            hd.setProgressBackground(new Color(216, 0, 0));      //red progressbar
                        }
                        System.out.println("*****************************Percentage is:" + a + "***********************************");
                        PanelPlayPause.lblExtentDeadline.setVisible(false);
                        if (a > 100.00) {
                            System.out.println("Greater than 100");
                            PanelPlayPause.lblExtentDeadline.setVisible(true);
                        } else {
                            System.out.println("Less than 100");
                        }
                        PanelPlayPause.taskProgressbar.setValue(a.intValue());
                        PanelPlayPause.lblStatus.setText(status2);
                        PanelPlayPause.lblStatus.setVisible(false);
                        // PanelPlayPause.lblTaskI
                        PanelPlayPause.lblTimesheetId.setText(tracking);
                        PanelPlayPause.lblTimesheetId.setVisible(false);

                        status_array[temp] = status2;
                        switch (status2) {
                            case "Working":
                                working_task = true;
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
                                PanelPlayPause.lblStop.setEnabled(true);
                                PanelPlayPause.lblPlay.setToolTipText("Pause Task");
                                //   panel1.setBackground(new Color(129,255,190));
                                panel1.setBackground(new Color(236, 252, 244));
                                break;
                            case "Stop":
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                PanelPlayPause.lblStop.setEnabled(false);
                                PanelPlayPause.lblPlay.setToolTipText("Start Task");
                                break;
                            case "Pause":
                                working_task = true;
                                task_paused = true;
                                win.setWorkingStatus(false);
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/pauseresume.png")));
                                PanelPlayPause.lblStop.setEnabled(false);
                                PanelPlayPause.lblPlay.setToolTipText("Resume Task");
                                //   panel1.setBackground(new Color(129,255,190));
                                panel1.setBackground(new Color(236, 252, 244));
                                //  panel1.setBackground(new Color(144,238,144));
                                break;
                            case "Done":
                                PanelPlayPause.lblPlay.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));
                                PanelPlayPause.lblStop.setEnabled(false);
                                PanelPlayPause.lblPlay.setEnabled(false);
                                PanelPlayPause.lblDone.setEnabled(false);
                                break;
                        }

                        PanelPlayPause.lblTask.setText(client_code1 + ":" + project_code1 + ":" + task);
                        if (task.length() > 30) {
                            PanelPlayPause.lblTask.setToolTipText("<html><p width=\"250px\">" + task + "</p></html>");
                        }
                        if (task.length() < 30) {
                            PanelPlayPause.lblTask.setToolTipText(task);
                        }
                        PanelPlayPause.lblAssigned.setText("Priority : " + priority);
                        panel1.addMouseListener(new MouseListener() {
                            JLabel lblStatus1 = (JLabel) panel1.getComponent(1);

                            @Override
                            public void mouseClicked(MouseEvent e) {
                                String pName = panel1.getName().toString();
                                //                      System.out.println(panel1.getName().toString());
                                //                       System.out.println("Name of Label at thtoStringis Panel is : " + panel1.getComponent(0).getName());
                                //           JLabel lbl = (JLabel) panel1.getComponent(0);
                                //           Component c = lbl.getRootPane();
                                //                     System.out.println("Text On Label1 at this Panel is : " + lbl.getText().toString());
                                //            JLabel lbl1 = (JLabel) panel1.getComponent(0);
                                //            System.out.println(panel1.getComponent(0).getName().toString());
                                System.out.println("Name of c1 is " + panel1.getComponent(1).getName());
                                System.out.println("Name of c2 is " + panel1.getComponent(2).getName());
                                System.out.println("Name of c3 is " + panel1.getComponent(3).getName());
                                System.out.println("Name of c4 is " + panel1.getComponent(4).getName());
                                System.out.println("Name of c5 is " + panel1.getComponent(5).getName());
                                System.out.println("Name of c6 is " + panel1.getComponent(6).getName());
                                System.out.println("Name of c7 is " + panel1.getComponent(7).getName());
                                System.out.println("Name of c8 is " + panel1.getComponent(8).getName());
                                System.out.println("Name of c9 is " + panel1.getComponent(9).getName());
                                System.out.println("Name of c10 is " + panel1.getComponent(10).getName());
                                System.out.println("Name of c11 is " + panel1.getComponent(11).getName());
                                //       System.out.println("Name is"+panel1.getComponent(10).getName());
                            }

                            @Override
                            public void mousePressed(MouseEvent e) {
                            }

                            @Override
                            public void mouseReleased(MouseEvent e) {
                            }

                            @Override
                            public void mouseEntered(MouseEvent e) {
                                //           abt.setVisible(true);
                                if (lblStatus1.getText().toString().equals("Stop")) {
                                    panel1.setBackground(new Color(240, 240, 240));
                                }

                                Point p = panel1.getLocation();
                                int x = (int) p.getX();
                                int y = (int) p.getY();
                                System.out.println(x + " " + y);
                                //        JLabel lbl = (JLabel) panel1.getComponent(0);
                                //       Component c = lbl.getRootPane();
                                //            abt.setLocation(screenRect.width - (dialog.getWidth() + abt.getWidth()),y);
                            }

                            @Override
                            public void mouseExited(MouseEvent e) {


                                if (lblStatus1.getText().toString().equals("Stop")) {
                                    panel1.setBackground(Color.white);
                                }
                                //         abt.setVisible(false);
                            }
                        });
                        try {

                            PanelPlayPause.lblDone.addMouseListener(new MouseListener() {
                                JLabel lbl_Done = (JLabel) panel1.getComponent(6);
                                JLabel lblPlayPause = (JLabel) panel1.getComponent(4);
                                JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
                                JLabel lbl_status1 = (JLabel) panel1.getComponent(1);
                                JLabel lbl_task = (JLabel) panel1.getComponent(11);

                                @Override
                                public void mouseClicked(MouseEvent e) {

                                    //   JLabel lblTasks = (JLabel) panel1.getComponent(9);
                                    //    String task=lblTasks.getText().toString();
                                    //   td=new TaskDetailDialog(dialog, true);
                                    //   td.setLocationRelativeTo(dialog);
                                    //    td.setDetailedTask(task);
                                    //    td.startTimer();
                                    String panelid = panel1.getName().toString();
                                    task_id = panelid;
                                    System.out.println("task_id is : " + task_id);
                             //       checkAndGetSubtaskIds();

                                    cd.setLocationRelativeTo(dialog);
                                    cd.setInfo("Do you want to Finish task?");
                                    cd.setVisible(true);
                                    String c = cd.getInput();
                                    if (interruptDialog) {
                                        c = "";
                                    }
                                    if (c.equals("yes")) {
                                        JLabel lblStatus1 = (JLabel) panel1.getComponent(1);
                                        String stts = lblStatus1.getText().toString();
                                        if (stts.equals("Working") && !stask.subtask_map.containsKey(task_id)) {
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
                                        if ("ok".equals(flag)) {
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
                                                if (stts.equals("Working")){
                                                    doOnStop(panel1, "Done","has_subtask");
                                                    if(!"task_not_stopped".equals(work_status)){
                                                           userId = main_userid;
                                                    work_status = "Done";
                                                     makeReviewAndFinish(stts,subtasks_id,"has_subtasks","review_required");
                                                    }
                                                 }else{
                                                    System.out.println("Equal");
                                                allSelected=true;
                                                userId = main_userid;
                                                work_status = "Done";
                                                makeReviewAndFinish(stts,subtasks_id,"has_subtasks","review_required");
                                                
                                                }
                                                
                                                
                                            }else{
                                                allSelected=false;
                                      
                                                if(!"".equals(subtasks_id)){
                                                  
                                                    if (stts.equals("Working")) {
                                                    doOnStop(panel1, "Done","has_subtask");
                                                   if(!"task_not_stopped".equals(work_status)){
                                                    userId = main_userid;
                                                    work_status = "Stop";
                                                     makeReviewAndFinish(stts,subtasks_id,"has_subtasks","no_review_required");
                                                    }
                                                 }else{
                                                userId = main_userid;
                                                work_status = "Stop"; 
                                                makeReviewAndFinish(stts,subtasks_id,"has_subtasks","no_review_required"); 
                                                    }
                                                    
                                               
                                            }else{
                                                showInfoDialog("No Task Selected");
                                            }
                                            }
                                            
                                        }else if("cancel".equals(flag)){
                                            
                                        }
                                    }else{
                                                userId = main_userid;
                                                work_status = "Done";
                                                makeReviewAndFinish(stts,task_id,"no_subtasks","review_required");
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
                                    System.out.println("Mouse Entered");
                                    if (lbl_status1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(new Color(240, 240, 240));
                                        System.out.println("Color set on ondone");
                                    }

                                    lbl_Done.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    if (lbl_status1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(Color.white);
                                    }

                                    lbl_Done.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }

                             

                                public void makeReviewAndFinish(String stts,String taskid,String mode,String review) {
                                    try {
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
               //                             showInfoDialog("<html><p width=\"155px\">" + "You have to select atleast one user for reviewing in order to finish task." + "</p></html>");
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
                            /*    PanelPlayPause.lblTaskInfoIcon.addMouseListener(new MouseListener() {
                             JLabel lbl_InfoIcon = (JLabel) panel1.getComponent(10);

                             @Override
                             public void mouseClicked(MouseEvent e) {
                             readxml.getUserList();
                             //   readxml.getListModel();
                             String panelid = panel1.getName().toString();
                             task_id = panelid;
                             try {
                             Thread.sleep(2000);
                             } catch (InterruptedException ex) {
                             Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                             }
                             reviewDlg.setTaskId(task_id);
                             reviewDlg.setLocationRelativeTo(dialog);
                             ReviewDialog.listUserReview.setSelectedIndex(0);
                             reviewDlg.setVisible(true);
                             if (ReadXml.response1.equals("ok")) {
                             showInfoDialog("Review Successful");
                             } else {
                             showInfoDialog("Review Failed");
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
                             panel1.setBackground(new Color(240, 240, 240));
                             lbl_InfoIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
                             }

                             @Override
                             public void mouseExited(MouseEvent e) {
                             panel1.setBackground(Color.white);
                             lbl_InfoIcon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                             }
                             });*/


                            MouseListener mListener = new MouseListener() {
                                JLabel lblStatus1 = (JLabel) panel1.getComponent(1);
                                JLabel lblTasks = (JLabel) panel1.getComponent(11);
                                JLabel lblPlus = (JLabel) panel1.getComponent(10);
                                JLabel lblDate = (JLabel) panel1.getComponent(9);
                                String task_id = panel1.getName().toString();

                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    if (e.getSource() == lblTasks) {
                                        String task = lblTasks.getText().toString();
                                        td = new TaskDetailDialog(dialog, true);
                                        td.setLocationRelativeTo(dialog);
                                        td.setDetailedTask(task);
                                        td.startTimer();
                                    }
                                    if (e.getSource() == lblPlus) {
                                        userId = main_userid;
                                        String flag = showExtentionDialog();
                                        String date = ed.getDate();
                                        System.out.println("flag is:::" + flag);
                                        System.out.println("date is:::" + date);
                                        if (!"".equals(date)) {
                                            String response = "";
                                            try {
                                                response = readxml.sendDeadlineRequest(userId, task_id, date);
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
                                    if (lblStatus1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(new Color(240, 240, 240));
                                    }

                                    if (e.getSource() == lblTasks) {
                                        lblTasks.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    }
                                    if (e.getSource() == lblPlus) {
                                        lblPlus.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                    }
                                }
                                @Override
                                public void mouseExited(MouseEvent e) {
                                    if (lblStatus1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(Color.white);
                                    }

                                    if (e.getSource() == lblTasks) {
                                        lblTasks.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    }
                                    if (e.getSource() == lblPlus) {
                                        lblPlus.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                    }
                                }

                                public String showExtentionDialog() {
                                    ed.setLocationRelativeTo(dialog);
                                    ed.setDate(lblDate.getText().toString());
                                    ed.setCurrentDate();
                                    ed.setVisible(true);
                                    String flag = ed.getFlag();
                                    if (flag.equals("wrong")) {
                                        showInfoDialog("Incorrect date!");
                                        showExtentionDialog();
                                    }
                                    return flag;
                                }
                            };

                            PanelPlayPause.lblTask.addMouseListener(mListener);
                            PanelPlayPause.lblExtentDeadline.addMouseListener(mListener);
                            /*   PanelPlayPause.lblTask.addMouseListener(new MouseListener() {
                             JLabel lblTasks = (JLabel) panel1.getComponent(11);

                             @Override
                             public void mouseClicked(MouseEvent e) {

                             String task = lblTasks.getText().toString();
                             td = new TaskDetailDialog(dialog, true);
                             td.setLocationRelativeTo(dialog);
                             td.setDetailedTask(task);
                             td.startTimer();
                             }

                             @Override
                             public void mousePressed(MouseEvent e) {
                             }

                             @Override
                             public void mouseReleased(MouseEvent e) {
                             }

                             @Override
                             public void mouseEntered(MouseEvent e) {
                             panel1.setBackground(new Color(240, 240, 240));
                             lblTasks.setCursor(new Cursor(Cursor.HAND_CURSOR));
                             }

                             @Override
                             public void mouseExited(MouseEvent e) {
                             panel1.setBackground(Color.white);
                             lblTasks.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                             }
                             });*/
                            PanelPlayPause.lblPlay.addMouseListener(new MouseListener() {
                                JLabel lblPlayPause = (JLabel) panel1.getComponent(4);
                                JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
                                JLabel lblStatus1 = (JLabel) panel1.getComponent(1);

                                @Override
                                public void mouseClicked(MouseEvent e) {

                                    String panelid;
                                    panelid = panel1.getName().toString();
                                    task_id = panelid;
                                    //                           System.out.println("Container of this element is : " + panelid);



                                    System.out.println("Name of this component is : " + lblStatus1.getText().toString());
                                    System.out.println("Status of this panel is : " + lblStatus1.getText().toString());


                                    JLabel lblTimesheet = (JLabel) panel1.getComponent(2);
                                    //      JLabel lblPlayPause = (JLabel) panel1.getComponent(4);
                                    //     JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
                                    String stts = lblStatus1.getText().toString();
                                    System.out.println("Status st this row is>> " + stts);
                                    System.out.println("Working task is >> " + working_task);
                                    System.out.println("Paused task is>> " + task_paused);
                                    if (stts.equals("Working")) {
                                        pauseDialog.setLocationRelativeTo(dialog);
                                        pauseDialog.setVisible(true);
                                        play_pause_comment = pauseDialog.getPauseString();
                                        String time = pauseDialog.getExpectedTime();
                                        System.out.println("Expected time is>> " + time);
                                        if (!pauseDialog.isVisible() && !play_pause_comment.equals("")) {
                                            try {
                                                //                                 System.out.println(play_pause_comment);
                                                //                   userId = dbHandler.getUserId();
                                                userId = main_userid;
                                                //                                   System.out.println(main_userid);
                                                //                  timesheet_id = dbHandler.getTimesheetId();
                                                timesheet_id = main_timeid;
                                                //                                System.out.println(main_timeid);
                                                work_status = "Pause";
                                                String time_id = dbHandler.getTimeId();
                                                String task_id = dbHandler.getTaskId();
                                                String user_id = dbHandler.getUserId();
                                                //  showInfoDialog(time_id+""+task_id+""+user_id);
                                                //    sendPlayRequest(userId, task_id, work_status);

                                                play_pause_comment = play_pause_comment.replaceAll("\\s+", " ");
                                                play_pause_comment = play_pause_comment.replaceAll(" ", "^");
                                                sendDataForTimesheetToPause(userId, task_id, work_status, time_id, play_pause_comment,time);
                                                if (timesheet_response.equals("done")) {
                                                    worker.shutdownNow();                          //stop timesheet uudate thread
                                                    screeShotListener.shutdownNow();                // stop mouse keyboard tracking thread
                                                    //                     dbHandler.deleteTimeSheetId();
                                                    main_timeid = "";
                                                    //                                        System.out.println(main_timeid);
                                                    lblStatus1.setText(work_status);
                                                    lblPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/pauseresume.png")));
                                                    lbl_Stop.setEnabled(false);
                                                    working_task = true;
                                                    task_paused = true;
                                                    win.setWorkingStatus(false);
                                                    showInfoDialog("Task paused!");
                                                    lblPlayPause.setToolTipText("Resume task");
                                                } else {
                                                    work_status = "Working";
                                                    lblStatus1.setText("Working");
                                                    win.setWorkingStatus(true);
                                                }
                                            } catch (SQLException ex) {
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (MalformedURLException ex) {
                                                showInfoDialog("Connection error! Please try again.");
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (IOException ex) {
                                                showInfoDialog("Connection error! Please try again.");
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }

                                        interruptDialog = false;
                                        //       strTimeSheet = JOptionPane.showInputDialog(dialog, "Why you want to pause?");
                                        //     System.out.println("Time sheet is : " + strTimeSheet);
                                    }
                                    if (stts.equals("Stop") && (working_task || task_paused)) {
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
                                        }
                                        cd.setVisible(true);
                                        String c = cd.getInput();
                                        if (interruptDialog) {
                                            c = "";
                                        }
                                        if (c.equals("yes")) {
                                            try {
                                                //         userId = dbHandler.getUserId();
                                                userId = main_userid;
                                                //                            System.out.println(main_userid);
                                                work_status = "Working";
                                                sendPlayRequest(userId, task_id, work_status);
                                                lbl_Stop.setEnabled(true);
                                                // lblTimesheet.setText(timesheet_id);
                                                lblStatus1.setText(work_status);
                                                lblPlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/pause.png")));
                                                working_task = true;
                                                //   panel1.setBackground(new Color(129,255,190));
                                                panel1.setBackground(new Color(236, 252, 244));
                                                //      panel1.setBackground(new Color(144,238,144));
                                                showInfoDialog(info);
                                                lblPlayPause.setToolTipText("Pause task");
                                                dbHandler.setTimeId(timesheet_id, userId, task_id);
                                            } catch (SQLException ex) {
                                                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                                            } catch (IOException ex) {
                                                //   work_status = "Stop";
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
                                                    //     tracker_thread  new Thread(win.doEvery10).start();
                                                }

                                            }
                                        }
                                        interruptDialog = false;
                                    }
                                    //                         System.out.println("Time id of this panel is : " + lblTimesheet.getText().toString());
                                    //    JLabel lbl2 = (JLabel) panel.getComponent(3);
                                    //   lbl2.setIcon(new ImageIcon(this.getClass().getResource("/dx/timesheet/pause.png")));
                                }

                                @Override
                                public void mousePressed(MouseEvent e) {
                                }

                                @Override
                                public void mouseReleased(MouseEvent e) {
                                }

                                @Override
                                public void mouseEntered(MouseEvent e) {
                                    if (lblStatus1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(new Color(240, 240, 240));
                                        System.out.println("Color set");
                                    }
                                    lblPlayPause.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    JLabel status1 = (JLabel) panel1.getComponent(1);
                                    if (status1.getText().toString().equals("Working") || status1.getText().toString().equals("Pause")) {
                                        //   panel1.setBackground(new Color(129,255,190));
                                        //    
                                        panel1.setBackground(new Color(236, 252, 244));

                                    }
                                    if (lblStatus1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(Color.white);
                                    }

                                    lblPlayPause.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                                }
                            });
                            PanelPlayPause.lblStop.addMouseListener(new MouseListener() {
                                JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
                                JLabel lblStatus1 = (JLabel) panel1.getComponent(1);

                                @Override
                                public void mouseClicked(MouseEvent e) {
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
                                    if (lblStatus1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(new Color(240, 240, 240));
                                    }

                                    lbl_Stop.setCursor(new Cursor(Cursor.HAND_CURSOR));
                                }

                                @Override
                                public void mouseExited(MouseEvent e) {
                                    if (lblStatus1.getText().toString().equals("Stop")) {
                                        panel1.setBackground(Color.white);
                                    }
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


                //   if(Win32IdleTime.auto_pause){                                       //if task is auto paused
                //        showMainDialog();
                //        Win32IdleTime.auto_pause=false;
                //   win.setWorkingStatus(false);
                //    }
                //    }

            }
        } catch (IOException | DOMException ex) {
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

    public void doOnStop(JPanel panel1, String tag,String subtask) {
        JLabel lbl_PlayPause = (JLabel) panel1.getComponent(4);
        JLabel lbl_Stop = (JLabel) panel1.getComponent(5);
        JLabel lbl_Done = (JLabel) panel1.getComponent(6);
        String panelid;
        panelid = panel1.getName().toString();
        task_id = panelid;
        //                       System.out.println("Container of this element is : " + panelid);
        JLabel lblStatus1 = (JLabel) panel1.getComponent(1);
        //                       System.out.println("Status of this panel is : " + lblStatus1.getText().toString());
        JLabel lblTimesheet = (JLabel) panel1.getComponent(2);
        String stts = lblStatus1.getText().toString();
        if (stts.equals("Working") && tag.equals("Stop")) {
            cd.setLocationRelativeTo(dialog);
            cd.setInfo("Do you want to stop task?");
            cd.setVisible(true);
            String c = cd.getInput();
            if (interruptDialog) {
                c = "";
            }
            if (c.equals("yes")) {
                stopOrDone(lblStatus1, lbl_PlayPause, lbl_Stop, done);
            }
            interruptDialog = false;
        } else if (stts.equals("Working") && tag.equals("Done")) {
               
            work_status="task_not_stopped";
            if (stopOrDone(lblStatus1, lbl_PlayPause, lbl_Stop, done)) {
                System.out.println("doneeeeee" + done);
                System.out.println("Task stopped!!!");
                userId = main_userid;
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                
                work_status = "Done";
                System.out.println("doneeeeee" + done);
                if("no_subtask".equals(subtask)){                                  // if no subtasks 
                    try {
                    System.out.println("doneeeeee" + done);
                    reviewDlg.setTaskId(task_id);
                    reviewDlg.setLocationRelativeTo(dialog);
                    ReviewDialog.listUserReview.setSelectedIndex(0);
                    reviewDlg.setVisible(true);
                    if (ReadXml.response1.equals("ok")) {
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
                //                              System.out.println(play_pause_comment);
                //              userId = dbHandler.getUserId();
                System.out.println(main_userid);
                userId = main_userid;
                //             timesheet_id = dbHandler.getTimesheetId();
                timesheet_id = main_timeid;
                //                              System.out.println(main_timeid);
                String time_id = dbHandler.getTimeId();
                String task_id = dbHandler.getTaskId();
                String user_id = dbHandler.getUserId();
                //     showInfoDialog(time_id + "" + task_id + "" + user_id);
                work_status = "Stop";
                play_pause_comment = play_pause_comment.replaceAll("\\s+", " ");
                play_pause_comment = play_pause_comment.replaceAll(" ", "^");
                sendDataForTimesheet(userId, task_id, work_status, time_id, play_pause_comment);
                if (timesheet_response.equals("done")) {
                    //      main_tasklength = main_tasklength - 1;
                    worker.shutdownNow();
                    screeShotListener.shutdownNow();
                    //               dbHandler.deleteTimeSheetId();
                    main_timeid = "";
                    lblStatus1.setText(work_status);
                    lbl_PlayPause.setIcon(new ImageIcon(this.getClass().getResource("/images/play.png")));

                    //       lbl_PlayPause.setEnabled(false);
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
                showInfoDialog("Connection error! Please try again.");
                Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            done = false;
            stopped = false;
        }
        return stopped;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public void showInfoDialog(String Info) {
        InfoDialog inf = new InfoDialog(dialog, true);
        inf.setLocationRelativeTo(dialog);
        inf.setInfo(Info);
        inf.setVisible(true);
    }

    public void showLoaderDialog() {
        infD.setLocationRelativeTo(panel.panelLoginContainer);
        infD.setVisible(true);
    }

    public void showLoaderDialog2() {
        infD.setLocationRelativeTo(TaskPanel.panelforScrollPane);
        infD.setVisible(true);
    }

    public void showLoaderDialog3() {
        infD.setLocationRelativeTo(WaitingPanel.panelWaitingContainer);
        infD.setVisible(true);
    }

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
                /*     if (working_task) {
                 showInfoDialog("Please pause working task first!");
                 }else{
                 ExitDialog extdlg=new ExitDialog(dialog, true);
                 extdlg.setLocationRelativeTo(dialog);
                 extdlg.setVisible(true);
                 String decision=extdlg.getInput();
                 System.out.println(decision);
                 switch (decision) {
                 case "yes":
                 timer2.setInitialDelay(0);
                 timer2.setDelay(10);
                 timer2.start();
                 break;
                 case "no":
                
                 break;
                 case "tray":
                 timer3.setInitialDelay(0);
                 timer3.setDelay(10);
                 timer3.start();
                 break;
                 } 
                 }*/
            }
            if (e.getSource() == trayIcons.trayIcon) {
                //         dialog.show();
                //     IconifyUtilityClass.restore(dialog);
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showMainDialog();
                }
                //     if (SwingUtilities.isRightMouseButton(e)) {
                //       stask.setLocationRelativeTo(dialog);
                //       stask.setVisible(true);
                //    }

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
     *
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
            if (e.getSource() == TaskPanel.lblSignOut2) {
                signOutBlock("");
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
                    act.openWebpage(new URL("http://www.designersx.com"));
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (e.getSource() == panel.lblAccount) {
                try {
                    act.openWebpage(new URL(Config.HTTP+Config.DOMAIN + "/users/register"));
                   // System.out.print(Config.HTTP+Config.DOMAIN + "/users/register");
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
            //     if (e.getSource() == TaskDetailDialog.lblCross) {
            //       td.setVisible(false);
            //    }
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

    public void signOutBlock(String exit_tag) {
        try {
            if (working_task) {
                showMainDialog();
                showInfoDialog("Please stop working task first!");
            } else {
                if (!task_reader_thread.isShutdown()) {
                    // refresh_worker.shutdownNow();
                    task_reader_thread.shutdownNow();
                }

                if (cd.isVisible()) {
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


                //  track_net_thread.destroy();
                //     System.out.println("track_net_thread alive"+track_net_thread.isAlive());

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
            showInfoDialog("Connection error");
            Logger.getLogger(PopUpLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refresh() throws SQLException {
        TaskPanel.panelforScrollPane.removeAll();
        countPanels = 0;
        //      String uname = dbHandler.getUserName();
        String uname = main_username;
        readPlayPauseTask(uname, main_password);

    }

    void sendDataForTimesheet(String userid, String taskid, String status, String timesheetid, String comment) throws MalformedURLException, IOException {
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "/TimeSheets/sheet/" + userid + "/" + taskid + "/" + status + "/" + timesheetid + "/" + comment);
        System.out.println("sendDataForTimesheet - "+url11);
        StringBuilder responseString = getStreamResponse(url11);
        timesheet_response = responseString.toString();
    }
    void sendDataForTimesheetToPause(String userid, String taskid, String status, String timesheetid, String comment,String time) throws MalformedURLException, IOException {
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "/TimeSheets/sheet/" + userid + "/" + taskid + "/" + status + "/" + timesheetid + "/" + comment +"/" + null +"/" +time);
        System.out.println("sendDataForTimesheetToPause - "+url11);
        StringBuilder responseString = getStreamResponse(url11);
        timesheet_response = responseString.toString();
    }

    public void sendPlayRequest(String userid, String taskid, String status) throws MalformedURLException, IOException, SQLException {
     
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "/TimeSheets/sheet/" + userid + "/" + taskid + "/" + status);
        System.out.println("sendPlayRequest - "+url11);
        StringBuilder responseString = getStreamResponse(url11);
        timesheet_id = responseString.toString();
        timesheet_response = responseString.toString();
        main_timeid = timesheet_id;
        //    dbHandler.insertTimesheetId(main_timeid);
    }
    public void sendDoneRequest(String userid, String taskid, String status,String subtasks_ids) throws MalformedURLException, IOException, SQLException {
        URL url11 = new URL(Config.HTTP+Config.DOMAIN + "/TimeSheets/sheet/" + userid + "/" + taskid + "/" + status +"/" + null+ "/" +null+"/" + subtasks_ids);
        System.out.println("sendDoneRequest - "+url11);
        StringBuilder responseString = getStreamResponse(url11);
        timesheet_id = responseString.toString();
        timesheet_response = responseString.toString();
        main_timeid = timesheet_id;
        //    dbHandler.insertTimesheetId(main_timeid);
    }

    void requestPassword(String username) {
        try {
            URL url11 = new URL(Config.HTTP+Config.DOMAIN + "/users/remotereset/" + username);
            System.out.println("requestPassword - "+url11);
            StringBuilder responseString = getStreamResponse(url11);
            server_response = responseString.toString();
        } catch (IOException ex) {
            hideLoaderDialog();
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
        URL url2 = new URL(Config.HTTP+Config.DOMAIN + "/users/url1/" + user_name + "/" + password + "/" + system_name + "/" + mac_address + "/" + router_ip + "/" + cpu_model + "/" + sys_ram + "/" + os_name + "/" + sys_hdd);
        System.out.println(" - "+url2);

        InputStream is = null;
        try {
            InputStream in = url2.openStream();
            Document doc = parse(in);
            doc.getDocumentElement().normalize();
            userId = getLoginElements(doc, "user_id");
            imgUrl = getLoginElements(doc, "image");
            name = getLoginElements(doc, "name");
            TaskPanel.lblUserName.setText(name);
        } catch (IOException | DOMException ex) {
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
}
