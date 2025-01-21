/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package jdk.jfr.event.runtime;

import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Array;
import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*;

public class TestClasses {

    protected TestClassPrivate testClassPrivate;
    protected TestClassPrivateStatic testClassPrivateStatic;

    public TestClasses() {
        testClassPrivate = new TestClassPrivate();
        testClassPrivateStatic = new TestClassPrivateStatic();
    }

    // Classes TestClassPrivate and TestClassPrivateStatic should be loaded at
    // the same time
    // as the base class TestClasses
    private class TestClassPrivate {
    }

    private static class TestClassPrivateStatic {
    }

    protected class TestClassProtected {
    }

    protected static class TestClassProtectedStatic {
    }

    // When loadClasses() is run, 3 new classes should be loaded.
    public void loadClasses() throws ClassNotFoundException {
        final ClassLoader cl = getClass().getClassLoader();
        cl.loadClass("jdk.jfr.event.runtime.TestClasses$TestClassProtected1");
        cl.loadClass("jdk.jfr.event.runtime.TestClasses$TestClassProtectedStatic1");
    }

    protected class TestClassProtected1 {
    }

    protected static class TestClassProtectedStatic1 {
        protected TestClassProtectedStaticInner testClassProtectedStaticInner = new TestClassProtectedStaticInner();

        protected static class TestClassProtectedStaticInner {
        }
    }

    public static class TestClassPublicStatic {
        public static class TestClassPublicStaticInner {
        }
    }

}

class TestClass {
    static {
        // force creation of hidden class (for the lambda form)
        Runnable r = () -> System.out.println("Hello");
        r.run();
    }

    public static Class<?>[] createNonFindableClasses(byte[] klassbuf) throws Throwable {
        // Create a hidden class and an array of hidden classes.
        Lookup lookup = MethodHandles.lookup();
        Class<?>[] classes = new Class[2];
        classes[0] = lookup.defineHiddenClass(klassbuf, false, NESTMATE).lookupClass();
        classes[1] = Array.newInstance(classes[0], 10).getClass(); // HAS ISSUES?
        return classes;
    }
}
