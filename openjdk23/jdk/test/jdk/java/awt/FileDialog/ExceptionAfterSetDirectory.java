/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 6308332
  @summary FileDialog.setDirectory() throws exception on Linux & Solaris
  @key headful
  @run main ExceptionAfterSetDirectory
*/

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

public class ExceptionAfterSetDirectory {
    FileDialog fd = null;
    Frame frame;

    public void start() throws InterruptedException,
            InvocationTargetException {
        EventQueue.invokeAndWait(() -> {
            frame = new Frame("ExceptionAfterSetDirectory");
            frame.setLayout(new FlowLayout());
            frame.setBounds(100, 100, 100, 100);
            frame.setVisible(true);
            fd = new FileDialog(frame, "file dialog", FileDialog.LOAD);
        });

        try {
            test();
        } catch (Exception e) {
            throw new RuntimeException("Test failed.", e);
        } finally {
            if (frame != null) {
                EventQueue.invokeAndWait(frame::dispose);
            }
            if (fd != null) {
                EventQueue.invokeAndWait(fd::dispose);;
            }
        }
    }

    private void test() throws InterruptedException, InvocationTargetException {
        final Robot r;

        try {
            r = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Can not initialize Robot.", e);
        }

        r.setAutoDelay(200);
        r.delay(500);

        EventQueue.invokeLater(() -> {
            fd.setVisible(true);
        });
        r.delay(2000);
        r.waitForIdle();

        if (System.getProperty("os.name").contains("OS X")) {
            // Workaround for JDK-7186009 - try to close file dialog pressing escape
            r.keyPress(KeyEvent.VK_ESCAPE);
            r.keyRelease(KeyEvent.VK_ESCAPE);
            r.delay(2000);
            r.waitForIdle();
        }

        if (fd.isVisible()) {
            EventQueue.invokeAndWait(() -> {
                fd.setVisible(false);
            });
            r.delay(2000);
            r.waitForIdle();
        }

        // Changing directory on hidden file dialog should not cause an exception
        EventQueue.invokeAndWait(() -> {
            fd.setDirectory("/");
        });
        r.delay(2000);
        r.waitForIdle();
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        ExceptionAfterSetDirectory test = new ExceptionAfterSetDirectory();
        test.start();
    }
}
