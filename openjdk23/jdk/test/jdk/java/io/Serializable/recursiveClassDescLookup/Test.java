/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4803747
 * @run main/timeout=20 Test
 * @summary Verify that a nested call to ObjectStreamClass.lookup from within
 *          the static initializer of a serializable class will not cause
 *          deadlock.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 1L;
    static {
        ObjectStreamClass.lookup(Foo.class);
    }
}

public class Test {
    public static void main(String[] args) throws Exception {
        Class<?> fooCl = Class.forName("Foo", false, Test.class.getClassLoader());
        ObjectStreamClass.lookup(fooCl);
        System.out.println("done.");
    }
}
