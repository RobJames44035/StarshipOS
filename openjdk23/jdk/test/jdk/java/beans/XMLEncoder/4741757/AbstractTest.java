/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.beans.DefaultPersistenceDelegate;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

abstract class AbstractTest {
    public abstract int getValue();

    public final String toString() {
        return Integer.toString(getValue());
    }

    static void test(AbstractTest object) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        XMLEncoder encoder = new XMLEncoder(output);
        encoder.setPersistenceDelegate(
                object.getClass(),
                new DefaultPersistenceDelegate(new String[] {"value"}));

        encoder.writeObject(object);
        encoder.close();

        System.out.print(output);

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        XMLDecoder decoder = new XMLDecoder(input);
        AbstractTest result = (AbstractTest) decoder.readObject();
        decoder.close();

        if (object.getValue() != result.getValue())
            throw new Error("Should be " + object);
    }
}
