/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
/*
 * @test
 * @key headful
 * @bug 6428694
 * @summary Checks that double click closes JOptionPane's input dialog.
 * @library /lib/client
 * @build ExtendedRobot
 * @author Mikhail Lapshin
 * @run main bug6428694
*/

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.InputEvent;

public class bug6428694 {
    private static JFrame frame;
    private static boolean mainIsWaitingForDialogClosing;
    private static ExtendedRobot robot;
    private static volatile boolean testPassed;

    public static void main(String[] args) throws Exception {
        robot = new ExtendedRobot();
        try {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    bug6428694.setupUI();
                }
            });
            robot.waitForIdle();
            test();
        } finally {
            stopEDT();
        }

        if (testPassed) {
            System.out.println("Test passed");
        } else {
            throw new RuntimeException("JOptionPane doesn't close input dialog " +
                    "by double click!");
        }
    }

    private static void setupUI() {
        frame = new JFrame("bug6428694 test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Object[] selectedItems = new Object[40];
        for (int i = 0; i < 39; i++) {
            selectedItems[i] = ("item: " + i);
        }
        JOptionPane.showInputDialog(frame,
                "Double click on selected item then click cancel",
                "Test Option Dialog", JOptionPane.WARNING_MESSAGE, null,
                selectedItems, selectedItems[0]);

        // We are here if double click has closed the dialog
        // or when the EDT is stopping
        testPassed = mainIsWaitingForDialogClosing;
    }

    private static void test() {

        mainIsWaitingForDialogClosing = true;

        // Perform double click on an item
        int frameLeftX = frame.getLocationOnScreen().x;
        int frameUpperY = frame.getLocationOnScreen().y;
        robot.mouseMove(frameLeftX + 150, frameUpperY + 120);
        robot.waitForIdle();
        robot.delay(100);
        robot.setAutoDelay(50);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        // Wait for the input dialog closing
        robot.waitForIdle();
        robot.delay(2000);

        mainIsWaitingForDialogClosing = false;
    }

    private static void stopEDT() {
        if (frame != null) {
            frame.dispose();
        }
    }
}
