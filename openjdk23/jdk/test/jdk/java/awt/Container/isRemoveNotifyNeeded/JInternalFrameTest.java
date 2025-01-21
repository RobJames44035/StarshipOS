/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6552803
  @summary moveToFront shouldn't remove peers of HW components
  @author anthony.petrov@...: area=awt.container
  @library ../../regtesthelpers
  @build Util
  @run main JInternalFrameTest
*/

/**
 * JInternalFrameTest.java
 *
 * summary:  movtToFront invoked on the JInternalFrame shouldn't
 *           recreate peers of HW descendants of the JInternalFrame.
 */

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import javax.swing.*;
import test.java.awt.regtesthelpers.Util;

public class JInternalFrameTest
{
    // Indicates whether the removeNotify() was invoked on the HW Canvas
    static volatile boolean isRemoveNotify = false;

    // The HW Canvas class.
    private static final class MyCanvas extends Canvas {
        private final Color background;
        public MyCanvas(Color background) {
            this.background = background;
            setPreferredSize(new Dimension(100, 100));
        }

        public void paint(Graphics g) {
            g.setColor(background);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        public void addNotify() {
            super.addNotify();
            System.err.println("addNotify() on " + background);
        }

        public void removeNotify() {
            super.removeNotify();
            isRemoveNotify = true;
            System.err.println("removeNotify() on " + background);
            Thread.dumpStack();
        }
    }


    private static void init()
    {
        // We create a JFrame with two JInternalFrame.
        // Each JInternalFrame contains a HW Canvas component.
        JFrame jframe = new JFrame("mixing test");
        JDesktopPane desktop = new JDesktopPane();
        jframe.setContentPane(desktop);
        JInternalFrame iframe1 = new JInternalFrame("iframe 1");
        iframe1.setIconifiable(true);
        iframe1.add(new MyCanvas(Color.RED));
        iframe1.setBounds(10, 10, 100, 100);
        iframe1.setVisible(true);
        desktop.add(iframe1);
        JInternalFrame iframe2 = new JInternalFrame("iframe 2");
        iframe2.setIconifiable(true);
        iframe2.add(new MyCanvas(Color.BLUE));
        iframe2.setBounds(50, 50, 100, 100);
        iframe2.setVisible(true);
        desktop.add(iframe2);

        jframe.setSize(300, 300);
        jframe.setVisible(true);

        // Wait until everything gets shown
        Util.waitForIdle(null);

        // Now cause a couple of z-order changing operations
        iframe2.moveToFront();
        Util.waitForIdle(null);
        iframe1.moveToFront();
        Util.waitForIdle(null);
        iframe2.moveToFront();

        // Wait until all the operations complete
        Util.waitForIdle(null);

        if (isRemoveNotify) {
            fail("The removeNotify() was invoked on the HW Canvas");
        }
        JInternalFrameTest.pass();

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

}// class JInternalFrameTest

//This exception is used to exit from any level of call nesting
// when it's determined that the test has passed, and immediately
// end the test.
class TestPassedException extends RuntimeException
{
}
