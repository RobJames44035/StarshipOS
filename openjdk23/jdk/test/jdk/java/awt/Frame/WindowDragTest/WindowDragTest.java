/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 7128738 7161759
 * @summary dragged dialog freezes system on dispose
 * @author Oleg Pekhovskiy: area=awt.toplevel
 * @library ../../regtesthelpers
 * @build Util
 * @run main WindowDragTest
 */

import java.awt.Frame;
import java.awt.event.InputEvent;
import java.awt.AWTException;
import test.java.awt.regtesthelpers.Util;
import java.awt.Robot;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WindowDragTest {

    static boolean passed = false;

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            robot.setAutoDelay(1000);

            Frame frame1 = new Frame();
            frame1.setBounds(50, 50, 300, 200);
            frame1.setVisible(true);
            frame1.toFront();
            frame1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Clicking frame1 succeeded - mouse is not captured
                    passed = true;
                }
            });
            robot.delay(1000);

            Frame frame2 = new Frame();
            frame2.setBounds(100, 100, 300, 200);
            frame2.setVisible(true);
            frame2.toFront();
            robot.delay(1000);

            Point p = frame2.getLocationOnScreen();
            Dimension d = frame2.getSize();

            // Move cursor to frame2 title bar to drag
            robot.mouseMove(p.x + (int)(d.getWidth() / 2), p.y + (int)frame2.getInsets().top / 2);
            Util.waitForIdle(robot);

            // Start window dragging
            robot.mousePress(InputEvent.BUTTON1_MASK);
            Util.waitForIdle(robot);

            // Dispose window being dragged
            frame2.dispose();
            Util.waitForIdle(robot);

            // Release mouse button to be able to get MOUSE_CLICKED event on Util.clickOnComp()
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            Util.waitForIdle(robot);

            // Click frame1 to check whether mouse is not captured by frame2
            Util.clickOnComp(frame1, robot);
            Util.waitForIdle(robot);

            frame1.dispose();
            if (passed) {
                System.out.println("Test passed.");
            }
            else {
                System.out.println("Test failed.");
                throw new RuntimeException("Test failed.");
            }
        }
        catch (AWTException e) {
            throw new RuntimeException("AWTException occurred - problem creating robot!");
        }
    }
}
