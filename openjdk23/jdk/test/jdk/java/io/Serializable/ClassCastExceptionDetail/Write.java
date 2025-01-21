/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4511532
 *
 * @clean Write Read Foo Gub
 * @compile Write.java
 * @run main Write
 * @clean Write Read Foo Gub
 * @compile Read.java
 * @run main Read
 *
 * @summary Verify that the message string of a ClassCastException thrown by
 *          ObjectInputStream when attempting to assign a value to a field of
 *          an incompatible type contains the names of the value's class, the
 *          field's declaring class, the field's type, and the field itself.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 0L;
    private Integer bar = 0;
}

class Gub extends Foo {
    private static final long serialVersionUID = 1L;
}

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(new Gub());
        oout.close();
    }
}
