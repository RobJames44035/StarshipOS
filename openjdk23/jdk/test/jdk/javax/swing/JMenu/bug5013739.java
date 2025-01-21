/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
  @test
  @bug 5013739
  @summary MNEMONIC CONFLICTS IN DISABLED/HIDDEN MENU ITEMS
  @library ../regtesthelpers
  @build JRobot
  @key headful
  @run main bug5013739
*/

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

public class bug5013739 {

    static boolean passed = true;
    static JFrame mainFrame;
    static JMenu file;

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        SwingUtilities.invokeAndWait(() -> {
            mainFrame = new JFrame("Bug5013739");
            JMenuBar mb = new JMenuBar();
            mainFrame.setJMenuBar(mb);
            file = new JMenu("File");
            JMenuItem about = new JMenuItem("About");
            about.setMnemonic('A');
            about.addActionListener(new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                    passed = false;
                }
            });
            file.add(about);
            about.setVisible(false);
            file.add("Open");
            file.add("Close");
            file.setMnemonic('F');
            mb.add(file);
            mainFrame.pack();
            mainFrame.setVisible(true);
            Util.blockTillDisplayed(mainFrame);
        });

        try {
            JRobot robo = JRobot.getRobot();
            robo.delay(500);
            robo.clickMouseOn(file);
            robo.hitKey(KeyEvent.VK_A);
            robo.delay(1000);
        } finally {
            if (mainFrame != null) {
                SwingUtilities.invokeAndWait(() -> mainFrame.dispose());
            }
        }
        if (!passed) {
            throw new RuntimeException("Hidden menu item is selectable "+
                    "via mnemonic. Test failed.");
        }
    }
}

class Util {
    public static Point blockTillDisplayed(Component comp) {
        Point p = null;
        while (p == null) {
            try {
                p = comp.getLocationOnScreen();
            } catch (IllegalStateException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                }
            }
        }
        return p;
    }
}
