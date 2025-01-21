/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838379
 * @summary Verify that serialization of enum constants that are instances of
 *          constant-specific subclasses functions properly.
 *
 * @compile Write.java
 * @run main Write
 * @clean Write
 * @compile Read.java
 * @run main Read
 * @clean Read
 */

import java.io.*;

enum Foo {
    a,
    b,
    c { int i = 5; },
    d { float f = 5.0f; }
}

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("foo.ser"));
        for (Foo f : Foo.values()) {
            oout.writeObject(f);
        }
        oout.close();
    }
}
