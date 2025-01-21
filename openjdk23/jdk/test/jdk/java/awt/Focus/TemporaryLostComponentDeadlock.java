/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4794413
 * @summary Tests that access to temporaryLostComponent from two different threads doesn't cause a deadlock
 * @key headful
 * @run main TemporaryLostComponentDeadlock
*/
import java.awt.Button;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Frame;

public class TemporaryLostComponentDeadlock {
    static Dialog frame1;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            frame = new Frame("frame");
            frame1 = new Dialog(frame, "Frame 1", false);
            frame1.add(new Button("focus owner"));
            frame1.pack();
            frame1.setLocationRelativeTo(null);
            frame1.setVisible(true);
        });

        Thread t1 = new Thread() {
            public void run() {
                synchronized(frame1) {
                    frame1.dispose();
                    synchronized(frame1) {
                        frame1.notify();
                    }
                }
            }
        };
        try {
            synchronized(frame1) {
                t1.start();
                frame1.wait();
            }
        } catch( InterruptedException ie) {
        } finally {
            if (frame != null) {
                frame.dispose();
            }
        }
        System.out.println("Test PASSED");
    }

}// class TemporaryLostComponentDeadlock
