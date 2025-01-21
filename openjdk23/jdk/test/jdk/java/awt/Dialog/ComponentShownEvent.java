/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
  @test
  @bug 4274360
  @summary Ensures that Dialogs receive COMPONENT_SHOWN events
  @key headful
  @run main ComponentShownEvent
*/

import java.awt.AWTException;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.InvocationTargetException;

public class ComponentShownEvent {

    volatile boolean componentShown = false;
    Frame f;
    Dialog d;

    public void start() throws InterruptedException,
                        InvocationTargetException, AWTException {
        Robot robot = new Robot();
        try {
            EventQueue.invokeAndWait(() -> {
                f = new Frame();
                d = new Dialog(f);

                d.addComponentListener(new ComponentAdapter() {
                    public void componentShown(ComponentEvent e) {
                        componentShown = true;
                    }
                });

                f.setSize(100, 100);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                d.setVisible(true);
            });

            robot.waitForIdle();
            robot.delay(1000);

            if (!componentShown) {
                throw new RuntimeException("test failed");
            }
        } finally {
            EventQueue.invokeAndWait(() -> {
                if (d != null) {
                    d.setVisible(false);
                    d.dispose();
                }
                if (f != null) {
                    f.setVisible(false);
                    f.dispose();
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException,
                               InvocationTargetException, AWTException {
        ComponentShownEvent test = new ComponentShownEvent();
        test.start();
        System.out.println("test passed");
    }
}
