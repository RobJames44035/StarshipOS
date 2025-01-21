/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6267983
  @summary PIT: MouseClicked is triggered when the mouse is released outside the tray icon, Win32
  @library    ../../../regtesthelpers
  @build      Util
  @run main MouseClickTest
*/

import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

import test.java.awt.regtesthelpers.Util;

public class MouseClickTest
{
    private static final int TIMEOUT = 3000;

    public void start ()
    {
        runTests();
    }

    public static final void main(String args[])
    {
        MouseClickTest test = new MouseClickTest();
        test.start();
    }

    void runTests()
    {
        Frame frame = new Frame("frame");
        frame.setBounds(100, 100, 100, 100);
        frame.setVisible(true);
        frame.validate();
        Util.waitTillShown(frame);

        Robot robot = Util.createRobot();
        robot.setAutoDelay(10);

        oneButtonPressRelease(frame, robot, false, 1);
        oneButtonPressRelease(frame, robot, true, 0);
        twoButtonPressRelease(frame, robot, false, 2);
        twoButtonPressRelease(frame, robot, true, 1);
    }

    /*
     * The function tests that the MOUSE_CLICKED event is triggered
     * when the robot just makes one button click. Also it verifies that
     * no event is coming when dragging happens until button will be
     * relealed.
     */
    public static void oneButtonPressRelease(final Component comp, final Robot robot,
                                             final boolean dragging, final int EXPECTED_EVENT_COUNT)
    {
        final AtomicInteger eventCount = new AtomicInteger(0);
        final MouseAdapter adapter = new MouseEventAdapter(eventCount, EXPECTED_EVENT_COUNT);
        comp.addMouseListener(adapter);

        Rectangle bounds = new Rectangle(comp.getLocationOnScreen(), comp.getSize());

        robot.mouseMove(bounds.x + bounds.width / 4, bounds.y + bounds.height / 2);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        if (dragging) {
            robot.mouseMove(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
        }
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        Util.waitForIdle(robot);
        waitForCondition(eventCount, EXPECTED_EVENT_COUNT, TIMEOUT);

        if (eventCount.get() != EXPECTED_EVENT_COUNT) {
            System.out.println("Wrong number of MouseClick events were generated: " +
                               eventCount.get() + ", expected: " + EXPECTED_EVENT_COUNT);
            throw new RuntimeException("Test failed!");
        }

        comp.removeMouseListener(adapter);
        System.out.println("Test passed.");
    }

    /*
     * The function tests sending of the MOUSE_CLICKED events when two
     * different mouse buttons are used to generate MOUSE_CLICKED event.
     * It verifies that number of coming MouseClick events equals to number
     * of mouse clicks are performed by the robot.
     */
    public static void twoButtonPressRelease(final Component comp, final Robot robot,
                                             final boolean dragging, final int EXPECTED_EVENT_COUNT)
    {
        final AtomicInteger eventCount = new AtomicInteger(0);
        final MouseAdapter adapter = new MouseEventAdapter(eventCount, EXPECTED_EVENT_COUNT);
        comp.addMouseListener(adapter);

        Rectangle bounds = new Rectangle(comp.getLocationOnScreen(), comp.getSize());

        robot.mouseMove(bounds.x + bounds.width / 4, bounds.y + bounds.height / 2);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        if (dragging) {
            robot.mouseMove(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
        }
        robot.mousePress(InputEvent.BUTTON2_MASK);
        robot.mouseRelease(InputEvent.BUTTON2_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        Util.waitForIdle(robot);
        waitForCondition(eventCount, EXPECTED_EVENT_COUNT, TIMEOUT);

        if (eventCount.get() != EXPECTED_EVENT_COUNT) {
            System.out.println("Wrong number of MouseClick events were generated: " +
                               eventCount.get() + ", expected: " + EXPECTED_EVENT_COUNT);
            throw new RuntimeException("Test failed!");
        }

        comp.removeMouseListener(adapter);
        System.out.println("Test passed.");
    }

    private static void waitForCondition(final AtomicInteger eventCount, int EXPECTED_EVENT_COUNT,
                                         long timeout)
    {
        synchronized (eventCount) {
            if (eventCount.get() != EXPECTED_EVENT_COUNT) {
                try {
                    eventCount.wait(timeout);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted unexpectedly!");
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

class MouseEventAdapter extends MouseAdapter {

    private final int EXPECTED_EVENT_COUNT;
    private final AtomicInteger eventCount;

    public MouseEventAdapter(final AtomicInteger eventCount, final int EXPECTED_EVENT_COUNT) {
        this.EXPECTED_EVENT_COUNT = EXPECTED_EVENT_COUNT;
        this.eventCount = eventCount;
    }

    public void mouseClicked(MouseEvent e) {
        System.out.println(e);
        synchronized (eventCount) {
            if (eventCount.incrementAndGet() == EXPECTED_EVENT_COUNT) {
                eventCount.notifyAll();
            }
        }
    }
}
