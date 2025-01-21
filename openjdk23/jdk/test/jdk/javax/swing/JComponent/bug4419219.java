/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
   @bug 4419219
   @summary Tests that registerKeyboardAction(null, ...) doen't throw NPE.
   @key headful
   @run main bug4419219
*/

import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

public class bug4419219 {
    static volatile boolean passed = true;
    static JFrame frame;
    static Robot robo;

    public static void main(String[] args) throws Exception {
        robo = new Robot();
        robo.setAutoWaitForIdle(true);
        robo.setAutoDelay(100);
        SwingUtilities.invokeAndWait(() -> {
            try {
                frame = new JFrame("bug4419219 Table");

                final String[] names = {"col"};
                final Object[][] data = {{"A"}, {"B"}, {"C"}, {"D"}, {"E"}};

                JTable tableView = (JTable)new TestTable(data, names);
                // unregister ctrl-A
                tableView.registerKeyboardAction(null,
                     KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK),
                     JComponent.WHEN_FOCUSED);

                frame.getContentPane().add(tableView);
                frame.setSize(250,250);
                frame.setLocationRelativeTo(null);
                frame.addWindowListener(new TestStateListener());
                frame.setVisible(true);
            } finally {
                if (frame != null) {
                    frame.dispose();
                }
           }
        });
        if (!passed) {
            throw new RuntimeException("Test failed.");
        }
    }

    static class TestStateListener extends WindowAdapter {
        public void windowOpened(WindowEvent ev) {
            robo.delay(1000);
            robo.mouseMove(100,100);
            robo.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robo.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robo.keyPress(KeyEvent.VK_CONTROL);
            robo.keyPress(KeyEvent.VK_A);
            robo.keyRelease(KeyEvent.VK_A);
            robo.keyRelease(KeyEvent.VK_CONTROL);
        }
    }

    static class TestTable extends JTable {

        public TestTable(Object[][] data, String[] names) {
            super(data, names);
        }

        protected  boolean processKeyBinding(KeyStroke ks,
                                             KeyEvent e,
                                             int condition,
                                             boolean pressed) {
            try {
                return super.processKeyBinding(ks, e, condition, pressed);
            } catch (NullPointerException ex) {
                passed = false;
            }
            return false;
        }
    }
}
