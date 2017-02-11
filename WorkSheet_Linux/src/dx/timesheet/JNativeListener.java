package dx.timesheet;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
/**
 * @author javaQuery
 * Global Keyboard Listener
 */
 public class JNativeListener implements NativeKeyListener,NativeMouseInputListener,NativeMouseWheelListener {
     
    static long idleTime = 0 ;
    static long start = System.currentTimeMillis();
    static int i=0;
    
    public static int getIdleState(){
        return i;
    }
    
    public static void idle(){
        Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                i=i+1;
                System.out.println(""+i);
            }
        };
        
        timer.schedule(timerTask, 1300,1300);
    }
    
    public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
//        System.out.println("Mosue Wheel Moved: " + e.getWheelRotation());
        i=0;
    }
     
    /* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
//        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        /* Terminate program when one press ESCAPE */
     i=0;
    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
//        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
    }

    public static void main() {
        try {
            
            // Get the logger for "org.jnativehook" and set the level to off.
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            // Don't forget to disable the parent handlers.
            logger.setUseParentHandlers(false);
            
            /* Register jNativeHook */
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            /* Its error */
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        JNativeListener nativeHookExample=new JNativeListener();

        /* Construct the example object and initialze native hook. */
        GlobalScreen.addNativeKeyListener(nativeHookExample);
        GlobalScreen.addNativeMouseListener(nativeHookExample);
        GlobalScreen.addNativeMouseMotionListener(nativeHookExample);
        GlobalScreen.addNativeMouseWheelListener(nativeHookExample);
        idle();
    }

    public void nativeMouseClicked(NativeMouseEvent e) {
    }

    public void nativeMousePressed(NativeMouseEvent e) {
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        i=0;
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
         i=0;
    }

}