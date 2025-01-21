/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4312433
 *
 * @clean Write Read A B
 * @compile Write.java
 * @run main Write
 * @clean Write Read A B
 * @compile Read.java
 * @run main Read
 *
 * @summary Verify that reading a back reference to a previously skipped value
 *          whose class is not available will throw a ClassNotFoundException
 */

import java.io.*;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    B b = new B();
}

class B implements Serializable {       // class not present on reading side
    private static final long serialVersionUID = 0L;
}

public class Write {
    public static void main(String[] args) throws Exception {
        A a = new A();
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(a);
        oout.writeObject(a.b);
        oout.close();
    }
}
