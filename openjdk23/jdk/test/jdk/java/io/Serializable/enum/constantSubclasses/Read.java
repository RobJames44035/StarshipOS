/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @bug 4838379
 * @summary Verify that serialization of enum constants that are instances of
 *          constant-specific subclasses functions properly.
 */

import java.io.*;

enum Foo {
    a,
    b { byte b = 3; },
    c,
    d { double d = 6.0; }
}

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
        } finally {
            in.close();
        }
    }
}
