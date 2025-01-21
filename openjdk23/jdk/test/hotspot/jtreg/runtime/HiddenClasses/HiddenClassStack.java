/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @summary Test that stack tracing isn't broken if an exception is thrown
 *          in a hidden class.
 * DESCRIPTION
 *     An exception is thrown by a hidden class.  Verify that the exception's
 *     stack trace contains the name of the current test class (i.e., verify
 *     that the stack trace is not broken).
 * @library /test/lib
 * @modules jdk.compiler
 * @run main HiddenClassStack
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import static java.lang.invoke.MethodHandles.Lookup.ClassOption.*;
import jdk.test.lib.compiler.InMemoryJavaCompiler;

public class HiddenClassStack {

    static byte klassbuf[] = InMemoryJavaCompiler.compile("TestClass",
        "public class TestClass { " +
        "    public TestClass() { " +
        "        throw new RuntimeException(\"boom\"); " +
        " } } ");

    public static void main(String[] args) throws Throwable {

        // An exception is thrown by class loaded by lookup.defineHiddenClass().
        // Verify that the exception's stack trace contains name of the current
        // test class.
        try {
            Lookup lookup = MethodHandles.lookup();
            Class<?> cl = lookup.defineHiddenClass(klassbuf, false, NESTMATE).lookupClass();
            Object obj = cl.newInstance();
            throw new Exception("Expected RuntimeException not thrown");
        } catch (RuntimeException e) {
            if (!e.getMessage().contains("boom")) {
                throw new RuntimeException("Wrong RuntimeException, e: " + e.toString());
            }
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(byteOS);
            e.printStackTrace(printStream);
            printStream.close();
            String stackTrace = byteOS.toString("ASCII");
            if (!stackTrace.contains(HiddenClassStack.class.getName())) {
                throw new RuntimeException("HiddenClassStack missing from stacktrace: " +
                                           stackTrace);
            }
        }
    }
}
