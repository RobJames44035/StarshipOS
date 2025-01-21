/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

// key: compiler.warn.non.private.method.weaker.access
// key: compiler.warn.default.ineffective
// key: compiler.warn.ineffectual.serial.field.interface

// options: -Xlint:serial

import java.io.*;

interface SerialInterfaceMethodsAndFields extends Serializable {
    public static final ObjectStreamField[] serialPersistentFields = {};
    public void readObject(ObjectInputStream stream)
        throws IOException, ClassNotFoundException;
    public void readObjectNoData() throws ObjectStreamException;
    public void writeObject(ObjectOutputStream stream) throws IOException;

    default public Object readResolve() throws ObjectStreamException {
        return null;
    }
}
