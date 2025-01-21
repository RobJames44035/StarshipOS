/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/* @test
 * @bug 4838379
 * @summary Verify that serialVersionUID and serialPersistentFields
 *          declarations made by enum types and constants are ignored.
 */

import java.io.*;
import java.util.Arrays;

enum Foo {

    foo,
    bar {
        @SuppressWarnings("serial") /* Incorrect declarations are being tested */
        private static final long serialVersionUID = 2L;
        // bar is implemented as an inner class instance, so the following
        // declaration would cause a compile-time error
        // private static final ObjectStreamField[] serialPersistentFields = {
        //    new ObjectStreamField("gub", Float.TYPE)
        // };
    };

    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    private static final long serialVersionUID = 1L;

    @SuppressWarnings("serial") /* Incorrect declarations are being tested */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("blargh", Integer.TYPE)
    };
}

public class Test {
    public static void main(String[] args) throws Exception {
        Class<?>[] classes =
            { Foo.class, Foo.foo.getClass(), Foo.bar.getClass() };
        for (int i = 0; i < classes.length; i++) {
            ObjectStreamClass desc = ObjectStreamClass.lookup(classes[i]);
            if (desc.getSerialVersionUID() != 0L) {
                throw new Error(
                    classes[i] + " has non-zero serialVersionUID: " +
                    desc.getSerialVersionUID());
            }
            ObjectStreamField[] fields = desc.getFields();
            if (fields.length > 0) {
                throw new Error(
                    classes[i] + " has non-empty list of fields: " +
                    Arrays.asList(fields));
            }
        }
    }
}
