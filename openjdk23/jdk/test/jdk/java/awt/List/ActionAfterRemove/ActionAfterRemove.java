/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6426186
  @summary XToolkit: List throws ArrayIndexOutOfBoundsException on double clicking when the List is empty
  @author Dmitry Cherepanov area=awt-list
  @library ../../regtesthelpers
  @build Util
  @run main ActionAfterRemove
*/

import java.awt.*;
import java.awt.event.*;
import test.java.awt.regtesthelpers.Util;

public class ActionAfterRemove
{
    private static volatile boolean passed = true;

    public static final void main(String args[])
    {
        // In order to handle all uncaught exceptions in the EDT
        final Thread.UncaughtExceptionHandler eh = new Thread.UncaughtExceptionHandler()
        {
            @Override
            public void uncaughtException(Thread t, Throwable e)
            {
                e.printStackTrace();
                passed = false;
            }
        };

        final Frame frame = new Frame();
        final List list = new List();
        Robot robot = null;


        list.add("will be removed");
        frame.add(list);

        frame.setLayout(new FlowLayout());
        frame.setBounds(100,100,300,300);
        frame.setVisible(true);

        list.select(0);
        list.remove(0);

        try{
            robot = new Robot();
        }catch(AWTException e){
            throw new RuntimeException(e);
        }

        Util.clickOnComp(list, robot);
        robot.waitForIdle();
        Util.clickOnComp(list, robot);
        robot.waitForIdle();

        if (!passed){
            throw new RuntimeException("Test failed: exception was thrown on EDT.");
        }

    }//End  init()
}
