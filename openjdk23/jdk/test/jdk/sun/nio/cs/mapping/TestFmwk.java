/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.lang.reflect.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;
import java.io.*;
import java.text.*;

/**
 * RBTestFmwk is a base class for tests that can be run conveniently from
 * the command line as well as under the Java test harness.
 * <p>
 * Sub-classes implement a set of methods named Test<something>. Each
 * of these methods performs some test. Test methods should indicate
 * errors by calling either err or errln.  This will increment the
 * errorCount field and may optionally print a message to the log.
 * Debugging information may also be added to the log via the log
 * and logln methods.  These methods will add their arguments to the
 * log only if the test is being run in verbose mode.
 */
public class TestFmwk {
    //------------------------------------------------------------------------
    // Everything below here is boilerplate code that makes it possible
    // to add a new test by simply adding a function to an existing class
    //------------------------------------------------------------------------

    protected TestFmwk() {
        // Create a hashtable containing all the test methods.
        testMethods = new Hashtable();
        Method[] methods = getClass().getDeclaredMethods();
        for( int i=0; i<methods.length; i++ ) {
            if( methods[i].getName().startsWith("Test")
                    || methods[i].getName().startsWith("test") ) {
                testMethods.put( methods[i].getName(), methods[i] );
            }
        }
    }

    protected void run(String[] args) throws Exception
    {
        System.out.println(getClass().getName() + " {");
        indentLevel++;

        // Set up the log and reference streams.  We use PrintWriters in order to
        // take advantage of character conversion.  The JavaEsc converter will
        // convert Unicode outside the ASCII range to Java's \\uxxxx notation.
        log = new PrintWriter(System.out,true);

        // Parse the test arguments.  They can be either the flag
        // "-verbose" or names of test methods. Create a list of
        // tests to be run.
        Vector testsToRun = new Vector( args.length );
        for( int i=0; i<args.length; i++ ) {
            if( args[i].equals("-verbose") ) {
                verbose = true;
            }
            else if( args[i].equals("-prompt") ) {
                prompt = true;
            } else if (args[i].equals("-nothrow")) {
                nothrow = true;
            } else {
                Object m = testMethods.get( args[i] );
                if( m != null ) {
                    testsToRun.addElement( m );
                }
                else {
                    usage();
                    return;
                }
            }
        }

        // If no test method names were given explicitly, run them all.
        if( testsToRun.size() == 0 ) {
            Enumeration methodNames = testMethods.elements();
            while( methodNames.hasMoreElements() ) {
                testsToRun.addElement( methodNames.nextElement() );
            }
        }

        // Run the list of tests given in the test arguments
        for( int i=0; i<testsToRun.size(); i++ ) {
            int oldCount = errorCount;

            Method testMethod = (Method)testsToRun.elementAt(i);
            writeTestName(testMethod.getName());

            try {
                testMethod.invoke(this, new Object[0]);
            }
            catch( IllegalAccessException e ) {
                errln("Can't acces test method " + testMethod.getName());
            }
            catch( InvocationTargetException e ) {
                errln("Uncaught exception thrown in test method "
                               + testMethod.getName());
                e.getTargetException().printStackTrace(this.log);
            }
            writeTestResult(errorCount - oldCount);
        }
        indentLevel--;
        writeTestResult(errorCount);

        if (prompt) {
            System.out.println("Hit RETURN to exit...");
            try {
                System.in.read();
            }
            catch (IOException e) {
                System.out.println("Exception: " + e.toString() + e.getMessage());
            }
        }
        if (nothrow) {
            System.exit(errorCount);
        }
    }

    /**
     * Adds given string to the log if we are in verbose mode.
     */
    protected void log( String message ) {
        if( verbose ) {
            indent(indentLevel + 1);
            log.print( message );
            log.flush();
        }
    }

    protected void logln( String message ) {
        log(message + System.getProperty("line.separator"));
    }

    /**
     * Report an error
     */
    protected void err( String message ) {
        errorCount++;
        indent(indentLevel + 1);
        log.print( message );
        log.flush();

        if (!nothrow) {
            throw new RuntimeException(message);
        }
    }

    protected void errln( String message ) {
        err(message + System.getProperty("line.separator"));
    }


    protected void writeTestName(String testName) {
        indent(indentLevel);
        log.print(testName);
        log.flush();
        needLineFeed = true;
    }

    protected void writeTestResult(int count) {
        if (!needLineFeed) {
            indent(indentLevel);
            log.print("}");
        }
        needLineFeed = false;

        if (count != 0)
            log.println(" FAILED");
        else
            log.println(" Passed");
    }

    private final void indent(int distance) {
        if (needLineFeed) {
            log.println(" {");
            needLineFeed = false;
        }
        log.print(spaces.substring(0, distance * 2));
    }

    /**
     * Print a usage message for this test class.
     */
    void usage() {
        System.out.println(getClass().getName() +
                            ": [-verbose] [-nothrow] [-prompt] [test names]");

        System.out.println("test names:");
        Enumeration methodNames = testMethods.keys();
        while( methodNames.hasMoreElements() ) {
            System.out.println("\t" + methodNames.nextElement() );
        }
    }

    private boolean     prompt = false;
    private boolean     nothrow = false;
    protected boolean   verbose = false;

    private PrintWriter log;
    private int         indentLevel = 0;
    private boolean     needLineFeed = false;
    private int         errorCount = 0;

    private Hashtable testMethods;
    private final String spaces = "                                          ";
}
