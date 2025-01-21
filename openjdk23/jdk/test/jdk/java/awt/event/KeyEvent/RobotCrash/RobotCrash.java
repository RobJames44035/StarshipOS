/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

 /*
 * @test
 * @key headful
 * @bug 8165555
 * @summary VM crash after creating Robot second time and accessing key codes in
 *          single JVM mode.
 * @run main RobotCrash
 */
import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;

public class RobotCrash implements Runnable {

    private Frame frame;

    public void robotKeyPressTest() throws Exception {

        SwingUtilities.invokeAndWait(() -> {
            frame = new Frame();
            frame.setSize(300, 300);
            frame.setVisible(true);
        });

        Robot robot = new Robot();
        robot.waitForIdle();
        Point pt = frame.getLocationOnScreen();
        robot.mouseMove(((int) pt.getX() + frame.getWidth()) / 2,
                ((int) pt.getY() + frame.getHeight()) / 2);
        robot.waitForIdle();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.waitForIdle();
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.waitForIdle();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.waitForIdle();
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.waitForIdle();

        SwingUtilities.invokeAndWait(() -> {
            frame.dispose();
        });
    }

    @Override
    public void run() {
        try {
            robotKeyPressTest();
        } catch (Exception e) {
            throw new RuntimeException("Test Failed" + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10; i++) {
            Thread t1 = new Thread(new RobotCrash());
            t1.start();
            t1.join();
            Thread t2 = new Thread(new RobotCrash());
            t2.start();
            t2.join();
            Thread.sleep(1000);
        }
    }
}
