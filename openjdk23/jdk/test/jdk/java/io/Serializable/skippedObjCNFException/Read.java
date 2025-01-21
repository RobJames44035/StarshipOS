/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @bug 4313167
 * @summary Verify that ClassNotFoundExceptions caused by values referenced
 *          (perhaps transitively) by "skipped" fields will not cause
 *          deserialization failure.
 */

import java.io.*;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    // skipped fields b, c, ca
}

class B implements Serializable {
    private static final long serialVersionUID = 0L;
    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    Object c;
}

// class C not available

public class Read {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("tmp.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            oin.readObject();
        } finally {
            in.close();
        }
    }
}
