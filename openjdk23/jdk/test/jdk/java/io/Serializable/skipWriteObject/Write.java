/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4392325
 *
 * @clean Write Read Foo Bar
 * @compile Write.java
 * @run main Write
 * @clean Write Read Foo Bar
 * @compile Read.java
 * @run main Read
 *
 * @summary Ensure that ObjectInputStream can successfully skip over an object
 *          written using a class-defined writeObject method for which the
 *          class is not resolvable.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 0L;
    Bar b = new Bar();
}

class Bar implements Serializable {
    private static final long serialVersionUID = 1L;

    int a, b;
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
}

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(new Object[] { "before", new Foo(), "after" });
        oout.close();
    }
}
