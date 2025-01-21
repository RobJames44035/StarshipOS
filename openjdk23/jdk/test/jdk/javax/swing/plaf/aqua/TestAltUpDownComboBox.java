/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
/* @test
 * @bug 7148092
 * @requires (os.family == "mac")
 * @summary Tests that alt+down arrow pulls down JComboBox popup
 * @key headful
 * @run main TestAltUpDownComboBox
*/

import java.awt.Container;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

public class TestAltUpDownComboBox {

    private static JFrame frame;
    private static JComboBox combo;

    public static void main(String[] argv) throws Exception {
        Robot robot = new Robot();
        robot.setAutoDelay(100);
        try {
            SwingUtilities.invokeAndWait(() -> {
                frame = new JFrame("");
                Object[] fruits = {"Banana", "Pear", "Apple"};
                combo = new JComboBox(fruits);
                Container pane = frame.getContentPane();
                pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
                pane.add(combo);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
            robot.waitForIdle();
            robot.delay(1000);
            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.delay(1000);

            if (!combo.isPopupVisible()) {
                throw new RuntimeException("comboBox is not visible");
            }

            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.delay(1000);

            if (combo.getSelectedIndex() != combo.getItemCount() - 1) {
                System.out.println(combo.getSelectedIndex());
                throw new RuntimeException("Alt+Down did not select last entry");
            }

            robot.keyPress(KeyEvent.VK_ALT);
            robot.keyPress(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_UP);
            robot.keyRelease(KeyEvent.VK_ALT);
            robot.delay(1000);

            if (combo.getSelectedIndex() != 0) {
                System.out.println(combo.getSelectedIndex());
                throw new RuntimeException("Alt+Up did not select first entry");
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
