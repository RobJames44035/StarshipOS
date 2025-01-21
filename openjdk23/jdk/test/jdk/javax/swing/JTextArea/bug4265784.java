/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4265784 4267291
 * @summary Tests work of TAB key in JTextArea
 * @key headful
 * @run main bug4265784
 */

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class bug4265784 {
    static JFrame frame;
    static JTextArea ta;
    static volatile Point p;
    static volatile int pos;
    static volatile int pos1;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame("bug4265784");
                ta = new JTextArea("some text", 50, 50);
                frame.getContentPane().add(ta);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
            robot.waitForIdle();
            robot.delay(1000);
            SwingUtilities.invokeAndWait(() -> {
                p = ta.getLocationOnScreen();
            });
            robot.mouseMove(p.x + 10, p.y + 10);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            SwingUtilities.invokeAndWait(() -> {
                pos = ta.getCaretPosition();
            });
            System.out.println(pos);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.waitForIdle();
            robot.delay(1000);
            SwingUtilities.invokeAndWait(() -> {
                pos1 = ta.getCaretPosition();
            });
            System.out.println(pos1);
            if (pos == pos1) {
                throw new RuntimeException("TAB ignored");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}
