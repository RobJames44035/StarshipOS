/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4983388 8015600
 * @summary shortcuts on menus do not work on JDS
 * @library ../../../../regtesthelpers
 * @build Util
 * @run main bug4983388
 */

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import static java.util.concurrent.TimeUnit.SECONDS;

public class bug4983388 {
    static JFrame frame;

    private static final CountDownLatch menuSelected = new CountDownLatch(1);

    private static class TestMenuListener implements MenuListener {
        @Override
        public void menuCanceled(MenuEvent e) {}
        @Override
        public void menuDeselected(MenuEvent e) {}

        @Override
        public void menuSelected(MenuEvent e) {
            System.out.println("menuSelected");
            menuSelected.countDown();
        }
    }

    private static void createAndShowGUI() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menuBar.add(menu);
        menu.addMenuListener(new TestMenuListener());

        frame = new JFrame("bug4983388");
        frame.setJMenuBar(menuBar);
        frame.setLocationRelativeTo(null);
        frame.setSize(250, 100);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException ex) {
            System.err.println("GTKLookAndFeel is not supported on this platform. "
                               + "Using default LaF for this platform.");
        }

        SwingUtilities.invokeAndWait(bug4983388::createAndShowGUI);

        Robot robot = new Robot();
        robot.setAutoDelay(50);
        robot.waitForIdle();
        robot.delay(500);

        Util.hitMnemonics(robot, KeyEvent.VK_F);

        try {
            if (!menuSelected.await(1, SECONDS)) {
                throw new RuntimeException("shortcuts on menus do not work");
            }
        } finally {
            SwingUtilities.invokeAndWait(frame::dispose);
        }
    }
}
