/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4524490
 * @summary Tests if in JFileChooser, ALT+L does not bring focus to 'Files' selection list in Motif LAF
 * @author Konstantin Eremin
 * @library ../../regtesthelpers
 * @library /test/lib
 * @build Util jdk.test.lib.Platform
 * @run main bug4524490
 */
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.*;

import jdk.test.lib.Platform;

public class bug4524490 {

    private static JFileChooser fileChooser;

    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);

        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                fileChooser = new JFileChooser();
                fileChooser.showOpenDialog(null);
            }
        });

        robot.waitForIdle();

        if (Platform.isOSX()) {
            Util.hitKeys(robot, KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_L);
        } else {
            Util.hitKeys(robot, KeyEvent.VK_ALT, KeyEvent.VK_L);
        }
        checkFocus();
    }

    private static void checkFocus() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                JList list = (JList) Util.findSubComponent(fileChooser, "javax.swing.JList");
                System.out.println("list focus: " + list.isFocusOwner());
                if (!list.isFocusOwner()) {
                    throw new RuntimeException("Focus is not transfered to the Folders list.");
                }
            }
        });
    }
}
