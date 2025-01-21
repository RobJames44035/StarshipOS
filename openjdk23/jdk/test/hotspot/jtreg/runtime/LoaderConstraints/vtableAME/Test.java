/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8186092
 * @compile ../common/Foo.java
 *          ../common/J.java
 *          I.java
 *          ../common/C.jasm
 *          Task.java
 *          ../common/PreemptingClassLoader.java
 * @run main/othervm Test
 */

import java.io.PrintStream;
import java.lang.reflect.*;

public class Test {

    // Test that LinkageError exceptions are not thrown during vtable creation,
    // for loader constraint errors, if the target method is an overpass method.
    //
    // In this test, during vtable creation for class Task, the target method
    // "Task.m()LFoo;" is an overpass method (that throws an AME).  So, even
    // though it is inheriting the method from its super class C, and Task has
    // a different class Foo than C, no LinkageError exception should be thrown
    // because the loader constraint check that would cause the LinkageError
    // should not be done.
    public static void main(String args[]) throws Exception {
        Class<?> c = test.Foo.class; // forces standard class loader to load Foo
        ClassLoader l = new PreemptingClassLoader("test.Task", "test.Foo", "test.I", "test.J");
        l.loadClass("test.Foo");
        l.loadClass("test.Task").newInstance();
        test.Task t = new test.Task();
        try {
            t.m(); // Should get AME
            throw new RuntimeException("Missing AbstractMethodError exception");
        } catch (AbstractMethodError e) {
            if (!e.getMessage().contains("Method test/Task.m()Ltest/Foo; is abstract")) {
                throw new RuntimeException("Wrong AME exception thrown: " + e.getMessage());
            }
        }
    }

}
