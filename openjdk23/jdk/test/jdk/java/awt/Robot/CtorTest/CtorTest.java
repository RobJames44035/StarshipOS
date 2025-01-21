/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6855323
  @summary  Robot(GraphicsDevice) constructor initializes LEGAL_BUTTON_MASK variable improperly
  @author Dmitry Cherepanov area=awt.robot
  @run main CtorTest
*/

/**
 * CtorRobot.java
 *
 * summary: creates Robot using one parameter constructor
 */

import java.awt.*;
import java.awt.event.*;

public class CtorTest
{
    public static void main(String []s) throws Exception
    {
        // one parameter constructor
        GraphicsDevice graphicsDevice = GraphicsEnvironment.
            getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Robot robot = new Robot(graphicsDevice);
        clickOnFrame(robot);
    }

    // generate mouse events
    private static void clickOnFrame(Robot robot) {
        Frame frame = new Frame();
        frame.setBounds(100, 100, 100, 100);
        frame.setVisible(true);

        robot.waitForIdle();

        // click in the middle of the frame
        robot.mouseMove(150, 150);
        robot.delay(50);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        robot.waitForIdle();
    }
}
