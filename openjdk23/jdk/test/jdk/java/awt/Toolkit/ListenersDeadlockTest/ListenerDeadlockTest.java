/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
  @test
  @bug 4338463
  @summary excessive synchronization in notifyAWTEventListeners leads to
  deadlock
*/

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;

public class ListenerDeadlockTest {
    public static final Object lock = new Object();

    public static final Toolkit toolkit = Toolkit.getDefaultToolkit();

    public static Panel panel = new Panel();

    public static final AWTEventListener listener = new AWTEventListener() {
        public void eventDispatched(AWTEvent e) {
            if (e.getSource() == panel) {
                System.out.println(e);
                System.out.println("No deadlock");
                synchronized(lock) {
                    lock.notifyAll();
                }
            }
        }
    };

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            toolkit.addAWTEventListener(listener, -1);

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    synchronized (toolkit) {
                        synchronized (lock) {
                            try {
                                lock.notifyAll();
                                lock.wait();
                            } catch (InterruptedException ex) {
                            }
                        }
                    }
                }
            });

            synchronized (lock) {
                thread.start();
                try {
                    lock.wait();
                } catch (InterruptedException ex) {
                }
            }

            panel.dispatchEvent(new ActionEvent(panel,
                ActionEvent.ACTION_PERFORMED, "Try"));
        });
    }
}
