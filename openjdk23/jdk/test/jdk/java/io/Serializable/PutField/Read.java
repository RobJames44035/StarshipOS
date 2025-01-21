/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @summary Verify that the ObjectOutputStream.PutField API works as
 *          advertised.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 0L;

    boolean z;
    byte b;
    char c;
    short s;
    int i;
    long j;
    float f;
    double d;
    String str;
}

public class Read {
    public static void main(String[] args) throws Exception {
        ObjectInputStream oin =
            new ObjectInputStream(new FileInputStream("tmp.ser"));
        Foo foo = (Foo) oin.readObject();
        oin.close();

        if ((! foo.z) ||
            (foo.b != 5) ||
            (foo.c != '5') ||
            (foo.s != 5) ||
            (foo.i != 5) ||
            (foo.j != 5) ||
            (foo.f != 5.0f) ||
            (foo.d != 5.0) ||
            (! foo.str.equals("5")))
        {
            throw new Error();
        }
    }
}
