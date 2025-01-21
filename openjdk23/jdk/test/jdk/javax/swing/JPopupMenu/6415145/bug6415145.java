/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */
/*
 * @test
 * @key headful
 * @bug 6415145
 * @summary REGRESSION: Selected item is not being updated while dragging above popup menu
 * @library /lib/client
 * @build ExtendedRobot
 * @author Mikhail Lapshin
 * @run main bug6415145
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.AWTException;
import java.awt.Component;

public class bug6415145 {
    private JFrame frame;
    private JButton button;
    private JPopupMenu popupMenu;
    private JMenuItem item1;
    private JMenuItem item2;
    private static ExtendedRobot robot;

    public static void main(String[] args) throws Exception {
        robot = new ExtendedRobot();
        final bug6415145 bugTest = new bug6415145();
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    bugTest.init();
                }
            });

            robot.waitForIdle();
            bugTest.test();
        } finally {
            bugTest.stopEDT();
        }
    }

    private void stopEDT() {
        if (frame != null) {
            frame.dispose();
        }
    }

    private void init() {
        popupMenu = new JPopupMenu("test menu");
        item1 = new JMenuItem("item 1");
        item2 = new JMenuItem("item 2");
        popupMenu.add(item1);
        popupMenu.add(item2);

        button = new JButton("test button");
        button.addMouseListener(new MouseListener());

        frame = new JFrame("test frame");
        frame.add(popupMenu);
        frame.add(button);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private class MouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            popupMenu.show(button, e.getX(), e.getY());
        }
    }

    private void test() throws AWTException {
        try {
            moveMouseTo(robot, button);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.waitForIdle();

            moveMouseTo(robot, item1);
            robot.waitForIdle();

            moveMouseTo(robot, item2);
            robot.waitForIdle();
            if ( (item1.isArmed()) || (!item2.isArmed()) ) {
                throw new RuntimeException("Selected item is not being updated" +
                        " while dragging above popup menu.");
            }
        } finally {
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }
    }
    private void moveMouseTo(ExtendedRobot robot, Component c) {
        java.awt.Point p = c.getLocationOnScreen();
        java.awt.Dimension size = c.getSize();
        p.x += size.width / 2;
        p.y += size.height / 2;
        robot.mouseMove(p.x, p.y);
        robot.delay(100);
    }
}
