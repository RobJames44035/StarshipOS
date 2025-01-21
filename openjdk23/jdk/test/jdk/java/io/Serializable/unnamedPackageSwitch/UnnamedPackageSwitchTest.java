/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4348213
 * @build UnnamedPackageSwitchTest pkg.A
 * @run main UnnamedPackageSwitchTest
 * @summary Verify that deserialization allows an incoming class descriptor
 *          representing a class in the unnamed package to be resolved to a
 *          local class with the same name in a named package, and vice-versa.
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

class A implements Serializable {
    private static final long serialVersionUID = 0L;
}

class TestObjectInputStream extends ObjectInputStream {
    TestObjectInputStream(InputStream in) throws IOException { super(in); }
    protected Class<?> resolveClass(ObjectStreamClass desc)
        throws IOException, ClassNotFoundException
    {
        String name = desc.getName();
        if (name.equals("A")) {
            return pkg.A.class;
        } else if (name.equals("pkg.A")) {
            return A.class;
        } else {
            return super.resolveClass(desc);
        }
    }
}

public class UnnamedPackageSwitchTest {
    public static void main(String[] args) throws Exception {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(new A());
        oout.writeObject(new pkg.A());
        oout.close();

        ObjectInputStream oin = new TestObjectInputStream(
            new ByteArrayInputStream(bout.toByteArray()));
        oin.readObject();
        oin.readObject();
    }
}
