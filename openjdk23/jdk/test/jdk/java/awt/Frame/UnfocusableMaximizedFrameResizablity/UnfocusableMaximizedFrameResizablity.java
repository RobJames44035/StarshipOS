/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4980161 7158623 8204860 8208125 8215280
  @summary Setting focusable window state to false makes the maximized frame resizable
  @compile UnfocusableMaximizedFrameResizablity.java
  @run main UnfocusableMaximizedFrameResizablity
*/

import java.awt.Toolkit;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.awt.Robot;

public class UnfocusableMaximizedFrameResizablity {

    private static Frame frame;
    private static Robot robot;
    private static boolean isProgInterruption = false;
    private static Thread mainThread = null;
    private static int sleepTime = 300000;

    private static void createAndShowFrame() throws Exception {

        //MAXIMIZED_BOTH frame is resizable on Mac OS by default. Nothing to test.
        if (System.getProperty("os.name").toLowerCase().startsWith("mac")) {
            cleanup();
            return;
        }

        //The MAXIMIZED_BOTH state is not supported by the toolkit. Nothing to test.
        if (!Toolkit.getDefaultToolkit().isFrameStateSupported(Frame.MAXIMIZED_BOTH)) {
            cleanup();
            return;
        }

        frame = new Frame("Unfocusable frame");
        frame.setMaximizedBounds(new Rectangle(0, 0, 300, 300));
        frame.setSize(200, 200);
        frame.setVisible(true);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setFocusableWindowState(false);

        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Robot creation failed");
        }
        robot.delay(2000);

        // The initial bounds of the frame
        final Rectangle bounds = frame.getBounds();

        // Let's move the mouse pointer to the bottom-right coner of the frame (the "size-grip")
        robot.mouseMove(bounds.x + bounds.width - 2, bounds.y + bounds.height - 2);
        robot.waitForIdle();

        // ... and start resizing
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.waitForIdle();
        robot.mouseMove(bounds.x + bounds.width + 20, bounds.y + bounds.height + 15);
        robot.waitForIdle();

        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        robot.waitForIdle();

        // The bounds of the frame after the attempt of resizing is made
        final Rectangle finalBounds = frame.getBounds();

        if (!finalBounds.equals(bounds)) {
            cleanup();
            throw new RuntimeException("The maximized unfocusable frame can be resized.");
        }
        cleanup();
    }

    private static void cleanup() {
        if (frame != null) {
            frame.dispose();
        }
        isProgInterruption = true;
        mainThread.interrupt();
    }

    public static void main(String args[]) throws Exception {

        mainThread = Thread.currentThread();

        try {
            createAndShowFrame();
            mainThread.sleep(sleepTime);
        } catch (InterruptedException e) {
            if (!isProgInterruption) {
                throw e;
            }
        }

        if (!isProgInterruption) {
            throw new RuntimeException("Timed out after " + sleepTime / 1000
                    + " seconds");
        }
    }
}

