/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 4811096
  @summary Tests whether a LW menu correctly overlaps a HW button
  @author anthony.petrov@...: area=awt.mixing
  @library ../regtesthelpers
  @build Util
  @run main LWPopupMenu
*/


/**
 * LWPopupMenu.java
 *
 * summary:  Tests whether a LW menu correctly overlaps a HW button
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import test.java.awt.regtesthelpers.Util;



public class LWPopupMenu
{

    //*** test-writer defined static variables go here ***

    static volatile boolean failed = true;

    private static void init()
    {
        JFrame f = new JFrame("LW menu test");

        JMenuBar menubar = new JMenuBar();
        f.setJMenuBar(menubar);

        // Create lightweight-enabled menu
        JMenu lmenu = new JMenu("Lite Menu");
        lmenu.add("Salad");
        lmenu.add( new AbstractAction("Fruit Plate") {
            public void actionPerformed(ActionEvent e) {
                failed = false;
            }
        });
        lmenu.add("Water");
        menubar.add(lmenu);

        // Create Heavyweight AWT Button
        Button heavy = new Button("  Heavyweight Button  ");

        // Add heavy button to box
        Box box = Box.createVerticalBox();
        box.add(Box.createVerticalStrut(20));
        box.add(heavy);
        box.add(Box.createVerticalStrut(20));

        f.getContentPane().add("Center", box);

        f.pack();
        f.show();

        Robot robot = Util.createRobot();
        robot.setAutoDelay(20);

        Util.waitForIdle(robot);

        // Activate the menu
        Point lLoc = lmenu.getLocationOnScreen();
        robot.mouseMove(lLoc.x + 5, lLoc.y + 5);

        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Util.waitForIdle(robot);


        // Click on the "Fruit Plate" menu item.
        //    It's assumed that the menu item is located
        //    above the heavyweight button.
        Point bLoc = heavy.getLocationOnScreen();
        robot.mouseMove(bLoc.x + 10, bLoc.y + 5);

        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        Util.waitForIdle(robot);

        if (failed) {
            LWPopupMenu.fail("The LW menu item did not received the click.");
        } else {
            LWPopupMenu.pass();
        }
    }//End  init()



    /*****************************************************
     * Standard Test Machinery Section
     * DO NOT modify anything in this section -- it's a
     * standard chunk of code which has all of the
     * synchronisation necessary for the test harness.
     * By keeping it the same in all tests, it is easier
     * to read and understand someone else's test, as
     * well as insuring that all tests behave correctly
     * with the test harness.
     * There is a section following this for test-
     * classes
     ******************************************************/
    private static boolean theTestPassed = false;
    private static boolean testGeneratedInterrupt = false;
    private static String failureMessage = "";

    private static Thread mainThread = null;

    private static int sleepTime = 300000;

    // Not sure about what happens if multiple of this test are
    //  instantiated in the same VM.  Being static (and using
    //  static vars), it aint gonna work.  Not worrying about
    //  it for now.
    public static void main( String args[] ) throws InterruptedException
    {
        mainThread = Thread.currentThread();
        try
        {
            init();
        }
        catch( TestPassedException e )
        {
            //The test passed, so just return from main and harness will
            // interepret this return as a pass
            return;
        }
        //At this point, neither test pass nor test fail has been
        // called -- either would have thrown an exception and ended the
        // test, so we know we have multiple threads.

        //Test involves other threads, so sleep and wait for them to
        // called pass() or fail()
        try
        {
            Thread.sleep( sleepTime );
            //Timed out, so fail the test
            throw new RuntimeException( "Timed out after " + sleepTime/1000 + " seconds" );
        }
        catch (InterruptedException e)
        {
            //The test harness may have interrupted the test.  If so, rethrow the exception
            // so that the harness gets it and deals with it.
            if( ! testGeneratedInterrupt ) throw e;

            //reset flag in case hit this code more than once for some reason (just safety)
            testGeneratedInterrupt = false;

            if ( theTestPassed == false )
            {
                throw new RuntimeException( failureMessage );
            }
        }

    }//main

    public static synchronized void setTimeoutTo( int seconds )
    {
        sleepTime = seconds * 1000;
    }

    public static synchronized void pass()
    {
        System.out.println( "The test passed." );
        System.out.println( "The test is over, hit  Ctl-C to stop Java VM" );
        //first check if this is executing in main thread
        if ( mainThread == Thread.currentThread() )
        {
            //Still in the main thread, so set the flag just for kicks,
            // and throw a test passed exception which will be caught
            // and end the test.
            theTestPassed = true;
            throw new TestPassedException();
        }
        theTestPassed = true;
        testGeneratedInterrupt = true;
        mainThread.interrupt();
    }//pass()

    public static synchronized void fail()
    {
        //test writer didn't specify why test failed, so give generic
        fail( "it just plain failed! :-)" );
    }

    public static synchronized void fail( String whyFailed )
    {
        System.out.println( "The test failed: " + whyFailed );
        System.out.println( "The test is over, hit  Ctl-C to stop Java VM" );
        //check if this called from main thread
        if ( mainThread == Thread.currentThread() )
        {
            //If main thread, fail now 'cause not sleeping
            throw new RuntimeException( whyFailed );
        }
        theTestPassed = false;
        testGeneratedInterrupt = true;
        failureMessage = whyFailed;
        mainThread.interrupt();
    }//fail()

}// class LWPopupMenu

//This exception is used to exit from any level of call nesting
// when it's determined that the test has passed, and immediately
// end the test.
class TestPassedException extends RuntimeException
{
}
