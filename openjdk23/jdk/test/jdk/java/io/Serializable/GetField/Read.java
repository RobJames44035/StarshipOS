/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @bug 4402830
 * @summary Verify that the ObjectInputStream.GetField API works properly for
 *          serialized fields which don't exist in the receiving object.
 */

import java.io.*;

class Foo implements Serializable {
    private static final long serialVersionUID = 0L;
    int blargh;

    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        ObjectInputStream.GetField fields = in.readFields();
        if (! fields.defaulted("blargh")) {
            throw new Error();
        }
        try {
            fields.defaulted("nonexistant");
            throw new Error();
        } catch (IllegalArgumentException ex) {
        }
        if ((fields.get("z", false) != true) ||
            (fields.get("b", (byte) 0) != 5) ||
            (fields.get("c", '0') != '5') ||
            (fields.get("s", (short) 0) != 5) ||
            (fields.get("i", 0) != 5) ||
            (fields.get("j", 0l) != 5) ||
            (fields.get("f", 0.0f) != 5.0f) ||
            (fields.get("d", 0.0) != 5.0) ||
            (! fields.get("str", null).equals("5")))
        {
            throw new Error();
        }
    }
}

public class Read {
    public static void main(String[] args) throws Exception {
        ObjectInputStream oin =
            new ObjectInputStream(new FileInputStream("tmp.ser"));
        oin.readObject();
        oin.close();
    }
}
