/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4882798
 * @summary confirms that added transformers all really run
 * @author Gabriel Adauto, Wily Technology
 *
 * @run build AddTransformerTest ATransformerManagementTestCase
 * @run shell MakeJAR.sh redefineAgent
 * @run main/othervm -javaagent:redefineAgent.jar AddTransformerTest AddTransformerTest
 */
public class
AddTransformerTest
    extends ATransformerManagementTestCase
{

    /**
     * Constructor for AddTransformerTest.
     * @param name
     */
    public AddTransformerTest(String name)
    {
        super(name);
    }

    public static void
    main (String[] args)
        throws Throwable {
        ATestCaseScaffold   test = new AddTransformerTest(args[0]);
        test.runTest();
    }

    protected final void
    doRunTest()
        throws Throwable {
        testAddTransformer();
    }

    /**
     * Add and check a bunch of transformers to the manager
     */
    public void
    testAddTransformer()
    {
        for (int i = 0; i < kTransformerSamples.length; i++)
        {
            addTransformerToManager(fInst, kTransformerSamples[i]);
        }

        verifyTransformers(fInst);
    }

}
