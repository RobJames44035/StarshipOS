/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @library ../../regtesthelpers
 * @build Util
 * @bug 4692443 7105030
 * @summary JMenu: MenuListener.menuSelected() event fired too late when using mnemonics
 * @author Alexander Zuev
 * @run main bug4692443
 */

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.*;

public class bug4692443 {

    public static PassedListener pass;
    public static FailedListener fail;
    public static volatile Boolean passed;
    public static JFrame mainFrame;

    public static void main(String args[]) throws Throwable {
        try {
            fail = new FailedListener();
            pass = new PassedListener();
            passed = false;
            Robot robo = new Robot();
            robo.setAutoDelay(100);

            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });

            robo.waitForIdle();
            robo.delay(1000);

            int altKey = java.awt.event.KeyEvent.VK_ALT;
            Util.hitMnemonics(robo, KeyEvent.VK_F); // Enter File menu
            robo.keyPress(KeyEvent.VK_S);  // Enter submenu
            robo.keyRelease(KeyEvent.VK_S);
            robo.keyPress(KeyEvent.VK_O); // Launch "One" action
            robo.keyRelease(KeyEvent.VK_O);
            robo.keyPress(KeyEvent.VK_M); // Launch "One" action
            robo.keyRelease(KeyEvent.VK_M);

            robo.waitForIdle();

            if (!passed) {
                throw new RuntimeException("Test failed.");
            }
        } finally {
             if (mainFrame != null) SwingUtilities.invokeAndWait(() -> mainFrame.dispose());
        }
    }


    private static void createAndShowGUI() {
        mainFrame = new JFrame("Bug 4692443");
        JMenuBar mbar = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.setMnemonic('F');
        menu.add(new JMenuItem("Menu Item 1")).setMnemonic('I');
        final JMenu submenu = new JMenu("Submenu");
        submenu.setMnemonic('S');
        submenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent e) {
                JMenuItem item = submenu.add(new JMenuItem("One", 'O'));
                item.addActionListener(pass);
                submenu.add(new JMenuItem("Two", 'w'));
                submenu.add(new JMenuItem("Three", 'r'));
            }
            public void menuDeselected(MenuEvent e) {
                submenu.removeAll();
            }
            public void menuCanceled(MenuEvent e) {
                submenu.removeAll();
            }
        });
        menu.add(submenu);
        JMenuItem menuItem = menu.add(new JMenuItem("Menu Item 2"));
        menuItem.setMnemonic('M');
        menuItem.addActionListener(fail);
        mbar.add(menu);
        mainFrame.setJMenuBar(mbar);

        mainFrame.setSize(200, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.toFront();
    }

    public static class FailedListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            throw new RuntimeException("Test failed.");
        }
    }

    public static class PassedListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            passed = true;
        }
    }

}
