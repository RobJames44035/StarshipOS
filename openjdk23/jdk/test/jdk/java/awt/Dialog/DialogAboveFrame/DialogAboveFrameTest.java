/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8169589 8171909
 * @summary Activating a dialog puts to back another dialog owned by the same frame
 * @author Dmitry Markov
 * @library ../../regtesthelpers
 * @build Util
 * @run main DialogAboveFrameTest
 */

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;

import test.java.awt.regtesthelpers.Util;

public class DialogAboveFrameTest {
    public static void main(String[] args) {
        Robot robot = Util.createRobot();

        Frame frame = new Frame("Frame");
        frame.setBackground(Color.BLUE);
        frame.setBounds(200, 50, 300, 300);
        frame.setVisible(true);

        Dialog dialog1 = new Dialog(frame, "Dialog 1", false);
        dialog1.setBackground(Color.RED);
        dialog1.setBounds(100, 100, 200, 200);
        dialog1.setVisible(true);

        Dialog dialog2 = new Dialog(frame, "Dialog 2", false);
        dialog2.setBackground(Color.GREEN);
        dialog2.setBounds(400, 100, 200, 200);
        dialog2.setVisible(true);

        Util.waitForIdle(robot);

        Util.clickOnComp(dialog2, robot);
        Util.waitForIdle(robot);

        Point point = dialog1.getLocationOnScreen();
        int x = point.x + (int)(dialog1.getWidth() * 0.9);
        int y = point.y + (int)(dialog1.getHeight() * 0.9);

        try {
            if (!Util.testPixelColor(x, y, dialog1.getBackground(), 10, 100, robot)) {
                throw new RuntimeException("Test FAILED: Dialog is behind the frame");
            }
        } finally {
            frame.dispose();
            dialog1.dispose();
            dialog2.dispose();
        }
    }
}

