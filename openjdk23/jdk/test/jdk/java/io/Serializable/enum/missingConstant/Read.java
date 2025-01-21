/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @bug 4838379
 * @summary Verify that deserialization of an enum constant that does not exist
 *          on the receiving side results in an InvalidObjectException.
 */

import java.io.*;

enum Foo { foo, bar }

public class Read {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("foo.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            for (Foo f : Foo.values()) {
                Object obj = oin.readObject();
                if (obj != f) {
                    throw new Error("expected " + f + ", got " + obj);
                }
            }
            try {
                Object obj = oin.readObject();
                throw new Error("read of " + obj + " should not succeed");
            } catch (InvalidObjectException e) {
                System.out.println("caught expected exception: " + e);
            }
        } finally {
            in.close();
        }
    }
}
