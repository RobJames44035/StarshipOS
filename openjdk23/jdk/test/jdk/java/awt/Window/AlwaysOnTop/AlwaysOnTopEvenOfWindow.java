/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 5028924
  @summary Always-on-top frame should be on top of Window.
  @author Yuri.Nesterenko: area=awt.toplevel
  @library ../../regtesthelpers
  @build Util
  @run main AlwaysOnTopEvenOfWindow
*/


import java.awt.*;
import java.awt.event.*;
import test.java.awt.regtesthelpers.Util;

/**
 * AlwaysOnTopEvenOfWindow.java
 * Summary: tests that a Frame marked always-on-top actually is on top of
 * a Window.
 * Test fails in case of override-redirect Window (e.g. with JDK6.0);
 */
public class AlwaysOnTopEvenOfWindow {
    static boolean clicked = false;
    public static void main(String args[]) {

        Window win = new Window(null);
        win.setBounds( 50,50, 300,50);
        win.addMouseListener( new MouseAdapter() {
            public void mouseClicked( MouseEvent me ) {
                clicked = true;
            }
        });
        Frame frame = new Frame("top");
        frame.setBounds(100, 20, 50, 300);
        frame.setAlwaysOnTop( true );

        // position robot before show(): there may be point-to-focus;
        Robot robot = Util.createRobot();
        robot.mouseMove(125, 75);

        frame.setVisible(true);
        win.setVisible(true);
        Util.waitForIdle(robot);
        if(!frame.isAlwaysOnTopSupported())  {
            // pass
            return;
        }
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Util.waitForIdle(robot);
        if( clicked ) {
            throw new RuntimeException("This part of Window should be invisible");
        }

    }
}
