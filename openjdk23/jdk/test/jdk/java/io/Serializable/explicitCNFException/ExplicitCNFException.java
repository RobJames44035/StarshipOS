/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/* @test
 * @bug 4407956
 * @summary Verify that ClassNotFoundExceptions explicitly constructed and
 *          thrown from with custom readObject/readExternal methods are
 *          propagated properly.
 */

import java.io.*;

class A implements Serializable {
    private static final long serialVersionUID = 1L;

    private void readObject(ObjectInputStream in)
        throws ClassNotFoundException, IOException
    {
        throw new ClassNotFoundException();
    }
}

class B implements Externalizable {
    private static final long serialVersionUID = 1L;

    public B() {}

    public void writeExternal(ObjectOutput out) throws IOException {}

    public void readExternal(ObjectInput in)
        throws ClassNotFoundException, IOException
    {
        throw new ClassNotFoundException();
    }
}

public class ExplicitCNFException {
    public static void main(String[] args) throws Exception {
        test(new A());
        test(new Object[]{ new A() });
        test(new B());
        test(new Object[]{ new B() });
    }

    static void test(Object obj) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.useProtocolVersion(ObjectStreamConstants.PROTOCOL_VERSION_2);
        oout.writeObject(obj);
        oout.close();
        ObjectInputStream oin = new ObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        try {
            oin.readObject();
            throw new Error();  // should not succeed
        } catch (ClassNotFoundException ex) {
        }
    }
}
