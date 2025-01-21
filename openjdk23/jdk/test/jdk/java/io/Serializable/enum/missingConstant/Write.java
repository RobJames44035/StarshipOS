/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838379
 * @summary Verify that deserialization of an enum constant that does not exist
 *          on the receiving side results in an InvalidObjectException.
 *
 * @compile Write.java
 * @run main Write
 * @clean Write
 * @compile Read.java
 * @run main Read
 * @clean Read
 */

import java.io.*;

enum Foo { foo, bar, baz }

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
