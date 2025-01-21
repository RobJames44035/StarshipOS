/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Creates a hidden class inside of a hidden class.
 * @library /test/lib
 * @modules jdk.compiler
 * @run main p.NestedHidden
 */

package p;

import java.lang.*;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*;
import jdk.test.lib.compiler.InMemoryJavaCompiler;


// Test that a hidden class can define its own hidden class by calling
// lookup.defineHiddenClass().
public class NestedHidden {
    static byte klassbuf[] = InMemoryJavaCompiler.compile("p.TestClass",
        "package p; " +
        "public class TestClass { " +
        "    public static void concat(String one, String two) throws Throwable { " +
        "        System.out.println(one + two);" +
        " } } ");

    public static void main(String args[]) throws Exception {
        // The hidden class calls lookup.defineHiddenClass(), creating a nested hidden class.
        byte klassbuf2[] = InMemoryJavaCompiler.compile("p.TestClass2",
            "package p; " +
            "import java.lang.invoke.MethodHandles; " +
            "import java.lang.invoke.MethodHandles.Lookup; " +
            "import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*; " +
            "public class TestClass2 { " +
            "    public static void doit() throws Throwable { " +
            "        Lookup lookup = MethodHandles.lookup(); " +
            "        Class<?> klass2 = lookup.defineHiddenClass(p.NestedHidden.klassbuf, true, NESTMATE).lookupClass(); " +
            "        Class[] dArgs = new Class[2]; " +
            "        dArgs[0] = String.class; " +
            "        dArgs[1] = String.class; " +
            "        try { " +
            "            klass2.getMethod(\"concat\", dArgs).invoke(null, \"CC\", \"DD\"); " +
            "        } catch (Throwable ex) { " +
            "            throw new RuntimeException(\"Exception: \" + ex.toString()); " +
            "        } " +
            "} } ");

        Lookup lookup = MethodHandles.lookup();
        Class<?> klass2 = lookup.defineHiddenClass(klassbuf2, true, NESTMATE).lookupClass();
        klass2.getMethod("doit").invoke(null);
    }
}
