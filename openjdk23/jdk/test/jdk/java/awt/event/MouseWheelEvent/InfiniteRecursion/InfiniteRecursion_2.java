/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6480024
  @library ../../../regtesthelpers
  @build Util Sysout AbstractTest
  @summary stack overflow on mouse wheel rotation within JApplet
  @run main InfiniteRecursion_2
*/

/**
 * InfiniteRecursion_2.java
 *
 * summary: put a JButton into JPanel and then put JPanel into Applet.
 * Add MouseWheelListener to Applet.
 * Add MouseListener to JPanel.
 * Rotating a wheel over the JButton would result in stack overflow.

 * summary: put a JButton into JApplet.
 * Add MouseWheelListener to JApplet.
 * Rotating a wheel over the JButton would result in stack overflow.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import test.java.awt.regtesthelpers.Util;
import test.java.awt.regtesthelpers.AbstractTest;

public class InfiniteRecursion_2 extends Frame {
    final static Robot robot = Util.createRobot();
    final static int MOVE_COUNT = 5;
    //*2 for both rotation directions,
    //*2 as Java sends the wheel event to every for nested component in hierarchy under cursor
    final static int EXPECTED_COUNT = MOVE_COUNT * 2 * 2;
    static int actualEvents = 0;

    public static void main(final String[] args) {
        InfiniteRecursion_2 app = new InfiniteRecursion_2();
        app.init();
        app.start();
    }

    public void init()
    {
        setLayout (new BorderLayout ());
    }//End  init()

    public void start ()
    {
        JPanel outputBox = new JPanel();
        JButton jButton = new JButton();

        this.setSize(200, 200);
        this.setLocationRelativeTo(null);
        this.addMouseWheelListener(new MouseWheelListener() {
                public void mouseWheelMoved(MouseWheelEvent e)
                {
                    System.out.println("Wheel moved on APPLET : "+e);
                    actualEvents++;
                }
            });

        outputBox.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e)
                {
                    System.out.println("MousePressed on OUTBOX : "+e);
                }

            });
        this.add(outputBox);
        outputBox.add(jButton);

        this.setVisible(true);
        this.validate();


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
    }// start()
}
