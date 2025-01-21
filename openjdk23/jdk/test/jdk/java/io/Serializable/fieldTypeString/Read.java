/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @bug 4404696
 * @summary Verify that serialization does not require matching type strings
 *          for non-primitive fields.
 *
 * NOTE: This test should be removed if it is determined that serialization
 * *should* consider type strings when matching non-primitive fields.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 0L;
    String obj;         // writer defines this field as type Object
}

class Bar implements Serializable {
    private static final long serialVersionUID = 0L;
    short q;            // writer defines this field as type int
}

public class Read {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("foo.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            Foo foo = (Foo) oin.readObject();
            if (! foo.obj.equals("foo")) {
                throw new Error();
            }
            try {
                oin.readObject();
                throw new Error();
            } catch (ClassCastException ex) {
            }
        } finally {
            in.close();
        }

        in = new FileInputStream("bar.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            oin.readObject();
            throw new Error();
        } catch (InvalidClassException ex) {
        } finally {
            in.close();
        }
    }
}
