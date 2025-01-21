/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 8216971
 * @summary For JCheckBoxMenuItem actionPerformed() is called twice, when
 *  apple.laf.useScreenMenuBar=true and modifier is InputEvent.META_DOWN_MASK
 * @library /test/lib
 * @run main DoubleActionTest
 */

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.Robot;
import jdk.test.lib.Platform;

public class DoubleActionTest {

    private static int metaDownCount = 0;
    private static JFrame frame;
    private static final int ORIGIN_X = 200;
    private static final int ORIGIN_Y = 200;

    public static void main(String[] args) throws Exception {
        if (!System.getProperty("os.name").startsWith("Mac")) {
            System.out.println("This test is only for Mac OS, passed " +
            "automatically on other platforms.");
            return;
        }

        try {
            System.setProperty("apple.laf.useScreenMenuBar", "true");

            SwingUtilities.invokeAndWait(DoubleActionTest::createAndShowGUI);

            Robot robot = new Robot();
            robot.setAutoDelay(100);
            testKeyPress(robot);
            robot.delay(1000);

        } finally {
            SwingUtilities.invokeAndWait(()->frame.dispose());
            if (metaDownCount != 1) {
                throw new RuntimeException("Test Failed: actionPerformed is called twice");
            }
        }
    }

    private static void createAndShowGUI() {
        frame = new JFrame();
        final JMenuBar menubar = new JMenuBar();
        final JMenu fileMenu = new JMenu("OPEN ME");
        final MyAction myAction = new MyAction();
        final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(myAction);

        fileMenu.add(menuItem);
        menubar.add(fileMenu);
        frame.setJMenuBar(menubar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(ORIGIN_X, ORIGIN_X, 200, 200);
        frame.setVisible(true);
    }

    private static class MyAction extends AbstractAction {

        MyAction() {
            putValue(Action.NAME, "HIT MY ACCELERATOR KEY");
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.META_DOWN_MASK));
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            System.out.println("Action! called with modifiers: " + e.getModifiers() + "\n" + e);
            metaDownCount++;
        }
    }

    private static void testKeyPress(Robot robot) throws Exception {
        robot.mouseMove(ORIGIN_X + 50, ORIGIN_Y + 50);
        robot.waitForIdle();
        robot.keyPress(KeyEvent.VK_META);
        robot.keyPress(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_E);
        robot.keyRelease(KeyEvent.VK_META);
        robot.waitForIdle();
    }
}
