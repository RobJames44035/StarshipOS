/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/* @test
 * @key headful
 * @bug 8326497
 * @summary Verifies that an iconified window is restored with Window.toFront()
 * @library /test/lib
 * @run main IconifiedToFront
 */

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Robot;
import java.awt.Toolkit;

public class IconifiedToFront {
    private static final int PAUSE_MS = 500;
    private static Robot robot;

    public static void main(String[] args) throws Exception {
        if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.ICONIFIED)) {
            return; // Nothing to test
        }

        robot = new Robot();
        IconifiedToFront.test1();
        IconifiedToFront.test2();
    }

    private static void test1() {
        Frame frame1 = new Frame("IconifiedToFront Test 1");
        try {
            frame1.setLayout(new FlowLayout());
            frame1.setSize(400, 300);
            frame1.setBackground(Color.green);
            frame1.add(new Label("test"));
            frame1.setVisible(true);
            pause();
            frame1.setExtendedState(Frame.ICONIFIED);
            pause();
            frame1.toFront();
            pause();
            int state = frame1.getExtendedState();
            if ((state & Frame.ICONIFIED) != 0) {
                throw new RuntimeException("Test Failed: state is still ICONIFIED: " + state);
            }
        } finally {
            frame1.dispose();
        }
    }

    private static void test2() {
        Frame frame1 = new Frame("IconifiedToFront Test 3");
        try {
            frame1.setLayout(new FlowLayout());
            frame1.setSize(400, 300);
            frame1.setBackground(Color.green);
            frame1.add(new Label("test"));
            frame1.setUndecorated(true);
            frame1.setVisible(true);
            pause();
            frame1.setExtendedState(Frame.ICONIFIED);
            pause();
            frame1.toFront();
            pause();
            int state = frame1.getExtendedState();
            if ((state & Frame.ICONIFIED) != 0) {
                throw new RuntimeException("Test Failed: state is still ICONIFIED: " + state);
            }
        } finally {
            frame1.dispose();
        }
    }

    private static void pause() {
        robot.delay(PAUSE_MS);
        robot.waitForIdle();
    }
}
