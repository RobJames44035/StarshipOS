/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
  @test
  @bug 6259328
  @summary Choice scrolls when dragging the parent frame while drop-down \
            is active
  @key headful
  @run main PressOutsideOpenedChoice
*/


import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.lang.reflect.InvocationTargetException;

public class PressOutsideOpenedChoice extends Frame {
    Robot robot;
    Choice choice1 = new Choice();
    Point pt;

    public static void main(String[] args)
            throws InterruptedException, InvocationTargetException {
        PressOutsideOpenedChoice pressOutsideOpenedChoice =
                new PressOutsideOpenedChoice();
        EventQueue.invokeAndWait(pressOutsideOpenedChoice::init);
        pressOutsideOpenedChoice.start();
    }

    public void init() {
        for (int i = 1; i < 50; i++) {
            choice1.add("item-" + i);
        }
        choice1.setForeground(Color.red);
        choice1.setBackground(Color.red);
        add(choice1);
        setLayout(new FlowLayout());
        setSize (200,200);
        setLocationRelativeTo(null);
        setVisible(true);
        validate();
    }

    public void start() {
        try {
            robot = new Robot();
            robot.setAutoWaitForIdle(true);
            robot.setAutoDelay(50);
            robot.delay(1000);
            testPressOutsideOpenedChoice(InputEvent.BUTTON1_DOWN_MASK);
        } catch (Throwable e) {
            throw new RuntimeException("Test failed. Exception thrown: " + e);
        } finally {
            EventQueue.invokeLater(this::dispose);
        }
    }

    public void testPressOutsideOpenedChoice(int button) {
        pt = choice1.getLocationOnScreen();
        robot.mouseMove(pt.x + choice1.getWidth() - choice1.getHeight() / 4,
                pt.y + choice1.getHeight() / 2);
        robot.delay(100);
        robot.mousePress(button);
        robot.mouseRelease(button);
        robot.delay(200);
        //move mouse outside of the choice
        robot.mouseMove(pt.x - choice1.getWidth() / 2,
                pt.y + choice1.getHeight() / 2);
        robot.delay(400);
        robot.mousePress(button);
        robot.mouseRelease(button);
        robot.delay(200);
        Color color = robot.getPixelColor(pt.x + choice1.getWidth() / 2,
                pt.y + 3 * choice1.getHeight());
        System.out.println("color got " +
                robot.getPixelColor(pt.x + choice1.getWidth() / 2,
                        pt.y + 3 * choice1.getHeight()));
        if (color.equals(Color.red)) {
            throw new RuntimeException("Test failed. Choice didn't close " +
                    "after mouse press outside of Choice " + button);
        } else {
            System.out.println("Test passed. " +
                    "Choice closed with MousePress outside");
        }
    }
}
