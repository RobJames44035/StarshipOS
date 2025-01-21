/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import java.awt.Robot;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;


/*
 * @test
 * @bug 4321273
 * @summary NotSerializableException during the menu serialization
 * @key headful
 * @run main bug4321273
*/

public class bug4321273 {
    public static JFrame frame;
    public static JMenu menu;

    public static void main(String[] args) throws Exception {
        try {
            Robot robot = new Robot();
            SwingUtilities.invokeAndWait(() -> {
                JMenuBar menuBar = new JMenuBar();
                frame = new JFrame();
                frame.setJMenuBar(menuBar);
                menu = new JMenu("Menu");
                menuBar.add(menu);
                menu.add(new JMenuItem("item 1"));
                menu.add(new JMenuItem("item 2"));
                menu.add(new JMenuItem("item 3"));
                frame.pack();
                frame.setVisible(true);
            });
            robot.waitForIdle();
            robot.delay(1000);
            SwingUtilities.invokeAndWait(() -> {
                menu.doClick();
                try {
                    ByteArrayOutputStream byteArrayOutputStream =
                            new ByteArrayOutputStream();
                    ObjectOutputStream oos =
                            new ObjectOutputStream(byteArrayOutputStream);
                    oos.writeObject(menu);
                } catch (Exception se) {
                    throw new RuntimeException("NotSerializableException " +
                            "during the menu serialization", se);
                }
            });

            robot.waitForIdle();
            robot.delay(100);
            System.out.println("Test Passed!");
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }
}
