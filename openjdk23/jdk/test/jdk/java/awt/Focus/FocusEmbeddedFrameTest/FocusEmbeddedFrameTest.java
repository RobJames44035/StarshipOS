/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @key       headful
 * @bug       6516675
 * @summary   Tests that EmbeddedFrame can be focused.
 * @requires (os.family == "windows")
 * @modules   java.desktop/java.awt.peer
 *            java.desktop/sun.awt
 *            java.desktop/sun.awt.windows
 * @library /java/awt/patchlib    ../../regtesthelpers
 * @build java.desktop/java.awt.Helper
 * @build     Util UtilInternal
 * @run       main FocusEmbeddedFrameTest
 */

import java.awt.*;
import java.awt.event.*;
import test.java.awt.regtesthelpers.Util;
import test.java.awt.regtesthelpers.UtilInternal;

public class FocusEmbeddedFrameTest {
    static Frame embedder = new Frame("Embedder");
    static Frame ef = null;
    static volatile boolean passed;

    Robot robot;

    public static void main(String[] args) {
        FocusEmbeddedFrameTest app = new FocusEmbeddedFrameTest();
        app.init();
        app.start();
    }

    public void init() {
        robot = Util.createRobot();
    }

    public void start() {

        if (!"sun.awt.windows.WToolkit".equals(Toolkit.getDefaultToolkit().getClass().getName())) {
            System.out.println("The test is for WToolkit only!");
            return;
        }

        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
                public void eventDispatched(AWTEvent e) {
                   System.err.println("--> " + e);
                }
            }, FocusEvent.FOCUS_EVENT_MASK | WindowEvent.WINDOW_EVENT_MASK);

        embedder.addNotify();

        try {
            ef = UtilInternal.createEmbeddedFrame(embedder);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new Error("Test error: couldn't create an EmbeddedFrame!");
        }

        ef.setSize(100, 100);
        ef.setBackground(Color.blue);

        embedder.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    FocusEmbeddedFrameTest.ef.requestFocus();
                }
            });

        ef.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    passed = true;
                }
            });

        embedder.setSize(150, 150);
        embedder.setVisible(true);

        Util.waitForIdle(robot);

        if (!passed) {
            throw new TestFailedException("the EmbeddedFrame didn't get focus on request!");
        }

        System.out.println("Test passed.");
    }
}

class TestFailedException extends RuntimeException {
    TestFailedException(String msg) {
        super("Test failed: " + msg);
    }
}
