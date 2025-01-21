/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.warn.ineffectual.serial.field.enum
// key: compiler.warn.ineffectual.serial.method.enum

// options: -Xlint:serial

import java.io.*;

enum IneffectualSerialEnum implements Serializable {
    INSTANCE;

    // The serialVersionUID field is ineffectual for enum classes.
    private static final long serialVersionUID = 42;

    // The readObject method is ineffectual for enum classes.
    private void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        return;
    }
}
