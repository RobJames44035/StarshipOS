/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @bug 4663191
 * @summary Verify that readObject and readObjectNoData methods will not be
 *          called on an object being deserialized if that object is already
 *          tagged with a ClassNotFoundException.
 */

import java.io.*;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    Object x;
}

class B extends A {
    private static final long serialVersionUID = 0L;
    private void readObjectNoData() throws ObjectStreamException {
        throw new Error("readObjectNoData called");
    }
}

class C extends B {
    private static final long serialVersionUID = 0L;
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        throw new Error("readObject called");
    }
}

public class Read {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("tmp.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            try {
                oin.readObject();
                throw new Error("readObject should not succeed");
            } catch (ClassNotFoundException e) {
                // expected
            }
            if (!oin.readObject().equals("after")) {
                throw new Error("subsequent object corrupted");
            }
        } finally {
            in.close();
        }
    }
}
