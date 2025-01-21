/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4882798
 * @summary test transformer add/remove pairs in sequence
 * @author Gabriel Adauto, Wily Technology
 *
 * @run build TransformMethodTest
 * @run shell MakeJAR.sh redefineAgent
 * @run main/othervm -javaagent:redefineAgent.jar TransformMethodTest TransformMethodTest
 * @key randomness
 */
import java.lang.instrument.*;

public class
TransformMethodTest
    extends ATransformerManagementTestCase
{

    /**
     * Constructor for TransformMethodTest.
     * @param name
     */
    public TransformMethodTest(String name)
    {
        super(name);
    }

    public static void
    main (String[] args)
        throws Throwable {
        ATestCaseScaffold   test = new TransformMethodTest(args[0]);
        test.runTest();
    }

    protected final void
    doRunTest()
        throws Throwable {
        testTransform();
    }

    /**
     * Verify that the transformers can be added and removed correctly
     */
    public void
    testTransform()
    {
        for (int i = 0; i < kTransformerSamples.length; i++)
        {
            ClassFileTransformer transformer = getRandomTransformer();
            addTransformerToManager(fInst, transformer);
            verifyTransformers(fInst);
            removeTransformerFromManager(fInst, transformer, true);
        }
    }

}
