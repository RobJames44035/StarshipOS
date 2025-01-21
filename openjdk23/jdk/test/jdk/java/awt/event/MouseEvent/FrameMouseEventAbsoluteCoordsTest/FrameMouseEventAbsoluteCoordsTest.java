/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4992908
  @summary Need way to get location of MouseEvent in screen  coordinates (Unit-test)
  @library ../../../regtesthelpers
  @build Util
  @run main FrameMouseEventAbsoluteCoordsTest
*/

import java.awt.*;
import java.awt.event.*;
import test.java.awt.regtesthelpers.Util;

// Part II
// Create Frame.
// Click on this frame.
// verify that our MouseEvent contain correct xAbs, yAbs values

public class FrameMouseEventAbsoluteCoordsTest implements MouseListener
{
    Robot robot;
    Frame frame = new Frame("MouseEvent Test Frame II");
    Button button = new Button("Just Button");
    Point mousePositionAbsolute;
    Point mousePosition;

    public static void main(final String[] args) {
        FrameMouseEventAbsoluteCoordsTest app = new FrameMouseEventAbsoluteCoordsTest();
        app.init();
        app.start();
    }

    public void init()
    {
        button.addMouseListener(this);
        frame.add(button);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }//End  init()

    public void start ()
    {
        frame.setVisible(true);
        Util.waitForIdle(robot);

        try {
            robot = new Robot();
            robot.setAutoWaitForIdle(true);
            mousePositionAbsolute = new Point(button.getLocationOnScreen().x + button.getWidth()/2,
                                              button.getLocationOnScreen().y + button.getHeight()/2);
            mousePosition = new Point(button.getWidth()/2,
                                      button.getHeight()/2);
            robot.mouseMove(mousePositionAbsolute.x,
                            mousePositionAbsolute.y );
            //            robot.delay(1000);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }catch(AWTException e) {
            throw new RuntimeException("Test Failed. AWTException thrown.");
        }
    }// start()

    public void mousePressed(MouseEvent e){
        checkEventAbsolutePosition(e, "MousePressed OK");
    };
    public void mouseReleased(MouseEvent e){
        checkEventAbsolutePosition(e, "MouseReleased OK");
    };
    public void mouseClicked(MouseEvent e){
        checkEventAbsolutePosition(e, "MouseClicked OK");
    };
    public void mouseEntered(MouseEvent e){
        System.out.println("mouse entered");
    };
    public void mouseExited(MouseEvent e){
        System.out.println("mouse exited");
    };

    public void checkEventAbsolutePosition(MouseEvent evt, String message){
        if (evt.getXOnScreen() != mousePositionAbsolute.x ||
            evt.getYOnScreen() != mousePositionAbsolute.y ||
            !evt.getLocationOnScreen().equals( mousePositionAbsolute )  ){
            throw new RuntimeException("get(X|Y)OnScreen() or getLocationOnScreen() works incorrectly: expected"+
                                       mousePositionAbsolute.x+":"+mousePositionAbsolute.y+
                                       "\n Got:"+ evt.getXOnScreen()+":"+evt.getYOnScreen());
        }
        if (evt.getX() != mousePosition.x ||
            evt.getY() != mousePosition.y ||
            !evt.getPoint().equals( mousePosition )  ){
            throw new RuntimeException("get(X|Y)() or getLocationOnScreen() works incorrectly: expected"+
                                       mousePositionAbsolute.x+":"+mousePositionAbsolute.y+"\n Got:"
                                       +evt.getX()+":"+evt.getY());
        }
        System.out.println(message);
    }

}// class
