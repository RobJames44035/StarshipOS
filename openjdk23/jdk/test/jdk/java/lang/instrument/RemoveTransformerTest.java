/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4882798
 * @summary simple remove of a transformer that was added
 * @author Gabriel Adauto, Wily Technology
 *
 * @run build RemoveTransformerTest
 * @run shell MakeJAR.sh redefineAgent
 * @run main/othervm -javaagent:redefineAgent.jar RemoveTransformerTest RemoveTransformerTest
 */
public class
RemoveTransformerTest
    extends ATransformerManagementTestCase
{

    /**
     * Constructor for RemoveTransformerTest.
     * @param name
     */
    public RemoveTransformerTest(String name)
    {
        super(name);
    }

    public static void
    main (String[] args)
        throws Throwable {
        ATestCaseScaffold   test = new RemoveTransformerTest(args[0]);
        test.runTest();
    }

    protected final void
    doRunTest()
        throws Throwable {
        testRemoveTransformer();
    }


    /**
     * Add and remove a bunch of transformers to the manager and
     * check that they get called.
     */
    public void
    testRemoveTransformer()
    {
        for (int i = 0; i < kTransformerSamples.length; i++)
        {
            addTransformerToManager(fInst, kTransformerSamples[i]);
            if (i % kModSamples == 1)
            {
                removeTransformerFromManager(fInst, kTransformerSamples[i]);
            }
        }

        verifyTransformers(fInst);
    }

}
