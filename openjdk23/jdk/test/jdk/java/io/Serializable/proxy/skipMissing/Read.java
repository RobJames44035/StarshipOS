/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @summary Verify that ObjectInputStream can skip over unresolvable serialized
 *          proxy instances.
 */

import java.io.*;
import java.lang.reflect.*;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    String a;
    String z;
}

class B implements Serializable {
    private static final long serialVersionUID = 0L;
    String s;

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        in.defaultReadObject();
        // leave proxy object unconsumed
    }
}

public class Read {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("tmp.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            A a = (A) oin.readObject();
            if (! (a.a.equals("a") && a.z.equals("z"))) {
                throw new Error("A fields corrupted");
            }
            B b = (B) oin.readObject();
            if (! b.s.equals("s")) {
                throw new Error("B fields corrupted");
            }
            try {
                oin.readObject();
                throw new Error("proxy read should not succeed");
            } catch (ClassNotFoundException ex) {
            }
        } finally {
            in.close();
        }
    }
}
