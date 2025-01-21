/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4345798
 * @summary Tests if Pressing enter to dismiss menu works when a JRootPane
 * has a default button.
 * @key headful
 * @run main bug4345798
 */

import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public class bug4345798 {
    private static JFrame f;
    private static JButton b;
    private static JMenu menu;
    private static volatile boolean passed = true;
    private static volatile Point p;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeAndWait(() -> {
                f = new JFrame("bug4345798");
                JMenuBar mbar = new JMenuBar();
                JMenuItem item = new JMenuItem("Open...");
                menu = new JMenu("File");
                item.addActionListener(new TestActionListener());
                menu.add(item);
                mbar.add(menu);

                f.setJMenuBar(mbar);

                b = new JButton("Default");
                b.addActionListener(new TestActionListener());
                f.getContentPane().add(b);
                JRootPane rp = f.getRootPane();
                rp.setDefaultButton(b);

                f.setSize(200, 200);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                b.requestFocus();
            });

            Robot robot = new Robot();
            robot.setAutoDelay(100);
            robot.waitForIdle();
            robot.delay(1000);

            SwingUtilities.invokeAndWait(() -> p = menu.getLocationOnScreen());
            robot.mouseMove(p.x, p.y);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            robot.keyPress(KeyEvent.VK_F10);
            robot.keyRelease(KeyEvent.VK_F10);

            robot.keyPress(KeyEvent.VK_DOWN);
            robot.keyRelease(KeyEvent.VK_DOWN);

            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (f != null) {
                    f.dispose();
                }
            });
        }

        if (!passed) {
            throw new RuntimeException("Test failed.");
        }
    }

    static class TestActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == b) {
                passed = false;
            }
        }
    }
}
