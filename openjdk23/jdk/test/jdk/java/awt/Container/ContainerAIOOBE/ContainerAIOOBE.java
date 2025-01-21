/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Component;
import java.awt.Container;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @test
 * @key headful
 * @bug 8059590
 * @summary ArrayIndexOutOfBoundsException occurs when Container with overridden getComponents() is deserialized.
 * @author Alexey Ivanov
 * @run main ContainerAIOOBE
 */
public class ContainerAIOOBE {

    public static void main(final String[] args) throws Exception {
        ZContainer z = new ZContainer();
        z.add(new Button());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(z);
        oos.flush();
        oos.close();

        byte[] array = baos.toByteArray();
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(array));

        // Reading the object must not throw ArrayIndexOutOfBoundsException
        ZContainer zz = (ZContainer) ois.readObject();

        if (zz.getComponentCount() != 1) {
            throw new Exception("deserialized object must have 1 component");
        }
        if (!(zz.getComponent(0) instanceof Button)) {
            throw new Exception("deserialized object must contain Button component");
        }
        if (zz.getComponents().length != 0) {
            throw new Exception("deserialized object returns non-empty array");
        }
        System.out.println("Test passed");
    }

    static class ZContainer extends Container {
        public Component[] getComponents() {
            return new Component[0];
        }
    }
}
