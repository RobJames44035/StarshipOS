/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 8065861
 * @summary Test to check pressing Escape key sets 'canceled' property of ProgressMonitor
 * @run main ProgressMonitorEscapeKeyPress
 */


import javax.swing.JFrame;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ProgressMonitorEscapeKeyPress {

    static volatile int counter = 0;
    static ProgressMonitor monitor;
    static TestThread testThread;
    static JFrame frame;
    static CountDownLatch progressLatch;
    static Robot robot;


    public static void main(String[] args) throws Exception {
        try {
            progressLatch = new CountDownLatch(20);
            createTestUI();
            robot = new Robot();
            robot.setAutoDelay(50);
            robot.setAutoWaitForIdle(true);
            testThread = new TestThread();
            testThread.start();
            Thread.sleep(100);
            if (progressLatch.await(15, TimeUnit.SECONDS)) {
                System.out.println("Progress monitor completed 20%, lets press Esc...");
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
                System.out.println("ESC pressed....");
            } else {
                System.out.println("Failure : No status available from Progress monitor...");
                throw new RuntimeException(
                        "Can't get the status from Progress monitor even after waiting too long..");
            }

            if (counter >= monitor.getMaximum()) {
                throw new RuntimeException("Escape key did not cancel the ProgressMonitor");
            }
            System.out.println("Test Passed...");
        } finally {
            disposeTestUI();
        }
    }

    private static void createTestUI() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            frame = new JFrame("Test");
            frame.setSize(300, 300);
            monitor = new ProgressMonitor(frame, "Progress", "1", 0, 100);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationByPlatform(true);
        });
    }


     private static void disposeTestUI() throws Exception {
           SwingUtilities.invokeAndWait(() -> {
               frame.dispose();
           });
       }
}


class TestThread extends Thread {
    @Override
    public void run() {
        System.out.println("TestThread started.........");
        for (ProgressMonitorEscapeKeyPress.counter = 0;
             ProgressMonitorEscapeKeyPress.counter <= 100;
             ProgressMonitorEscapeKeyPress.counter += 1) {
            ProgressMonitorEscapeKeyPress.robot.delay(100);
            ProgressMonitor monitor = ProgressMonitorEscapeKeyPress.monitor;
            if (!monitor.isCanceled()) {
                monitor.setNote("" + ProgressMonitorEscapeKeyPress.counter);
                monitor.setProgress(ProgressMonitorEscapeKeyPress.counter);
                ProgressMonitorEscapeKeyPress.progressLatch.countDown();
                System.out.println("Progress bar is in progress....."
                        + ProgressMonitorEscapeKeyPress.counter + "%");
            }
            if (monitor.isCanceled()) {
                System.out.println("$$$$$$$$$$$$$$$ Monitor canceled");
                break;
            }
        }
    }
}

