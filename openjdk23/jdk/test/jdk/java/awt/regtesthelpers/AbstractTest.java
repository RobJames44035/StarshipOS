/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * <p>This is the base class for automatic main tests.
 * <p>When using jtreg you would include this class into
 * the build list via something like:
 * <pre>
     @library ../../../regtesthelpers
     @build AbstractTest
     @run main YourTest
   </pre>
 * Note that if you are about to create a test based on
 * Applet-template, then put those lines into html-file, not in java-file.
 * <p> And put an
 * import test.java.awt.regtesthelpers.AbstractTest;
 * into the java source.
 */

package test.java.awt.regtesthelpers;

public abstract class AbstractTest
{
    public static void pass()
    {
        Sysout.println( "The test passed." );
        Sysout.println( "The test is over, hit  Ctl-C to stop Java VM" );
    }//pass()

    public static void fail()
    {
        //test writer didn't specify why test failed, so give generic
        fail("no reason given.");
    }

    public static void fail( String whyFailed )
    {
        Sysout.println( "The test failed: " + whyFailed );
        Sysout.println( "The test is over, hit  Ctl-C to stop Java VM" );
        throw new RuntimeException( whyFailed );
    }

    public static void fail(Exception ex) throws Exception
    {
        Sysout.println( "The test failed with exception:" );
        ex.printStackTrace();
        Sysout.println( "The test is over, hit  Ctl-C to stop Java VM" );
        throw ex;
    }
}
