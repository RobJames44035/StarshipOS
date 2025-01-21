/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 7160951 8152492 8178448
 * @summary [macosx] ActionListener called twice for JMenuItem using ScreenMenuBar
 * @requires (os.family == "mac")
 * @run main ActionListenerCalledTwiceTest
 */

import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class ActionListenerCalledTwiceTest {

    static final String[] menuItems = { "Item1", "Item2", "Item3",
                                        "Item4", "Item5", "Item6" };
    static final KeyStroke[] keyStrokes = {
        KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.META_DOWN_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0),
        KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.SHIFT_DOWN_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.META_DOWN_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK),
        KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, InputEvent.META_DOWN_MASK)
    };

    static JMenuBar bar;
    static JFrame frame;
    static volatile int listenerCallCounter = 0;

    public static void main(String[] args) throws Exception {
        if (!System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            System.out.println("This test is for MacOS only." +
                    " Automatically passed on other platforms.");
            return;
        }

        try {
            Robot robot = new Robot();
            robot.setAutoDelay(100);

            System.setProperty("apple.laf.useScreenMenuBar", "true");
            SwingUtilities.invokeAndWait(
                    ActionListenerCalledTwiceTest::createAndShowGUI);

            robot.waitForIdle();
            robot.delay(1000);

            testForTwice(robot, "");

            SwingUtilities.invokeAndWait(
                    ActionListenerCalledTwiceTest::testDefaultMenuBar);
            robot.waitForIdle();

            testForTwice(robot, "DefaultMenuBar");
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                frame.dispose();
                Desktop.getDesktop().setDefaultMenuBar(null);
            });
        }
    }

    private static void createAndShowGUI() {
        JMenu menu = new JMenu("Menu");

        for (int i = 0; i < menuItems.length; ++i) {
            JMenuItem newItem = new JMenuItem(menuItems[i]);
            newItem.setAccelerator(keyStrokes[i]);
            newItem.addActionListener(e -> listenerCallCounter++);
            menu.add(newItem);
        }

        bar = new JMenuBar();
        bar.add(menu);
        frame = new JFrame("Test");
        frame.setJMenuBar(bar);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static int getModKeyCode(int mod) {
        if ((mod & (InputEvent.SHIFT_DOWN_MASK | InputEvent.SHIFT_MASK)) != 0) {
            return KeyEvent.VK_SHIFT;
        }

        if ((mod & (InputEvent.CTRL_DOWN_MASK | InputEvent.CTRL_MASK)) != 0) {
            return KeyEvent.VK_CONTROL;
        }

        if ((mod & (InputEvent.ALT_DOWN_MASK | InputEvent.ALT_MASK)) != 0) {
            return KeyEvent.VK_ALT;
        }

        if ((mod & (InputEvent.META_DOWN_MASK | InputEvent.META_MASK)) != 0) {
            return KeyEvent.VK_META;
        }

        return 0;
    }

    private static void testForTwice(Robot robot, String exceptionPrefix)
                                        throws Exception{
        for (int i = 0; i < menuItems.length; ++i) {
            KeyStroke ks = keyStrokes[i];
            int modKeyCode = getModKeyCode(ks.getModifiers());

            if (modKeyCode != 0) {
                robot.keyPress(modKeyCode);
            }

            robot.keyPress(ks.getKeyCode());
            robot.keyRelease(ks.getKeyCode());

            if (modKeyCode != 0) {
                robot.keyRelease(modKeyCode);
            }

            robot.waitForIdle();

            if (listenerCallCounter != 1) {
                throw new Exception(exceptionPrefix
                        + " Test failed: ActionListener for " + menuItems[i]
                        + " called " + listenerCallCounter + " times instead of 1!");
            }

            listenerCallCounter = 0;
        }
    }

    private static void testDefaultMenuBar() {
        if (Desktop.getDesktop().isSupported(Desktop.Action.APP_MENU_BAR)) {
            Desktop.getDesktop().setDefaultMenuBar(bar);
            frame.setExtendedState(JFrame.ICONIFIED);
        }
    }
}
