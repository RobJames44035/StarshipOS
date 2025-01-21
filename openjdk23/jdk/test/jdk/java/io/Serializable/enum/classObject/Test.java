/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838379
 * @summary Verify that serialization of Class objects for enum types works
 *          properly.
 */

import java.io.*;

enum Foo { foo, bar { int i = 0; }, baz { double d = 3.0; } }

public class Test {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        Class<?>[] classes = { Enum.class, Foo.foo.getClass(),
                            Foo.bar.getClass(), Foo.baz.getClass() };
        for (int i = 0; i < classes.length; i++) {
            oout.writeObject(classes[i]);
        }
        oout.close();

        ObjectInputStream oin = new ObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        for (int i = 0; i < classes.length; i++) {
            Object obj = oin.readObject();
            if (obj != classes[i]) {
                throw new Error("expected " + classes[i] + ", got " + obj);
            }
        }
    }
}
