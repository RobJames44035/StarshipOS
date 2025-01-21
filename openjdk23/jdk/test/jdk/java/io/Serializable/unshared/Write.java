/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/* @test
 * @bug 4311991
 *
 * @clean Write Read Foo Bar
 * @compile Write.java
 * @run main Write
 * @clean Write Read Foo Bar
 * @compile Read.java
 * @run main Read
 *
 * @summary Test ObjectOutputStream.writeUnshared/readUnshared functionality.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final ObjectStreamField[] serialPersistentFields =
        new ObjectStreamField[] {
            new ObjectStreamField("shared1", String.class),
            new ObjectStreamField("shared2", String.class, false),
            new ObjectStreamField("unshared1", String.class, true),
            new ObjectStreamField("unshared2", String.class, true)
        };

    String shared1, shared2, unshared1, unshared2;

    Foo() {
        shared1 = shared2 = unshared1 = unshared2 = "foo";
    }
}

class Bar implements Serializable {
    private static final long serialVersionUID = 0L;
    @SuppressWarnings("serial") /* Incorrect use is being tested */
    Object obj;

    Bar(Object obj) {
        this.obj = obj;
    }
}

public class Write {
    public static void main(String[] args) throws Exception {
        String str1 = "foo";
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);

        oout.writeObject(str1);
        oout.writeObject(str1);
        oout.writeUnshared(str1);
        oout.writeUnshared(str1);
        oout.writeObject(new Foo());
        oout.close();

        ByteArrayInputStream bin =
            new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream oin = new ObjectInputStream(bin);
        str1 = (String) oin.readObject();
        if (oin.readObject() != str1) {
            throw new Error();
        }
        String str2 = (String) oin.readObject();
        String str3 = (String) oin.readObject();
        if (str2 == str1 || str3 == str1 || str2 == str3) {
            throw new Error();
        }
        if (! (str1.equals(str2) && str1.equals(str3))) {
            throw new Error();
        }

        Foo foo = (Foo) oin.readObject();
        if ((foo.shared1 != foo.shared2) ||
            (foo.shared1 == foo.unshared1) ||
            (foo.shared1 == foo.unshared2) ||
            (foo.shared2 == foo.unshared1) ||
            (foo.shared2 == foo.unshared2) ||
            (foo.unshared1 == foo.unshared2))
        {
            throw new Error();
        }

        // write out object to be read by Read.main()
        oout = new ObjectOutputStream(new FileOutputStream("tmp.ser"));
        oout.writeObject(new Bar(str1));
        oout.writeObject(str1);
        oout.close();
    }
}
