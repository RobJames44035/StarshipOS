/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4663191
 * @summary Verify that readObject and readObjectNoData methods will not be
 *          called on an object being deserialized if that object is already
 *          tagged with a ClassNotFoundException.
 *
 * @clean Write Read A B C X
 * @compile Write.java
 * @run main Write
 * @clean Write Read A B C X
 * @compile Read.java
 * @run main Read
 */

import java.io.*;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    Object x = new X();
}

class C extends A {
    private static final long serialVersionUID = 0L;
}

class X implements Serializable {
    private static final long serialVersionUID = 0L;
}

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(new C());
        oout.writeObject("after");
        oout.close();
    }
}
