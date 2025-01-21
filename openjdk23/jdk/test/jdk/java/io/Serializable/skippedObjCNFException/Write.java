/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4313167
 *
 * @clean Write Read A B C
 * @compile Write.java
 * @run main Write
 * @clean Write Read A B C
 * @compile Read.java
 * @run main Read
 *
 * @summary Verify that ClassNotFoundExceptions caused by values referenced
 *          (perhaps transitively) by "skipped" fields will not cause
 *          deserialization failure.
 */

import java.io.*;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    // all three following fields not present on reading side
    B b = new B();
    C c = new C();
    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    Object ca = new Object[] { new C() };
}

class B implements Serializable {
    private static final long serialVersionUID = 0L;
    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    Object c = new C();
}

class C implements Serializable {       // class not present on reading side
    private static final long serialVersionUID = 0L;
}

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(new A());
        oout.close();
    }
}
