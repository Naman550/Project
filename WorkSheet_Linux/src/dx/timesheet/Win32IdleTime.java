/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dx.timesheet;

/**
 *
 * @author Me
 */
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.*;
import com.sun.jna.win32.*;
import java.awt.AWTException;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 * Utility method to retrieve the idle time on Windows and sample code to test
 * it. JNA shall be present in your classpath for this to work (and compile).
 *
 * @author ochafik
 */
public class Win32IdleTime implements MouseListener{                        
    private static boolean first=true;

    HardwareDetails hd = new HardwareDetails();
    String window_state = "";
    static double activeTime=0,sleepTime=0;
    // PopUpLogin p=new PopUpLogin();
     
    ReadXml rdxml = new ReadXml();
    DatabaseHandler dbHandler = new DatabaseHandler();
    boolean task_working = false;
    
    boolean task_paused = false;
    ScheduledExecutorService tracker = Executors.newSingleThreadScheduledExecutor();
    // tracker = Executors.newSingleThreadScheduledExecutor();
    boolean threadInterrupet=false;
    static boolean auto_pause=false;
    
    public void setState(String state) {
        window_state = state;
    }

    public String getState() {
        return window_state;
    }

    public void setPauseStatus(boolean paused) {
        task_paused = paused;
    }

    public boolean getPauseStatus() {
        return task_paused;
    }

    public void setWorkingStatus(boolean working) {
        task_working = working;
    }

