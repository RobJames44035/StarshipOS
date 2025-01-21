/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8061636
 * @summary fix for 7079254 changes behavior of MouseListener, MouseMotionListener
 * @library ../../regtesthelpers
 * @build Util
 * @author Alexander Zvegintsev
 * @run main RemovedComponentMouseListener
 */

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import test.java.awt.regtesthelpers.Util;

public class RemovedComponentMouseListener extends JFrame {

    static boolean mouseReleasedReceived;
    static JButton button;

    public RemovedComponentMouseListener() {
        JPanel panel = new JPanel();
        JPanel buttonPanel = new JPanel();
        button = new JButton("Button");

        setSize(300, 300);

        buttonPanel.add(button);
        panel.add(buttonPanel);
        setContentPane(panel);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                buttonPanel.remove(button);
                panel.add(button);
                button.revalidate();
                button.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mouseReleasedReceived = true;
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            new RemovedComponentMouseListener();
        });

        Robot r = Util.createRobot();
        r.setAutoDelay(100);
        r.waitForIdle();
        Util.pointOnComp(button, r);

        r.waitForIdle();
        r.mousePress(InputEvent.BUTTON1_MASK);
        r.waitForIdle();
        r.mouseRelease(InputEvent.BUTTON1_MASK);
        r.waitForIdle();
        if (!mouseReleasedReceived) {
            throw new RuntimeException("mouseReleased event was not received");
        }
    }
}
