/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4882798
 * @summary make sure no transformers run when none are registered
 * @author Gabriel Adauto, Wily Technology
 *
 * @run build NoTransformerAddedTest
 * @run shell MakeJAR.sh redefineAgent
 * @run main/othervm -javaagent:redefineAgent.jar NoTransformerAddedTest NoTransformerAddedTest
 */
public class
NoTransformerAddedTest
    extends ATransformerManagementTestCase
{

    /**
     * Constructor for NoTransformerAddedTest.
     * @param name
     */
    public NoTransformerAddedTest(String name)
    {
        super(name);
    }

    public static void
    main (String[] args)
        throws Throwable {
        ATestCaseScaffold   test = new NoTransformerAddedTest(args[0]);
        test.runTest();
    }

    protected final void
    doRunTest()
        throws Throwable {
        testNoTransformersAdded();
    }

    /**
     * Add no transformers to the manager and check it
     */
    public void
    testNoTransformersAdded()
    {
        verifyTransformers(fInst);
    }

}
