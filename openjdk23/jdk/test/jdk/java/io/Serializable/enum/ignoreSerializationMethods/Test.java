/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838379
 * @summary Verify that custom serialization methods defined by enum types are
 *          not invoked during serialization or deserialization.
 */

import java.io.*;

enum Foo {

    foo,
    bar {
        @SuppressWarnings("serial") /* Incorrect declarations are being tested */
        private void writeObject(ObjectOutputStream out) throws IOException {
            throw new Error("bar.writeObject invoked");
        }
        @SuppressWarnings("serial") /* Incorrect declarations are being tested */
        private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException
        {
            throw new Error("bar.readObject invoked");
        }
        @SuppressWarnings("serial") /* Incorrect declarations are being tested */
        Object writeReplace() throws ObjectStreamException {
            throw new Error("bar.writeReplace invoked");
        }
        // readResolve cannot be defined until Enum.readResolve is removed
        // Object readResolve() throws ObjectStreamException {
        //    throw new Error("bar.readResolve invoked");
        // }
    };

    @SuppressWarnings("serial") /* Incorrect use is being tested */
    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new Error("Foo.writeObject invoked");
    }
    @SuppressWarnings("serial") /* Incorrect use is being tested */
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        throw new Error("Foo.readObject invoked");
    }
    @SuppressWarnings("serial") /* Incorrect use is being tested */
    Object writeReplace() throws ObjectStreamException {
        throw new Error("Foo.writeReplace invoked");
    }
    // readResolve cannot be defined until Enum.readResolve is removed
    // Object readResolve() throws ObjectStreamException {
    //    throw new Error("Foo.readResolve invoked");
    // }
}

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
