/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/* @test
 * @bug 4662327
 * @summary Verify that ObjectInputStream.defaultReadObject() throws a
 *          ClassNotFoundException if any of the non-primitive field values it
 *          reads in are tagged with ClassNotFoundExceptions.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("serial") /* Incorrect use is being tested */
    Object obj = new Bar();

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        if (obj == null) {
            throw new Error(
                "ClassNotFoundException masked by defaultReadObject()");
        }
    }
}

class Bar implements Serializable {
    private static final long serialVersionUID = 1L;
}

class TestObjectInputStream extends ObjectInputStream {
    TestObjectInputStream(InputStream in) throws IOException { super(in); }

    protected Class<?> resolveClass(ObjectStreamClass desc)
        throws IOException, ClassNotFoundException
    {
        if (desc.getName().equals(Bar.class.getName())) {
            throw new ClassNotFoundException();
        }
        return super.resolveClass(desc);
    }
}

public class DefaultReadObjectCNFException {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(new Foo());
        oout.writeObject("after");
        oout.close();
        ObjectInputStream oin = new TestObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        try {
            oin.readObject();
        } catch (ClassNotFoundException e) {
            // expected
        }
        if (!oin.readObject().equals("after")) {
            throw new Error("subsequent object corrupted");
        }
    }
}
