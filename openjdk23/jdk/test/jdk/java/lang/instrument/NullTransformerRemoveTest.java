/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4920005 4882798
 * @summary make sure removeTransformer(null) throws NullPointerException
 * @author Robert Field as modified from the code of Gabriel Adauto, Wily Technology
 *
 * @run build NullTransformerRemoveTest
 * @run shell MakeJAR.sh redefineAgent
 * @run main/othervm -javaagent:redefineAgent.jar NullTransformerRemoveTest NullTransformerRemoveTest
 */
public class
NullTransformerRemoveTest
    extends ATransformerManagementTestCase
{

    /**
     * Constructor for NullTransformerRemoveTest.
     * @param name
     */
    public NullTransformerRemoveTest(String name) {
        super(name);
    }

    public static void
    main (String[] args)  throws Throwable {
        ATestCaseScaffold   test = new NullTransformerRemoveTest(args[0]);
        test.runTest();
    }

    protected final void
    doRunTest() {
        testNullTransformerRemove();
    }


    /**
     * Remove null transformers from the manager and check
     * that it throws NullPointerException
     */
    public void
    testNullTransformerRemove() {
        boolean caught = false;

        try {
            fInst.removeTransformer(null);
        } catch (NullPointerException npe) {
            caught = true;
        }
        assertTrue(caught);
    }

}
