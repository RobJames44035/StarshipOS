/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package org.openjdk.tests.java.lang.invoke;

import org.testng.annotations.Test;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

import static org.testng.Assert.fail;

/**
 * Ensure that the $deserializeLambda$ method is present when it should be, and absent otherwise
 */

@Test(groups = { "serialization-hostile" })
public class DeserializeMethodTest {
    private void assertDeserializeMethod(Class<?> clazz, boolean expectedPresent) {
        try {
            Method m = clazz.getDeclaredMethod("$deserializeLambda$", SerializedLambda.class);
            if (!expectedPresent)
                fail("Unexpected $deserializeLambda$ in " + clazz);
        }
        catch (NoSuchMethodException e) {
            if (expectedPresent)
                fail("Expected to find $deserializeLambda$ in " + clazz);
        }
    }

    static class Empty {}

    public void testEmptyClass() {
        assertDeserializeMethod(Empty.class, false);
    }

    static class Cap1 {
        void foo() {
            Runnable r = (Runnable & Serializable) () -> { };
        }
    }

    public void testCapturingSerLambda() {
        assertDeserializeMethod(Cap1.class, true);
    }

    static class Cap2 {
        void foo() {
            Runnable r = () -> { };
        }
    }

    public void testCapturingNonSerLambda() {
        assertDeserializeMethod(Cap2.class, false);
    }

    interface Marker { }
    static class Cap3 {
        void foo() {
            Runnable r = (Runnable & Marker) () -> { };
        }
    }

    public void testCapturingNonserIntersectionLambda() {
        assertDeserializeMethod(Cap3.class, false);
    }
}
