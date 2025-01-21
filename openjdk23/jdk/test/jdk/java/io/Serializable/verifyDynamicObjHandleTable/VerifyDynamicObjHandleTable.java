/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4146453
 * @summary Test that regrow of object/handle table of ObjectOutputStream works.
 */

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

class A implements Serializable {
    private static final long serialVersionUID = 1L;

    static HashSet<A> writeObjectExtent = new HashSet<>();

    private void writeObject(ObjectOutputStream out) throws IOException {
        if (writeObjectExtent.contains(this)) {
            throw new InvalidObjectException("writeObject: object " +
                                             this.toString() + " has already "
                                             + "been serialized and should " +
                                             "have be serialized by reference.");
        } else {
            writeObjectExtent.add(this);
        }
        out.defaultWriteObject();
    }

    A() {
    }
}

public class VerifyDynamicObjHandleTable {
    public static void main(String args[])
        throws IOException, ClassNotFoundException
    {
        ObjectOutputStream out =
            new ObjectOutputStream(new ByteArrayOutputStream(3000));
        for (int i = 0; i < 1000; i++) {
            out.writeObject(new A());
        }

        // Make sure that serialization subsystem does not
        // allow writeObject to be called on any objects that
        // have already been serialized. These objects should be
        // written out by reference.
        Iterator<A> iter = A.writeObjectExtent.iterator();
        while (iter.hasNext()) {
            out.writeObject(iter.next());
        }

        out.close();
    }
}
