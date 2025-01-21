/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 *
 * @clean Write Read A B I Handler
 * @compile Write.java Handler.java
 * @run main Write
 * @clean Write Read A B I Handler
 * @compile Read.java Handler.java
 * @run main Read
 *
 * @summary Verify that ObjectInputStream can skip over unresolvable serialized
 *          proxy instances.
 */

import java.io.*;
import java.lang.reflect.*;

interface I {}          // interface present only on writing side

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    String a = "a";
    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    Object proxy;
    String z = "z";

    A(Object proxy) { this.proxy = proxy; }
}

class B implements Serializable {
    private static final long serialVersionUID = 0L;
    String s = "s";
    transient Object proxy;

    B(Object proxy) { this.proxy = proxy; }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(proxy);
    }
}

public class Write {
    public static void main(String[] args) throws Exception {
        Object proxy = Proxy.newProxyInstance(
            Write.class.getClassLoader(),
            new Class<?>[] { I.class }, new Handler());
        ObjectOutputStream oout = new ObjectOutputStream(
            new FileOutputStream("tmp.ser"));
        oout.writeObject(new A(proxy));
        oout.reset();
        oout.writeObject(new B(proxy));
        oout.writeObject(proxy);
        oout.close();
    }
}