    public void doIfUserIdle() {
        try {        
            dbHandler.connect();
            String timeid = new DatabaseHandlerId().getId();
            String taskid=  PopUpLogin.task_id;
            String userid = PopUpLogin.userId;

            String response = "";
            try {
                response = rdxml.sendDataForTimesheet(userid, taskid, "Pause", timeid, "User^Idle");
                auto_pause=true;
                setPauseStatus(true);  
                threadInterrupet=true;
            } catch (MalformedURLException ex) {
                Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
            }
                         // make this true for executing code in refreshToReadTaskLength thread
            if (!response.equals("done")) {
                try {
                    System.out.println("Sleeping for 30 seconds");
                    Thread.sleep(30000);
                   
                } catch (InterruptedException ex) {
                    Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
                }
                doIfUserIdle();          //again execute method if response is not "done"
            } else {
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void sleepThread(final long millis) {
        
           
                        try{
                    Thread.sleep(millis);
                }
                catch (InterruptedException ex) {
                       threadInterrupet=true;
                       Thread.currentThread().interrupt();
                       Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
                }
            
    }  

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("To change body of generated methods, choose Tools | Templates.");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public interface Kernel32 extends StdCallLibrary {

        Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32", Kernel32.class);

        /**
         * Retrieves the number of milliseconds that have elapsed since the
         * system was started.
         *
         * @see http://msdn2.microsoft.com/en-us/library/ms724408.aspx
         * @return number of milliseconds that have elapsed since the system was
         * started.
         */
        public int GetTickCount();
    };
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = (User32) Native.loadLibrary("X11", User32.class);
        /**
         * Contains the time of the last input.
         *
         * @see
         * http://msdn.microsoft.com/library/default.asp?url=/library/en-us/winui/winui/windowsuserinterface/userinput/keyboardinput/keyboardinputreference/keyboardinputstructures/lastinputinfo.asp
         */
        public static class LASTINPUTINFO extends Structure {

            public int cbSize = 8;
            /// Tick count of when the last input event was received.
            public int dwTime;
            @SuppressWarnings("rawtypes")
            @Override
            protected List getFieldOrder() {
                return Arrays.asList(new String[]{"cbSize", "dwTime"});
            }
        }
        /**
         * Retrieves the time of the last input event.
         *
         * @see
         * http://msdn.microsoft.com/library/default.asp?url=/library/en-us/winui/winui/windowsuserinterface/userinput/keyboardinput/keyboardinputreference/keyboardinputfunctions/getlastinputinfo.asp
         * @return time of the last input event, in milliseconds
         */
        public boolean GetLastInputInfo(LASTINPUTINFO result);
    };
    /**
     * Get the amount of milliseconds that have elapsed since the last input
     * event (mouse or keyboard)
     *
     * @return idle time in milliseconds
     */
    public static int getIdleTimeMillisWin32() {
//        User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
//        System.out.println("ee == "+lastInputInfo);
//        User32.INSTANCE.GetLastInputInfo(lastInputInfo);
//       
//        return Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime;]
        
        if(first){JNativeListener.main();}
        first=false;
        int idleTime=JNativeListener.getIdleState();
        idleTime=idleTime*1000;
        return idleTime;
    }
    enum State2 {
        UNKNOWN1, ONLINE1, IDLE1, AWAY1
    };
    enum State {
        UNKNOWN, ONLINE, IDLE, AWAY
    };
    
    public void trackTime(){
    
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                for(;;){
                    try {
                        int idleSec1 = getIdleTimeMillisWin32() / 1000;
                        if(idleSec1<=1){
                            activeTime +=1;
                        }
                        else{
                            sleepTime+=1;
                        }
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        t.start();
    
    }
    
    public void trackTime(String usr){
        addMouseListener(this);
        threadInterrupet=false;
        trackTime();
        State2 state1 = State2.UNKNOWN1;
  //      DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        long endTime = System.currentTimeMillis()+10000;
        System.out.println("NamanEndTime - "+endTime);
               long startTime=System.currentTimeMillis();
               System.out.println("NamanStartTime - "+startTime);
               String one_or_zero="";
               Double total_zeros=0.0;
               Double total_ones=0.0;
               Double total_ones_in_minute=0.0;
        int i=1;
        
        while(i<=60){
            if(threadInterrupet || !PopUpLogin.working_task){
                System.out.println("Break on enter i");
                    break;
                }
            int j=1;
            Double one=0.0;
            while (j <= 10) {
                if(threadInterrupet || !PopUpLogin.working_task){
                    System.out.println("Break on enter j");
                    break;
                }
                //sleepThread(50000);
                 int idleSec1 = getIdleTimeMillisWin32() / 1000;
                 System.out.println("NamanIdleSec1  -  "+idleSec1);
            State2 newState =
                    idleSec1 < 2 ? State2.ONLINE1 :State2.IDLE1;
            
                System.out.println("NamanNewState  -  "+newState);
                if (newState.equals(State2.ONLINE1)) {
                    one_or_zero = "1";
                    System.out.println("one_or_zero -- 1");
                    one=1.0;
                }
                if (newState.equals(State2.IDLE1) && (one_or_zero.equals("0") || one_or_zero.equals(""))) {
                    one_or_zero = "0";
                    one=0.0;
                    System.out.println("0");
                }else{
                    one=1.0;
                }
           //     if(j==10){
            //        break;
            //    }
                System.out.println("j is "+j);
                 j++;
                 
            }
            if(threadInterrupet || !PopUpLogin.working_task){
                System.out.println("Break on exit j");
                    break;
                }
            System.out.println("Nbefore total_ones till now is>> "+total_ones);
            total_ones=total_ones+one;
            
            System.out.println("NAfter total_ones till now is>> "+total_ones);
            System.out.println("one till now is>> "+one);
            if(i==10){
                 
                
          //      total_ones_in_minute=total_ones_in_minute+total_zeros;
                total_ones_in_minute=total_ones_in_minute+total_ones;
                System.out.println("total_ones is>> "+total_ones);
                System.out.println("total_ones_in_minute is>> "+total_ones_in_minute);
                
                
                i=0;
                total_ones_in_minute=0.0;
                total_zeros=0.0;
                total_ones=0.0;
                one_or_zero="";
                Random r = new Random();
                int firstDelay  = r.nextInt(5);
                int sendDelay = r.nextInt(5);
                

                int finalDelay=5+sendDelay+firstDelay;
            
            System.out.println(""+finalDelay);
                System.out.println("\n\n\n......Thread Going To Sleep..."+finalDelay+"....\n\n\n");
                 sleepThread(finalDelay*60*1000);
//                sleepThread(60*1000);
                if(!threadInterrupet || PopUpLogin.working_task){
                   try {
                        System.out.println("\n\n\n\n********percenteagepercenteage*********"+activeTime+"**\n\n\n\n");
                        Double percenteage=((activeTime/(63*finalDelay)))*100;
//                        Double percenteage=((activeTime/63))*100;
                        if(percenteage>99){
                            percenteage=99.0-firstDelay;
                        }
                        System.out.println("\n\n\n\n********percenteagepercenteage*********"+percenteage+"**\n\n\n\n");
                        hd.getScreenShot(usr,String.valueOf(percenteage.intValue()));
                        System.out.println("total_Naman is>> "+activeTime);
                        System.out.println("total_arora is>> "+sleepTime);
                        sleepTime=0;
                        activeTime=0;
                } catch (AWTException ex) {
                    Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
                }
                  
                }
               
            }
            one_or_zero="";one=0.0;
            System.out.println("i is "+i);
            i++;
        }
        System.out.print("Exit from while");
    }
    // TEST
    public void getIdleTime() {
        if (!System.getProperty("os.name").contains("Linux")) {
            System.err.println(""
                    + "t"
                    + "t");
            System.exit(1);
        }
        State state = State.UNKNOWN;
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        //  p=new PopUpLogin();

        for (;;) {
            int idleSec = getIdleTimeMillisWin32()/1000;
            State newState =
                    idleSec < 5*60 ? State.ONLINE : idleSec > 50*60 ? State.AWAY : State.IDLE;
//                    System.out.println("\n\n\nidleSec  ---->>"+idleSec);
//                    System.out.println("\n\n\nNew State  ---->>"+newState);
                    
            if (newState != state) {
                state = newState;
                setState(String.valueOf(state));
                Component c = null;
                String s = null;
                if (LoginPanel.lblDx.isDisplayable()) {
                    c = SwingUtilities.windowForComponent(LoginPanel.lblDx);
                    s = "LoginPanel";
                }
                if (TaskPanel.lblDx.isDisplayable()) {
                    c = SwingUtilities.windowForComponent(TaskPanel.lblDx);
                    s = "TaskPanel";
                }
//                if (ForgotPwdPanel.lblDx.isDisplayable()) {
//                    c = SwingUtilities.windowForComponent(ForgotPwdPanel.lblDx);
//                    s = "ForgotPwdPanel";
//                }
                if (WaitingPanel.lblDx.isDisplayable()) {
                    c = SwingUtilities.windowForComponent(WaitingPanel.lblDx);
                    s = "WaitingPanel";
                }
                //   Component c=SwingUtilities.windowForComponent(PopUpLogin.jLbl);
                if (state.equals(State.IDLE)) {
                    if (task_working) {
                        doIfUserIdle();
                    }
                       //    tracker.scheduleWithFixedDelay(doEvery10, 0, 10, TimeUnit.SECONDS);
                    System.out.println("Dialog visibility:" + c.isVisible() + " on " + s + " and height is " + c.getHeight());
                    System.out.println(/*dateFormat.format(new Date()) + " # " +*/state);
                }
                if (state.equals(State.IDLE) || state.equals(State.AWAY) || state.equals(State.ONLINE)) {
                    System.out.println(state);
                }


                //
                // just for fun, if the state is AWAY (screensaver is coming!)
                // we move the mouse wheel using java.awt.Robot just a little bit to change
                // the state and prevent the screen saver execution.
                //
                //        if (state == State.AWAY) {
                //          System.out.println("Activate the mouse wheel to change state!");
                java.awt.Robot robot;
                try {
                    robot = new java.awt.Robot();
                    //          robot.mouseWheel(-1);
                    //     robot.mouseWheel(1);
                    //        for(int i=0;i<10;i++){
       //             robot.keyPress(KeyEvent.VK_ENTER);
        //            robot.keyPress(KeyEvent.VK_T);

                    //        }

                } catch (AWTException ex) {
                    //          Logger.getLogger(Win32IdleTime.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
        }
    }
}