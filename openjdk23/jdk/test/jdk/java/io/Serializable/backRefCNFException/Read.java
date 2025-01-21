/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @bug 4312433
 * @summary Verify that reading a back reference to a previously skipped value
 *          whose class is not available will throw a ClassNotFoundException
 */

import java.io.*;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
    // field b skipped
}

// class B not available

public class Read {
    public static void main(String[] args) throws Exception {
        FileInputStream in = new FileInputStream("tmp.ser");
        try {
            ObjectInputStream oin = new ObjectInputStream(in);
            oin.readObject();
            try {
                oin.readObject();
                throw new Error("back reference read succeeded");
            } catch (ClassNotFoundException ex) {
            }
        } finally {
            in.close();
        }
    }
}
