/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/**
 * @test
 * @bug 4920005 4882798
 * @summary make sure redefineClasses throws NullPointerException in the right places.
 * @author Robert Field as modified from the code of Gabriel Adauto, Wily Technology
 *
 * @run build NullRedefineClassesTests
 * @run shell MakeJAR.sh redefineAgent
 * @run main/othervm -javaagent:redefineAgent.jar NullRedefineClassesTests NullRedefineClassesTests
 */

import java.lang.instrument.ClassDefinition;
import java.lang.instrument.UnmodifiableClassException;

public class
NullRedefineClassesTests
    extends ASimpleInstrumentationTestCase
{

    /**
     * Constructor for NullRedefineClassesTests.
     * @param name
     */
    public NullRedefineClassesTests(String name) {
        super(name);
    }

    public static void
    main (String[] args) throws Throwable {
        ATestCaseScaffold   test = new NullRedefineClassesTests(args[0]);
        test.runTest();
    }

    protected final void
    doRunTest() throws ClassNotFoundException, UnmodifiableClassException  {
        testNullRedefineClasses();
    }


    public void
    testNullRedefineClasses() throws ClassNotFoundException, UnmodifiableClassException {
        boolean caught;

        // Test that a null argument throws NullPointerException
        caught = false;
        try {
            fInst.redefineClasses(null);
        } catch (NullPointerException npe) {
            caught = true;
        }
        assertTrue(caught);

        // Test that a null element throws NullPointerException
        caught = false;
        try {
            fInst.redefineClasses(new ClassDefinition[]{ null });
        } catch (NullPointerException npe) {
            caught = true;
        }
        assertTrue(caught);

        // Test that a null element amonst others throws NullPointerException
        caught = false;
        ClassDefinition cd = new ClassDefinition(DummyClass.class, new byte[] {1, 2, 3});
        try {
            fInst.redefineClasses(new ClassDefinition[]{ cd, null });
        } catch (NullPointerException npe) {
            caught = true;
        }
        assertTrue(caught);

        // Test that a null class throws NullPointerException
        caught = false;
        try {
            new ClassDefinition(null, new byte[] {1, 2, 3});
        } catch (NullPointerException npe) {
            caught = true;
        }
        assertTrue(caught);

        // Test that a null byte array throws NullPointerException
        caught = false;
        try {
            new ClassDefinition(DummyClass.class, null);
        } catch (NullPointerException npe) {
            caught = true;
        }
        assertTrue(caught);
    }
}
