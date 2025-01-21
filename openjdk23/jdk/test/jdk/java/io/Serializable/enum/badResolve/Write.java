/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838379
 * @summary Verify that enum classes present in a serialization stream cannot
 *          be resolved by the receiver to non-enum classes, and vice-versa.
 *
 * @compile Write.java
 * @run main Write
 * @clean Write
 * @compile Read.java
 * @run main Read
 * @clean Read
 */

import java.io.*;

enum EnumToNonEnum { foo }

class NonEnumToEnum implements Serializable {
    private static final long serialVersionUID = 0L;
}

public class Write {
    public static void main(String[] args) throws Exception {
        write(EnumToNonEnum.class, "0.ser");
        write(NonEnumToEnum.class, "1.ser");
        write(EnumToNonEnum.foo, "2.ser");
        write(new NonEnumToEnum(), "3.ser");
    }

    static void write(Object obj, String filename) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream(filename));
        oout.writeObject(obj);
        oout.close();
    }
}
