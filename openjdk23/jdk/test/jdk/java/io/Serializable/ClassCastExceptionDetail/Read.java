/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @bug 4511532
 * @summary Verify that the message string of a ClassCastException thrown by
 *          ObjectInputStream when attempting to assign a value to a field of
 *          an incompatible type contains the names of the value's class, the
 *          field's declaring class, the field's type, and the field itself.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 0L;
    private Float bar;
}

class Gub extends Foo {
    private static final long serialVersionUID = 1L;
}

public class Read {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("tmp.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            oin.readObject();
            throw new Error("readObject should not succeed");
        } catch (ClassCastException e) {
            String s = e.getMessage();
            System.out.println("ClassCastException message: " + s);
            if (s == null ||
                s.indexOf(Foo.class.getName()) == -1 ||
                s.indexOf(Integer.class.getName()) == -1 ||
                s.indexOf(Float.class.getName()) == -1 ||
                s.indexOf(Gub.class.getName()) == -1 ||
                s.indexOf("bar") == -1)
            {
                throw new Error("ClassNotFoundException message incomplete");
            }
        } finally {
            in.close();
        }
    }
}
