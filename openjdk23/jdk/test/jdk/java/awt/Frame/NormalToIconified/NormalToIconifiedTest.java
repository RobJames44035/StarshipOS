/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 8171949 8214046
 * @summary Tests that bitwise mask is set and state listener is notified during state transition.
 * @author Dmitry Markov
 * @library ../../regtesthelpers
 * @build Util
 * @run main NormalToIconifiedTest
 */

import java.awt.Frame;
import java.awt.Robot;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.concurrent.atomic.AtomicBoolean;

import test.java.awt.regtesthelpers.Util;

public class NormalToIconifiedTest {

    public static void main(String[] args) {
        test(false);
        test(true);
    }

    private static void test(final boolean undecorated) {
        AtomicBoolean listenerNotified = new AtomicBoolean(false);

        Robot robot = Util.createRobot();
        Frame testFrame = new Frame("Test Frame");
        testFrame.setUndecorated(undecorated);
        testFrame.setSize(200, 200);
        testFrame.addWindowStateListener(new WindowStateListener() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                listenerNotified.set(true);
                synchronized (listenerNotified) {
                    listenerNotified.notifyAll();
                }
            }
        });
        testFrame.setVisible(true);
        Frame mainFrame = new Frame("Main Frame");
        mainFrame.setSize(200, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        Util.waitForIdle(robot);
        try {
            Util.clickOnComp(mainFrame, robot);
            Util.waitForIdle(robot);

            // NORMAL -> ICONIFIED
            listenerNotified.set(false);
            testFrame.setExtendedState(Frame.ICONIFIED);
            Util.waitForIdle(robot);

            Util.waitForCondition(listenerNotified, 2000);
            if (!listenerNotified.get()) {
                throw new RuntimeException("Test FAILED! Window state listener was not notified during NORMAL to" +
                        "ICONIFIED transition");
            }
            if (testFrame.getExtendedState() != Frame.ICONIFIED) {
                throw new RuntimeException("Test FAILED! Frame is not in ICONIFIED state");
            }

            // ICONIFIED -> NORMAL
            listenerNotified.set(false);
            testFrame.setExtendedState(Frame.NORMAL);
            Util.waitForIdle(robot);

            Util.waitForCondition(listenerNotified, 2000);
            if (!listenerNotified.get()) {
                throw new RuntimeException("Test FAILED! Window state listener was not notified during ICONIFIED to" +
                        "NORMAL transition");
            }
            if (testFrame.getExtendedState() != Frame.NORMAL) {
                throw new RuntimeException("Test FAILED! Frame is not in NORMAL state");
            }
        } finally {
            testFrame.dispose();
            mainFrame.dispose();
        }
    }
}

