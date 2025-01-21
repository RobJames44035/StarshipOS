/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4977491 8160767
 * @summary State changes should always be reported as events
 * @run main MaximizedToIconified
 */

/*
 * MaximizedToIconified.java
 *
 * summary:  Invoking setExtendedState(ICONIFIED) on a maximized
 *           frame should not combine the maximized and iconified
 *           states in the newState of the state change event.
 */

import java.awt.Frame;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

public class MaximizedToIconified
{
    static volatile int lastFrameState;
    static volatile boolean failed = false;
    static volatile Toolkit myKit;
    private static Robot robot;

    private static void checkState(Frame frame, int state) {
        frame.setExtendedState(state);
        robot.waitForIdle();
        robot.delay(100);

        System.out.println("state = " + state + "; getExtendedState() = " + frame.getExtendedState());

        if (failed) {
            frame.dispose();
            throw new RuntimeException("getOldState() != previous getNewState() in WINDOW_STATE_CHANGED event.");
        }
        if (lastFrameState != frame.getExtendedState()) {
            frame.dispose();
            throw new RuntimeException("getExtendedState() != last getNewState() in WINDOW_STATE_CHANGED event.");
        }
        if (frame.getExtendedState() != state) {
            frame.dispose();
            throw new RuntimeException("getExtendedState() != " + state + " as expected.");
        }
    }

    private static void examineStates(int states[]) {

        Frame frame = new Frame("test");
        frame.setSize(200, 200);
        frame.setVisible(true);

        lastFrameState = Frame.NORMAL;

        robot.waitForIdle();

        frame.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent e) {
                System.out.println("last = " + lastFrameState + "; getOldState() = " + e.getOldState() +
                        "; getNewState() = " + e.getNewState());
                if (e.getOldState() == lastFrameState) {
                    lastFrameState = e.getNewState();
                } else {
                    System.out.println("Wrong getOldState(): expected = " + lastFrameState + "; received = " +
                            e.getOldState());
                    failed = true;
                }
            }
        });

        for (int state : states) {
            if (myKit.isFrameStateSupported(state)) {
                checkState(frame, state);
            } else {
                System.out.println("Frame state = " + state + " is NOT supported by the native system. The state is skipped.");
            }
        }

        if (frame != null) {
            frame.dispose();
        }
    }

    private static void doTest() {

        myKit = Toolkit.getDefaultToolkit();

        // NOTE! Compound states (like MAXIMIZED_BOTH | ICONIFIED) CANNOT be used,
        //    because Toolkit.isFrameStateSupported() method reports these states
        //    as not supported. And such states will simply be skipped.
        examineStates(new int[] {Frame.MAXIMIZED_BOTH, Frame.ICONIFIED, Frame.NORMAL});
        System.out.println("------");
        examineStates(new int[] {Frame.ICONIFIED, Frame.MAXIMIZED_BOTH, Frame.NORMAL});
        System.out.println("------");
        examineStates(new int[] {Frame.NORMAL, Frame.MAXIMIZED_BOTH, Frame.ICONIFIED});
        System.out.println("------");
        examineStates(new int[] {Frame.NORMAL, Frame.ICONIFIED, Frame.MAXIMIZED_BOTH});

    }

    public static void main( String args[] ) throws Exception
    {
        robot = new Robot();
        doTest();

    }

}
