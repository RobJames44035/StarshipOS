/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4490677
 *
 * @clean Write Read Foo
 * @compile Write.java Foo.java
 * @run main Write
 * @clean Write Read Foo
 * @compile Read.java
 * @run main Read
 *
 * @summary Verify that array serialVersionUID conflicts caused by changes in
 *          package scope do not cause deserialization to fail.
 */

import java.io.*;

public class Write {
    public static void main(String[] args) throws Exception {
        ObjectOutputStream oout =
            new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(new Foo[0]);
        oout.close();
    }
}
