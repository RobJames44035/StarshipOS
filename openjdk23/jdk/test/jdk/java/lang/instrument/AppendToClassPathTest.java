/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4882798
 * @summary simple test for the Class-Path manifest attribute
 * @author Gabriel Adauto, Wily Technology; Robert Field, Sun Microsystems
 *
 * @run build AppendToClassPathTest
 * @run shell AppendToClassPathSetUp.sh
 * @run shell MakeJAR.sh classpathAgent
 * @run main/othervm -javaagent:classpathAgent.jar AppendToClassPathTest AppendToClassPathTest
 */

import java.io.*;

public class
AppendToClassPathTest
    extends ASimpleInstrumentationTestCase
{

    /**
     * Constructor for AppendToClassPathTest.
     * @param name
     */
    public AppendToClassPathTest(String name)
    {
        super(name);
    }

    public static void
    main (String[] args)
        throws Throwable {
        ATestCaseScaffold   test = new AppendToClassPathTest(args[0]);
        test.runTest();
    }

    protected final void
    doRunTest()
        throws Throwable {
        testAppendToClassPath();
    }


    public void
    testAppendToClassPath()
        throws  IOException,
                ClassNotFoundException
    {
        Class sentinelClass;
        ClassLoader loader = this.getClass().getClassLoader();

        // load the "hidden" class, it should be loaded by the system loader
        sentinelClass = loader.loadClass("ExampleForClassPath");
        assertTrue(sentinelClass.getClassLoader() == ClassLoader.getSystemClassLoader());
    }

}
