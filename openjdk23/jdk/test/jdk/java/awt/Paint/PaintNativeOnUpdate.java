/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;

/**
 * @test
 * @key headful
 * @bug 7157680
 * @library /lib/client
 * @build ExtendedRobot
 * @author Sergey Bylokhov
 * @run main/othervm -Dsun.java2d.uiScale=1 PaintNativeOnUpdate
 */
public final class PaintNativeOnUpdate extends Label {

    private boolean fullUpdate = true;

    public static void main(final String[] args) throws AWTException {
        ExtendedRobot robot = new ExtendedRobot();
        robot.setAutoDelay(50);
        final Frame frame = new Frame();
        final Component label = new PaintNativeOnUpdate();
        frame.setBackground(Color.RED);
        frame.add(label);
        frame.setSize(300, 300);
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        robot.waitForIdle(1000);
        label.repaint();// first paint
        robot.waitForIdle(1000);
        label.repaint();// incremental paint
        robot.waitForIdle(1000);

        Point point = label.getLocationOnScreen();
        Color color = robot.getPixelColor(point.x + label.getWidth() / 2,
                                          point.y + label.getHeight() / 2);
        if (!color.equals(Color.GREEN)) {
            System.err.println("Expected color = " + Color.GREEN);
            System.err.println("Actual color = " + color);
            throw new RuntimeException();
        }
        frame.dispose();
    }

    @Override
    public void update(final Graphics g) {
        if (fullUpdate) {
            //full paint
            g.setColor(Color.GREEN);
            g.fillRect(0, 0, getWidth(), getHeight());
            fullUpdate = false;
        } else {
            // Do nothing
            // incremental paint
        }
    }

    @Override
    public void paint(final Graphics g) {
        // Do nothing
    }
}
