/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838379
 * @summary Verify that basic serialization of non-specialized enum constants
 *          functions properly.
 */

import java.io.*;

enum Foo { klaatu, barada, nikto }

public class Test {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        for (Foo f : Foo.values()) {
            oout.writeObject(f);
        }
        oout.close();
        ObjectInputStream oin = new ObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        for (Foo f : Foo.values()) {
            Object obj = oin.readObject();
            if (obj != f) {
                throw new Error("expected " + f + ", got " + obj);
            }
        }
    }
}
