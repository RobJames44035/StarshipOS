/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6480024
  @library ../../../regtesthelpers
  @build Util Sysout AbstractTest
  @summary stack overflow on mouse wheel rotation
  @author Andrei Dmitriev: area=awt.event
  @run main InfiniteRecursion
*/

/**
 * InfiniteRecursion.java
 *
 * summary: put a JButton into JFrame.
 * Add MouseWheelListener to JFrame.
 * Add MouseListener to JButton.
 * Rotating a wheel over the JButton would result in stack overflow.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import test.java.awt.regtesthelpers.Util;
import test.java.awt.regtesthelpers.AbstractTest;
import test.java.awt.regtesthelpers.Sysout;

public class InfiniteRecursion {
    final static Robot robot = Util.createRobot();
    final static int MOVE_COUNT = 5;

    //*2 for both rotation directions,
    //*2 as Java sends the wheel event to every for nested component in hierarchy under cursor
    final static int EXPECTED_COUNT = MOVE_COUNT * 2 * 2;

    static int actualEvents = 0;

    public static void main(String []s)
    {
        JFrame frame = new JFrame("A test frame");
        frame.setSize(200, 200);
        frame.addMouseWheelListener(new MouseWheelListener() {
                public void mouseWheelMoved(MouseWheelEvent e) {
                    System.out.println("Wheel moved on FRAME : "+e);
                    actualEvents++;
                }
            });

        JButton jButton = new JButton();
        /*
          outputBox.addMouseWheelListener(new MouseWheelListener() {
          public void mouseWheelMoved(MouseWheelEvent e){}
          });
        */
        jButton.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    System.out.println("MousePressed on jButton : "+e);
                }

            });
        frame.add(jButton);

        frame.setVisible(true);

        Util.waitForIdle(robot);

        Util.pointOnComp(jButton, robot);
        Util.waitForIdle(robot);

        for (int i = 0; i < MOVE_COUNT; i++){
            robot.mouseWheel(1);
            robot.delay(10);
        }

        for (int i = 0; i < MOVE_COUNT; i++){
            robot.mouseWheel(-1);
            robot.delay(10);
        }


        Util.waitForIdle(robot);
        //Not fair to check for multiplier 4 as it's not specified actual number of WheelEvents
        //result in a single wheel rotation.
        if (actualEvents != EXPECTED_COUNT) {
            AbstractTest.fail("Expected events count: "+ EXPECTED_COUNT+" Actual events count: "+ actualEvents);
        }
    }
}
