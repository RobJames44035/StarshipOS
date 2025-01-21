/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.awt.EventQueue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JSpinner;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import static javax.swing.UIManager.getInstalledLookAndFeels;

/**
 * @test
 * @bug 7124397 8256376
 * @key headful
 * @summary Verifies that JSpinner can be serialized/deserialized correctly.
 * @run main/othervm SerializationTest
 * @run main/othervm -Djava.awt.headless=true SerializationTest
 */
public final class SerializationTest {

    public static void main(String[] argv) throws Exception {
        for (UIManager.LookAndFeelInfo laf : getInstalledLookAndFeels()) {
            EventQueue.invokeAndWait(() -> setLookAndFeel(laf));
            EventQueue.invokeAndWait(() -> {
                JSpinner spinner = new JSpinner();
                JSpinner firstCopy = (JSpinner) createCopy(spinner);
                JSpinner secondCopy = (JSpinner) createCopy(firstCopy);
            });
        }
    }

    private static Object createCopy(Serializable objectToCopy) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(objectToCopy);
            InputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void setLookAndFeel(UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Unsupported LookAndFeel: " + laf.getClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
