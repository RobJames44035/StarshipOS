/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4632671
 * @summary Verify that reading an object whose class descriptor has both
 *          SC_SERIALIZABLE and SC_EXTERNALIZABLE bits set results in an
 *          InvalidClassException.
 *
 * @build Foo
 * @run main/othervm Read
 */
import java.io.*;

public class Read {
    public static void main(String[] args) throws Exception {
        try {
            /*
             * Foo.ser contains a doctored serialized representation of an
             * instance of class Foo, in which both SC_SERIALIZABLE and
             * SC_EXTERNALIZABLE flags have been set for Foo's class
             * descriptor.
             */
            File f = new File(System.getProperty("test.src", "."), "Foo.ser");
            FileInputStream in = new FileInputStream(f);
            try {
                new ObjectInputStream(in).readObject();
                throw new Error(
                    "read succeeded for object whose class descriptor has " +
                    "both SC_SERIALIZABLE and SC_EXTERNALIZABLE flags set");
            } finally {
                in.close();
            }
        } catch (InvalidClassException e) {
        }
    }
}
